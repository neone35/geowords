package com.github.neone35.geowords.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.stetho.Stetho;
import com.github.neone35.geowords.Injection;
import com.github.neone35.geowords.R;
import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.models.remote.WordResponse;
import com.github.neone35.geowords.ui.detail.DetailActivity;
import com.github.neone35.geowords.utils.NetworkUtils;
import com.jakewharton.rxbinding.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;
    public static final String KEY_WORD_PARCELABLE = "word-parcelable";

    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.actv_search)
    AutoCompleteTextView actvSearch;
    @BindString(R.string.no_internet)
    String stringNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpActivity();
        if (NetworkUtils.isNetworkConnected(this)) {
            listenOnActv();
        } else {
            ToastUtils.showShort(stringNoInternet);
        }
    }

    // unsubscribe onDestroy to continue operations onPause (ex. addNewWord)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    private void listenOnActv() {
        // listen for enter click
        RxTextView.editorActionEvents(actvSearch)
                // make 100ms delay between queries
                .debounce(100, TimeUnit.MILLISECONDS)
                .filter(event -> event.actionId() == EditorInfo.IME_ACTION_NEXT)
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    // text must be longer than 2 chars
                    String actvText = actvSearch.getText().toString();
                    if (actvText.length() > 2) {
                        mPresenter.fetchWord(actvText);
                    } else {
                        showTopToast("Word must be longer than 2 chars");
                    }
                }, throwable -> {
                    showTopToast("Word not found");
                    throwable.printStackTrace();
                });

        // set chars num to show auto complete dropdown
        RxAutoCompleteTextView.threshold(actvSearch).call(1);
        // listen for item click
        RxAutoCompleteTextView.itemClickEvents(actvSearch)
                // make 100ms delay between queries
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(adapterViewItemClickEvent -> {
                    int clickedPos = adapterViewItemClickEvent.position();
                    // cast selected object into correct one
                    Word selectedWord = (Word) actvSearch.getAdapter().getItem(clickedPos);
                    String selectedItemText = selectedWord.getWord();
                    mPresenter.fetchWord(selectedItemText);
                }, throwable -> {
                    showTopToast("Failed to load word");
                    throwable.printStackTrace();
                });
    }

    private void showTopToast(String message) {
        ToastUtils.showShort(message);
        int abHeight = Objects.requireNonNull(getSupportActionBar()).getHeight();
        int statusBarHeight = 24;
        ToastUtils.setGravity(Gravity.TOP, 0, abHeight + statusBarHeight);
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
                // where to subscribe
                Injection.provideWordsRepository(getApplicationContext(), Schedulers.io()),
                this,
                // where to observe
                AndroidSchedulers.mainThread());
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showWordsHistory(List<Word> wordList) {
        Logger.d("showWordsHistory is called!");
        ArrayList<Word> words = new ArrayList<>(wordList);
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.activity_main_history_item, words);
        actvSearch.setAdapter(adapter);
    }

    // called after successful word fetch
    @Override
    public void showWordDetailsUi(WordResponse wordResponse) {
        // make new Word instance from response
        Word word = new Word(wordResponse.getWord(),
                wordResponse.getResults().get(0).getPartOfSpeech(),
                System.currentTimeMillis(), null, 0);
        // history is refreshed automatically on DB insert
        // because RxJava subscribed on getWords to call showWordsHistory onSuccess
        mPresenter.addNewWord(word);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(KEY_WORD_PARCELABLE, Parcels.wrap(wordResponse));
        startActivity(detailIntent);
        Logger.d("showWordDetailsUi is called with " + wordResponse.getWord());
    }

    @Override
    public void showLoadingWordError(String word) {
        showTopToast("Failed to fetch word " + word);
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
