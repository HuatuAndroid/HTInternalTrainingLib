package com.zhiyun88.www.module_main.commonality.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.adapter.UserInfoAdapter;
import com.zhiyun88.www.module_main.commonality.bean.UserInfoBean;

import java.util.List;

public class UserInfoActivity extends BaseActivity {

    private MultipleStatusView multipleStatusView;
    private TopBarView topbarview;
    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        setListener();
        processLogic(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_userinfo);
        userInfoBean = getIntent().getParcelableExtra("userInfoBean");
        topbarview = getViewById(R.id.topbarview);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        ListView listView = getViewById(R.id.p_lv);

        if (userInfoBean == null) {
            multipleStatusView.showError();
        }else {
            multipleStatusView.showContent();
            listView.setAdapter(new UserInfoAdapter(this,userInfoBean));
        }
    }

    @Override
    protected void setListener() {
        topbarview.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        topbarview.getCenterTextView().setText(R.string.main_personal_details);
    }
}
