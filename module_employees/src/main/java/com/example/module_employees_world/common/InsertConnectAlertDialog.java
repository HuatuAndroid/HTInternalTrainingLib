package com.example.module_employees_world.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.module_employees_world.R;

/**
 * @author liuzhe
 * @date 2019/3/28
 *
 * 插入超链接的dialog
 */
public class InsertConnectAlertDialog {

    private Activity activity;
    private EditText mEtConnect;
    private EditText mEtConnectContent;
    private TextView tvDialogLeft;
    private TextView tvDialogRight;

    private AlertDialog alertDialog;

    public  InsertConnectAlertDialog(Activity activity) {
        try {
            this.activity = activity;
            alertDialog = new AlertDialog.Builder(activity).create();
            initDlg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(){
        alertDialog.show();
    }

    private void initDlg(){
        show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_insert_connect);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CommonUtils.showSoftInput(activity);

        mEtConnect = window.findViewById(R.id.mEtConnect);
        mEtConnectContent = window.findViewById(R.id.mEtConnectContent);
        tvDialogLeft = window.findViewById(R.id.tvDialogLeft);
        tvDialogRight = window.findViewById(R.id.tvDialogRight);
    }

    public void setCancelable(boolean isCancelable){
        alertDialog.setCancelable(isCancelable);
    }

    public void setRightOnClickListener(View.OnClickListener okOnClickListener){
        tvDialogRight.setOnClickListener(okOnClickListener);
    }

    public void setLeftOnClickListener(View.OnClickListener cancelOnClickListener){
        tvDialogLeft.setOnClickListener(cancelOnClickListener);
    }

    public String getmEtConnectContentString(){
        return mEtConnectContent.getText().toString();
    }

    public String getmEtConnectString(){
        return mEtConnect.getText().toString();
    }

    public void dismiss(){
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing(){
        if (alertDialog.isShowing()){
            return true;
        }
        return false;
    }

}
