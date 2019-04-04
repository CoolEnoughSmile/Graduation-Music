package com.ge.music.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Context context;
    private static Toast toast;

    public static void showToast(String text){
        toast.setText(text);
        toast.show();
    }

    public static void init(Context c){
        context = c;
        toast = Toast.makeText(context,null,Toast.LENGTH_SHORT);
    }

}
