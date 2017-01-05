package com.pinkstar.main.data;


import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@SuppressLint("NewApi")
public class Parser {
    String result=null;
    InputStream is=null;
    JSONObject json_data;
    JSONArray json_data1;
    int res_code;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    // constructor
    public Parser() { }
    public JSONObject getJSONFromUrl(String url, ArrayList<NameValuePair> nameValuePairs) {
        // Making HTTP request
        StrictMode.setThreadPolicy(policy);

        //http post
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            Log.e("log_tag", "connection success ");

        }


        catch(Exception e)
        {
              Log.e("log_tag", "Error in http connection " + e.toString());
            e.printStackTrace();


        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");

            }
            is.close();

            result=sb.toString();
            //Log.e("log_tag", "result" + result);

        }
        catch(Exception e)
        {
             Log.e("log_tag", "Error converting result " + e.toString());
            e.printStackTrace();
        }


        try{
            json_data = new JSONObject(result);

        }

        catch(Exception e)
        {
              Log.e("log_tag", "Error parsing data " + e.toString());

        }
        return json_data;
    }

    public JSONObject getJSONFromUrl1(String url) {
        // Making HTTP request
        StrictMode.setThreadPolicy(policy);

        //http post
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            //Log.e("log_tag", "connection success ");

        }


        catch(Exception e)
        {
            //  Log.e("log_tag", "Error in http connection "+e.toString());


        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
                //Intent i = news Intent(getBaseContext(),Register2.class);
                // startActivity(i);
            }
            is.close();

            result=sb.toString();
            Log.e("log_tag", "result" + result);

        }
        catch(Exception e)
        {
            //  Log.e("log_tag", "Error converting result "+e.toString());
        }


        try{
            json_data = new JSONObject(result);

        }

        catch(JSONException e)
        {
            //  Log.e("log_tag", "Error parsing data "+e.toString());

        }
        return json_data;
    }
    public String getJSONFromUrl3(String url) {
        // Making HTTP request
        StrictMode.setThreadPolicy(policy);

        //http post
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            Log.e("log_tag", "connection success " + is);

        }


        catch(Exception e)
        {
             //Log.e("log_tag", "Error in http connection "+e.toString());


        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");

            }
            is.close();

            result=sb.toString();
           //Log.e("log_tag", "result1" + result);
           // Log.e("log_tag", "result" + result);

        }
        catch(Exception e)
        {
             Log.e("log_tag", "Error converting result " + e.toString());
        }



        return result;
    }
    public JSONObject getJSONFromUrl2(String url, ArrayList<NameValuePair> nameValuePairs) {
        // Making HTTP request
        StrictMode.setThreadPolicy(policy);

        //http post
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPatch httppost = new HttpPatch(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            //Log.e("log_tag", "connection success ");

        }


        catch(Exception e)
        {
            //  Log.e("log_tag", "Error in http connection "+e.toString());


        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
                //Intent i = news Intent(getBaseContext(),Register2.class);
                // startActivity(i);
            }
            is.close();

            result=sb.toString();
            Log.e("log_tag", "result" + result);

        }
        catch(Exception e)
        {
            //  Log.e("log_tag", "Error converting result "+e.toString());
        }


        try{
            json_data = new JSONObject(result);

        }

        catch(JSONException e)
        {
            //  Log.e("log_tag", "Error parsing data "+e.toString());

        }
        return json_data;
    }

    public JSONObject getJSONFromUrl3(String url, ArrayList<NameValuePair> nameValuePairs) {
        // Making HTTP request
        StrictMode.setThreadPolicy(policy);

        //http post
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            Log.e("log_tag", "connection success ");

        }


        catch(Exception e)
        {
            Log.e("log_tag", "Error in http connection " + e.toString());
            e.printStackTrace();


        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");

            }
            is.close();

            result=sb.toString();
            Log.e("log_tag", "result" + result);

        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error converting result " + e.toString());
            e.printStackTrace();
        }


        try{
            json_data = new JSONObject(result);

        }

        catch(Exception e)
        {
            Log.e("log_tag", "Error parsing data " + e.toString());

        }
        return json_data;
    }

    public JSONObject getJSONFromUrl3(String url, JSONObject obj) {
        // Making HTTP request
        StrictMode.setThreadPolicy(policy);

        //http post
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-type", "application/json");
            httppost.setEntity(new StringEntity(obj.toString()));
            HttpResponse response = httpclient.execute(httppost);
            res_code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            //Log.e("log_tag", "connection success " + res_code);

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
            e.printStackTrace();


        }
        //convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");

            }
            is.close();

            result = sb.toString();
            // Log.e("log_tag", "result" + result);

        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
            e.printStackTrace();
        }


        try {

            if (res_code == 200) {
                json_data = new JSONObject(result);
            } else if (res_code == 201) {
                json_data = new JSONObject(result);
            } else {
                json_data = new JSONObject();
                json_data.put("res", "" + res_code);
                json_data.put("result",result);
            }

        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data " + e.toString());

        }
        return json_data;
    }

}