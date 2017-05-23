package com.pinkstar.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pinkstar.main.Browser_plas;
import com.pinkstar.main.Operater;
import com.pinkstar.main.R;
import com.pinkstar.main.adapter.CityAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class OneFragment extends Fragment implements View.OnClickListener {

    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout mob_plan;
    ImageView mob_contact;
    public static EditText mob_mobile, mob_amount;
    public static TextView operatos;
    Button btn_proceed;
    private static final int REQUEST_CODE = 1;
    String type = "prepaid", operator_name_code = "", request = "recharge_prepaid", num;
    String url = Apis.Base, amount, mobile, opera;
    public static String operator_code = "", circle_code = "";
    JSONObject json,jsonObject;


    public OneFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.mob_radioGroup);
        mob_plan = (LinearLayout) view.findViewById(R.id.mob_plan);
        mob_contact = (ImageView) view.findViewById(R.id.mob_contact);
        mob_mobile = (EditText) view.findViewById(R.id.mob_mobile);
        operatos = (TextView) view.findViewById(R.id.mob_operater);
        mob_amount = (EditText) view.findViewById(R.id.mob_amount);
        btn_proceed = (Button) view.findViewById(R.id.mob_proceed);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) view.findViewById(checkedId);
                if (radioButton.getText().toString().equals("Postpaid")) {
                    mob_plan.setVisibility(View.INVISIBLE);
                    type = "postpaid";
                    request = "recharge_postpaid";
                } else {
                    mob_plan.setVisibility(View.VISIBLE);
                    type = "prepaid";
                    request = "recharge_prepaid";
                }

            }
        });

        mob_plan.setOnClickListener(this);
        mob_contact.setOnClickListener(this);
        operatos.setOnClickListener(this);
        btn_proceed.setOnClickListener(this);



        mob_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mob_mobile.getText().toString().length() == 4) {
                    num = mob_mobile.getText().toString();
                    new AttempCircle().execute();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public static void setAmount(String amount) {

        mob_amount.setText(amount);

    }


    public static void setText(String opera, String circle) {

        String operator_name = "";
        String circle_name = "";
        circle_code = circle;
        operator_code = opera;

        try {
            JSONObject object = new JSONObject(Apis.Oprator);
            JSONArray json = object.getJSONArray("Operator");

            JSONArray jsonArray = object.getJSONArray("Circle");


            for (int i = 0; i < jsonArray.length(); i++) {

                if (jsonArray.getJSONObject(i).getString("Code").equals(circle)) {
                    circle_name = jsonArray.getJSONObject(i).getString("Operator");
                    break;
                }


            }

            for (int i = 0; i < json.length(); i++) {

                if (json.getJSONObject(i).getString("Code").equals(opera)) {
                    operator_name = json.getJSONObject(i).getString("Operator");
                    break;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exp", e.toString());
        }


        operatos.setText(operator_name + "-" + circle_name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Toast.makeText(getActivity(), "Fragment Got it: " + requestCode + ", " + resultCode, Toast.LENGTH_SHORT).show();
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = intent.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex).replaceAll("[ +]", "").replace("-", "");

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                Log.e("log", "ZZZ number : " + number + " , name : " + name);

                mob_mobile.setText("+91 " + number.substring(number.length() - 10).toString());

            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == mob_contact) {
            Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(pickContactIntent, REQUEST_CODE);

        } else if (v == operatos) {
            Intent in = new Intent(getActivity(), Operater.class);
            in.putExtra("type", "postpaid");
            startActivity(in);

        } else if (v == mob_plan) {

            if (operatos.getText().toString().equals("")) {
                Dialogs.showCenterToast(getActivity(), "Select Operator");
            } else {
                Intent in = new Intent(getActivity(), Browser_plas.class);
                in.putExtra("circle", circle_code);
                in.putExtra("operator", operator_code);
                startActivity(in);
            }

        } else if (v == btn_proceed) {
            valid();
        }

    }

    public void valid() {
        mobile = mob_mobile.getText().toString();
        amount = mob_amount.getText().toString();
        opera = operatos.getText().toString();

        int str = 0;
        int reablestar = Integer.parseInt(SaveSharedPreference.getBalStar(getActivity()));

        if (mobile.equals("")) {

        } else {
            str = Integer.parseInt(amount);
        }

        if (mobile.equals("")) {
            Dialogs.showCenterToast(getActivity(), "Enter Mobile Number");
        } else if (opera.equals("")) {
            Dialogs.showCenterToast(getActivity(), "Select Operator");
        } else if (amount.equals("")) {
            Dialogs.showCenterToast(getActivity(), "Enter Amount");
        } else if (mobile.length() < 10) {
            Dialogs.showCenterToast(getActivity(), "Enter Valid Mobile Number");
        } else if (str >= reablestar) {
            Dialogs.showCenterToast(getActivity(), "Enter Valid Amount");
        } else {
            if (mobile.length() == 10) {
                mobile = "+91-" + mobile;
            }
            new AttempPrepaid().execute();
        }
    }

    private class AttempCircle extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Dialogs.showProDialog(getActivity(), "Wait...");

        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> builder = new ArrayList<NameValuePair>();
            builder.add(new BasicNameValuePair("rquest", "areaOperatorCircleCode"));
            builder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(getActivity())));
            builder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(getActivity())));
            builder.add(new BasicNameValuePair("recharge_number", num));
            builder.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, builder);
                Log.e("Json_list", "" + json);


            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml

            // Dialogs.dismissDialog();

            try {

                JSONObject jsonObject = new JSONObject(json.getString("result"));

                setText(jsonObject.getString("operator_code"), jsonObject.getString("circle_code"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class AttempPrepaid extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialogs.showProDialog(getActivity(), "Wait...");

        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> builder = new ArrayList<NameValuePair>();
            builder.add(new BasicNameValuePair("rquest", "newRecharge"));
            builder.add(new BasicNameValuePair("amount", amount));
            builder.add(new BasicNameValuePair("operator", operator_code));
            builder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(getActivity())));
            builder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(getActivity())));
            builder.add(new BasicNameValuePair("recharge_number", mobile));
            builder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            builder.add(new BasicNameValuePair("type", type));

            try {
                Parser perser = new Parser();
                jsonObject = perser.getJSONFromUrl(url, builder);
                Log.e("Json_list", "" + url + builder.toString());
                Log.e("jsonobj", "" + jsonObject);


            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            Dialogs.dismissDialog();

            try {

                if(jsonObject.getJSONObject("uData").equals("1")) {

                    String result = jsonObject.getString("result");
                    JSONObject object = new JSONObject(result);
                    String error = object.getString("message").replace("\r", "").replace(" ", "");
                    Log.e("error", error);
                    String msg = "Recharge successfully";

                   /* JSONArray jso_error = new JSONArray(Apis.json_error);

                    for (int i = 0; i < jso_error.length(); i++) {

                        if (jso_error.getJSONObject(i).getString("Code").replace(" ", "").equals(error)) {
                            msg = jso_error.getJSONObject(i).getString("Message");
                            Log.e("errormsg", msg);
                            break;
                        }


                    }*/
                    Dialogs.showCenterToast(getActivity(), msg);
                }
                else if(jsonObject.getJSONObject("uData").equals("2")) {
                    String result = jsonObject.getString("result");
                    JSONObject object = new JSONObject(result);
                    String error = object.getString("message").replace("\r", "").replace(" ", "");
                    Log.e("error", error);
                    String msg = "Recharge successfully";

                   /* JSONArray jso_error = new JSONArray(Apis.json_error);

                    for (int i = 0; i < jso_error.length(); i++) {

                        if (jso_error.getJSONObject(i).getString("Code").replace(" ", "").equals(error)) {
                            msg = jso_error.getJSONObject(i).getString("Message");
                            Log.e("errormsg", msg);
                            break;
                        }


                    }*/
                    Dialogs.showCenterToast(getActivity(), msg);
                }
                else if(jsonObject.getJSONObject("uData").equals("-1")) {
                    String result = jsonObject.getString("result");
                    JSONObject object = new JSONObject(result);
                    String error = object.getString("message").replace("\r", "").replace(" ", "");
                    Log.e("error", error);
                    String msg = "Recharge successfully";


                    Dialogs.showCenterToast(getActivity(), error);
                }
                else {

                    String result = jsonObject.getString("result");
                    JSONObject object = new JSONObject(result);
                    String error = object.getString("message").replace("\r", "").replace(" ", "");
                    Log.e("error", error);
                    String msg = "Recharge successfully";

                    JSONArray jso_error = new JSONArray(Apis.json_error);

                    for (int i = 0; i < jso_error.length(); i++) {

                        if (jso_error.getJSONObject(i).getString("Code").replace(" ", "").equals(error)) {
                            msg = jso_error.getJSONObject(i).getString("Message");
                            Log.e("errormsg", msg);
                            break;
                        }


                    }
                    Dialogs.showCenterToast(getActivity(), msg);

                }




            } catch (Exception e) {
                e.printStackTrace();
                Log.e("exp", ""+e.toString());
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}