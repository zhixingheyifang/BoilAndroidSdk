package com.example.nuonuo.boilandroidsdk.utils;

import android.util.Log;

/**
 * Android 禁止Edittext弹出系统软键盘
 * https://blog.csdn.net/sinat_27672523/article/details/56839837
 */
public class MyUtils {
    public static final  String TAG="MyUtils";

    public void getThreadInfo(){
        //获取线程信息
        Log.d(TAG, " Thread name: " + Thread.currentThread().getName());
        Log.d(TAG, "Thread.currentThread().getId():" + Thread.currentThread().getId());
    }

    public void getClassInfo(){
        //获取类名
        Log.d(TAG, this.getClass().getName());//适用于非静态方法,静态方法不行
        Log.d(TAG, Thread.currentThread().getStackTrace()[1].getClassName());//适用于静态方法
        //获取方法名
        Log.d(TAG, Thread.currentThread().getStackTrace()[1].getMethodName());
        //获取代码行号
        Log.d(TAG, "Thread.currentThread().getStackTrace()[1].getLineNumber():" + Thread.currentThread().getStackTrace()[1].getLineNumber());
    }


}
