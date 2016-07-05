package com.dclover.gpsutilities.thoitiet.services;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.dclover.gpsutilities.thoitiet.datas.SearchLoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class SuggestionAdapter extends ArrayAdapter<String> {
    protected static final String TAG = "SuggestionAdapter";
    private List<String> suggestions;

    /* renamed from: com.sundroid.myapplication.services.SuggestionAdapter.1 */
    class C02081 extends Filter {
        C02081() {
        }

        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            CityParser jp = new CityParser();
            if (constraint != null) {
                List<SearchLoc> new_suggestions = jp.getParseJsonWCF(constraint.toString());
                SuggestionAdapter.this.suggestions.clear();
                for (int i = 0; i < new_suggestions.size(); i++) {
                    SuggestionAdapter.this.suggestions.add(((SearchLoc) new_suggestions.get(i)).toString());
                }
                filterResults.values = SuggestionAdapter.this.suggestions;
                filterResults.count = SuggestionAdapter.this.suggestions.size();
            }
            return filterResults;
        }

        protected void publishResults(CharSequence contraint, FilterResults results) {
            if (results == null || results.count <= 0) {
                SuggestionAdapter.this.notifyDataSetInvalidated();
            } else {
                SuggestionAdapter.this.notifyDataSetChanged();
            }
        }
    }

    public SuggestionAdapter(Activity context, String nameFilter) {
        super(context, 17367050);
        this.suggestions = new ArrayList();
    }

    public int getCount() {
        return this.suggestions.size();
    }

    public String getItem(int index) {
        return (String) this.suggestions.get(index);
    }

    public Filter getFilter() {
        return new C02081();
    }
}
