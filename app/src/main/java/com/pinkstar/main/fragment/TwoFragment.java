package com.pinkstar.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pinkstar.main.Operater;
import com.pinkstar.main.R;


public class TwoFragment extends Fragment{

    EditText et_amount,et_smart;
    TextView operator;
    String operator_code,circle_code;
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

        et_amount=(EditText)view.findViewById(R.id.dth_amount);
        et_smart=(EditText)view.findViewById(R.id.dth_samart);
        operator=(TextView)view.findViewById(R.id.dth_oprater);

        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Operater.class);
                in.putExtra("type", "dth");
                startActivity(in);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("pause", "resume2");
        if (Operater.type.equals("dth")) {
            operator.setText(Operater.operator_name2);
            operator_code = Operater.operator_code2;
            circle_code = Operater.circle_code2;
        }
    }

}