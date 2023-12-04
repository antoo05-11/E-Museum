package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_museum.R;
import com.example.e_museum.activities.MainActivity;
import com.example.e_museum.fragments.fragments_dialog.MuseumConfirmDialogFragment;
import com.example.e_museum.models.Museum;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MuseumsListAdapter extends RecyclerView.Adapter<MuseumsListAdapter.MuseumViewHolder> implements Filterable {
    private final Activity activity;
    private List<Museum> museums;
    private final List<Museum> museumsOld;

    public MuseumsListAdapter(Activity activity, List<Museum> museums) {
        this.activity = activity;
        this.museums = museums;
        this.museumsOld = museums;
    }

    public static class MuseumViewHolder extends RecyclerView.ViewHolder {
        private final TextView museumNameTextView;
        private final TextView museumAddressTextView;
        private final ImageView museumImageView;
        private final ShimmerFrameLayout shimmerFrameLayout;
        private final ShimmerFrameLayout shimmerMuseumNameTextView;
        private final ShimmerFrameLayout shimmerMuseumShortTextView;

        public MuseumViewHolder(@NonNull View itemView) {
            super(itemView);
            museumImageView = itemView.findViewById(R.id.thing_image);
            museumAddressTextView = itemView.findViewById(R.id.museum_short_tv);
            museumNameTextView = itemView.findViewById(R.id.museum_name_tv);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);
            shimmerMuseumNameTextView = itemView.findViewById(R.id.shimmer_museum_name_tv);
            shimmerMuseumShortTextView = itemView.findViewById(R.id.shimmer_museum_short_tv);
        }
    }

    @NonNull
    @Override
    public MuseumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_museum, parent, false);
        return new MuseumViewHolder(view);
    }

    private void confirmMuseum(Museum museum) {
        DialogFragment newFragment = new MuseumConfirmDialogFragment(activity, museum);
        newFragment.show(((AppCompatActivity) activity).getSupportFragmentManager(), "confirm museum chose");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MuseumViewHolder holder, int position) {
        Museum museum = museums.get(position);
        if (museum == null) {
            return;
        }
        if (museum.getMuseumID() == -1) {
            holder.shimmerFrameLayout.startShimmer();
            holder.shimmerMuseumNameTextView.startShimmer();
            holder.shimmerMuseumShortTextView.startShimmer();

            holder.museumNameTextView.setText("");
            holder.museumAddressTextView.setText("");
        } else {
            holder.shimmerFrameLayout.startShimmer();

            holder.shimmerMuseumNameTextView.hideShimmer();
            holder.shimmerMuseumShortTextView.hideShimmer();

            holder.museumNameTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.museumAddressTextView.setBackgroundColor(Color.TRANSPARENT);

            holder.museumNameTextView.setText(museum.getName());
            holder.museumAddressTextView.setText(museum.getAddress());

            holder.itemView.setOnClickListener((v) -> {
                confirmMuseum(museum);
            });

            Picasso.get()
                    .load(String.format(MainActivity.fileServerURL + "museum_%d_preview_image.png", museum.getMuseumID()))
                    .fit()
                    .centerInside()
                    .into(holder.museumImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.shimmerFrameLayout.hideShimmer();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (museums != null) return museums.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase();
                if (input.isEmpty()) {
                    museums = museumsOld;
                } else {
                    List<Museum> list = new ArrayList<>();
                    for (Museum museum : museumsOld) {
                        if (museum.getName().toLowerCase().contains(input)) {
                            list.add(museum);
                        }
                    }
                    museums = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = museums;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                museums = (List<Museum>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
