package com.effizent.esplapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.effizent.esplapp.R;
import com.effizent.esplapp.RetroApiResponses.NotificationListApi;
import com.effizent.esplapp.databinding.ItemNotificationlistBinding;
import com.effizent.esplapp.session.SessionManager;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontex;
    private List<NotificationListApi> notificationListApiList;
    Activity activity;
    SessionManager sessionManager;

    ItemNotificationlistBinding binding;
    public NotificationAdapter(Context mcontex, List<NotificationListApi> notificationListApiList)
    {
        this.mcontex = mcontex;
        this.notificationListApiList = notificationListApiList;
        activity= (Activity) mcontex;
        sessionManager = new SessionManager(mcontex.getApplicationContext());

    }

    @NonNull
    @Override
    public ViewHolderPollAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_notificationlist, parent, false);
        return new ViewHolderPollAdapter(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder h, final int position)
    {
        if (h instanceof ViewHolderPollAdapter)
        {
            final ViewHolderPollAdapter holder = (ViewHolderPollAdapter) h;
            holder.binding.notifTitleTV.setText(notificationListApiList.get(position).getTitle());
            holder.binding.notifMsg.setText(notificationListApiList.get(position).getNotificationMessage());
            holder.binding.dateTV.setText(notificationListApiList.get(position).getCreatedDate());
        }



    }
    @Override
    public int getItemCount()
    {
        return notificationListApiList != null ? notificationListApiList.size() : 0;
    }



    public class ViewHolderPollAdapter extends RecyclerView.ViewHolder
    {
        private ItemNotificationlistBinding binding;

        public ViewHolderPollAdapter(ItemNotificationlistBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }

}