package com.ge.music.CESView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class CountDownTimerButton extends Button implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private OnClickListener onClickListener;
    private String text;

    public CountDownTimerButton(Context context) {
        super(context);
        init();
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                setEnabled(true);
                setText(text);
            }
        };
        setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        if (onClickListener instanceof CountDownTimerButton) {
            super.setOnClickListener(onClickListener);
        }else {
            this.onClickListener = onClickListener;
        }
    }

    @Override
    public void onClick(View v) {
        text = getText().toString();
        countDownTimer.start();
        if (onClickListener != null){
            onClickListener.onClick(v);
            setEnabled(false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        countDownTimer.cancel();
    }
}
