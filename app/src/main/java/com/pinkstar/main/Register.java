package com.pinkstar.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.InternetStatus;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class Register extends Activity {
    private EditText et_email, et_password, et_name, et_phone, et_confrm_password;
    private Button btn_login, btn_fb;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String str_email, str_password, str_name, url = Apis.Base, udata, status, type = "android";
    JSONObject json;
    String name[];
    private CallbackManager callbackManager;
    int lenth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btnRegister);
        btn_fb = (Button) findViewById(R.id.btn_fb);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FacebookSdk.sdkInitialize(Register.this);
        callbackManager = CallbackManager.Factory.create();


        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                facaBook();
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_email = et_email.getText().toString();
                str_password = et_password.getText().toString();
                str_name = et_name.getText().toString();

                name = str_name.split(" ");
                lenth = name.length;

                if (str_name.equals("")) {
                    et_name.setError("Enter Name");
                } else if (str_email.equals("")) {
                    et_email.setError("Enter Email");
                } else if (!str_email.matches(EMAIL_PATTERN)) {
                    et_email.setError("Enter Valid Email");
                } else if (str_password.equals("")) {
                    et_password.setError("Enter Password");
                } else {
                    new AttempLogin().execute();
                }
            }
        });


    }


    private class AttempLogin extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Register.this, "Sign Up...");
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
                if (str_name.contains(" ")) {
                    strBuilder.add(new BasicNameValuePair("fname", str_name.replace(name[lenth - 1], "")));
                    strBuilder.add(new BasicNameValuePair("lname", name[lenth - 1]));
                } else {
                    strBuilder.add(new BasicNameValuePair("fname", str_name));
                    strBuilder.add(new BasicNameValuePair("lname", ""));
                }

                strBuilder.add(new BasicNameValuePair("email", str_email));
                strBuilder.add(new BasicNameValuePair("password", str_password));
                strBuilder.add(new BasicNameValuePair("rquest", "newRegistration"));
                strBuilder.add(new BasicNameValuePair("regtype", type));
                strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Register.this)));
                strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));

                Log.e("Log_tag", "" + strBuilder.toString());

                // Create an array
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, strBuilder);
                Log.e("Log_tag", "" + json);


                udata = json.getString("uData");


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();


            if (udata.equals("1")) {
                startActivity(new Intent(Register.this, Refferal.class));
                try {
                    SaveSharedPreference.setUserID(Register.this, json.getJSONObject("result").getString("client_id"));
                    SaveSharedPreference.setUSERAuth(Register.this, json.getJSONObject("result").getString("token_id"));
                    SaveSharedPreference.setBalStar(Register.this, json.getJSONObject("star").getString("redeemable_star"));
                    SaveSharedPreference.setTotal(Register.this, json.getJSONObject("star").getString("balance_star"));
                } catch (Exception e) {

                }
            } else {
                Dialogs.showDialog(Register.this, "Server Failed");
            }


        }
    }

    @Override
    public void onBackPressed() {

    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.pinkstar.main",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public void facaBook() {


        if (InternetStatus.isConnectingToInternet(Register.this)) {
            LoginManager.getInstance().logInWithReadPermissions(Register.this, Arrays.asList("public_profile", "user_friends", "email"));
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.e("access", "" + AccessToken.getCurrentAccessToken().getToken().toString());
                    RequestData1();
                }

                @Override
                public void onCancel() {
                    //Toast.makeText(Login.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                    System.out.println("Facebook Login failed!!");
                }

                @Override
                public void onError(FacebookException exception) {
                    // Toast.makeText(Login.this, "Facebook Login failed!!", Toast.LENGTH_LONG).show();
                    System.out.println("Facebook Login failed!!");
                    System.out.println("Facebook Login " + exception.toString());
                    if (exception instanceof FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                    }
                }
            });

        } else {
            Dialogs.showCenterToast(Register.this, "No Internet Connection");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void RequestData1() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                Log.e("Log_json", "" + json);

                try {
                    if (json != null) {
                        et_name.setText(json.getString("first_name") + " " + json.getString("last_name"));
                        if (json.has("email")) {
                            et_email.setText(json.getString("email"));
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();


    }
}
