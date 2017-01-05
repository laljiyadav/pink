package com.pinkstar.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

public class MyWallet extends Activity implements View.OnClickListener {

    TextView txt_total, txt_read, txt_name;
    ImageView star_img, show_img;
    ProgressBar progress;
    Button btn_add;
    EditText ed_stae;
    String get_star;
    LinearLayout lin_add;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        inIt();
    }

    public void inIt() {
        star_img = (ImageView) findViewById(R.id.star_img);
        show_img = (ImageView) findViewById(R.id.img);
        txt_total = (TextView) findViewById(R.id.txt_total);
        txt_read = (TextView) findViewById(R.id.txt_read);
        txt_name = (TextView) findViewById(R.id.name);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        btn_add = (Button) findViewById(R.id.btn_add);
        ed_stae = (EditText) findViewById(R.id.ed_star);
        lin_add = (LinearLayout) findViewById(R.id.lin_add);

        txt_total.setText("" + (Integer.parseInt(SaveSharedPreference.getTotal(MyWallet.this))));
        txt_read.setText(SaveSharedPreference.getBalStar(MyWallet.this));

        txt_name.setText(SaveSharedPreference.getUserName(MyWallet.this) + " " + SaveSharedPreference.getLastName(MyWallet.this));

        btn_add.setOnClickListener(this);

        star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.star_dialog(MyWallet.this);
            }
        });

        Dialogs.Touch(MyWallet.this, star_img);

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

    @Override
    public void onClick(View v) {

        if (v == btn_add) {
            if (flag) {
                lin_add.setVisibility(View.VISIBLE);
                flag = false;
            } else {

                if (valid()) {

                }

            }
        }

    }

    public boolean valid() {

        get_star = ed_stae.getText().toString();
        if (get_star.equals("")) {
            ed_stae.setError("Enter Star");
            return false;
        } else {
            return true;

        }
    }
}
