package com.pinkstar.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ThreeFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout data_plan;
    ImageView data_contact;
    EditText data_mobile;
    TextView operator, data_amount;
    private static final int REQUEST_CODE = 1;
    String type = "prepaid", operator_code = "", circle_code = "", operator_name_code = "";
    Button date_pro;
    String amount, mobile, opeator_name,url=Apis.Base;
    JSONObject json;


    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_three, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.data_radioGroup);
        data_plan = (LinearLayout) view.findViewById(R.id.data_plan);
        data_contact = (ImageView) view.findViewById(R.id.data_contact);
        data_mobile = (EditText) view.findViewById(R.id.data_mobile);
        operator = (TextView) view.findViewById(R.id.data_operater);
        data_amount = (TextView) view.findViewById(R.id.data_amount);
        date_pro = (Button) view.findViewById(R.id.data_proceed);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                radioButton = (RadioButton) view.findViewById(checkedId);
                //Toast.makeText(getActivity(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                if (type.equals("postpaid")) {
                    data_plan.setVisibility(View.INVISIBLE);
                    type = "postpaid";
                } else {
                    data_plan.setVisibility(View.VISIBLE);
                    type = "prepaid";
                }

            }
        });

        data_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContactIntent, REQUEST_CODE);
            }
        });

        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Operater.class);
                in.putExtra("type", "datacard");
                startActivity(in);
            }
        });

        /*data_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Browser_plas.class);
                if(circle_code.equals("")) {
                    Dialogs.showCenterToast(getActivity(),"Select Operator");
                }
                else {
                    in.putExtra("circle", circle_code);
                    in.putExtra("operator", operator_code);
                    startActivity(in);
                }
            }
        });*/

        date_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = data_mobile.getText().toString();
                amount = data_amount.getText().toString();
                opeator_name = operator.getText().toString();

                int str = 0;
                int reablestar = Integer.parseInt(SaveSharedPreference.getBalStar(getActivity()));

                if (mobile.equals("")) {

                } else {
                    str = Integer.parseInt(amount);
                }

                if (mobile.equals("")) {
                    data_mobile.setError("Enter Datacard Number");

                } else if (amount.equals("")) {
                    Dialogs.showCenterToast(getActivity(), "Enter Amount");

                } else if (opeator_name.equals("")) {
                    Dialogs.showCenterToast(getActivity(), "Select Operator");
                } else if (str >= reablestar) {
                    Dialogs.showCenterToast(getActivity(), "Enter Valid Amount");
                } else {

                    new AttempDth().execute();

                }

            }
        });

        return view;
    }


    private class AttempDth extends AsyncTask<Void, Integer, String> {

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
            builder.add(new BasicNameValuePair("operator", operator_name_code));
            builder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(getActivity())));
            builder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(getActivity())));
            builder.add(new BasicNameValuePair("recharge_number", mobile));
            builder.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            builder.add(new BasicNameValuePair("type", type));

            try {
                Parser perser = new Parser();
                json = perser.getJSONFromUrl(url, builder);
                Log.e("Json_list", "" + url + builder.toString());


            } catch (Exception e) {

            }


            return null;

        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the listview in listview_main.xml
            Dialogs.dismissDialog();
            try {
                if (json.getString("uData").equals("1")) {

                    Dialogs.showCenterToast(getActivity(), "Recharge Successfully");

                } else {
                    Dialogs.showCenterToast(getActivity(), "Server Error");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Toast.makeText(getActivity(), "Fragment Got it: " + requestCode + ", " + resultCode, Toast.LENGTH_SHORT).show();
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

                data_mobile.setText("+91 " + number.substring(number.length() - 10).toString());

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("pause", "resume3");

        if (Operater.type.equals("datacard")) {
            operator.setText(Operater.operator_name1);
            operator_code = Operater.operator_code1;
            circle_code = Operater.circle_code1;
            operator_name_code = Operater.operator_name_code1;
            data_amount.setText(TopUp.amount1);
        }
    }

}