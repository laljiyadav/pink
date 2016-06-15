package com.pinkstar.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pinkstar.main.Browser_plas;
import com.pinkstar.main.Operater;
import com.pinkstar.main.R;
import com.pinkstar.main.data.SaveSharedPreference;


public class OneFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout mob_plan;
    ImageView mob_contact;
    EditText mob_mobile;
    TextView operatos;
    private static final int REQUEST_CODE = 1;
    String operator_code, circle_code,type="prepaid";

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


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                radioButton = (RadioButton) view.findViewById(checkedId);
                //Toast.makeText(getActivity(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                if (radioButton.getText().toString().equals("Postpaid")) {
                    mob_plan.setVisibility(View.INVISIBLE);
                    type="postpaid";
                } else {
                    mob_plan.setVisibility(View.VISIBLE);
                    type="prepaid";
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
                }
                else {
                    in.putExtra("type", "prepaid");
                }
                startActivity(in);
            }
        });

        mob_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Browser_plas.class);
                startActivity(in);
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


    @Override
    public void onResume() {
        super.onResume();
        Log.e("pause", "resume1");
        if (Operater.type.equals("prepaid")||Operater.type.equals("postpaid")) {
            operatos.setText(Operater.operator_name);
            operator_code = Operater.operator_code;
            circle_code = Operater.circle_code;
        }
    }
}