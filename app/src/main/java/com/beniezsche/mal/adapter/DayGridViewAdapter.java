package com.beniezsche.mal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

import com.beniezsche.mal.R;

import java.util.ArrayList;

public class DayGridViewAdapter extends ArrayAdapter<Integer> {

    public DayGridViewAdapter(@NonNull Context context, ArrayList<Integer> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_day, parent, false);
        }

        Integer day = getItem(position);
        TextView tvDay = listitemView.findViewById(R.id.tv_day);

        tvDay.setText(day == 0? "" : String.valueOf(day) );
        return listitemView;
    }
}