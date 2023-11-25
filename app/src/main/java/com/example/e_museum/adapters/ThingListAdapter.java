package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
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
import com.squareup.picasso.Picasso;

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
            thingNameTextView = itemView.findViewById(R.id.thing_name_tv);
        }
    }

    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thing, parent, false);
        view.setOnClickListener((v) -> {

        });
        return new ThingViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, int position) {
        Thing thing = things.get(position);
        if (thing == null) {
            return;
        }
        holder.thingNameTextView.setText(thing.getName());
       // holder.thingShortTextView.setText(thing.getShortText());
        Picasso.get()
                .load(String.format("https://muzik-files-server.000webhostapp.com/emuseum/thing_%d_%d_preview_image.png", thing.getMuseumID(), thing.getThingID()))
                .fit()
                .centerInside()
                .into((ImageView) holder.thingImageView);
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
