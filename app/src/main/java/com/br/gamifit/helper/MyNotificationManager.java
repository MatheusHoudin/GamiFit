package com.br.gamifit.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.br.gamifit.R;

public class MyNotificationManager {

    private Context context;
    public static final int NOTIFICATION_ID = 100;
    private final String myChannel = "GAMIFIT";

    public MyNotificationManager(Context context){
        this.context = context;
    }

    public void showNotification(String from, String content, Intent intent){
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,myChannel);
        Notification notification = builder.setAutoCancel(true)
                                    .setContentIntent(pendingIntent)
                                    .setContentTitle(from)
                                    .setContentText(content)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}
