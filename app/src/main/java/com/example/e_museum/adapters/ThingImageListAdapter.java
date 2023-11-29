package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.model.content.GradientColor;
import com.example.e_museum.PaletteUtils;
import com.example.e_museum.R;
import com.example.e_museum.activities.ViewImagesActivity;
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
    public void onBindViewHolder(@NonNull ThingImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
                                        0f,
                                        GradientDrawable.Orientation.TOP_BOTTOM, null
                                );
                        ViewPager2 viewPager = (ViewPager2) holder.itemView.getParent().getParent();
                        if (viewPager.getCurrentItem() == position) {
                            activity.findViewById(R.id.frameLayout).setBackground(backgroundDominantColor);
                        }

                        holder.imageView.setOnClickListener((v) -> {
                            Intent intent = new Intent(activity.getApplicationContext(), ViewImagesActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        });
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

        public ThingImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thing_image_inside);

        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
