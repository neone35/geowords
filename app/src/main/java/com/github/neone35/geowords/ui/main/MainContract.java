package com.github.neone35.geowords.ui.main;

import com.github.neone35.geowords.BasePresenter;
import com.github.neone35.geowords.BaseView;
import com.github.neone35.geowords.data.models.local.Word;
import com.github.neone35.geowords.data.models.remote.WordResponse;

import java.util.List;

import androidx.annotation.NonNull;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showWordsHistory(List<Word> words);

        void showWordsMarkers(List<Word> words);

        void showWordDetailsUi(WordResponse wordResponse);

        void showLoadingWordError(String word);

        void showNoWords(String message);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWordsHistory();

        void addNewWord(Word newWord);

        void fetchWord(@NonNull String requestedWord);
    }

}
