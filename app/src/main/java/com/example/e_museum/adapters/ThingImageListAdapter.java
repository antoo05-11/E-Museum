package com.example.e_museum.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.model.content.GradientColor;
import com.example.e_museum.PaletteUtils;
import com.example.e_museum.R;
import com.squareup.picasso.Callback;
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
                .load(imageURL)
                .fit()
                .centerInside()
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();
                        GradientDrawable backgroundDominantColor =
                                new PaletteUtils().getDominantGradient(
                                        imageBitmap,
                                        15.0f,
                                        GradientDrawable.Orientation.TOP_BOTTOM, null
                                );
                        holder.container.setBackground(backgroundDominantColor);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return imageURLs.size();
    }


    public static class ThingImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final ImageView container;

        public ThingImageViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.thing_image_inside_bg_image_view);
            imageView = itemView.findViewById(R.id.thing_image_inside);
        }
    }
}
