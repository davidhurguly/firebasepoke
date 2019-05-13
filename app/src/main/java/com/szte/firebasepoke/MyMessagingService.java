package com.szte.firebasepoke;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyMessagingService";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Context context = getApplicationContext();

                    Toast toast = Toast.makeText(context, remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }


}
