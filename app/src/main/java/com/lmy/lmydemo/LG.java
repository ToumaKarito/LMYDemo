package com.lmy.lmydemo;

import android.os.Process;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanglang on 16-7-29.
 */
public class LG {

    public static boolean DEBUG = true;
    private static final String WTAG = "Launcher3";
    // I
    public static void i(String msg) {
        if (DEBUG) {
            Log.i(WTAG, _FILE_()+"【" + getLineMethod() + "】" + msg);
        }
    }
    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(WTAG, TAG+"【" + getFileLineMethod() + "】" + msg);
//            Log.i(WTAG, TAG+":" + msg);
        }
    }
    public static void i(String TAG, String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(WTAG, TAG+"【" + getFileLineMethod() + "】" + msg, tr);
        }
    }


    // D
    public static void d(String msg) {
        if (DEBUG) {
            Log.d(WTAG, _FILE_()+"【" + getLineMethod() + "】" + msg);
        }
    }
    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(WTAG, TAG+"【" + getFileLineMethod() + "】" + msg);
        }
    }
    public static void d(String TAG, String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(WTAG, TAG+"【" + getFileLineMethod() + "】" + msg, tr);
        }
    }


    // E
    public static void e(String msg) {
        if (DEBUG) {
            Log.e(WTAG, _FILE_()+"【" + getFileLineMethod() + "】" + msg);
        }
    }
    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(WTAG, TAG+"【" + getFileLineMethod() + "】" + msg);
//            Log.e(WTAG, TAG+":" + msg);
        }
    }
    public static void e(String TAG, String msg, Throwable tr)
    {
        if (DEBUG)
            Log.e(WTAG, TAG+"【" + getFileLineMethod() + "】" + msg, tr);
    }


    // V
    public static void v(String msg) {
        if (DEBUG)
            Log.v(WTAG, _FILE_()+ "【" + getFileLineMethod() + "】" + msg);
    }
    public static void v(String TAG, String msg) {
        if (DEBUG)
            Log.v(WTAG, TAG+":" + msg);
    }
    public static void v(String TAG, String msg, Throwable tr) {
        if (DEBUG)
            Log.v(WTAG, TAG+ "【" + getFileLineMethod() + "】" + msg, tr);
    }


    // W
    public static void w(String msg) {
        if (DEBUG)
            Log.w(WTAG, _FILE_()+ "【" + getFileLineMethod() + "】" + msg);
    }
    public static void w(String TAG, String msg) {
        if (DEBUG)
            Log.w(WTAG, TAG+"【" + getFileLineMethod() + "】" + msg);
    }
    public static void w(String TAG, String msg, Throwable tr) {
        if (DEBUG)
            Log.w(WTAG, TAG+ "【" + getFileLineMethod() + "】" + msg, tr);
    }

    public static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer =
                new StringBuffer("").append(traceElement.getFileName())
                        .append(" | ")
                        .append(Thread.currentThread().getId())
                        .append(" | ")
                        .append(traceElement.getLineNumber())
                        .append(" | ")
                        .append(traceElement.getMethodName())
                        .append("");
        return toStringBuffer.toString();
    }

    public static String getLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer =
                new StringBuffer("").append(traceElement.getLineNumber())
                        .append(" | ")
                        .append(Process.myTid()+"")
                        .append(" | ")
                        .append(traceElement.getMethodName())
                        .append("");
        return toStringBuffer.toString();
    }

    public static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return traceElement.getFileName();
    }

    public static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    public static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }

    public static String _TIME_() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }




}
