package com.github.neone35.geowords.ui.detail;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.github.neone35.geowords.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_WORD_STRING = "word_string";

    private GoogleMap mMap;
    // keep all disposables in one variable to easily unsubscribe
    private CompositeDisposable mDisps = new CompositeDisposable();
    private static LatLng mUserLatLng;
    private Context mCtx;
    private RxLocation rxLocation;
    private RxPermissions rxPermissions;
    private String mWord;
    private SupportMapFragment mMapFragment;

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

        rxLocation = new RxLocation(Objects.requireNonNull(this.getActivity()));
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        mCtx = this.getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);

        // Inflate support map fragment into this layout
        // getChildFragmentManager returns private fragment manager to manage fragments inside this fragment
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_google_map);
        if (mMapFragment != null) {
            mMapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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
                            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(mUserLatLng, 12.0f);
                            mMap.animateCamera(cu);
                        });
                mDisps.add(locationDisp);
            }
        }
    }
}
