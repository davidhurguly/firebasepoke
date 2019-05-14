package com.szte.firebasepoke;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


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
                remoteMessageSender(otherToken, messageBox);
            }
        });

        final TextView tokenDisplay = findViewById(R.id.tokenDisplay);
        FirebaseInstanceId
                .getInstance()
                .getInstanceId()
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

    private void remoteMessageSender(TextView otherToken, TextView messageBox) {
        new NotificationSender().execute(
                        otherToken.getText().toString(),
                        messageBox.getText().toString());
    }

}
