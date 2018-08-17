package com.example.nuonuo.boilandroidsdk;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nuonuo.boilandroidsdk.activity.AsyncTaskActivity;
import com.example.nuonuo.boilandroidsdk.activity.CustomeDialogActivity;
import com.example.nuonuo.boilandroidsdk.activity.DailogActivity;
import com.example.nuonuo.boilandroidsdk.activity.HanderTestActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getAndroidManifestMetaDate(View view) {
        /**
         * 参考：
         * 1. 讲讲Android Gradle manifestPlaceholders 占位符详解的使用
         * https://www.cnblogs.com/zhaohongtian/p/6808962.html
         */
        try {
            ApplicationInfo appInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            String value = appInfo.metaData.getString("DIANJU_KEY");
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handerActivity(View view) {
        startActivity(new Intent(this, HanderTestActivity.class));
    }

    public void dailogActivity(View view) {
        startActivity(new Intent(this, DailogActivity.class));
    }

    public void asyncTaskActivity(View view) {
        startActivity(new Intent(this, AsyncTaskActivity.class));
    }

    public void nestedScrollActivity(View view) {
        startActivity(new Intent(this, CustomeDialogActivity.class));
    }


}
