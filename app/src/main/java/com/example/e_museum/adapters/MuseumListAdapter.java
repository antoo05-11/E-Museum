package com.example.e_museum.adapters;

import android.app.Activity;
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
import com.example.e_museum.entities.Museum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MuseumListAdapter extends RecyclerView.Adapter<MuseumListAdapter.MuseumViewHolder> implements Filterable {
    private Activity activity;
    private List<Museum> museums;
    private List<Museum> museumsOld;

    public MuseumListAdapter(Activity activity, List<Museum> museums) {
        this.activity = activity;
        this.museums = museums;
        this.museumsOld = museums;
    }

    public static class MuseumViewHolder extends RecyclerView.ViewHolder {
        private final TextView museumNameTextView;
        private final TextView museumAddressTextView;
        private final ImageView museumImageView;

        public MuseumViewHolder(@NonNull View itemView) {
            super(itemView);
            museumImageView = itemView.findViewById(R.id.museum_image);
            museumAddressTextView = itemView.findViewById(R.id.museum_address);
            museumNameTextView = itemView.findViewById(R.id.museum_name);

        }
    }

    @NonNull
    @Override
    public MuseumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_preview_box, parent, false);
        return new MuseumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumViewHolder holder, int position) {
        Museum museum = museums.get(position);
        if (museum == null) {
            return;
        }
        holder.museumNameTextView.setText(museum.getName());
        holder.museumAddressTextView.setText(museum.getAddress());
        Picasso.get()
                .load("https://toquoc.mediacdn.vn/Uploaded/minhkhanh/2017_07_27/BIN_6105_DVJJ.jpg")
                .fit()
                .centerInside()
                .into((ImageView) holder.museumImageView);
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

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                museums = (List<Museum>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
