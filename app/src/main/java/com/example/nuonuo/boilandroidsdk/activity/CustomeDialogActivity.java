package com.example.nuonuo.boilandroidsdk.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.nuonuo.boilandroidsdk.R;
import com.example.nuonuo.boilandroidsdk.view.radiogroup.RadioGroupAuto;

public class CustomeDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_radio);
        showEdDialog();

    }
    private void showEdDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_radio, null);
        final AlertDialog.Builder radioDialog = new AlertDialog.Builder(this)
                .setTitle("标题")
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioGroupAuto radioGroupAuto = view.findViewById(R.id.radioGroup_sex_id);


                        for (int i = 0; i < radioGroupAuto.getChildCount(); i++) {
                            RadioButton rb = (RadioButton) radioGroupAuto.getChildAt(i);
                            if (rb.isChecked()) {
                                Toast.makeText(CustomeDialogActivity.this, "i:" + i, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }


                    }
                });

        radioDialog.create().show();
    }

}
