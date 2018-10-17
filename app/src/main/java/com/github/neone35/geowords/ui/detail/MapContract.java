package com.github.neone35.geowords.ui.detail;

import com.github.neone35.geowords.BasePresenter;
import com.github.neone35.geowords.BaseView;
import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.ui.main.MainContract;

public interface MapContract {

    interface View extends BaseView<MainContract.Presenter> {

        void loadMarker(String wordUrl);

    }

    interface Presenter extends BasePresenter {

        void updateWordObject(Word word);

        Word getWordObject(String word);
    }
}
