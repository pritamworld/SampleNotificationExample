package com.pritesh.samplenotificationexample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

//TODO Main Activity code
public class MainActivity extends Activity
{
    NotificationManager notifyMgr;
    int getValue;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getValue = 0;
    }

//TODO Display custom notification function
    private void customNotification(String message)
    {
        int notificationId = getValue;
        getValue++;

        Intent notificationIntent = new Intent(getApplicationContext(), ShowNotificationActivity.class);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("jsonPushMessage", "Welcome");
        notificationIntent.putExtra("GCMid", "" + notificationId);
        notificationIntent.setAction("Yes");
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.tfs_large_notify_icon);
        builder.setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message)
                .setContentIntent(pendingNotificationIntent);
                //.addAction(0, "Yes", pendingNotificationIntent)
                //.addAction(0, "No", pendingNotificationIntent);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setColor(getResources().getColor(R.color.color_app_toyota));
            builder.setSmallIcon(R.drawable.tfs_notify_icon);
        } else {
            builder.setContentTitle(getString(R.string.app_name));
            builder.setColor(getResources().getColor(R.color.color_app_toyota));
            builder.setSmallIcon(R.drawable.tfs_notify_icon);
        }
        builder.setAutoCancel(true);

        notifyMgr = (NotificationManager)getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMgr.notify(notificationId, builder.build());
    }

    protected void showNotificationClick(View view)
    {
        String msg="Long long text to test long long My Test Notification Message with custom test for testing the notification etc etc etc hello workd";
        customNotification(msg);
    }
}
