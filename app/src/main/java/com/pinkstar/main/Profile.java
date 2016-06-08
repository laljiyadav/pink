package com.pinkstar.main;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Profile extends Activity implements View.OnClickListener {

    ToggleButton toggleButton;
    TextView txt_gender, txt_birth, txt_annversary, txt_fullaname, txt_save;
    ImageView profileimage;
    private DatePickerDialog fromDatePickerDialog, fromDatePickerDialog1;
    private SimpleDateFormat dateFormatter;
    int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Bitmap bitmap;
    Uri fileUri;
    String encodedSting, udata, url = Apis.Base;
    JSONObject json;
    String email, mobile, dob, annversary, gender, name;
    EditText et_number, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        txt_gender = (TextView) findViewById(R.id.txt_gender);
        txt_annversary = (TextView) findViewById(R.id.txt_anneversary);
        txt_fullaname = (TextView) findViewById(R.id.txt_fullname);
        txt_save = (TextView) findViewById(R.id.txt_save);
        profileimage = (ImageView) findViewById(R.id.img);
        txt_birth = (TextView) findViewById(R.id.txt_birth);
        et_number = (EditText) findViewById(R.id.ed_number);
        et_email = (EditText) findViewById(R.id.ed_email);

        txt_fullaname.setText(SaveSharedPreference.getUserName(Profile.this));
        txt_birth.setText(SaveSharedPreference.getBirth(Profile.this));
        txt_annversary.setText(SaveSharedPreference.getAnnversary(Profile.this));
        et_number.setText(SaveSharedPreference.getMobile(Profile.this).replace("+91-",""));
        et_email.setText(SaveSharedPreference.getUserEMAIL(Profile.this));
        txt_gender.setText(SaveSharedPreference.getUserIMAGE(Profile.this));

        if (SaveSharedPreference.getGender(Profile.this).equalsIgnoreCase("Female")) {
            toggleButton.setChecked(false);
        } else {
            toggleButton.setChecked(true);
        }

        txt_birth.setOnClickListener(this);
        txt_save.setOnClickListener(this);
        txt_annversary.setOnClickListener(this);
        profileimage.setOnClickListener(this);
        setDateTimeField();
        setDateTimeField1();


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

        } else {
            new ImageDownloader().execute(SaveSharedPreference.getUserIMAGE(Profile.this));
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
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i.createChooser(i, "Select Picture"), CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }

        if (v == txt_save) {
            if (valid()) {
                new AttempProfile().execute();
            }

        }


    }

    public boolean valid() {
        email = et_email.getText().toString();
        mobile = et_number.getText().toString();
        dob = txt_birth.getText().toString();
        annversary = txt_annversary.getText().toString();
        gender = txt_gender.getText().toString();
        name = txt_fullaname.getText().toString().replace(" ", "");

        if (email.equals("")) {
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
        } else if (annversary.equals("")) {
            Dialogs.showCenterToast(Profile.this, "Select annversary date");
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


            StringBuilder strBuilder = new StringBuilder(url);
            strBuilder.append("first_name=" + name);
            strBuilder.append("&last_name=" + "");
            strBuilder.append("&dob=" + dob);
            strBuilder.append("&gender=" + gender);
            strBuilder.append("&email=" + email);
            strBuilder.append("&anniversary=" + annversary);
            strBuilder.append("&image_name=" + "");
            strBuilder.append("&image_text=" + encodedSting);
            strBuilder.append("&rquest=update_profile_details");
            strBuilder.append("&mobile=" + SaveSharedPreference.getMobile(Profile.this));
            strBuilder.append("&token_id=" + SaveSharedPreference.getUserID(Profile.this));

            Log.e("Log_tag", "" + strBuilder.toString());

            // Create an array
            Parser perser = new Parser();
            json = perser.getJSONFromUrl1(strBuilder.toString());
            Log.e("Log_tag", "" + json);
            try {

                udata = json.getString("udata");


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

                } else if (udata.equals("0")) {
                    Dialogs.showCenterToast(Profile.this, "Profile is not updated");

                } else {
                    Dialogs.showCenterToast(Profile.this, "Server Error");
                }
            } catch (Exception e) {

            }


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                fileUri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    profileimage.setImageBitmap(bitmap);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                    byte[] data1 = bos.toByteArray();


                    encodedSting = Base64.encodeToString(data1, Base64.DEFAULT);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... param) {
            // TODO Auto-generated method stub
            return downloadBitmap(param[0]);
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("Async-Example", "onPostExecute Called");
            //drawer_image.setImageBitmap(result);

            profileimage.setImageBitmap(result);
        }

        private Bitmap downloadBitmap(String url) {
            // initilize the default HTTP client object
            final DefaultHttpClient client = new DefaultHttpClient();
            //forming a HttoGet request
            final HttpGet getRequest = new HttpGet(url);
            try {
                HttpResponse response = client.execute(getRequest);
                //check 200 OK for success
                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Log.w("ImageDownloader", "Error " + statusCode +
                            " while retrieving bitmap from " + url);
                    return null;
                }
                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        // getting contents from the stream
                        inputStream = entity.getContent();
                        // decoding stream data back into image Bitmap that android understands
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        return bitmap;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {
                // You Could provide a more explicit error message for IOException
                getRequest.abort();
                Log.e("ImageDownloader", "Something went wrong while" +
                        " retrieving bitmap from " + url + e.toString());
            }
            return null;
        }
    }
}
