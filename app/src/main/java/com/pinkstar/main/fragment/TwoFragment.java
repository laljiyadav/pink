package com.pinkstar.main.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


public class TwoFragment extends Fragment {

    EditText et_amount, et_smart;
    TextView operator;
    String operator_code, circle_code, operator_name_code;
    Button btn_pro;
    String smart_no, opeator_name, amount, url = Apis.Base, type = "dth";
    JSONObject json;


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_two, container, false);

        et_amount = (EditText) view.findViewById(R.id.dth_amount);
        et_smart = (EditText) view.findViewById(R.id.dth_samart);
        operator = (TextView) view.findViewById(R.id.dth_oprater);
        btn_pro = (Button) view.findViewById(R.id.dth_proceed);

        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Operater.class);
                in.putExtra("type", "dth");
                startActivity(in);
            }
        });

        btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smart_no = et_smart.getText().toString();
                amount = et_amount.getText().toString();
                opeator_name = operator.getText().toString();

                int str = 0;
                int reablestar = Integer.parseInt(SaveSharedPreference.getBalStar(getActivity()));

                if (smart_no.equals("")) {

                } else {
                    str = Integer.parseInt(amount);
                }

                if (smart_no.equals("")) {
                    et_smart.setError("Enter Smartcard Number");

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
            builder.add(new BasicNameValuePair("recharge_number", smart_no));
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
    public void onResume() {
        super.onResume();
        Log.e("pause", "resume2");
        if (Operater.type.equals("dth")) {
            operator.setText(Operater.operator_name2);
            operator_code = Operater.operator_code2;
            circle_code = Operater.circle_code2;
            operator_name_code = Operater.operator_name_code2;
        }
    }

}