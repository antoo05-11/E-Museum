package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_museum.R;
import com.example.e_museum.entities.Collection;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

//Lấy ra từ item thing preview.
public class CollectionsListAdapter extends RecyclerView.Adapter<CollectionsListAdapter.CollectionsListHolder> {
    private final Activity activity;

    private final List<Collection> collections;

    public CollectionsListAdapter(Activity activity, List<Collection> collections) {
        this.activity = activity;
        this.collections = collections;
    }

    public static class CollectionsListHolder extends RecyclerView.ViewHolder {
        ImageView thingImage1;
        ImageView thingImage2;
        TextView thingNameText;

        ShimmerFrameLayout thingShimmerName;
        ShimmerFrameLayout thingShimmerViewContainer;
        ShimmerFrameLayout thingShimmerViewContainer1;

        public CollectionsListHolder(@NonNull View itemView) {
            super(itemView);
            thingImage1 = itemView.findViewById(R.id.thing_image_1);
            thingImage2 = itemView.findViewById(R.id.thing_image_2);
            thingNameText = itemView.findViewById(R.id.thing_name);

            thingShimmerName = itemView.findViewById(R.id.shimmer_thing_name);
            thingShimmerViewContainer = itemView.findViewById(R.id.thing_shimmer_view_container);
            thingShimmerViewContainer1 = itemView.findViewById(R.id.thing_shimmer_view_container_1);
        }
    }

    @NonNull
    @Override
    public CollectionsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thing_preview, parent, false);
        return new CollectionsListAdapter.CollectionsListHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CollectionsListHolder holder, int position) {
        Collection collection = collections.get(position);
        if (collection == null) {
            return;
        }
        if (collection.getCollectionID() == -1) {
            holder.thingShimmerName.startShimmer();
            holder.thingShimmerViewContainer.startShimmer();
            holder.thingShimmerViewContainer1.startShimmer();

            holder.thingNameText.setText("");
        } else {
            holder.thingShimmerViewContainer.startShimmer();
            holder.thingShimmerViewContainer1.startShimmer();

            holder.thingShimmerName.hideShimmer();

            holder.thingNameText.setBackgroundColor(Color.TRANSPARENT);

            holder.thingNameText.setText(collection.getName());
        }
        Picasso.get()
                .load(String.format("https://muzik-files-server.000webhostapp.com/emuseum/museum_%d_preview_image.png", collection.getCollectionID()))
                .fit()
                .centerInside()
                .into(holder.thingImage1, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.thingShimmerViewContainer.hideShimmer();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        Picasso.get()
                .load(String.format("https://muzik-files-server.000webhostapp.com/emuseum/museum_%d_preview_image.png", collection.getCollectionID()))
                .fit()
                .centerInside()
                .into(holder.thingImage2, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.thingShimmerViewContainer1.hideShimmer();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }


    @Override
    public int getItemCount() {
        return collections.size();
    }
}
