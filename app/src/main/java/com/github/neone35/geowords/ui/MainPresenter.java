package com.github.neone35.geowords.ui;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.source.WordRepository;

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
        mMainView.setPresenter(this);
        mScheduler = scheduler;
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
                        throwable -> mMainView.showNoWords());
        mCompDisps.add(wordsDisp);

    }

    @Override
    public void addNewWord() {

    }

    @Override
    public void openWordDetails(@NonNull Word requestedWord) {
        mCompDisps.clear();
        Disposable wordDisp = mWordRepository.getWord(requestedWord.getWord())
                .observeOn(mScheduler)
                .subscribe(word -> mMainView.showWordDetailsUi(word.getWord()));
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
