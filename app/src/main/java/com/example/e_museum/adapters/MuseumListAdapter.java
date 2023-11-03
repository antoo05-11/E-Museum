package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_museum.entities.Museum;
import com.example.e_museum.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MuseumListAdapter extends BaseAdapter {
    private Activity activity;
    private List<Museum> museums;

    public MuseumListAdapter(Activity activity, List<Museum> museums) {
        this.activity = activity;
        this.museums = museums;
    }

    @Override
    public int getCount() {
        return museums.size();
    }

    @Override
    public Object getItem(int position) {
        return museums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        convertView = inflater.inflate(R.layout.museum_preview_box, null);

        ((TextView) convertView.findViewById(R.id.museum_name)).setText(museums.get(position).getName());
        ((TextView) convertView.findViewById(R.id.museum_address)).setText(museums.get(position).getAddress());

        Picasso.get()
                .load("https://toquoc.mediacdn.vn/Uploaded/minhkhanh/2017_07_27/BIN_6105_DVJJ.jpg")
                .fit()
                .centerInside()
                .into((ImageView) convertView.findViewById(R.id.museum_image));
        return convertView;
    }
}
