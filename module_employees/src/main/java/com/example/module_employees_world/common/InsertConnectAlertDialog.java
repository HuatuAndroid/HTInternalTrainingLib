package com.example.module_employees_world.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.utils.SoftKeyboardUtils;

/**
 * @author liuzhe
 * @date 2019/3/28
 * <p>
 * 插入超链接的dialog
 */
public class InsertConnectAlertDialog {

    private Activity activity;
    private EditText mEtConnect;
    private EditText mEtConnectContent;
    private TextView tvDialogLeft;
    private TextView tvDialogRight;

    private AlertDialog alertDialog;

    public InsertConnectAlertDialog(Activity activity) {
        try {
            this.activity = activity;
            alertDialog = new AlertDialog.Builder(activity).create();
            initDlg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        alertDialog.show();
    }

    private void initDlg() {
        show();
        Window mWindow = alertDialog.getWindow();
        mWindow.setContentView(R.layout.dialog_insert_connect);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CommonUtils.showSoftInput(activity);

        mEtConnect = mWindow.findViewById(R.id.mEtConnect);
        mEtConnectContent = mWindow.findViewById(R.id.mEtConnectContent);
        tvDialogLeft = mWindow.findViewById(R.id.tvDialogLeft);
        tvDialogRight = mWindow.findViewById(R.id.tvDialogRight);

        mEtConnect.setOnClickListener(v -> showSoftKeyboard(mEtConnect));

        mEtConnect.setOnFocusChangeListener((v, hasFocus) -> showSoftKeyboard(mEtConnect));

        mEtConnectContent.setOnClickListener(v -> showSoftKeyboard(mEtConnectContent));

        mEtConnectContent.setOnFocusChangeListener((v, hasFocus) -> showSoftKeyboard(mEtConnectContent));
    }

    public void setCancelable(boolean isCancelable) {
        alertDialog.setCancelable(isCancelable);
    }

    public void setRightOnClickListener(View.OnClickListener okOnClickListener) {
        tvDialogRight.setOnClickListener(okOnClickListener);
    }

    public void setLeftOnClickListener(View.OnClickListener cancelOnClickListener) {
        tvDialogLeft.setOnClickListener(cancelOnClickListener);
    }

    public void showSoftKeyboard(EditText mEditText) {

        if (mEditText != null) {
            //调用系统输入法
//            SoftKeyboardUtils.showSoftKeyboard(alertDialog.getWindow().getContext(), alertDialog.getWindow());
            InputMethodManager inputManager = (InputMethodManager) mEditText
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(mEditText, 0);
        }

    }

    public String getmEtConnectContentString() {
        return mEtConnectContent.getText().toString();
    }

    public String getmEtConnectString() {
        return mEtConnect.getText().toString();
    }

    public void dismiss() {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        if (alertDialog.isShowing()) {
            return true;
        }
        return false;
    }

}
