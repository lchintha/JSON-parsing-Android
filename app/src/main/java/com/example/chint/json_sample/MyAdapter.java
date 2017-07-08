package com.example.chint.json_sample;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chint on 7/6/2017.
 */

public class MyAdapter extends ArrayAdapter<ListInfo> {
    Context ctx;
    int resource;
    ArrayList<ListInfo> objects;

    public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ListInfo> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.layout_row, parent, false);
        }

        ListInfo lo = getItem(position);

        ImageView img = (ImageView)v.findViewById(R.id.image);
        Picasso.with(ctx).load(lo.getImage()).into(img);
        TextView title = (TextView)v.findViewById(R.id.name);
        title.setText(lo.getTitle());
        TextView desc = (TextView)v.findViewById(R.id.location);
        desc.setText(lo.getDescription());

        return v;
    }
}
