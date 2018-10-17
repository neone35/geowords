package com.github.neone35.geowords.ui.detail;

import com.github.neone35.geowords.BasePresenter;
import com.github.neone35.geowords.BaseView;
import com.github.neone35.geowords.data.models.local.Word;

public interface MapContract {

    interface View extends BaseView<MapContract.Presenter> {

        void loadMarker(Word word);

        void showNoWordError(String word);

    }

    interface Presenter extends BasePresenter {

        void updateWordObject(Word word);

        void getWordObject(String word);
    }
}
