package com.example.module_employees_world.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.hss01248.dialog.StyledDialog;

public class DialogUtils extends Dialog {

    private TextView dialog_title, dialog_content, dialog_yes, dialog_no,dialog_centre_btn;
    private Dialog dialog;
    private LinearLayout dialog_btn_ll;
    private OnClickListener onClickListener;
    private OnCentreClickListenter onCentreClickListenter;
    private View view;

    public DialogUtils(@NonNull Context context) {
        this(context,0);
    }

    private DialogUtils(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initDialog(context);
    }

    private DialogUtils initDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.main_custom_dialog, null);
        dialog = StyledDialog.buildCustom(view, Gravity.CENTER).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog_title = view.findViewById(R.id.dialog_title);
        dialog_content = view.findViewById(R.id.dialog_content);
        dialog_yes = view.findViewById(R.id.dialog_yes);
        dialog_no = view.findViewById(R.id.dialog_no);
        dialog_btn_ll = view.findViewById(R.id.dialog_btn_ll);
        dialog_centre_btn = view.findViewById(R.id.dialog_centre_btn);

        dialog_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null)return;
                onClickListener.setYesClickListener();
                closeDiaLog();
            }
        });
        dialog_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null)return;
                onClickListener.setNoClickListener();
                closeDiaLog();
            }
        });
        dialog_centre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCentreClickListenter == null)return;
                onCentreClickListenter.setCentreClickListener();
                closeDiaLog();
            }
        });
        return this;
    }


    public DialogUtils setTitle(String title) {
        dialog_title.setText(title);
        return this;
    }

    public DialogUtils setContent(String content) {
        dialog_content.setText(content);
        return this;
    }

    public DialogUtils setBtn1Txt(String btn1Txt) {
        dialog_yes.setText(btn1Txt);
        return this;
    }

    public DialogUtils setBtn2Txt(String btn2Txt) {
        dialog_no.setText(btn2Txt);
        return this;
    }
    public DialogUtils setbtncentre(String btnCentreTxt) {
        dialog_centre_btn.setText(btnCentreTxt);
        return this;
    }

    public DialogUtils setBtn2TxtColor(@ColorInt int btn2TxtColor) {
        dialog_no.setTextColor(btn2TxtColor);
        return this;
    }

    public DialogUtils hitBtn(boolean isHit) {
        if (isHit) {
            dialog_btn_ll.setVisibility(View.GONE);
            dialog_centre_btn.setVisibility(View.VISIBLE);
        }else {
            dialog_btn_ll.setVisibility(View.VISIBLE);
            dialog_centre_btn.setVisibility(View.GONE);
        }
        return this;
    }


    public void setOnClickListenter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnCentreClickListenter(OnCentreClickListenter onCentreClickListenter) {
        this.onCentreClickListenter = onCentreClickListenter;
    }

    private void closeDiaLog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public interface OnClickListener {
        void setYesClickListener();
        void setNoClickListener();
    }
    public interface OnCentreClickListenter {
        void setCentreClickListener();
    }
}
