package com.example.nuonuo.boilandroidsdk;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class DailogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);//触屏不会关闭
        //打开但是不获取焦点，即不弹出输入法（不建议用，返回键失灵卡死）
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        setContentView(R.layout.activity_dailog);

    }
}
