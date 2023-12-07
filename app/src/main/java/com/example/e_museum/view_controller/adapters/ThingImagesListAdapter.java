package com.example.e_museum.view_controller.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import androidx.viewpager2.widget.ViewPager2;

import com.example.e_museum.utils.PaletteUtils;
import com.example.e_museum.R;
import com.example.e_museum.view_controller.activities.ViewImagesActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThingImagesListAdapter extends RecyclerView.Adapter<ThingImagesListAdapter.ThingImageViewHolder> {
    private final Activity activity;
    private final List<String> imageURLs;

    public ThingImagesListAdapter(Activity activity, List<String> imageURLs) {
        this.activity = activity;
        this.imageURLs = imageURLs;
    }

    @NonNull
    @Override
    public ThingImagesListAdapter.ThingImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thing_image, parent, false);
        return new ThingImageViewHolder(view);
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
                        PaletteUtils paletteUtils = new PaletteUtils();
                        GradientDrawable backgroundDominantColor =
                                paletteUtils.getDominantGradient(
                                        imageBitmap,
                                        0f,
                                        GradientDrawable.Orientation.TOP_BOTTOM, null
                                );
                        ViewPager2 viewPager = (ViewPager2) holder.itemView.getParent().getParent();
                        if (viewPager.getCurrentItem() == position) {
                            activity.findViewById(R.id.thing_view_root).setBackground(backgroundDominantColor);
                        }
                        holder.borderContainer.setCardBackgroundColor(paletteUtils.getDominantColor(imageBitmap));
                        holder.imageView.setOnClickListener((v) -> {
                            Intent intent = new Intent(activity.getApplicationContext(), ViewImagesActivity.class);
                            intent.putExtra("imageURL", imageURL);
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
        private final CardView borderContainer;

        public ThingImageViewHolder(@NonNull View itemView) {
            super(itemView);
            borderContainer = itemView.findViewById(R.id.border_container);
            imageView = itemView.findViewById(R.id.thing_image_inside);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
