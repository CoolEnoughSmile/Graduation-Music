package com.ge.music.CESView;

import android.app.AlertDialog;
import android.content.Context;

import com.ge.music.R;

public class LoadingDialog extends AlertDialog {

    public LoadingDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
    }

}
