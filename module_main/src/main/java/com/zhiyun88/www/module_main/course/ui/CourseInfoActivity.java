package com.zhiyun88.www.module_main.course.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jungan.www.common_coorinator.CoordinatorTabLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.adapter.CoordinatorPagerAdapter;
import com.jungan.www.module_playvideo.event.BjyPlayTimeEvent;
import com.zhiyun88.www.module_main.course.bean.CourseInfoBean;
import com.zhiyun88.www.module_main.course.fragment.CommentListFrament;
import com.zhiyun88.www.module_main.course.fragment.CourseOutFragment;
import com.zhiyun88.www.module_main.course.fragment.TeacherListFrament;
import com.zhiyun88.www.module_main.course.fragment.WebViewFragment;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseInfoContranct;
import com.zhiyun88.www.module_main.course.mvp.presenter.CourseInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 课程详情
 */
public class CourseInfoActivity extends MvpActivity<CourseInfoPresenter> implements CourseInfoContranct.CourseInfoView{

    private TopBarView course_tb;
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private ArrayList<Fragment> mFragments;
    private List<String> mTitles ;
    private ViewPager mViewPager;
    private MultipleStatusView multipleStatusView;
    private boolean isCourseTaskInfo;
    private TextView isbuy_tv;
    private  String courseId;
    private boolean isHaveBuy=false;
    private CourseOutFragment courseOutFragment;
    private CourseInfoBean courseInfoBean;
    @Override
    protected CourseInfoPresenter onCreatePresenter() {
        return new CourseInfoPresenter(this);
    }
    private boolean isLoad=false;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_course_courseinfo);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        multipleStatusView.showContent();
        multipleStatusView.showLoading();
        course_tb=getViewById(R.id.course_tb);
        isbuy_tv=getViewById(R.id.isbuy_tv);
        mTitles=new ArrayList<>();
        mCoordinatorTabLayout = getViewById(R.id.coordinatortablayout);
        mFragments = new ArrayList<>();
        mViewPager = getViewById(R.id.vp);
        try {
//            String uid = getIntent().getData().getQueryParameter("uid");
//            String token=getIntent().getData().getQueryParameter("token");
//            courseId =getIntent().getData().getQueryParameter("id");
            courseId=getIntent().getData().getLastPathSegment();
            isCourseTaskInfo=false;
//            hApp.newInstance().toMainActivity(uid, token, new LoginStatusCall() {
//                @Override
//                public void LoginError(String msg, int code) {
//                    if(code==1040){
//                        mPresenter.getCourseInfoData(courseId);
//                    }else {
//                        ErrorData();
//                    }
//                }
//            });
            mPresenter.getCourseInfoData(courseId);
        } catch (Exception e) {
            courseId =getIntent().getStringExtra("courseId");
            isCourseTaskInfo=getIntent().getBooleanExtra("isCourseTaskInfo",false);
            mPresenter.getCourseInfoData(courseId);
        }
        if(isCourseTaskInfo){
            course_tb.getCenterTextView().setText("培训详情");
        }else {
            course_tb.getCenterTextView().setText("课程详情");
        }

        RxBus.getIntanceBus().registerRxBus(BjyPlayTimeEvent.class, new Consumer<BjyPlayTimeEvent>() {
            @Override
            public void accept(BjyPlayTimeEvent event) throws Exception {
                Log.d("kaelli", "event :"+event.toString());
                try {
                    mPresenter.uploadWatchVideoTime(event.playId, event.begin_time, event.end_time, event.watch_time, event.abort_time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void initFragments(CourseInfoBean courseInfoBean) {
        mFragments.add(WebViewFragment.newInstcace(courseInfoBean.getInfo().getDetails_url()));
        courseOutFragment=CourseOutFragment.newInstcace(courseInfoBean.getChapter(),isCourseTaskInfo,courseInfoBean.getInfo().getTitle(),isHaveBuy);
        mFragments.add(courseOutFragment);
        mFragments.add(TeacherListFrament.newInstance(courseInfoBean.getTeacher()));
        mFragments.add(CommentListFrament.newInstance(courseInfoBean.getInfo().getId()));

    }
    private void initViewPager() {
            if(isCourseTaskInfo){
                mTitles.add("培训详情");
                mTitles.add("培训大纲");
                mTitles.add("名师介绍");
                mTitles.add("培训评价");
            }else {
                mTitles.add("课程详情");
                mTitles.add("课程大纲");
                mTitles.add("名师介绍");
                mTitles.add("课程评价");
            }
             mViewPager.setAdapter(new CoordinatorPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
            mViewPager.setOffscreenPageLimit(3);

        isLoad=true;
    }
    @Override
    protected void setListener() {
        isbuy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.buyCourse(courseId);
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCourseInfoData(courseId);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        course_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(TopBarView.ACTION_LEFT_BUTTON==action){
                    finish();
                }
            }
        });
    }
    @Override
    public void initCoorLayout(CourseInfoBean courseInfoBean) {
        LogTools.e(courseInfoBean.toString()+"------>>>");
        this.courseInfoBean=courseInfoBean;

        if(isCourseTaskInfo){
            mCoordinatorTabLayout
                    .setBackEnable(true)
                    .setClassFly(true)
                    .setCourceTitle(courseInfoBean.getInfo().getTitle())
                    .setStartCourseTime(courseInfoBean.getInfo().getStart_end_date())
                    .setStartCourseAddress(courseInfoBean.getInfo().getAddress())
                    .settalkTeacher(courseInfoBean.getInfo().getTeacher_name())
                    .setPxBm(courseInfoBean.getInfo().getDepartment())
                    .sethaveBm(courseInfoBean.getInfo().getApply_num())
                    .setsyl("（剩余"+courseInfoBean.getInfo().getSurplus_num()+"名额）")
                    .setupWithViewPager(mViewPager);
        }else {
//            if(courseInfoBean.getInfo().getIs_buy().equals("0")){
//                //未添加
//                isbuy_state("加入学习",true,false,false);
//            }else if(courseInfoBean.getInfo().getIs_buy().equals("1")){
//                //已加入
//                isbuy_state("已加入",false,false,true);
////                isbuy_tv.setVisibility(View.GONE);
//            }
            mCoordinatorTabLayout
                    .setBackEnable(true)
                    .setClassFly(false)
                    .setTwoCourseName(courseInfoBean.getInfo().getTitle())
                    .setTwoDes(courseInfoBean.getInfo().getSubtitle())
                    .setTwollNum(courseInfoBean.getInfo().getApply_num()+"人学过 | "+courseInfoBean.getInfo().getPage_view()+"人浏览")
                    .setTwoTeacherName("主讲人："+courseInfoBean.getInfo().getTeacher_name())
                    .setupWithViewPager(mViewPager);
        }

        initFragments(courseInfoBean);
        initViewPager();
        courseOrTrain(courseInfoBean);
        try {
            GlideManager.getInstance().setCommonPhoto(mCoordinatorTabLayout.getImageView(),R.drawable.course_image ,CourseInfoActivity.this , courseInfoBean.getInfo().getCover()==null||courseInfoBean.getInfo().getCover().equals("")?"http://ww.baid.com":courseInfoBean.getInfo().getCover(), false);
        }catch (Exception e){
            GlideManager.getInstance().setCommonPhoto(mCoordinatorTabLayout.getImageView(),R.drawable.course_image ,CourseInfoActivity.this , "http://ww.baid.com", false);
        }

    }

    private void courseOrTrain(CourseInfoBean courseInfoBean) {
        if(courseInfoBean.getInfo().getIs_buy().equals("0")){
            //未添加
            isbuy_state(isCourseTaskInfo?"立即报名":"加入学习",true,false,false);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("1")){
            //已加入
//            isbuy_state(applied,false,true,true);
            isbuy_state(isCourseTaskInfo?"已报名":"已加入",false,false,true);
            //已加入的默认显示“课程大纲”模块
            mViewPager.setCurrentItem(1,true);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("2")){
            //已截止
//            isbuy_state(abort,false,true,false);
            isbuy_state("报名截止",false,true,false);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("3")){
            //已结束
//            isbuy_state(finished,false,true,false);
            isbuy_state("已结束",false,true,false);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("4")){
            //已截止并报名
//            isbuy_state(abort,false,true,true);
            isbuy_state("报名截止",false,true,true);
        }else if(courseInfoBean.getInfo().getIs_buy().equals("5")){
            //已结束并报名
//            isbuy_state(finished,false,true,true);
            isbuy_state("已结束",false,true,true);
        }
    }

    private void isbuy_state(String apply,boolean enabled,boolean selected,boolean haveBuy) {
        isbuy_tv.setText(apply);
        isbuy_tv.setEnabled(enabled);
        isbuy_tv.setSelected(selected);
        isHaveBuy=haveBuy;
    }

    @Override
    public void joinSuccess(String msg) {
        courseInfoBean.getInfo().setIs_buy("1");
//        if(isCourseTaskInfo){
//            courseOrTrain(courseInfoBean);
//        }else {
//            isbuy_state("已报名",false,true,true);
//        }
        courseOrTrain(courseInfoBean);
        showErrorMsg(msg);
        courseOutFragment.setisBuy(true);
    }

    @Override
    public void ShowLoadView() {
            multipleStatusView.showLoading();
    }

    @Override
    public void NoNetWork() {
        multipleStatusView.showNoNetwork();
    }

    @Override
    public void NoData() {
        multipleStatusView.showEmpty();
    }

    @Override
    public void ErrorData() {
        multipleStatusView.showError();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void showErrorMsg(String msg) {
        showLongToast(msg);
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
        multipleStatusView.showContent();
        initCoorLayout((CourseInfoBean) o);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isLoad)
            return;
//        mPresenter.getCourseInfoData(courseId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getIntanceBus().unSubscribe(this);
    }
}
