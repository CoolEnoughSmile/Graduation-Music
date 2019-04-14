package com.ge.music.CESView;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Timer;

public class TimerButton extends android.support.v7.widget.AppCompatButton {

    private Timer timer;

    public TimerButton(Context context) {
        super(context);
        init();
    }

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean callOnClick() {

        return super.callOnClick();
    }

    private void init() {
        timer = new Timer();
    }
}
