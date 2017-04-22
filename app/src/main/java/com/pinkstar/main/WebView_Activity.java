package com.pinkstar.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sudhir on 2/6/2017.
 */

public class WebView_Activity extends Activity {

    WebView webView;
    WebViewClient client;
    static Boolean status = false;
    String star;
    ProgressDialog progressDialog;
    JSONObject jsonObject;
    String udata, url = Apis.Base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_);
        star = getIntent().getExtras().getString("star");

        new AttempUrl().execute();



    }

    private class AttempUrl extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(WebView_Activity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(WebView_Activity.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "addStar"));
            strBuilder.add(new BasicNameValuePair("from", "app"));
            strBuilder.add(new BasicNameValuePair("star", star));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            strBuilder.add(new BasicNameValuePair("token_id", "" + SaveSharedPreference.getUSERAuth(WebView_Activity.this)));


            try {

                Parser perser = new Parser();
                jsonObject = perser.getJSONFromUrl(url, strBuilder);
                Log.e("Log_tag", "" + strBuilder.toString());
                Log.e("Log_tag", "" + jsonObject);

                udata = jsonObject.getString("udata");


                if (udata.equals("1")) {


                }

            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();

            if (udata.equals("1")) {

                try {
                    setWebView(jsonObject.getJSONObject("result").getString("url"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }

    public void setWebView(String url) {
        webView = (WebView) findViewById(R.id.web_view);


        progressDialog = new ProgressDialog(WebView_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        String postData = "&mobile=" + SaveSharedPreference.getMobile(WebView_Activity.this);


        Log.e("login", postData);
        webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
    }


    @Override
    public void onBackPressed() {
        alertDialog(WebView_Activity.this, "Are you sure you want to quit transaction?");

    }


    public void alertDialog(Context context, String txt_val) {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog2.setContentView(R.layout.dialog_web);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;
        TextView txthead = (TextView) dialog2.findViewById(R.id.txthead);
        Button btn = (Button) dialog2.findViewById(R.id.btnok);
        Button btncancel = (Button) dialog2.findViewById(R.id.btncancel);
        txthead.setText(txt_val);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog2.show();
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //String url1 = "http://pinkstarapp.com/api_old/thankyou.php?udata=2&message=Cancel&tid=105162185589";
            String first[] = url.split("\\?");
            String second[] = first[1].split("&");
            String third[] = second[1].split("=");
            String fouth[] = second[0].split("=");
            String fifth[] = second[2].split("=");

            if(fouth[1].equals("1"))
            {
                Dialogs.showDialog(WebView_Activity.this,"Your Transection id is "+fifth[1]);
            }

            Log.e("sencon", third[1]);
            Log.e("sencon", fouth[1]);
            Log.e("sencon", fifth[1]);

            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }
    }


}
