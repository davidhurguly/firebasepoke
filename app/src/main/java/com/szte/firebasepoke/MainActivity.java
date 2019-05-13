package com.szte.firebasepoke;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.RemoteMessage;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView otherToken = findViewById(R.id.otherToken);

        final TextView messageBox = findViewById(R.id.messageBox);

        final Button sendToken = findViewById(R.id.sendButton);
        sendToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final TextView tokenDisplay = findViewById(R.id.tokenDisplay);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        tokenDisplay.setText(task.getResult().getToken());
                    }
                });
    }

    protected void getMessage(RemoteMessage remoteMessage){

        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT);
        toast.show();
    }

    private void remoteMessageAssebler(){
        RemoteMessage remoteMessage;

    }


}
