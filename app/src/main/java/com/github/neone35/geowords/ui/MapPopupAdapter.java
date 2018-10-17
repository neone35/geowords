package com.github.neone35.geowords.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.neone35.geowords.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapPopupAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;
    @BindView(R.id.tv_detail_word)
    TextView tvDetailWord;
    @BindView(R.id.tv_detail_part_of_speech)
    TextView tvDetailPartOfSpeech;
    @BindView(R.id.tv_detail_synonyms_label)
    TextView tvDetailSynonymsLabel;
    @BindView(R.id.tv_detail_synonyms)
    TextView tvDetailSynonyms;
    @BindView(R.id.tv_detail_type_of_label)
    TextView tvDetailTypeOfLabel;
    @BindView(R.id.tv_detail_type_of)
    TextView tvDetailTypeOf;

    public MapPopupAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }


    // Modifies entire infoWindow (with frame and background)
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    // Modifies only contents if infoWindow
    @Override
    public View getInfoContents(Marker marker) {
        @SuppressLint("InflateParams")
        View popupView = inflater.inflate(R.layout.fragment_detail, null, false);
        ButterKnife.bind(this, popupView);

        String markerTitle = marker.getTitle();
        if (markerTitle != null)
            tvDetailWord.setText(markerTitle);
        else tvDetailWord.setVisibility(View.GONE);

        String markerSnippet = marker.getSnippet();
        if (markerSnippet != null)
            tvDetailPartOfSpeech.setText(markerSnippet);
        else tvDetailPartOfSpeech.setVisibility(View.GONE);

        tvDetailSynonymsLabel.setVisibility(View.GONE);
        tvDetailSynonyms.setVisibility(View.GONE);
        tvDetailTypeOfLabel.setVisibility(View.GONE);
        tvDetailTypeOf.setVisibility(View.GONE);

        return popupView;
    }
}
