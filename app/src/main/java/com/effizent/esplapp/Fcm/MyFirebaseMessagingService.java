package com.effizent.esplapp.Fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.effizent.esplapp.Activity.HomeActivity;
import com.effizent.esplapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static final int BADGE_ICON_SMALL = 1;

    int MESSAGE_NOTIFICATION_ID = 0;
    Intent intent;
    String message, contentTitle;
    private SharedPreferences sp;

    NotificationCompat.Builder mBuilder;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("getData",remoteMessage.getData()+"");

        //{contentTitle=Accountant Pending Invoices, message=Test}
        contentTitle = remoteMessage.getData().get("contentTitle");
        message = remoteMessage.getData().get("message");

        sendNotification();

    }


    @SuppressLint("WrongConstant")
    public void sendNotification()
    {
        Context context = getBaseContext();
        String id = "my_channel_01";

        intent = new Intent(context, HomeActivity.class);
        MESSAGE_NOTIFICATION_ID = (int) (System.currentTimeMillis() & 0xfffffff);

        PendingIntent pIntent = PendingIntent.getActivity(context, MESSAGE_NOTIFICATION_ID, intent, MESSAGE_NOTIFICATION_ID);

        mBuilder = new NotificationCompat.Builder(context,id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        }


        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(contentTitle)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentText(message)
                .setContentIntent(pIntent)
                .setBadgeIconType(BADGE_ICON_SMALL)

                .setDefaults(Notification.DEFAULT_ALL).setAutoCancel(true)
                .setChannelId(id);


        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                mNotificationManager.createNotificationChannel(mChannel);
                mChannel.setShowBadge(true);

            }
        }
        mBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;

        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }


}