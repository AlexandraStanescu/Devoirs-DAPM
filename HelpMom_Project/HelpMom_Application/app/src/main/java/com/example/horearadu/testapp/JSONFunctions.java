package com.example.horearadu.testapp;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;

import org.apache.http.params.HttpParams;
import org.json.*;
import java.io.*;

import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

/**
 * Created by horearadu on 10/01/15.
 */
public class JSONFunctions {
    public static JSONObject getJSONfromURL(String url, List<NameValuePair> params) {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            httppost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

       } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
       }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {
            Log.i("bla", result);
            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }
}
