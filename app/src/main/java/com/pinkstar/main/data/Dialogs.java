package com.pinkstar.main.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pinkstar.main.Account;
import com.pinkstar.main.MainActivity;
import com.pinkstar.main.MyWallet;
import com.pinkstar.main.NearBy;
import com.pinkstar.main.Profile;
import com.pinkstar.main.R;
import com.pinkstar.main.Recharge;
import com.pinkstar.main.Search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sudhir on 4/12/2016.
 */
public class Dialogs {

    private static ProgressDialog progressDialog = null;
    private static AlertDialog alertDialog = null;
    private Activity activity;


    public static void showProDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    public static void Touch(Context context, final ImageView star_img) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels - 100;
        final int width = displaymetrics.widthPixels - 120;

        Log.e("height", "" + height);
        Log.e("width", "" + width);


        star_img.setOnTouchListener(new View.OnTouchListener() {
            PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
            PointF StartPT = new PointF(); // Record Start Position of 'img'

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eid = event.getAction();
                switch (eid) {
                    case MotionEvent.ACTION_MOVE:
                        PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                        star_img.setX((int) (StartPT.x + mv.x));
                        star_img.setY((int) (StartPT.y + mv.y));

                        // Log.e("x", "" + (int) (StartPT.x + mv.x));
                        // Log.e("y", "" + (int) (StartPT.y + mv.y));

                        if ((int) (StartPT.x + mv.x) < 5 && (int) (StartPT.y + mv.y) > height) {
                            star_img.setX(10);
                            star_img.setY(height);
                            StartPT = new PointF(10, height);
                        } else if ((int) (StartPT.x + mv.x) > width && (int) (StartPT.y + mv.y) > height) {
                            star_img.setX(width);
                            star_img.setY(height);
                            StartPT = new PointF(width, height);
                        } else if ((int) (StartPT.y + mv.y) < 5 && (int) (StartPT.x + mv.x) > width) {
                            star_img.setX(width);
                            star_img.setY(10);
                            StartPT = new PointF(width, 10);
                        } else if ((int) (StartPT.y + mv.y) < 5 && (int) (StartPT.x + mv.x) < 5) {
                            star_img.setX(10);
                            star_img.setY(10);
                            StartPT = new PointF(10, 10);
                        } else if ((int) (StartPT.x + mv.x) > width) {
                            star_img.setX(width);
                            star_img.setY((int) (StartPT.y + mv.y));
                            StartPT = new PointF(width, (int) (StartPT.y + mv.y));
                        } else if ((int) (StartPT.y + mv.y) > height) {
                            star_img.setX((int) (StartPT.x + mv.x));
                            star_img.setY(height);
                            StartPT = new PointF((int) (StartPT.x + mv.x), height);
                        } else if ((int) (StartPT.x + mv.x) < 5) {
                            star_img.setX(10);
                            star_img.setY((int) (StartPT.y + mv.y));
                            StartPT = new PointF(10, (int) (StartPT.y + mv.y));
                        } else if ((int) (StartPT.y + mv.y) < 5) {
                            star_img.setX((int) (StartPT.x + mv.x));
                            star_img.setY(10);
                            StartPT = new PointF((int) (StartPT.x + mv.x), 10);
                        } else {
                            StartPT = new PointF(star_img.getX(), star_img.getY());
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        DownPT.x = event.getX();
                        DownPT.y = event.getY();
                        StartPT = new PointF(star_img.getX(), star_img.getY());

                        break;


                    case MotionEvent.ACTION_UP:

                        break;

                    default:
                        break;
                }
                return false;
            }
        });

    }

    //dismiss the progress dialog
    public static void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static float distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return Float.parseFloat("" + dist);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public static void showDialog(Context context, String msg) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    public static void alertDialog(final Context context, String txt_val, String txt_val1, String btn, String btn1) {


        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#20000000")));
        dialog2.setContentView(R.layout.dialog_location);
        dialog2.setCancelable(false);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;


        TextView txt = (TextView) dialog2.findViewById(R.id.txt_text);
        TextView txt1 = (TextView) dialog2.findViewById(R.id.txt_text1);

        final Button dont = (Button) dialog2.findViewById(R.id.dont);
        final Button allow = (Button) dialog2.findViewById(R.id.allow);


        txt.setText(Html.fromHtml(txt_val));
        txt1.setText(txt_val1);
        dont.setText(btn);
        allow.setText(btn1);

        dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dont.getText().toString().equalsIgnoreCase("Don't Allow")) {


                } else {
                    dialog2.dismiss();

                }

            }
        });

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allow.getText().toString().equalsIgnoreCase("Allow")) {
                    GPSTracker gpsTracker = new GPSTracker(context);
                    Location location = gpsTracker.getLocation();
                    if (location == null) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                        dialog2.dismiss();
                    } else {
                        dialog2.dismiss();
                    }

                } else {
                    dialog2.dismiss();
                }
            }
        });


        dialog2.show();


    }

    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void hideSystemUI(View mDecorView, Context context) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView = new View(context);
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);// hide status bar
        //| View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    public void showSystemUI(View mDecorView) {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static void star_dialog(final Context context ,boolean check) {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50ffffff")));
        dialog2.setContentView(R.layout.star_dialog);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;

        RelativeLayout layout = (RelativeLayout) dialog2.findViewById(R.id.rel_star);
        TextView star_near = (TextView) dialog2.findViewById(R.id.star_near);
        TextView star_home = (TextView) dialog2.findViewById(R.id.star_home);
        TextView star_redeem = (TextView) dialog2.findViewById(R.id.star_redeem);
        TextView star_account = (TextView) dialog2.findViewById(R.id.star_account);
        TextView star_wallet = (TextView) dialog2.findViewById(R.id.star_wallet);
        ImageView star_search = (ImageView) dialog2.findViewById(R.id.star_search);
        GPSTracker gpsTracker = new GPSTracker(context);
        final Location location = gpsTracker.getLocation();


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        star_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location == null) {
                    Dialogs.alertDialog(context, "Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
                    dialog2.dismiss();
                } else {
                    Intent in = new Intent(context, Search.class);
                    context.startActivity(in);
                    dialog2.dismiss();
                }
            }
        });


        star_near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location == null) {
                    Dialogs.alertDialog(context, "Allow &#34;Pink Star&#34; to access your location while you use this app?", "Location is required", "Don't Allow", "Allow");
                    dialog2.dismiss();
                } else {
                    Intent in = new Intent(context, NearBy.class);
                    context.startActivity(in);
                    dialog2.dismiss();
                }
            }
        });

        star_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, MainActivity.class);
                context.startActivity(in);

                dialog2.dismiss();

            }
        });

        star_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Recharge.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });

        star_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Account.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });

        star_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, MyWallet.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });


        if(check) {
            dialog2.show();
        }
    }


    //dismiss the progress dialog
    public static void disDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    //to show a center toast.
    public static void showCenterToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //hide soft key board
    public static void hideSoftKeyboard(Context ctx, EditText view) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
