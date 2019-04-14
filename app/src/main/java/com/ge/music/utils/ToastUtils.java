package com.ge.music.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;

    public static void showToast(Context context,String text){
        if (toast == null){
            toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
