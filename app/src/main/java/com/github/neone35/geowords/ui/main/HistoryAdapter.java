package com.github.neone35.geowords.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.github.neone35.geowords.R;
import com.github.neone35.geowords.data.models.local.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;


public class HistoryAdapter extends ArrayAdapter<Word> {
    private ArrayList<Word> items;
    private ArrayList<Word> itemsAll;
    private ArrayList<Word> suggestions;
    private int viewResourceId;

    @SuppressWarnings("unchecked")
    HistoryAdapter(Context context, int viewResourceId,
                   ArrayList<Word> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<Word>) items.clone();
        this.suggestions = new ArrayList<>();
        this.viewResourceId = viewResourceId;
    }

    private static String getFormattedDateFromTimestamp(long timestampInMilliSeconds) {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
        return new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
    }

    @Nullable
    @Override
    public Word getItem(int position) {
        return items.get(position);
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            if (vi != null) {
                v = vi.inflate(viewResourceId, null);
            }
        }
        Word word = items.get(position);
        if (word != null && v != null) {
            TextView tvWord = v.findViewById(R.id.tv_word);
            TextView tvPartOfSpeech = v.findViewById(R.id.tv_part_of_speech);
            TextView tvTimestamp = v.findViewById(R.id.tv_timestamp);
            tvWord.setText(word.getWord());
            tvPartOfSpeech.setText(word.getPartOfSpeech());
            String formattedDate = getFormattedDateFromTimestamp(word.getTimeMillis());
            tvTimestamp.setText(formattedDate);
        }
        return v;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            return ((Word) (resultValue)).getWord();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Word word : itemsAll) {
                    if (word.getWord().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(word);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<Word> filteredList = (ArrayList<Word>) results.values;
            if (results.count > 0) {
                clear();
                for (Word word : filteredList) {
                    add(word);
                }
                notifyDataSetChanged();
            }
        }
    };

}
