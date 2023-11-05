package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
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
import com.example.e_museum.entities.Notification;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> implements Filterable {
    List<Notification> notifications;
    Activity activity;

    public NotificationListAdapter(Activity activity, List<Notification> notifications) {
        this.notifications = notifications;
        this.activity = activity;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_preview_box, parent, false);
        view.setOnClickListener((v) -> {
        });
        return new NotificationListAdapter.NotificationViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        if (notification == null) {
            return;
        }
        holder.notificationNameTextView.setText(notification.getName());
        holder.notificationShortTextView.setText(notification.getShortText());
        Picasso.get()
                .load(String.format("https://muzik-files-server.000webhostapp.com/emuseum/notification_%d_preview_image.png", notification.getNotificationID()))
                .fit()
                .centerInside()
                .into((ImageView) holder.notificationImageView);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final TextView notificationNameTextView;
        private final TextView notificationShortTextView;
        private final ImageView notificationImageView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationImageView = itemView.findViewById(R.id.notification_image);
            notificationShortTextView = itemView.findViewById(R.id.notification_short);
            notificationNameTextView = itemView.findViewById(R.id.notification_name);
        }
    }
}
