package com.github.neone35.geowords.ui;

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
    public void loadWords() {
        mCompDisps.clear();
        Disposable wordsDisp = mWordRepository.getWords()
                .observeOn(mScheduler)
                .subscribe(
                        // onNext
                        mMainView::showWords,
                        // onError
                        throwable -> mMainView.showNoWords(throwable.getMessage()));
        mCompDisps.add(wordsDisp);

    }

    @Override
    public void addNewWord(Word newWord) {
        mWordRepository.insertOrUpdateWord(newWord);
    }

    @Override
    public void fetchWord(@NonNull String requestedWord) {
        Logger.d("fetchword is called!");
        mCompDisps.clear();
        Disposable wordDisp = mWordRepository.getWord(requestedWord)
                .observeOn(mScheduler)
                .subscribe(word -> mMainView.showWordDetailsUi(word),
                        throwable -> Logger.d(throwable));
        mCompDisps.add(wordDisp);
    }

    @Override
    public void subscribe() {
        loadWords();
    }

    @Override
    public void unsubscribe() {
        mCompDisps.clear();
    }
}
