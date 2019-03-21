package com.example.module_employees_world.ui.home;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.module_employees_world.R;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;

/**
 * 员工天地首页
 */
public class EmplayeesActivity extends MvpActivity {

    @Override
    protected BasePreaenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplayees_layout);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
