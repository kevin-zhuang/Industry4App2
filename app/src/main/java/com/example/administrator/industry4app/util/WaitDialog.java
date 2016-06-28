package com.example.administrator.industry4app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator
 * on 2016/6/23.
 */
public class WaitDialog {

    private static final String TAG = "WaitDialog";
    private static ProgressDialog progressDialog;
    private static Runnable runnable;
    private static Handler handler = new Handler();

    public static void showDialog(Context context, String message, int disappearTime) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
        runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        };

        handler.postDelayed(runnable, disappearTime);
    }

    public static void immediatelyDismiss() {
        if (progressDialog != null && handler != null && runnable != null) {
            progressDialog.dismiss();
            handler.removeCallbacks(runnable);
        }
    }


}
