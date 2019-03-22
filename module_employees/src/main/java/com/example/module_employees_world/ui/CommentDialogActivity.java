package com.example.module_employees_world.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.module_employees_world.R;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.utils.StatusBarUtil;

public class CommentDialogActivity extends MvpActivity {

    @Override
    protected BasePreaenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_dialog);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
