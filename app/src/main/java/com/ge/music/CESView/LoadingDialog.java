package com.ge.music.CESView;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;

import com.ge.music.R;

public class LoadingDialog extends AlertDialog {

    public LoadingDialog(Context context) {
        super(context);
        setView(LayoutInflater.from(context).inflate(R.layout.dialog_loading,null));
        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));
        setCancelable(false);
    }

}
