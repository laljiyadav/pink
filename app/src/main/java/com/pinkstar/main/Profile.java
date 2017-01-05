package com.pinkstar.main;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Profile extends Activity implements View.OnClickListener {

    ToggleButton toggleButton;
    TextView txt_gender, txt_birth, txt_annversary, txt_save;
    ImageView profileimage;
    private DatePickerDialog fromDatePickerDialog, fromDatePickerDialog1;
    private SimpleDateFormat dateFormatter;
    int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Bitmap bitmap;
    Uri fileUri;
    String encodedSting, udata, udata1, url = Apis.Base;
    JSONObject json;
    String email, mobile, dob, annversary, gender, fisrt_name, last_name;
    EditText et_number, et_email, et_first, et_last;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

    }


    public void init() {

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        txt_gender = (TextView) findViewById(R.id.txt_gender);
        txt_annversary = (TextView) findViewById(R.id.txt_anneversary);
        txt_save = (TextView) findViewById(R.id.txt_save);
        profileimage = (ImageView) findViewById(R.id.img);
        txt_birth = (TextView) findViewById(R.id.txt_birth);
        et_number = (EditText) findViewById(R.id.ed_number);
        et_email = (EditText) findViewById(R.id.ed_email);
        et_first = (EditText) findViewById(R.id.ed_first);
        et_last = (EditText) findViewById(R.id.ed_last);
        progress = (ProgressBar) findViewById(R.id.progressBar);

        et_first.setText(SaveSharedPreference.getUserName(Profile.this));
        et_last.setText(SaveSharedPreference.getLastName(Profile.this));
        et_number.setText(SaveSharedPreference.getMobile(Profile.this).replace("+91-", ""));
        et_email.setText(SaveSharedPreference.getUserEMAIL(Profile.this));
        txt_gender.setText(SaveSharedPreference.getGender(Profile.this));

        txt_birth.setOnClickListener(this);
        txt_save.setOnClickListener(this);
        txt_annversary.setOnClickListener(this);
        profileimage.setOnClickListener(this);
        setDateTimeField();
        setDateTimeField1();

        if (SaveSharedPreference.getGender(Profile.this).equalsIgnoreCase("Female")) {
            toggleButton.setChecked(false);
            txt_gender.setText("Female");

        } else {
            toggleButton.setChecked(true);
            txt_gender.setText("Male");
        }

        if(!SaveSharedPreference.getBirth(Profile.this).equalsIgnoreCase("0000-00-00")) {
            txt_birth.setText(SaveSharedPreference.getBirth(Profile.this));
        }
        if(!SaveSharedPreference.getAnnversary(Profile.this).equalsIgnoreCase("0000-00-00")) {
            txt_annversary.setText(SaveSharedPreference.getAnnversary(Profile.this));
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_gender.setText("Male");
                } else {
                    txt_gender.setText("Female");
                }
            }
        });

        if (SaveSharedPreference.getUserIMAGE(Profile.this).equals("")) {

            progress.setVisibility(View.GONE);
        } else {

            Picasso.with(Profile.this)
                    .load(SaveSharedPreference.getUserIMAGE(Profile.this))
                    .into(profileimage, new com.squareup.picasso.Callback() {
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

    private void setDateTimeField() {

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                txt_birth.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void setDateTimeField1() {

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                txt_annversary.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View v) {
        if (v == txt_birth) {
            fromDatePickerDialog.show();
        }

        if (v == txt_annversary) {
            fromDatePickerDialog1.show();
        }

        if (v == profileimage) {
            PickImage();
        }

        if (v == txt_save) {
            if (valid()) {
                new AttempProfile().execute();
            }


        }


    }


    public void PickImage() {
        final Dialog dialog2 = new Dialog(Profile.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.camera_popup);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;

        TextView txtCamera = (TextView) dialog2.findViewById(R.id.camera);
        TextView txtGallery = (TextView) dialog2.findViewById(R.id.gallary);

        txtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 0);
                dialog2.dismiss();
            }
        });
        txtGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
                dialog2.dismiss();
            }
        });


        dialog2.show();
    }

    public boolean valid() {
        email = et_email.getText().toString();
        mobile = et_number.getText().toString();
        dob = txt_birth.getText().toString();
        annversary = txt_annversary.getText().toString();
        gender = txt_gender.getText().toString();
        fisrt_name = et_first.getText().toString();
        last_name = et_last.getText().toString();

        if (fisrt_name.equals("")) {
            Dialogs.showCenterToast(Profile.this, "Enter First Name");
            return false;
        } else if (last_name.equals("")) {
            Dialogs.showCenterToast(Profile.this, "Enter Last Name");
            return false;
        } else if (email.equals("")) {
            Dialogs.showCenterToast(Profile.this, "Enter Email");
            return false;
        } else if (!email.matches(EMAIL_PATTERN)) {
            Dialogs.showCenterToast(Profile.this, "Enter Valid Email");
            return false;
        } else if (mobile.equals("")) {
            Dialogs.showCenterToast(Profile.this, "Enter number");
            return false;
        } else if (dob.equals("")) {
            Dialogs.showCenterToast(Profile.this, "Select birth date");
            return false;
        } else if (mobile.length() < 10) {
            Dialogs.showCenterToast(Profile.this, "Select valid number");
            return false;
        } else if (mobile.length() == 10) {
            mobile = "+91-" + mobile;
            return true;
        }


        return true;
    }


    private class AttempProfile extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Profile.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("fname", fisrt_name));
            nameValuePairs.add(new BasicNameValuePair("lname", last_name));
            nameValuePairs.add(new BasicNameValuePair("dob", dob));
            nameValuePairs.add(new BasicNameValuePair("gender", gender));
            nameValuePairs.add(new BasicNameValuePair("anniversary", annversary));
            nameValuePairs.add(new BasicNameValuePair("rquest", "profileUpdate"));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Profile.this)));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Profile.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            Log.e("Log_tag", "" + nameValuePairs);

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, nameValuePairs);
            try {

                udata = json.getString("uData");


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();
            try {
                if (udata.equals("1")) {
                    Dialogs.showCenterToast(Profile.this, "Profile is successfully update");
                    new AttempProfileDetail().execute();

                } else if (udata.equals("0")) {
                    Dialogs.showCenterToast(Profile.this, "Profile is not updated");

                } else {
                    Dialogs.showCenterToast(Profile.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }

    private class AttempProfileImage extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Profile.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


            nameValuePairs.add(new BasicNameValuePair("image_url", encodedSting));
            nameValuePairs.add(new BasicNameValuePair("rquest", "profilePictureUpdate"));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Profile.this)));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Profile.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));

            Log.e("Log_tag", "" + nameValuePairs);

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, nameValuePairs);
            try {

                udata = json.getString("uData");


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();
            try {
                if (udata.equals("1")) {
                    Dialogs.showCenterToast(Profile.this, "Profile Image successfully update");
                    new AttempProfileDetail().execute();

                } else {
                    Dialogs.showCenterToast(Profile.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;

                        }


                    }

                    try {
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        profileimage.setImageBitmap(bitmap);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 75, bos);
                        byte[] data1 = bos.toByteArray();


                        encodedSting = Base64.encodeToString(data1, Base64.DEFAULT);
                        new AttempProfileImage().execute();
                    } catch (Exception e) {

                    }

                }

                break;
            case 1:
                fileUri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    profileimage.setImageBitmap(bitmap);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 75, bos);
                    byte[] data1 = bos.toByteArray();


                    encodedSting = Base64.encodeToString(data1, Base64.DEFAULT);
                    new AttempProfileImage().execute();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    private class AttempProfileDetail extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(Profile.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> strBuilder = new ArrayList<NameValuePair>();
            strBuilder.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(Profile.this)));
            strBuilder.add(new BasicNameValuePair("rquest", "profileDetail"));
            strBuilder.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(Profile.this)));
            strBuilder.add(new BasicNameValuePair("api_token", Apis.Api_Token));


            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl(url, strBuilder);

            Log.e("url", "" + strBuilder.toString());
            try {

                udata1 = json.getString("uData");


            } catch (Exception e) {
                Log.e("Log_Exception", e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            Dialogs.dismissDialog();

            try {
                if (udata1.equals("1")) {
                    SaveSharedPreference.setUserName(Profile.this, json.getJSONObject("result").getString("first_name"));
                    SaveSharedPreference.setLastName(Profile.this, json.getJSONObject("result").getString("last_name"));
                    SaveSharedPreference.setUserEMAIL(Profile.this, json.getJSONObject("result").getString("email"));
                    SaveSharedPreference.setBirth(Profile.this, json.getJSONObject("result").getString("dob"));
                    SaveSharedPreference.setAnnversary(Profile.this, json.getJSONObject("result").getString("anniversary"));
                    SaveSharedPreference.setUserIMAGE(Profile.this, json.getJSONObject("result").getString("image_url"));
                    SaveSharedPreference.setBalStar(Profile.this, json.getJSONObject("star").getString("redeemable_star"));
                    SaveSharedPreference.setTotal(Profile.this, json.getJSONObject("star").getString("balance_star"));
                    SaveSharedPreference.setGender(Profile.this, json.getJSONObject("result").getString("gender"));
                    init();

                }
            } catch (Exception e) {

            }

        }
    }


}
