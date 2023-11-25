package com.example.e_museum.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_museum.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThingImageListAdapter extends RecyclerView.Adapter<ThingImageListAdapter.ThingImageViewHolder> {
    private final Activity activity;
    private final List<String> imageURLs;

    public ThingImageListAdapter(Activity activity, List<String> imageURLs) {
        this.activity = activity;
        this.imageURLs = imageURLs;
    }

    @NonNull
    @Override
    public ThingImageListAdapter.ThingImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thing_image, parent, false);
        ThingImageListAdapter.ThingImageViewHolder viewHolder = new ThingImageListAdapter.ThingImageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThingImageViewHolder holder, int position) {
        String imageURL = imageURLs.get(position);
        if (imageURL == null) {
            return;
        }
        Picasso.get()
                .load("https://muzik-files-server.000webhostapp.com/emuseum/notifications/notification_1_preview_image.png")
                .fit()
                .centerInside()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageURLs.size();
    }


    public static class ThingImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ThingImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thing_image_inside);
        }
    }
}
