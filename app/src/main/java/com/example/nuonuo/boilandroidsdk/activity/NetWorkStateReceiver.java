package com.example.nuonuo.boilandroidsdk.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.nuonuo.boilandroidsdk.utils.NetWorkStateUtil;


public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetWorkStateUtil.getNetWorkState(context)) {
            Toast.makeText(context, "网络连接上了", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "网络关闭", Toast.LENGTH_SHORT).show();
        }
    }
}

