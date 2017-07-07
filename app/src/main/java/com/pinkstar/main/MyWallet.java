package com.pinkstar.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pinkstar.main.adapter.StarOfferAdapter;
import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyWallet extends Activity implements View.OnClickListener {

    TextView txt_name, txt_total, txt_redeem;
    ImageView star_img, show_img;
    ProgressBar progress;
    Button btn_add;
    //static EditText ed_stae;
    String get_star;
    LinearLayout lin_add;
    boolean flag = true;
    JSONObject jsonObject;
    String udata, url = Apis.Base;
    ArrayList<HashMap<String, String>> offerList = new ArrayList<HashMap<String, String>>();
    CountDownTimer downTimer;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        inIt();
    }

    public void inIt() {
        star_img = (ImageView) findViewById(R.id.star_img);
        show_img = (ImageView) findViewById(R.id.img);
        txt_name = (TextView) findViewById(R.id.name);
        txt_redeem = (TextView) findViewById(R.id.txt_redeem);
        txt_total = (TextView) findViewById(R.id.txt_total);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        btn_add = (Button) findViewById(R.id.btn_add);


        txt_name.setText(SaveSharedPreference.getUserName(MyWallet.this) + " " + SaveSharedPreference.getLastName(MyWallet.this));
        txt_total.setText(getResources().getString(R.string.rs)+" "+SaveSharedPreference.getTotal(MyWallet.this)+"\n"+"Total Stars");
        txt_redeem.setText(getResources().getString(R.string.rs)+" "+SaveSharedPreference.getBalStar(MyWallet.this)+"\n"+"Redeemable Stras");


        btn_add.setOnClickListener(this);

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(MyWallet.this, true);
            }
        });

        Dialogs.Touch(MyWallet.this, star_img);


        downTimer = new CountDownTimer(86400, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                //int abc = Integer.parseInt(txt_read.getText().toString());

            }

            @Override
            public void onFinish() {

            }
        };
        downTimer.start();

        if (SaveSharedPreference.getUserIMAGE(MyWallet.this).equals("")) {
            progress.setVisibility(View.GONE);
        } else {
            Picasso.with(MyWallet.this)
                    .load(SaveSharedPreference.getUserIMAGE(MyWallet.this))
                    .into(show_img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (progress != null) {
                                progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {


                        }
                    });
        }

    }


    public void alertDialog() {


        final Dialog dialog2 = new Dialog(MyWallet.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.dialog_amount);
        dialog2.setCancelable(true);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;

        final EditText txt_stars=(EditText)dialog2.findViewById(R.id.ed_stars);
        Button brn_add=(Button)dialog2.findViewById(R.id.btn_add);


        brn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String star=txt_stars.getText().toString();
                if(star.trim().equals(""))
                {
                    txt_stars.setError("Enter Stars");
                }
                else {
                    startActivity(new Intent(MyWallet.this,WebView_Activity.class).putExtra("star",star));
                    dialog2.dismiss();
                }

            }
        });


        dialog2.show();

    }

        @Override
    public void onClick(View v) {

            if(v==btn_add)
            {
                alertDialog();
            }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        downTimer.cancel();
    }
}
