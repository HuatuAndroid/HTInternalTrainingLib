package com.zhiyun88.www.module_main.commonality.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;

public class DownloadManagerActivity extends MvpActivity{

    private TopBarView topBarView;
    private TextView fileNum;

    @Override
    protected BasePreaenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_downloadmanager);
        topBarView = getViewById(R.id.topbarview);
        fileNum = getViewById(R.id.download_image_num);
    }

    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
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
        topBarView.getCenterTextView().setText(R.string.main_my_download);
        fileNum.setText("5");
    }
}
