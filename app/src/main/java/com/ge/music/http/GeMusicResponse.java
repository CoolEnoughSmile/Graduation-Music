package com.ge.music.http;

import com.google.gson.annotations.SerializedName;

public class GeMusicResponse<T>{

    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;
    @SerializedName("user")
    private T data;

    public GeMusicResponse() {
    }

    public GeMusicResponse(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GeMusicResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
