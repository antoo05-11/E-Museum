package com.example.e_museum.view_controller.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_museum.R;
import com.example.e_museum.utils.PaletteUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MuseumImagesListAdapter extends RecyclerView.Adapter<MuseumImagesListAdapter.MuseumImageHolder> {
    private final List<String> museumImageURLsList;
    private final Activity activity;

    public MuseumImagesListAdapter(Activity activity, List<String> museumImageURLsList) {
        this.museumImageURLsList = museumImageURLsList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MuseumImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_museum_image, parent, false);
        return new MuseumImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumImageHolder holder, int position) {
        String museumImageURL = museumImageURLsList.get(position);
        if (!museumImageURL.isEmpty()) {
            Picasso.get().load(museumImageURL).into(holder.museumImageView, new Callback() {
                @Override
                public void onSuccess() {
                    holder.shimmerLayout.hideShimmer();
                    Bitmap imageBitmap = ((BitmapDrawable) holder.museumImageView.getDrawable()).getBitmap();
                    GradientDrawable backgroundDominantColor =
                            new PaletteUtils().getDominantGradient(
                                    imageBitmap,
                                    0f,
                                    GradientDrawable.Orientation.TOP_BOTTOM, null
                            );
                    holder.museumImageView.setBackground(backgroundDominantColor);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return museumImageURLsList.size();
    }

    public Activity getActivity() {
        return activity;
    }

    public static class MuseumImageHolder extends RecyclerView.ViewHolder {
        private final ShimmerFrameLayout shimmerLayout;
        private final ImageView museumImageView;

        public MuseumImageHolder(@NonNull View itemView) {
            super(itemView);
            shimmerLayout = itemView.findViewById(R.id.shimmer_museum_main_image_view);
            museumImageView = itemView.findViewById(R.id.museum_main_image_view);
        }
    }
}
