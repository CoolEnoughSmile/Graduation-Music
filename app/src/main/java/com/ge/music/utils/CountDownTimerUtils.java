package com.ge.music.utils;

import android.os.CountDownTimer;
import android.widget.Button;

public class CountDownTimerUtils extends CountDownTimer {

    private Button button;
    private String text;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.button = button;
        this.text = button.getText().toString();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        button.setEnabled(false);
        button.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        button.setEnabled(true);
        button.setText(text);
    }

    public void release(){
        cancel();
        if (button !=null) {
            button = null;
        }
    }
}
