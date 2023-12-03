package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.e_museum.R;
import com.example.e_museum.activities.MainActivity;
import com.example.e_museum.activities.ViewThingActivity;
import com.example.e_museum.entities.Thing;
import com.example.e_museum.utils.PaletteUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThingsListAdapter extends RecyclerView.Adapter<ThingsListAdapter.ThingViewHolder> implements Filterable {
    private final Activity activity;
    private final ArrayList<Thing> things;

    public ThingsListAdapter(Activity activity, ArrayList<Thing> things) {
        this.activity = activity;
        this.things = things;
    }

    public static class ThingViewHolder extends RecyclerView.ViewHolder {
        private final TextView thingNameTextView;
        private final ImageView thingImageView;
        private final LinearLayout containerLayout;

        public ThingViewHolder(@NonNull View itemView) {
            super(itemView);
            thingImageView = itemView.findViewById(R.id.thing_image);
            thingNameTextView = itemView.findViewById(R.id.thing_name_tv);
            containerLayout = itemView.findViewById(R.id.container_layout);
        }

        public ImageView getThingImageView() {
            return thingImageView;
        }
    }

    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thing, parent, false);
        return new ThingViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Thing thing = things.get(position);
        if (thing == null) {
            return;
        }
        holder.thingNameTextView.setText(thing.getName());
        // holder.thingShortTextView.setText(thing.getShortText());
        Picasso.get()
                .load(MainActivity.fileServerURL + String.format("thing_images/%d_1.png",
                        thing.getThingID()))
                .fit()
                .centerInside()
                .into(holder.thingImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) holder.thingImageView.getDrawable()).getBitmap();
                        PaletteUtils paletteUtils = new PaletteUtils();
                        GradientDrawable backgroundDominantColor =
                                paletteUtils.getDominantGradient(
                                        imageBitmap,
                                        0f,
                                        GradientDrawable.Orientation.TOP_BOTTOM, null
                                );
                        if(holder.itemView.getParent() == null) return;
                        ViewPager2 viewPager = (ViewPager2) holder.itemView.getParent().getParent();
                        if (viewPager.getCurrentItem() == position) {
                            activity.findViewById(R.id.root_view).setBackground(backgroundDominantColor);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.containerLayout.setOnClickListener(view -> {
            Intent intent = new Intent(activity.getApplicationContext(), ViewThingActivity.class);
            intent.putExtra("thing", thing);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return things.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
