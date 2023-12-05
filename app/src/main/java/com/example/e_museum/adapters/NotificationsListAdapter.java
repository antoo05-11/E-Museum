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
import com.example.e_museum.activities.NotificationViewActivity;
import com.example.e_museum.entities.Notification;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.NotificationViewHolder> implements Filterable {
    private List<Notification> notifications;
    private List<Notification> oldNotifications;
    private final Activity activity;

    public NotificationsListAdapter(Activity activity, List<Notification> notifications) {
        this.notifications = notifications;
        this.activity = activity;
        this.oldNotifications = notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications.clear();
        this.notifications.addAll(notifications);
        this.oldNotifications = notifications;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase();
                if (input.isEmpty()) {
                    notifications = oldNotifications;
                } else {
                    List<Notification> list = new ArrayList<>();
                    for (Notification noti : oldNotifications) {
                        if (noti.getName().toLowerCase().contains(input)) {
                            list.add(noti);
                        }
                    }
                    notifications = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = notifications;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifications = (List<Notification>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        NotificationViewHolder viewHolder = new NotificationViewHolder(view);

        view.setOnClickListener((v) -> {
            int position = notifications.indexOf((Notification) v.getTag());
            if (position != -1) {
                Notification notification = notifications.get(position);
                if (notification != null) {
                    Intent intent = new Intent(activity, NotificationViewActivity.class);
                    intent.putExtra("notification_id", notification.getNotificationID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
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
        if (notification.getNotificationID() == -1) {
            holder.shimmerFrameLayout.startShimmer();
            holder.shimmerNotificationNameTextView.startShimmer();
            holder.shimmerNotificationShortTextView.startShimmer();

            holder.notificationNameTextView.setText("");
            holder.notificationShortTextView.setText("");
        } else {
            holder.shimmerFrameLayout.startShimmer();

            holder.shimmerNotificationNameTextView.hideShimmer();
            holder.shimmerNotificationShortTextView.hideShimmer();

            holder.notificationNameTextView.setSelected(true);
            holder.notificationNameTextView.setBackgroundColor(Color.TRANSPARENT);
            holder.notificationShortTextView.setBackgroundColor(Color.TRANSPARENT);

            holder.notificationNameTextView.setText(notification.getName());
            holder.notificationShortTextView.setText(notification.getShortText());

            Picasso.get()
                    .load(String.format(MainActivity.fileServerURL + "notifications/notification_%d_preview_image.png", notification.getNotificationID()))
                    .fit()
                    .centerInside()
                    .into(holder.notificationImageView, new Callback() {

                        @Override
                        public void onSuccess() {
                            holder.shimmerFrameLayout.hideShimmer();
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
            holder.itemView.setTag(notification);
        }
    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final TextView notificationNameTextView;
        private final TextView notificationShortTextView;
        private final ImageView notificationImageView;
        private final ShimmerFrameLayout shimmerFrameLayout;
        private final ShimmerFrameLayout shimmerNotificationNameTextView;
        private final ShimmerFrameLayout shimmerNotificationShortTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationImageView = itemView.findViewById(R.id.notification_image);
            notificationShortTextView = itemView.findViewById(R.id.notification_short);
            notificationNameTextView = itemView.findViewById(R.id.notification_name);

            shimmerFrameLayout = itemView.findViewById(R.id.notification_shimmer_view_container);
            shimmerNotificationNameTextView = itemView.findViewById(R.id.shimmer_notification_name);
            shimmerNotificationShortTextView = itemView.findViewById(R.id.shimmer_notification_short);
        }
    }
}
