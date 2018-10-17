package com.github.neone35.geowords.ui.detail;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.FrameLayout;

import com.github.neone35.geowords.R;
import com.github.neone35.geowords.data.models.remote.WordResponse;
import com.github.neone35.geowords.ui.main.MainActivity;

import org.parceler.Parcels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.fl_details)
    FrameLayout flWeb;
    @BindView(R.id.fl_map)
    FrameLayout flMap;
    Parcelable mWordResponseParcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        FragmentManager mFragmentManager = getSupportFragmentManager();

        // check if intent bundle received successfully and setup view
        Bundle mainExtrasBundle = getIntent().getExtras();
        if (mainExtrasBundle != null) {
            mWordResponseParcel = mainExtrasBundle.getParcelable(MainActivity.KEY_WORD_PARCELABLE);
            // only create fragment if there was no configuration change
            if (savedInstanceState == null && mWordResponseParcel != null) {
                DetailFragment additiveFragment = DetailFragment.newInstance(mWordResponseParcel);
                mFragmentManager.beginTransaction()
                        .add(R.id.fl_details, additiveFragment)
                        .commit();
                WordResponse wordResponse = Parcels.unwrap(mWordResponseParcel);
                String requestedWord = wordResponse.getWord();
                MapFragment mapFragment = MapFragment.newInstance(requestedWord);
                // inflate Map fragment here
                mFragmentManager.beginTransaction()
                        .add(R.id.fl_map, mapFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mWordResponseParcel = savedInstanceState.getParcelable(MainActivity.KEY_WORD_PARCELABLE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MainActivity.KEY_WORD_PARCELABLE, mWordResponseParcel);
        super.onSaveInstanceState(outState);
    }

}
