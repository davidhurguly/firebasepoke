package com.szte.firebasepoke;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationSender extends AsyncTask<String, Void, Void> {

    private final String authKey = "AAAAqXY9J8c:APA91bFp3mAsszOWr-b53fqHzA9GiiSerKl1V-UxIa0HY3VMSzajQWjW6pvV6F-1iN1oyjrSN--FMUM_fm9jnw-zma7-m_RezYzx0zqi19hZRpAw0uttZk5rWL8gJA3h7LVIXD2E7o1O";
    private final String FMCurl = "https://fcm.googleapis.com/fcm/send";

    @Override
    protected Void doInBackground(String... strings) {

        String deviceIdKey = strings[0];
        String messageBox = strings[1];

        try {
            URL url = new URL(FMCurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=" + authKey);
                conn.setRequestProperty("Content-Type", "application/json");

            JSONObject data = new JSONObject();
                data.put("to", deviceIdKey.trim());

            JSONObject info = new JSONObject();
                info.put("title", "FCM Notification");
                info.put("body", messageBox);
                data.put("notification", info);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data.toString());
                wr.flush();
                wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("LocalMessage: " + e.getLocalizedMessage());
            System.out.println("Cause: " + e.getCause());
            System.out.println("Stack: " + e.getStackTrace());
            System.out.println("Message: " + e.getMessage());
        }

        return null;
    }
}
