package com.example.administrator.industry4app.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.industry4app.R;

/**
 * Created by Administrator
 * on 2016/6/16.
 */
public class ToastUtil {

    private static Toast toast; //默认Toast实例
    private static Toast customToast; //自定义Toast实例
    private static View layout;//自定义布局
    private static TextView textView;//自定义显示文本

    /**
     * 多次触发Toast，只显示最后一次
     * @param context 上下文
     * @param showInfo 要显示的文本
     */
    public static void showToast(Context context,CharSequence showInfo){
        if(customToast!=null) customToast.cancel();

        if(toast==null) toast = Toast.makeText(context,showInfo,Toast.LENGTH_SHORT);
        else toast.setText(showInfo);

        toast.show();
    }

    /**
     * 多次触发Toast，只显示最后一次
     * @param context 上下文
     * @param resId 要显示的文本id
     */
    public static void showToast(Context context,int resId){
        showToast(context, context.getResources().getText(resId));
    }


    /**
     * 自定义Toast
     * @param context 上下文
     * @param showInfo 要显示的文本
     */
    @SuppressLint("InflateParams")
    public static void customToast(Context context,CharSequence showInfo){
        if(layout==null) layout = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast,null);

        if(textView==null) textView = (TextView)layout.findViewById(R.id.tvShowInfo);

        if(toast!=null) toast.cancel();

        if(customToast==null){
            customToast = new Toast(context);
            textView.setText(showInfo);
        }else{
            textView.setText(showInfo);
        }
        customToast.setView(layout);
        customToast.show();
    }

    /**
     * 自定义Toast
     * @param context 上下文
     * @param resId 要显示的文本id
     */
    public static void customToast(Context context,int resId){
        customToast(context, context.getResources().getText(resId));
    }

}
