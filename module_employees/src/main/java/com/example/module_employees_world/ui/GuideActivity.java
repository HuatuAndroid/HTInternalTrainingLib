package com.example.module_employees_world.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.common.StartActivityCommon;
import com.example.module_employees_world.contranct.GuideContranct;
import com.example.module_employees_world.presenter.GuidePresenter;
import com.example.module_employees_world.ui.home.CommunityActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;

/**
 * @author liuzhe
 * @date 2019/3/21
 * <p>
 * 广告页
 */
public class GuideActivity extends MvpActivity<GuidePresenter> implements GuideContranct.View {

    private ImageView mImageView;
    private TextView mTvTime, mTvSkip, mTvBottomData;

    @Override
    protected GuidePresenter onCreatePresenter() {
        return new GuidePresenter(this,this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_guide);

        mImageView = findViewById(R.id.mImageView);
        mTvTime = findViewById(R.id.mTvTime);
        mTvSkip = findViewById(R.id.mTvSkip);
        mTvBottomData = findViewById(R.id.mTvBottomData);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        //启动倒计时
        mPresenter.countDown();

    }


    @Override
    protected void setListener() {

        mImageView.setOnClickListener(this);
        mTvSkip.setOnClickListener(this);

    }

    /**
     * 修改倒计时的时间
     * @param aLong
     */
    @Override
    public void upData_mTvTime(long aLong){
        mTvTime.setText(aLong+"");
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.mImageView) {     //背景图片的点击响应


        } else if (i == R.id.mTvSkip) { //跳过按钮的点击响应

            StartActivityCommon.startActivity(this, CommunityActivity.class);

        } else {
        }

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(String msg) {
    }

    @Override
    public void showLoadV(String msg) {
    }

    @Override
    public void closeLoadV() {
    }

    @Override
    public void SuccessData(Object o) {
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return null;
    }
}
