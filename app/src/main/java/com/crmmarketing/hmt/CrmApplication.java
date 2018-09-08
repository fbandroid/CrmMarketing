package com.crmmarketing.hmt;

import android.app.Application;

import com.facebook.stetho.Stetho;


public class CrmApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    //TODO  1--admin 2--mini admin 3--Team leader 4--Marketing executive 5--Dealer 6--Operation Ex

}