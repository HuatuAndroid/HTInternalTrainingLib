package com.zhiyun88.www.module_main.community.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wb.baselib.utils.ToastUtils;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;

public class CustomDialog extends Dialog {
    private Context context;
    private TextView commit_tv;
    private ImageView showName_tv;
    private EditText content_et;
    private LinearLayout have_name_ll;
    private CommunityConfig.OnEditChangeListener onEditChangeListener;
    private boolean is_show = false;
    private boolean isReply;
    private String user_name;

    public CustomDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.main_custom_dialog_bottom, null);
        this.setContentView(view);
        content_et = (EditText) view.findViewById(R.id.details_content);
        have_name_ll = (LinearLayout) view.findViewById(R.id.have_name_ll);
        showName_tv = (ImageView) view.findViewById(R.id.show_name);
        commit_tv = (TextView) view.findViewById(R.id.details_commit);
        if (user_name == null || user_name.equals("")) {
            content_et.setHint("请输入你要评论的内容");
        } else {
            content_et.setHint("回复: " + user_name);
        }
        setListener();
    }

    public void setOnEditChangeListener(CommunityConfig.OnEditChangeListener onEditChangeListener) {
        this.onEditChangeListener = onEditChangeListener;
    }

    public void setIsReply(String user_name) {
        this.user_name = user_name;
    }

    public void setSendSuccess() {
        commit_tv.setEnabled(true);
        dismiss();
    }


    public void setListener() {
        have_name_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_show = !is_show;
                showName_tv.setSelected(is_show);
            }
        });
        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditChangeListener == null) return;
                String str = content_et.getText().toString().trim();
                if (str.isEmpty()){
                    ToastUtils.showToast(context,"输入不能为空");
                }else {
                    onEditChangeListener.getEditChange(str, is_show);
                    commit_tv.setEnabled(false);
                }

            }
        });
    }
}
