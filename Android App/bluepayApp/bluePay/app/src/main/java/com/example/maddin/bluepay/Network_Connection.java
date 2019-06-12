package com.example.maddin.bluepay;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Network_Connection {
    private static Network_Connection instance;
    private static URL url;
    private static StringBuilder sb = new StringBuilder();


    private Network_Connection() {
        try {
//            url = new URL ("http://pr-245.lab.if.haw-landshut.de/handy.php");
     //       url = new URL ("http://10.48.133.10/test/handy.php"); uni
       //     192.168.178.32 hause
            //192.168.0.102 tho's home
            url = new URL ("http://192.168.0.102:8082/odai-test/handy.php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Network_Connection getInstance() {
        if (instance == null) {
            return Network_Connection.instance = new Network_Connection();
        }
        return Network_Connection.instance;
    }

    public String postConnection (String params) {

        InputStream input = null;
        /*List<NameValuePair> paramsList = new ArrayList<>();
        for(String param: params.split("&")){
            paramsList.add(new BasicNameValuePair(param.split("=")[0], param.split("=")[1]));
        }*/
        HttpURLConnection connection;
        String tmp = "";
        try {
            /*ServiceHandling sh = new ServiceHandling();
            String json = sh.call("http://10.0.2.2:8082/test/handy.php", ServiceHandling.POST, paramsList);*/
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            if (!sb.toString().equals(""))
                connection.setRequestProperty("Cookie", sb.toString());

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();



            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(params);

            output.flush();
            output.close();

            input = connection.getInputStream();
            tmp = convertStreamToString(input);
            input.close();

            if (sb.toString().equals("")) {
                List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
                if (cookies != null) {
                    for (String cookie : cookies) {
                        if (sb.length() > 0) {
                            sb.append("; ");
                        }
                        String value = cookie.split(";")[0];
                        sb.append(value);
                    }
                }
            }
        } catch (IOException e) {
            Log.e("Network_Connection", "Error in postConnection()", e);
        }
        return tmp;
    }

    private String convertStreamToString (InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();

        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            Log.e("Network_Connection", "Error in convertStreamToString", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e("Network_Connection", "Error in convertStreamToString", e);
            }
        }
        return response.toString();
    }




}
