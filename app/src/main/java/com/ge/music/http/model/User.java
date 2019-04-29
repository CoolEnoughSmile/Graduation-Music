package com.ge.music.http.model;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;

public class User {

    private String userId;
    private String userName;
    private String phone;
    private String password;
    private String avator;
    private String token;
    private String gender;

    public User() {
    }

    public User(String userId, String userName, String phone, String password, String avator, String token, String gender) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.avator = avator;
        this.token = token;
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", avator='" + avator + '\'' +
                ", token='" + token + '\'' +
                ", gender=" + gender +
                '}';
    }

    public static void save(User user) {
        String userStr = GsonUtils.toJson(user);
        SPUtils.getInstance().put("user", userStr);
    }

    public static User get() {
        String userStr = SPUtils.getInstance().getString("user");
        User user = null;
        try {
            user = GsonUtils.fromJson(userStr, User.class);
        }catch (Exception e){
            LogUtils.e(e);
        }
        return user;
    }

}

