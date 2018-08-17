package com.example.nuonuo.boilandroidsdk;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.example.nuonuo.boilandroidsdk.utils.Utils;

public class AppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
