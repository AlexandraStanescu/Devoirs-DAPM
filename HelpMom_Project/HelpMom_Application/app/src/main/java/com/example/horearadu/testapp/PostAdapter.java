package com.example.horearadu.testapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by horearadu on 12/01/15.
 */
public class PostAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> values;
    private final ArrayList<String> contents;

    public PostAdapter(Context context, ArrayList<String> values, ArrayList<String> contents) {
        super(context, R.layout.post_item_layout, values);
        this.context = context;
        this.values = values;
        this.contents = contents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.post_item_layout, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.postTitleText);
        TextView bodyView = (TextView) rowView.findViewById(R.id.postBodyText);

        titleView.setText(values.get(position));
        bodyView.setText(contents.get(position));

        return rowView;
    }
}
