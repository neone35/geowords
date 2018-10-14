package com.github.neone35.geowords.ui;

import com.github.neone35.geowords.BasePresenter;
import com.github.neone35.geowords.BaseView;
import com.github.neone35.geowords.data.models.local.Word;

import java.util.List;

import androidx.annotation.NonNull;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Word> words);

        void showWordDetailsUi(Word wordResponse);

        void showLoadingWordError();

        void showNoWords(String message);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords();

        void addNewWord(Word newWord);

        void fetchWord(@NonNull String requestedWord);
    }

}
