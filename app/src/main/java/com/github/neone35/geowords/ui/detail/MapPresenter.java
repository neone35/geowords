package com.github.neone35.geowords.ui.detail;

import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.source.WordRepository;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MapPresenter implements MapContract.Presenter {

    @NonNull
    private final WordRepository mWordRepository;
    @NonNull
    private final MapContract.View mMapView;
    @NonNull
    private CompositeDisposable mCompDisps;
    // main thread scheduler
    private Scheduler mScheduler;

    MapPresenter(@NonNull WordRepository wordRepository,
                  @NonNull MapContract.View mapView,
                  Scheduler scheduler) {
        mWordRepository = wordRepository;
        mMapView = mapView;
        mCompDisps = new CompositeDisposable();
        mScheduler = scheduler;
        mMapView.setPresenter(this);
    }

    @Override
    public void updateWordObject(Word wordToUpdate) {
        Logger.d("updateWordObject is called!");
        mWordRepository.insertOrUpdateWord(wordToUpdate);
        mMapView.loadMarker(wordToUpdate);
    }

    @Override
    public void getWordObject(String wordToGet) {
        Logger.d("getWordObject is called!");
        Disposable fetchWordDisp = mWordRepository.getWord(wordToGet)
                .observeOn(mScheduler)
                .subscribe(
                        // onNext
                        mMapView::loadMarker,
                        // onError
                        throwable -> {
                            mMapView.showNoWordError(wordToGet);
                            throwable.printStackTrace();
                        });
        mCompDisps.add(fetchWordDisp);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompDisps.clear();
    }
}
