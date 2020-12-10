package com.example.ksinfo;

import android.app.Application;

public  class GlobalApplication extends Application {

    private String loginStatus;

    public String getLoginStatus(){
        return loginStatus;
    }

    public void setLoginStatus(String loginVariable){
        this.loginStatus = loginVariable;
    }

}
