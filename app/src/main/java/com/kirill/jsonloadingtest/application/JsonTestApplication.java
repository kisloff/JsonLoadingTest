package com.kirill.jsonloadingtest.application;

import android.app.Application;

import com.kirill.jsonloadingtest.service.Service;

/**
 * Created by alex on 5/16/15.
 */
public class JsonTestApplication extends Application {

    public static final String APP_TAG = "JsonTest";

    public Service service;

    @Override
    public void onCreate() {
        super.onCreate();

        service = new Service(getApplicationContext());
    }
}
