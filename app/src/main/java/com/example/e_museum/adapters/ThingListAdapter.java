package com.example.e_museum.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_museum.R;
import com.example.e_museum.entities.Thing;

import java.util.ArrayList;

public class ThingListAdapter extends RecyclerView.Adapter<ThingListAdapter.ThingViewHolder> implements Filterable {
    private Activity activity;
    private ArrayList<Thing> things;

    public ThingListAdapter(Activity activity, ArrayList<Thing> things) {
        this.activity = activity;
        this.things = things;
    }

    public static class ThingViewHolder extends RecyclerView.ViewHolder {
        private final TextView thingNameTextView;
        private final TextView thingShortTextView;
        private final ImageView thingImageView;

        public ThingViewHolder(@NonNull View itemView) {
            super(itemView);
            thingImageView = itemView.findViewById(R.id.thing_image);
            thingShortTextView = itemView.findViewById(R.id.thing_short);
            thingNameTextView = itemView.findViewById(R.id.thing_name);
        }
    }

    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
