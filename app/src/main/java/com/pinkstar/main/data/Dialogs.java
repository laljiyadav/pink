package com.pinkstar.main.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinkstar.main.Account;
import com.pinkstar.main.MyWallet;
import com.pinkstar.main.NearBy;
import com.pinkstar.main.Profile;
import com.pinkstar.main.R;
import com.pinkstar.main.Recharge;
import com.pinkstar.main.Search;

/**
 * Created by sudhir on 4/12/2016.
 */
public class Dialogs {

    private static ProgressDialog progressDialog = null;
    private static AlertDialog alertDialog = null;
    private Activity activity;


    public static void showProDialog(Context context,String msg){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    //dismiss the progress dialog
    public static void dismissDialog(){
        if (progressDialog!= null) {
            progressDialog.dismiss();
        }
    }

    public static Object getJsonToClassObject(String jsonStr,Class<?> classs){
        try{
            Gson gson = new GsonBuilder().create();
            return (Object)gson.fromJson(jsonStr, classs);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void showDialog(Context context,String msg){
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    public static void star_dialog(final Context context)
    {
        final Dialog dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        dialog2.setContentView(R.layout.star_dialog);
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        lp.dimAmount=0.0f;
        Window window = dialog2.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp.gravity = Gravity.CENTER;

        TextView star_near=(TextView)dialog2.findViewById(R.id.star_near);
        TextView star_home=(TextView)dialog2.findViewById(R.id.star_home);
        TextView star_redeem=(TextView)dialog2.findViewById(R.id.star_redeem);
        TextView star_account=(TextView)dialog2.findViewById(R.id.star_account);
        TextView star_wallet=(TextView)dialog2.findViewById(R.id.star_wallet);
        ImageView star_search=(ImageView)dialog2.findViewById(R.id.star_search);

        star_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,Search.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });


        star_near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,NearBy.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });

        star_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,Profile.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });

        star_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,Recharge.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });

        star_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,Account.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });

        star_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,MyWallet.class);
                context.startActivity(in);
                dialog2.dismiss();
            }
        });


        dialog2.show();
    }



    //dismiss the progress dialog
    public static void disDialog(){
        if (progressDialog!= null) {
            progressDialog.dismiss();
        }
    }

    //to show a center toast.
    public static void showCenterToast(Context context, String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //hide soft key board
    public static void hideSoftKeyboard(Context ctx, EditText view){
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
