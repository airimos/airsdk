package com.uniview.airimos.sdkdemo;

import android.app.Application;

import com.uniview.airimos.airsdk.Airimos;

/**
 * Created by 健 on 2015/3/4.
 */
public class APP extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Airimos.init(getApplicationContext());
    }
}
