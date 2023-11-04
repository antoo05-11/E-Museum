package com.example.e_museum.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_museum.ConfirmDialog;
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
            museumImageView = itemView.findViewById(R.id.thing_image);
            museumAddressTextView = itemView.findViewById(R.id.thing_short);
            museumNameTextView = itemView.findViewById(R.id.thing_name);

        }
    }

    @NonNull
    @Override
    public MuseumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_preview_box, parent, false);
        view.setOnClickListener((v) -> {
            confirmMuseum();
        });
        return new MuseumViewHolder(view);
    }

    private void confirmMuseum() {
        DialogFragment newFragment = new ConfirmDialog();
        newFragment.show(((AppCompatActivity)activity).getSupportFragmentManager(), "confirm museum chose");

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
                .load(String.format("https://muzik-files-server.000webhostapp.com/emuseum/museum_%d_preview_image.png", museum.getMuseumID()))
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
