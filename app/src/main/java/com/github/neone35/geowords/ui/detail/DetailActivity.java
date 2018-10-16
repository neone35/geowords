package com.github.neone35.geowords.ui.detail;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.FrameLayout;

import com.github.neone35.geowords.R;
import com.github.neone35.geowords.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private DetailFragment mDetailFragment;
    @BindView(R.id.fl_details)
    FrameLayout flWeb;
    @BindView(R.id.fl_map)
    FrameLayout flMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();

        // check if intent bundle received successfully and setup view
        Bundle mainExtrasBundle = getIntent().getExtras();
        if (mainExtrasBundle != null) {
            Parcelable wordResponseParcel = mainExtrasBundle.getParcelable(MainActivity.KEY_WORD_PARCELABLE);
            // only create fragment if there was no configuration change
            if (savedInstanceState == null) {
                DetailFragment additiveFragment = DetailFragment.newInstance(wordResponseParcel);
                mFragmentManager.beginTransaction()
                        .add(R.id.fl_details, additiveFragment)
                        .commit();
            }
        }
    }
}
