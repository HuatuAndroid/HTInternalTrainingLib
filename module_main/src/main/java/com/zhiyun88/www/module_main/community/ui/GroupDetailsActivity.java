package com.zhiyun88.www.module_main.community.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.adapter.ViewPageTabAdapter;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxMessageBean;
import com.zhiyun88.www.module_main.DialogUtils;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.GroupInfoBean;
import com.zhiyun88.www.module_main.community.fragment.CommunityDiscussFragment;
import com.zhiyun88.www.module_main.community.mvp.contranct.GroupDetailsContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.GroupDetailsPresenter;

import java.util.ArrayList;

public class GroupDetailsActivity extends MvpActivity<GroupDetailsPresenter> implements GroupDetailsContranct.GroupDetailsView{

    private TopBarView topBarView;
    private ImageView imageView;
    private TextView title;
    private TextView num;
    private TextView join;
    private TextView content;
    private ScrollIndicatorView scrollIndicatorView;
    private ViewPager mViewPager;
    private GroupInfoBean groupInfoBean;
    private String groupId;


    @Override
    protected GroupDetailsPresenter onCreatePresenter() {
        return new GroupDetailsPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group_detail_neixun);
        groupId = getIntent().getStringExtra("groupId");
        topBarView = getViewById(R.id.topbarview);
        if (TextUtils.isEmpty(groupId)) {
            return;
        }
        imageView = getViewById(R.id.group_image);
        title = getViewById(R.id.group_title);
        num = getViewById(R.id.group_num);
        join = getViewById(R.id.group_join);
        content = getViewById(R.id.group_content);
        scrollIndicatorView = getViewById(R.id.spring_indicator);
        mViewPager = getViewById(R.id.viewpager);
        mPresenter.getGroupDetails(groupId,"1" );
        initFragment();
    }

    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
                if (action == TopBarView.ACTION_RIGHT_TEXT) {
                    if (groupInfoBean == null)return;
                    if (groupInfoBean.getIs_group().equals("1")) {
                        Intent intent = new Intent(GroupDetailsActivity.this,ReleaseTopicActivity.class);
                        intent.putExtra("groupId", groupId);
                        startActivity(intent);
                    }else {
                        DialogUtils dialogUtils = new DialogUtils(GroupDetailsActivity.this);
                        dialogUtils.setTitle("提示")
                                .setContent("请先加入小组")
                                .hitBtn(true)
                                .setbtncentre("确定")
                                .setOnCentreClickListenter(new DialogUtils.OnCentreClickListenter() {
                                    @Override
                                    public void setCentreClickListener() {

                                    }
                                });
                    }
                }
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupInfoBean == null) return;
                if (groupInfoBean.getIs_group().equals("1")) {
                    DialogUtils dialogUtils = new DialogUtils(GroupDetailsActivity.this);
                    dialogUtils.setTitle("提示")
                            .setContent("确定要退出小组?")
                            .setOnClickListenter(new DialogUtils.OnClickListener() {
                                @Override
                                public void setYesClickListener() {
                                    join.setEnabled(false);
                                    mPresenter.setGroup(groupInfoBean.getId(), "0");
                                }

                                @Override
                                public void setNoClickListener() {

                                }
                            });

                } else {
                    join.setEnabled(false);
                    mPresenter.setGroup(groupInfoBean.getId(), "1");
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void SuccessData(Object o) {
        groupInfoBean = (GroupInfoBean) o;
        GlideManager.getInstance().setRoundPhoto(imageView, R.drawable.course_image ,this , groupInfoBean.getImg() ,4 );
        title.setText(groupInfoBean.getName());
        num.setText("成员: "+ groupInfoBean.getUser_count()+"人");
        content.setText(groupInfoBean.getIntroduce());
        if (groupInfoBean.getIs_group().equals("0")){
            join.setText("加入小组");
            join.setSelected(false);
        }else {
            join.setText("退出小组");
            join.setSelected(true);
        }
    }

    private void initFragment() {
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Fragment> mFragments = new ArrayList<>();
        str.add("热门");
        str.add("最新");
        mFragments.add(CommunityDiscussFragment.newInstance("1",groupId,true));
        mFragments.add(CommunityDiscussFragment.newInstance("2",groupId,true));
        scrollIndicatorView.setSplitAuto(true);
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener() {
            @Override
            public TextView getTextView(View tabItemView, int position) {
                return (TextView) tabItemView.findViewById(R.id.test_tv);
            }
        }.setColor(getResources().getColor(R.color.main_text_blue_458), Color.BLACK));
        ColorBar colorBar = new ColorBar(this, getResources().getColor(R.color.main_text_blue_458), 4);
        scrollIndicatorView.setScrollBar(colorBar);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, mViewPager);
        ViewPageTabAdapter viewPageTabAdapter= new ViewPageTabAdapter(getSupportFragmentManager(), this, mFragments, str);
        indicatorViewPager.setAdapter(viewPageTabAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void joinGroup() {
        String is_group;
        if (groupInfoBean.getIs_group().equals("0")){
            join.setText("退出小组");
            join.setSelected(true);
            groupInfoBean.setIs_group("1");
            int userCount = Integer.parseInt(groupInfoBean.getUser_count()) + 1;
            groupInfoBean.setUser_count(userCount+"");
            is_group = "1";
        }else {
            join.setText("加入小组");
            join.setSelected(false);
            groupInfoBean.setIs_group("0");
            int userCount = Integer.parseInt(groupInfoBean.getUser_count()) - 1;
            groupInfoBean.setUser_count(userCount+"");
            is_group = "0";
        }
        num.setText("成员: "+ groupInfoBean.getUser_count()+"人");
        RxBus.getIntanceBus().post(new RxMessageBean(592,groupInfoBean.getId(),is_group));
        join.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getIntanceBus().unSubscribe(this);
    }
}
