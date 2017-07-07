package com.pinkstar.main;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.data.Apis;
import com.pinkstar.main.data.Dialogs;
import com.pinkstar.main.data.GPSTracker;
import com.pinkstar.main.data.Parser;
import com.pinkstar.main.data.SaveSharedPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    TextView testView;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    String url = Apis.Base, udata;
    JSONObject json;
    PictureCallback jpegCallback;
    GPSTracker gpsTracker;
    Location location;
    String id, amount, type, print;
    LinearLayout lin_print, lin_unprint;
    ImageView stamp, image;
    Animation a, a1;
    TextView gotit;
    ImageView imageView;
    


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        lin_print = (LinearLayout) findViewById(R.id.print);
        lin_unprint = (LinearLayout) findViewById(R.id.unprint);
        imageView = (ImageView) findViewById(R.id.ok);
        gotit = (TextView) findViewById(R.id.gotit);
        surfaceHolder = surfaceView.getHolder();
        gpsTracker = new GPSTracker(CameraActivity.this);
        location = gpsTracker.getLocation();

        id = getIntent().getExtras().getString("id");
        amount = getIntent().getExtras().getString("amount");
        type = getIntent().getExtras().getString("type");
        print = getIntent().getExtras().getString("print");

        a = AnimationUtils.loadAnimation(this, R.anim.anim);
        a1 = AnimationUtils.loadAnimation(this, R.anim.down);

        if (print.equalsIgnoreCase("print")) {
            lin_print.setVisibility(View.VISIBLE);
            lin_unprint.setVisibility(View.GONE);
        } else {
            lin_print.setVisibility(View.GONE);
            lin_unprint.setVisibility(View.VISIBLE);

            stamp = (ImageView) findViewById(R.id.imageView3);
            image = (ImageView) findViewById(R.id.image);
            stamp.setVisibility(View.VISIBLE);
            stamp.startAnimation(a);
        }
        imageView.setEnabled(false);

        gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_print.setVisibility(View.GONE);
                lin_unprint.setVisibility(View.GONE);
                imageView.setEnabled(true);
                gotit.setVisibility(View.GONE);

            }
        });


        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                stamp.startAnimation(a1);
                image.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        a1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                stamp.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        jpegCallback = new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                try {
                    outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                    outStream.write(data);
                    outStream.close();
                    Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 75, bos);
                byte[] data1 = bos.toByteArray();


                HomeDetail.encodeString = Base64.encodeToString(data1, Base64.DEFAULT);
                location = gpsTracker.getLocation();
                new AttempBillUpload().execute();

                //Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();
                refreshCamera();
            }
        };
    }

    public void captureImage(View v) throws IOException {
        //take the picture
        camera.takePicture(null, null, jpegCallback);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        refreshCamera();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // open the camera
            camera = Camera.open();
        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.set("orientation", "portrait");
        parameters.setRotation(90);
        camera.setParameters(parameters);

        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // stop preview and release camera
        camera.stopPreview();
        camera.release();
        camera = null;
    }


    private class AttempBillUpload extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Dialogs.showProDialog(CameraActivity.this, "Wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("rquest", "billUpload"));
            nameValuePairs.add(new BasicNameValuePair("mobile", SaveSharedPreference.getMobile(CameraActivity.this)));
            nameValuePairs.add(new BasicNameValuePair("api_token", Apis.Api_Token));
            nameValuePairs.add(new BasicNameValuePair("lat", "" + location.getLatitude()));
            nameValuePairs.add(new BasicNameValuePair("long", "" + location.getLongitude()));
            nameValuePairs.add(new BasicNameValuePair("vendor_id", id));
            nameValuePairs.add(new BasicNameValuePair("token_id", SaveSharedPreference.getUSERAuth(CameraActivity.this)));
            nameValuePairs.add(new BasicNameValuePair("star_type", type));
            nameValuePairs.add(new BasicNameValuePair("discount_amount", amount));
            nameValuePairs.add(new BasicNameValuePair("image_url", HomeDetail.encodeString));

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
                    Dialogs.showCenterToast(CameraActivity.this, "Bill Upload successfully");
                    finish();
                } else if (udata.equals("25")) {
                    Dialogs.showCenterToast(CameraActivity.this, "You can not upload ");
                    finish();
                } else if (udata.equals("26")) {
                    Dialogs.showCenterToast(CameraActivity.this, "You can not upload ");
                    finish();
                } else {
                    Dialogs.showCenterToast(CameraActivity.this, json.getJSONObject("result").getString("message"));
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
