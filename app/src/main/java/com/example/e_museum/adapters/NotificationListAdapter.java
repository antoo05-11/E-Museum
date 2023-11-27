package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_museum.R;
import com.example.e_museum.entities.Notification;
import com.example.e_museum.activities.NotificationViewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> implements Filterable {
    List<Notification> notifications;
    Activity activity;

    Fragment containerFragment;

    public NotificationListAdapter(Activity activity, List<Notification> notifications, Fragment containerFragment) {
        this.notifications = notifications;
        this.activity = activity;
        this.containerFragment = containerFragment;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        NotificationViewHolder viewHolder = new NotificationViewHolder(view);

        view.setOnClickListener((v) -> {
            int position = notifications.indexOf(v.getTag());
            if (position != -1) {
                Notification notification = notifications.get(position);
                if (notification != null) {
                    Intent intent = new Intent(containerFragment.getActivity(), NotificationViewActivity.class);
                    intent.putExtra("notification_id", notification.getNotificationID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    containerFragment.startActivity(intent);
                }
            }
        });
        return viewHolder;
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        if (notification == null) {
            return;
        }
        holder.notificationNameTextView.setSelected(true);
        holder.notificationNameTextView.setText(notification.getName());
        holder.notificationShortTextView.setText(notification.getShortText());
        Picasso.get()
                .load(String.format("https://muzik-files-server.000webhostapp.com/emuseum/notifications/notification_%d_preview_image.png", notification.getNotificationID()))
                .fit()
                .centerInside()
                .into(holder.notificationImageView);
        holder.itemView.setTag(notification);
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
