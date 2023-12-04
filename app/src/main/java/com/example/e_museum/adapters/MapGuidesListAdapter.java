package com.example.e_museum.adapters;

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
import com.example.e_museum.models.MapGuide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MapGuidesListAdapter extends RecyclerView.Adapter<MapGuidesListAdapter.MapGuideHolder> {
    private final Activity activity;
    private final List<MapGuide> mapGuides;

    public MapGuidesListAdapter(Activity activity, List<MapGuide> mapGuides) {
        this.activity = activity;
        this.mapGuides = mapGuides;
    }

    @NonNull
    @Override
    public MapGuideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map_guide, parent, false);
        return new MapGuideHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapGuideHolder holder, int position) {
        MapGuide mapGuide = mapGuides.get(position);
        if (!mapGuide.getContent().isEmpty()) {
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params1.setMargins(20, 0, 20, 0);

            TextView titleTextView = new TextView(activity.getApplicationContext());
            titleTextView.setText(mapGuide.getTitle());
            titleTextView.setTextSize(18f);
            titleTextView.setTypeface(null, Typeface.BOLD);
            titleTextView.setTextColor(Color.BLACK);
            titleTextView.setGravity(Gravity.CENTER);
            titleTextView.setLayoutParams(params1);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(20, 0, 20, 0);

            TextView contentTextView = new TextView(activity.getApplicationContext());
            contentTextView.setText(mapGuide.getContent());
            contentTextView.setTextSize(16f);
            contentTextView.setTextColor(Color.argb(204, 0, 0, 0));
            contentTextView.setGravity(Gravity.CENTER);
            contentTextView.setLayoutParams(params2);

            holder.container.addView(titleTextView);
            holder.container.addView(contentTextView);
        } else {
            ImageView imageView = new ImageView(activity.getApplicationContext());
            Picasso.get().load(mapGuide.getImageURL()).fit().into(imageView);

            holder.container.addView(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mapGuides.size();
    }

    public static class MapGuideHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        public MapGuideHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
        }
    }

    public List<MapGuide> getMapGuides() {
        return mapGuides;
    }
}
