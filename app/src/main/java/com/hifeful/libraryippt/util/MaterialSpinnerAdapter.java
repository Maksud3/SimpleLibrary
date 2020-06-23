package com.hifeful.libraryippt.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.List;

import androidx.annotation.NonNull;

public class MaterialSpinnerAdapter<T> extends ArrayAdapter<T> {
    public List<T> items;
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            results.values = items;
            results.count = items.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notifyDataSetChanged();
        }
    };


    public MaterialSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        items = objects;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }
}
