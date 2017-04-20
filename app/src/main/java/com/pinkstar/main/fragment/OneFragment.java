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


public class OneFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout mob_plan;
    ImageView mob_contact;
    EditText mob_mobile, mob_amount;
    TextView operatos;
    Button btn_proceed;
    private static final int REQUEST_CODE = 1;
    String operator_code = "", circle_code = "", type = "prepaid", operator_name_code = "", request = "recharge_prepaid";
    String url = Apis.Base, amount, mobile, opera;
    JSONObject json;


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
                // checkedId is the RadioButton selected
                radioButton = (RadioButton) view.findViewById(checkedId);
                //Toast.makeText(getActivity(), radioButton.getText(), Toast.LENGTH_SHORT).show();
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

        mob_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContactIntent, REQUEST_CODE);
            }
        });

        operatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Operater.class);
                if (type.equals("postpaid")) {
                    in.putExtra("type", "postpaid");
                } else {
                    in.putExtra("type", "prepaid");
                }
                startActivity(in);
            }
        });

        mob_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Browser_plas.class);
                if (circle_code.equals("")) {
                    Dialogs.showCenterToast(getActivity(), "Select Operator");
                } else {
                    in.putExtra("circle", circle_code);
                    in.putExtra("operator", operator_code);
                    startActivity(in);
                }
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    mob_mobile.setError("Enter Mobile Number");
                } else if (opera.equals("")) {
                    Dialogs.showCenterToast(getActivity(), "Select Operator");
                } else if (amount.equals("")) {
                    mob_amount.setError("Enter Amount");
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
        });

        return view;
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
    public void onResume() {
        super.onResume();

        if (Operater.type.equals("prepaid") || Operater.type.equals("postpaid")) {
            operatos.setText(Operater.operator_name);
            operator_code = Operater.operator_code;
            circle_code = Operater.circle_code;
            operator_name_code = Operater.operator_name_code;
            Log.e("pause", "resume11" + operator_name_code);
            Log.e("pause", "resume11" + operator_code);
            Log.e("pause", "resume11" + circle_code);
            mob_amount.setText(TopUp.amount);
        }
    }
}