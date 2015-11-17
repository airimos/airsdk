package com.uniview.airimos.airdemo;

import android.app.Application;

import com.uniview.airimos.airsdk.UNV;

/**
 * Created by 健 on 2015/3/4.
 */
public class APP extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        UNV.init(getApplicationContext());
    }
}
