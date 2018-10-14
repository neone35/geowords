package com.github.neone35.geowords.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.facebook.stetho.Stetho;
import com.github.neone35.geowords.Injection;
import com.github.neone35.geowords.R;
import com.github.neone35.geowords.data.models.local.Word;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private boolean SEARCH_ITEM_SELECTED = false;
    private MainContract.Presenter mPresenter;

    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.actv_search)
    AutoCompleteTextView actvSearch;
    @BindString(R.string.no_internet)
    String stringNoInternet;
    String[] languages={"Android","java","IOS","SQL","JDBC","Web services"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpActivity();
        ArrayAdapter<String> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages);
        actvSearch.setAdapter(adapter);
        // number of characters to type for dropdown
        actvSearch.setThreshold(1);
        actvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = adapterView.getAdapter().getItem(i).toString();
                Logger.d(selectedItemText);
                mPresenter.fetchWord(selectedItemText);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    private void setUpActivity() {
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        setSupportActionBar(toolbarMain);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the presenter
        mPresenter = new MainPresenter(
                Injection.provideTasksRepository(getApplicationContext()),
                this,
                // where to observe
                AndroidSchedulers.mainThread());
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showWords(List<Word> tasks) {

    }

    @Override
    public void showWordDetailsUi(Word wordResponse) {
        mPresenter.addNewWord(wordResponse);
        // and show ui
        Logger.d("showWordDetailsUi is called!");
        Logger.d(wordResponse.getWord());
    }

    @Override
    public void showLoadingWordError() {

    }

    @Override
    public void showNoWords(String message) {
        Logger.d("No words found in DB: " + message);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
