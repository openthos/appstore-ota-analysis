package com.example.Tool;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat.Field;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class CrashHandler  implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;


    private static CrashHandler INSTANCE;


    private Context mContext;


    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }


    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println("我是异常--------"+ex);
        ex.printStackTrace();
        Toast.makeText(mContext,  "adf", 1000).show();
        Throwable cause = ex.getCause();
        while (cause != null) {
            System.out.println("我在数据异常中孩子  但是我还是可能没有进来");
            cause.printStackTrace();
            cause = cause.getCause();
        }
        if (!handleException(ex) && mDefaultHandler != null) {

            mDefaultHandler.uncaughtException(thread, ex);
        } else {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }



    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }


        new Thread() {

            public void run() {

                Looper.prepare();


                Toast.makeText(mContext, "正在退出     aadsfasdf", Toast.LENGTH_LONG).show();
                Looper.loop();

            }

        }.start();

        return true;
    }


    //*********************************************************************************************

}