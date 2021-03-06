package com.example.car4u;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application
{
    private static Context appContext;
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public void onCreate()
    {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getContext()
    {
        return appContext;
    }
}

