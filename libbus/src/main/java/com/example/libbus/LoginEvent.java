package com.example.libbus;

/**
 * 登录事件
 */
public class LoginEvent {

    private String LoginStatus;

    public LoginEvent(String loginStatus) {
        LoginStatus = loginStatus;
    }

    public String getLoginStatus() {
        return LoginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        LoginStatus = loginStatus;
    }
}
