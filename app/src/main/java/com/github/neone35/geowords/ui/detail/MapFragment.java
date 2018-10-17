package com.github.neone35.geowords.ui.detail;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.github.neone35.geowords.Injection;
import com.github.neone35.geowords.R;
import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.utils.MapUtils;
import com.github.neone35.geowords.utils.PrefUtils;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Objects;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MapFragment extends Fragment implements
        OnMapReadyCallback, MapContract.View {

    private static final String ARG_WORD_STRING = "word_string";

    private MapContract.Presenter mPresenter;
    private GoogleMap mMap;
    // keep all disposables in one variable to easily unsubscribe
    private CompositeDisposable mDisps = new CompositeDisposable();
    private static LatLng mUserLatLng;
    private static LatLng mWordLatLng;
    private Context mCtx;
    private RxLocation rxLocation;
    private RxPermissions rxPermissions;
    private String mWord;
    private SupportMapFragment mMapFragment;
    private Word mDbWord;
    private static final String KEY_CUSTOM_ICON = "custom_icon";
    private PrefUtils mPrefUtils;
    private MenuItem mDefaultIconItem;
    private MenuItem mCustomIconItem;
    private int[] mIconIds = {R.drawable.ic_twotone_explore_24px, R.drawable.ic_twotone_grade_24px,
            R.drawable.ic_twotone_language_24px, R.drawable.ic_twotone_offline_bolt_24px,
    R.drawable.ic_twotone_work_24px};
    private LayoutInflater mInflater;

    public MapFragment() {
        // Required empty public constructor
    }

    static MapFragment newInstance(String param1) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WORD_STRING, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWord = getArguments().getString(ARG_WORD_STRING);
        }
        setHasOptionsMenu(true);

        rxLocation = new RxLocation(Objects.requireNonNull(this.getActivity()));
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        mCtx = this.getActivity();
        mPresenter = new MapPresenter(
                // where to subscribe
                Injection.provideWordsRepository(mCtx, Schedulers.io()),
                this,
                // where to observe
                AndroidSchedulers.mainThread());
        mPrefUtils = PrefUtils.getInstance(PreferenceManager.getDefaultSharedPreferences(mCtx));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflater = inflater;
        View rootView = mInflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);

        // Inflate support map fragment into this layout
        // getChildFragmentManager returns private fragment manager to manage fragments inside this fragment
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_google_map);
        if (mMapFragment != null) {
            mMapFragment.setRetainInstance(true);
            mMapFragment.getMapAsync(this);
            // get Word object from database and load marker on complete
            mPresenter.getWordObject(mWord);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisps.clear();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        MenuItem iconItem = menu.findItem(R.id.menu_detail_icon);
        mDefaultIconItem = iconItem.getSubMenu().findItem(R.id.menu_detail_default_icon);
        mCustomIconItem = iconItem.getSubMenu().findItem(R.id.menu_detail_custom_icon);
        if (!mPrefUtils.getBoolean(KEY_CUSTOM_ICON)) {
            // set default icon option checked
            mDefaultIconItem.setChecked(true);
            mCustomIconItem.setChecked(false);
        } else {
            mDefaultIconItem.setChecked(false);
            mCustomIconItem.setChecked(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail_default_icon:
                mPrefUtils.putBoolean(KEY_CUSTOM_ICON, false);
                mDefaultIconItem.setChecked(true);
                mCustomIconItem.setChecked(false);
                return true;
            case R.id.menu_detail_custom_icon:
                mPrefUtils.putBoolean(KEY_CUSTOM_ICON, true);
                mCustomIconItem.setChecked(true);
                mDefaultIconItem.setChecked(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new MapPopupAdapter(mInflater));

        // locate user after location permission granted
        Disposable permissionDisp = rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        locateUser();
                    } else {
                        ToastUtils.showShort("Could not find your location");
                    }
                });
        mDisps.add(permissionDisp);
        // listen for long press with or without user location
        listenForLongMapPress();
    }

    private void listenForLongMapPress() {
        mMap.setOnMapLongClickListener(latLng -> {
            int iconId = 0;
            // set custom icon id if option enabled
            if (mPrefUtils.getBoolean(KEY_CUSTOM_ICON)) {
                int rnd = new Random().nextInt(mIconIds.length);
                iconId = mIconIds[rnd];
            }
            // update local DB word with latlng
            Word updatedWord = new Word(mWord, mDbWord.getPartOfSpeech(), mDbWord.getTimeMillis(), latLng, iconId);
            mPresenter.updateWordObject(updatedWord);
        });
    }

    private void locateUser() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // ask for location permission only on M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mCtx.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Disposable locationDisp = rxLocation.location().updates(locationRequest)
                        .flatMap(location -> rxLocation.geocoding().fromLocation(location).toObservable())
                        .subscribe(address -> {
                            // Add a marker of user location & zoom to it
                            mUserLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                            mMap.addMarker(MapUtils.generateMarker(mCtx, mUserLatLng, "You", R.drawable.ic_target_24dp));
                            zoomToWordOrUser();
                        });
                mDisps.add(locationDisp);
            }
        }
    }

    private void zoomToWordOrUser() {
        CameraUpdate cu;
        // add user marker
        // if word marker doesn't exist, zoom to user
        if (mDbWord.getLatLng() != null) {
            mWordLatLng = mDbWord.getLatLng();
            cu = CameraUpdateFactory.newLatLngZoom(mWordLatLng, 15.0f);
        } else {
            cu = CameraUpdateFactory.newLatLngZoom(mUserLatLng, 15.0f);
        }
        mMap.animateCamera(cu);
    }


    // called on successful Word object retrieval from database
    @Override
    public void loadMarker(Word word) {
        // set local variable to update on long press
        mDbWord = word;
        // generate marker with default/custom icon
        if (word.getLatLng() != null) {
            MarkerOptions markerOptions =
                    MapUtils.generateMarker(mCtx,
                            word.getLatLng(),
                            word.getWord(),
                            word.getIconId())
                            .snippet(word.getPartOfSpeech());
            mMap.clear();
            mMap.addMarker(markerOptions);
        } else {
            ToastUtils.showShort("No marker set for word " + word.getWord());
        }
    }

    @Override
    public void showNoWordError(String word) {
        ToastUtils.showShort("No word " + word + " found");
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
