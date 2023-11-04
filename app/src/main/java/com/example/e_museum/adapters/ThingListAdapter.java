package com.example.e_museum.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.e_museum.entities.Thing;

import java.util.ArrayList;

public class ThingListAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Thing> things;

    public ThingListAdapter(Activity activity, ArrayList<Thing> things) {
        this.activity = activity;
        this.things = things;
    }

    @Override
    public int getCount() {
        return things.size();
    }

    @Override
    public Object getItem(int position) {
        return things.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
