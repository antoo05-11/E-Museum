package com.example.e_museum.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Looper;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Handler;

import okhttp3.internal.http2.Http2Reader;

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
        if (notification.getNotificationID() == -1) {

            holder.shimmerFrameLayout.startShimmer();
            holder.shimmerNotificationNameTextView.startShimmer();
            holder.shimmerNotificationShortTextView.startShimmer();

            holder.notificationNameTextView.setText("");
            holder.notificationShortTextView.setText("");
        } else {
            holder.shimmerFrameLayout.startShimmer();

            new CountDownTimer(3000,1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    holder.shimmerNotificationNameTextView.hideShimmer();
                    holder.shimmerNotificationShortTextView.hideShimmer();

                    holder.notificationNameTextView.setSelected(true);
                    holder.notificationNameTextView.setBackgroundColor(Color.TRANSPARENT);
                    holder.notificationShortTextView.setBackgroundColor(Color.TRANSPARENT);

                    holder.notificationNameTextView.setText(notification.getName());
                    holder.notificationShortTextView.setText(notification.getShortText());


                    Picasso.get()
                            .load(String.format("https://muzik-files-server.000webhostapp.com/emuseum/notifications/notification_%d_preview_image.png", notification.getNotificationID()))
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
            }.start();
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
