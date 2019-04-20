package com.ge.music.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SMSEventHandler extends EventHandler {

    private SMSEventListener smsEventListener;
    public static final String COUNTRY = "86";

    public SMSEventHandler(SMSEventListener smsEventListener){
        super();
        this.smsEventListener = smsEventListener;
    }

    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    LogUtils.i("验证码提交成功");
                    smsEventListener.onVCodeCheckSuccess();
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    LogUtils.i("验证码发送成功");
                    smsEventListener.onVCodeSendSuccess();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                Throwable t = (Throwable) data;
                LogUtils.e(t);
                smsEventListener.onError();
                ToastUtils.showLong(t.getMessage());
            }
            return false;
        }
    });

    @Override
    public void afterEvent(int event, int result, Object data) {
        Message message = Message.obtain();
        message.arg1 = event;
        message.arg2 = result;
        message.obj = data;
        handler.sendMessage(message);
    }

    public interface SMSEventListener {
        void onVCodeSendSuccess();
        void onVCodeCheckSuccess();
        void onError();
    }

}
