package com.github.neone35.geowords.ui.main;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.source.WordRepository;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements MainContract.Presenter {

    @NonNull
    private final WordRepository mWordRepository;
    @NonNull
    private final MainContract.View mMainView;
    @NonNull
    private CompositeDisposable mCompDisps;
    // main thread scheduler
    private Scheduler mScheduler;

    MainPresenter(@NonNull WordRepository wordRepository,
                  @NonNull MainContract.View mainView,
                  Scheduler scheduler) {
        mWordRepository = wordRepository;
        mMainView = mainView;
        mCompDisps = new CompositeDisposable();
        mScheduler = scheduler;
        mMainView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadWordsHistory() {
        Logger.d("loadWordsHistory is called!");
        Disposable loadWordsDisp = mWordRepository.getWords()
                .observeOn(mScheduler)
                .subscribe(
                        // onNext
                        mMainView::showWordsHistory,
                        // onError
                        throwable -> mMainView.showNoWords(throwable.getMessage()));
        mCompDisps.add(loadWordsDisp);
    }

    @Override
    public void addNewWord(Word newWord) {
        Logger.d("addNewWord is called!");
        Disposable fetchWordDisp = mWordRepository.getWord(newWord.getWord())
                .observeOn(mScheduler)
                .subscribe(
                        // onNext - if word exists, don't replace
                        word -> Logger.d("Word " + word.getWord() + " already exists in DB"),
                        // onError - word doesn't exist, create new
                        throwable -> {
                            throwable.printStackTrace();
                            mWordRepository.insertOrUpdateWord(newWord);
                        });
        mCompDisps.add(fetchWordDisp);
    }

    @Override
    public void fetchWord(@NonNull String requestedWord) {
        Logger.d("fetchword is called!");
        mMainView.setLoadingIndicator(true);
        Disposable fetchWordDisp = mWordRepository.fetchWord(requestedWord)
                .observeOn(mScheduler)
                .subscribe(
                        // onNext
                        wordResponse -> {
                            mMainView.setLoadingIndicator(false);
                            mMainView.showWordDetailsUi(wordResponse);
                        },
                        // onError
                        throwable -> {
                            mMainView.setLoadingIndicator(false);
                            mMainView.showLoadingWordError(requestedWord);
                        });
        mCompDisps.add(fetchWordDisp);
    }

    @Override
    public void subscribe() {
        loadWordsHistory();
    }

    @Override
    public void unsubscribe() {
        mCompDisps.clear();
    }
}
