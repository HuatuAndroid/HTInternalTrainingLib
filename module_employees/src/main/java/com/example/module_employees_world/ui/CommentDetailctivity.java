package com.example.module_employees_world.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.module_employees_world.R;
import com.wb.baselib.utils.StatusBarUtil;

/**
 * author:LIENLIN
 * date:2019/3/27
 * 评论详情
 */
public class CommentDetailctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detailctivity);
        StatusBarUtil.setStatusLayout(this, Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
    }
}
