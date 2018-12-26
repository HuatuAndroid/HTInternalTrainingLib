package com.jungan.www.common_dotest.questionview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.adapter.CommonQuestionAdapter;
import com.jungan.www.common_dotest.base.BaseFragment;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.bean.UserPostBean;
import com.jungan.www.common_dotest.bean.UserPostData;
import com.jungan.www.common_dotest.call.TestViewpageCall;
import com.jungan.www.common_dotest.config.QuestTestConfig;
import com.jungan.www.common_dotest.config.QuestionTypeConfig;
import com.jungan.www.common_dotest.view.MyViewPager;
import com.jungan.www.common_dotest.view.NormalViewPager;
import com.wb.baselib.http.HttpManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonQuestionBankView extends LinearLayout{
    private NormalViewPager mViewPager;
    private CommonQuestionAdapter mAdapter;
    private List<QuestionBankBean> questionBankBeanList;
    private Map<String,UserPostData> userPostDataList;
    private UserPostBean userPostBean;
    private TestViewpageCall mCall;
    private int userDoTestNum=0;
    private Handler mhandle = new Handler();
    private long currentSecond = 0;//当前毫秒数
    private boolean isPause = false;//是否暂停
    private int lastOption=0;
    public void setPause(boolean pause) {
        isPause = pause;
        if(!isPause){
            new Thread(timeRunable).start();
        }
    }
    public int getUserDoTestNum() {
        return userDoTestNum;
    }
    public void setmCall(TestViewpageCall mCall) {
        this.mCall = mCall;
    }

    public Map<String, UserPostData> getUserPostDataList() {
        return userPostDataList;
    }
    public UserPostBean getUserPostBean() {
        try {
            userPostBean.setReport_id(questionBankBeanList.get(0).getReport_id());
            List<UserPostData> userPostData=new ArrayList<>();
            for (UserPostData v : userPostDataList.values()) {
                userPostData.add(v);
            }
            userPostBean.setAnswer_data(userPostData);
            userPostBean.setUid(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"));
        }catch (Exception e){
            return null;
        }
        return userPostBean;
    }

    public List<QuestionBankBean> getQuestionBankBeanList() {
        return questionBankBeanList;
    }

    public CommonQuestionBankView(Context context) {
        this(context,null);
    }

    public CommonQuestionBankView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommonQuestionBankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
    private void initView(Context context,AttributeSet attributeSet){
        userPostDataList=new HashMap<>();
        userPostBean=new UserPostBean();
        View mView= LayoutInflater.from(context).inflate(R.layout.layout_question,this);
        mViewPager=mView.findViewById(R.id.question_vp);
        mViewPager.setChangeViewCallback(new NormalViewPager.ChangeViewCallback() {
            @Override
            public void changeView(boolean left, boolean right) {
            }

            @Override
            public void getCurrentPageIndex(int index) {
                if(index<=questionBankBeanList.size()-1){
                    questionBankBeanList.get(lastOption).setUser_dotime(currentSecond+"");
                    lastOption=index;
                    if(questionBankBeanList.get(index).getUser_dotime()==null||questionBankBeanList.get(index).getUser_dotime().equals("")){
//                    //当前没有答题
                        currentSecond=0;
                    }else {
                        currentSecond=Long.parseLong(questionBankBeanList.get(index).getUser_dotime());
                    }
                    if(isPause){
                        setPause(false);
                    }
                }else {
                    setPause(true);
                }
                if(mCall==null)
                    return;
                mCall.currentPage(index);
            }
        });
    }

    public void initData(List<QuestionBankBean> questionBankBean, FragmentManager fragmentManager,boolean analisys,boolean isShow){
        for(QuestionBankBean questionBankBean1:questionBankBean){
            Log.e("虎丘",questionBankBean1.getQuestionType()+"----"+questionBankBean1.getMaterial_type());
        }
        this.questionBankBeanList=questionBankBean;
        QuestTestConfig.testTime=0;
        mAdapter=new CommonQuestionAdapter(fragmentManager,questionBankBean,analisys,isShow);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(questionBankBean.size());
        new Thread(timeRunable).start();
        new Thread(TestData).start();
    }
    Runnable TestData =new  Runnable(){
        @Override
        public void run() {
            for(int i=0;i<questionBankBeanList.size();i++){
                String userAnswer=questionBankBeanList.get(i).getUser_answer();
                if(userAnswer==null||userAnswer.equals("")){
                    UserPostData userPostData=new UserPostData();
                    userPostData.setQues_id(questionBankBeanList.get(i).getQuestId());
                    userPostData.setUser_answer("");
                    userPostData.setUser_time("0");
                    userPostDataList.put(questionBankBeanList.get(i).getQuestId(),userPostData);
                }else {
                    if(userPostDataList.containsKey(questionBankBeanList.get(i).getQuestId())){
                        //已经保存了 需要修改
                        UserPostData userPostData=userPostDataList.get(questionBankBeanList.get(i).getQuestId());
                        userPostData.setUser_time(questionBankBeanList.get(i).getUser_dotime());
                        userPostData.setUser_answer(userAnswer);
                        userDoTestNum=userDoTestNum+1;
                    }else {
                        //没有保存需要添加
                        UserPostData userPostData=new UserPostData();
                        userPostData.setQues_id(questionBankBeanList.get(i).getQuestId());
                        userPostData.setUser_answer(userAnswer);
                        userPostData.setUser_time(questionBankBeanList.get(i).getUser_dotime());
                        userPostDataList.put(questionBankBeanList.get(i).getQuestId(),userPostData);
                    }

//                    if(questionBankBeanList.get(i).getUser_answer()==null||questionBankBeanList.get(i).getUser_answer().equals("")){
////                        userDoTestNum=+1;
//                    }else {
//                        userDoTestNum=+1;
//                    }
                }
            }
        }
    };
    public void cutCurrentQuestion(String userAnswer){
        if(userPostDataList.containsKey(questionBankBeanList.get(mViewPager.getCurrentItem()).getQuestId())){
            //已经保存了 需要修改
            UserPostData userPostData=userPostDataList.get(questionBankBeanList.get(mViewPager.getCurrentItem()).getQuestId());
            if(userPostData.getUser_answer()==null||userPostData.getUser_answer().equals("")){
                userDoTestNum=userDoTestNum+1;
            }
            userPostData.setUser_time(currentSecond/1000+"");
            userPostData.setUser_answer(userAnswer);
        }else {
            //没有保存需要添加
            UserPostData userPostData=new UserPostData();
            userPostData.setQues_id(questionBankBeanList.get(mViewPager.getCurrentItem()).getQuestId());
            userPostData.setUser_answer(userAnswer);
            userPostData.setUser_time(currentSecond/1000+"");
            userPostDataList.put(questionBankBeanList.get(mViewPager.getCurrentItem()).getQuestId(),userPostData);

        }
//        if(questionBankBeanList.get(mViewPager.getCurrentItem()).getUser_answer()==null||questionBankBeanList.get(mViewPager.getCurrentItem()).getUser_answer().equals("")){
////            userDoTestNum=+1;
//        }else {
//
//        }
        QuestTestConfig.testTime=0;

    }
    public int currentPage(){
        return mViewPager.getCurrentItem();
    }
    private Runnable timeRunable = new Runnable() {
        @Override
        public void run() {
            currentSecond = currentSecond + 1000;
            QuestTestConfig.testTime=currentSecond/1000;
                //递归调用本runable对象，实现每隔一秒一次执行任务
            if (!isPause) {
                mhandle.postDelayed(this, 1000);
            }
        }
    };
    public void userSaveTest(int iscoll){
        questionBankBeanList.get(currentPage()).setIsCollect(iscoll);
    }
    public void setTestPage(int page){
        mViewPager.setCurrentItem(page);
    }
}
