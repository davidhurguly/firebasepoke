package com.szte.firebasepoke;


import android.app.Notification;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SERVER_AUTH_KEY = "AAAAqXY9J8c:APA91bFp3mAsszOWr-b53fqHzA9GiiSerKl1V-UxIa0HY3VMSzajQWjW6pvV6F-1iN1oyjrSN--FMUM_fm9jnw-zma7-m_RezYzx0zqi19hZRpAw0uttZk5rWL8gJA3h7LVIXD2E7o1O";
    private static final String API_URL = "https://fcm.googleapis.com/fcm/send";

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

    private void remoteMessageSender(TextView otherToken, TextView messageBox){

        final String authKey = SERVER_AUTH_KEY;
        final String FMCurl = API_URL;
        final String DeviceIdKey = otherToken.getText().toString();

            try {
                URL url = new URL(FMCurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=" + authKey);
                conn.setRequestProperty("Content-Type", "application/json");

                System.out.println(DeviceIdKey);

                JSONObject data = new JSONObject();
                    data.put("to", DeviceIdKey.trim());

                JSONObject info = new JSONObject();
                    info.put("title", "FCM Notificatoin");
                    info.put("body", messageBox.getText().toString());
                    data.put("notification", info);

                System.out.println(data.toString());

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data.toString());
                    wr.flush();
                    wr.close();

                int responseCode = conn.getResponseCode();
                    System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


            }
                catch(Exception e)
            {
                System.out.println(e);
            }


        }

}
