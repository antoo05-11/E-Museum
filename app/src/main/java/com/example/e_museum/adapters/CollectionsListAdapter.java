package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.e_museum.activities.MainActivity;
import com.example.e_museum.activities.ViewCollectionActivity;
import com.example.e_museum.entities.Collection;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CollectionsListAdapter extends RecyclerView.Adapter<CollectionsListAdapter.CollectionsListHolder> implements Filterable {
    private final Activity activity;

    private List<Collection> collections;
    private final List<Collection> collectionsOld;

    public CollectionsListAdapter(Activity activity, List<Collection> collections) {
        this.activity = activity;
        this.collections = collections;
        this.collectionsOld = collections;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase().trim();
                if (input.isEmpty()) {
                    collections = collectionsOld;
                } else {
                    List<Collection> list = new ArrayList<>();
                    for (Collection collection : collectionsOld) {
                        if (collection.getName().toLowerCase().contains(input)) {
                            list.add(collection);
                        }
                    }
                    collections = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = collections;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                collections = (List<Collection>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class CollectionsListHolder extends RecyclerView.ViewHolder {
        private final ImageView thingImage1;
        private final ImageView thingImage2;
        private final ImageView thingImage3;
        private final TextView thingNameText;
        private final TextView thingsNumTextView;
        private final ShimmerFrameLayout thingsNumTextViewShimmer;
        private final ShimmerFrameLayout thingShimmerName;
        private final ShimmerFrameLayout thingShimmerViewContainer;
        private final ShimmerFrameLayout thingShimmerViewContainer1;
        private final ShimmerFrameLayout thingShimmerViewContainer2;


        public CollectionsListHolder(@NonNull View itemView) {
            super(itemView);
            thingImage1 = itemView.findViewById(R.id.thing_image_1);
            thingImage2 = itemView.findViewById(R.id.thing_image_2);
            thingImage3 = itemView.findViewById(R.id.thing_image_3);
            thingNameText = itemView.findViewById(R.id.thing_name);
            thingsNumTextView = itemView.findViewById(R.id.things_num);

            thingShimmerName = itemView.findViewById(R.id.shimmer_thing_name);
            thingShimmerViewContainer = itemView.findViewById(R.id.thing_shimmer_view_container);
            thingShimmerViewContainer1 = itemView.findViewById(R.id.thing_shimmer_view_container_1);
            thingShimmerViewContainer2 = itemView.findViewById(R.id.thing_shimmer_view_container_2);
            thingsNumTextViewShimmer = itemView.findViewById(R.id.shimmer_things_num);
        }
    }

    @NonNull
    @Override
    public CollectionsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
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
            holder.thingNameText.setText("");
            holder.thingsNumTextView.setText("");
            return;
        }

        holder.thingsNumTextView.setText(String.format("Số hiện vật: " + collection.getThingsNum()));
        holder.thingsNumTextViewShimmer.hideShimmer();
        holder.thingsNumTextView.setBackgroundColor(Color.TRANSPARENT);

        holder.thingNameText.setText(collection.getName());
        holder.thingShimmerName.hideShimmer();
        holder.thingNameText.setBackgroundColor(Color.TRANSPARENT);

        Picasso.get()
                .load(MainActivity.fileServerURL + String.format("thing_images/%d_1.png",
                        collection.getThingsList().get(0).getThingID()))
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
                .load(MainActivity.fileServerURL + String.format("thing_images/%d_1.png",
                        collection.getThingsList().get(1).getThingID()))
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

        Picasso.get()
                .load(MainActivity.fileServerURL + String.format("thing_images/%d_1.png",
                        collection.getThingsList().get(2).getThingID()))
                .fit()
                .centerInside()
                .into(holder.thingImage3, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.thingShimmerViewContainer2.hideShimmer();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity.getApplicationContext(), ViewCollectionActivity.class);
            intent.putExtra("collection", collection);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return collections.size();
    }
}
