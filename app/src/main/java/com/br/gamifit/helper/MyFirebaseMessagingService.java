package com.br.gamifit.helper;

import android.content.Intent;
import android.util.Log;

import com.br.gamifit.activity.DashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("MessageReceived", "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.d("MessageReceived", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notifyUser(remoteMessage.getFrom(),remoteMessage.getNotification().getBody());
        }

    }

    private void notifyUser(String from,String content){
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
        myNotificationManager.showNotification(from,content,new Intent(getApplicationContext(),DashboardActivity.class));
    }
}
