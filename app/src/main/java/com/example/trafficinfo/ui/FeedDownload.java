package com.example.trafficinfo.ui;

// Author: Liam Rutherford
// Matriculation Number: S2024208

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FeedDownload
{
    public FeedDownload() {}

    public String downloadFile(String urlString) throws IOException{

        URL url = new URL(urlString);
        InputStream in;
        StringBuilder result = new StringBuilder();
        int response;
        URLConnection conn = url.openConnection();
        try{

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(5000);
            httpConn.connect();

            response = httpConn.getResponseCode();

            if(response == HttpURLConnection.HTTP_OK) {
                Log.e("XML TAG", "Connection Found");
                in = httpConn.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);

                String x;
                while((x = br.readLine()) != null) {
                    result.append(x);
                }
            } else {
                Log.e("XML TAG", "Connection not found!");
            }

        } catch (IOException e) {
            Log.e("Sorry can not connect!", e.toString());
        }
        return result.toString();
    }

}