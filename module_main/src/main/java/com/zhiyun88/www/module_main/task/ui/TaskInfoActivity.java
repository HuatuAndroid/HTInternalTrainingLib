package com.zhiyun88.www.module_main.task.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.common.Constant;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.call.LoginStatusCall;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.dotesting.ui.CommonTestActivity;
import com.zhiyun88.www.module_main.dotesting.ui.CuntActivity;
import com.zhiyun88.www.module_main.hApp;
import com.zhiyun88.www.module_main.task.adapter.TaskInfoListAdapter;
import com.zhiyun88.www.module_main.task.bean.TaskData;
import com.zhiyun88.www.module_main.task.bean.TaskInfoListBean;
import com.zhiyun88.www.module_main.task.mvp.contranct.TaskInfoContranct;
import com.zhiyun88.www.module_main.task.mvp.presenter.TaskInfoPresenter;

import java.util.ArrayList;
import java.util.List;

public class TaskInfoActivity extends MvpActivity<TaskInfoPresenter> implements TaskInfoContranct.TaskInfoView {
    private ListView mListView;
    private MultipleStatusView multiplestatusview;
    private List<TaskData> taskDataLists;
    private TaskInfoListAdapter mAdapter;
    private TextView end_time_tv,task_pross_tv,task_status_tv;
    private TopBarView task_tb;
    private String taskId;
    private boolean isWc=false;
    @Override
    protected TaskInfoPresenter onCreatePresenter() {
        return new TaskInfoPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_task_taskinfolist);
        multiplestatusview=getViewById(R.id.multiplestatusview);
        multiplestatusview.showContent();
        multiplestatusview.showLoading();
        mListView=getViewById(R.id.task_infolist_lv);
        end_time_tv=getViewById(R.id.end_time_tv);
        task_pross_tv=getViewById(R.id.task_pross_tv);
        task_tb=getViewById(R.id.task_tb);
        task_status_tv=getViewById(R.id.task_status_tv);


        try {
            taskId=getIntent().getData().getLastPathSegment();
//            String uid = getIntent().getData().getQueryParameter("uid");
//            String token=getIntent().getData().getQueryParameter("token");
//            taskId =getIntent().getData().getQueryParameter("id");
//            hApp.newInstance().toMainActivity(uid, token, new LoginStatusCall() {
//                @Override
//                public void LoginError(String msg, int code) {
//                    if(code==1040){
//                        mPresenter.getTaskInfoList(taskId);
//                    }else {
//                        ErrorData();
//                    }
//                }
//            });
            mPresenter.getTaskInfoList(taskId);
        } catch (Exception e) {
            taskId = getIntent().getStringExtra("taskId");
            mPresenter.getTaskInfoList(taskId);
        }
        taskDataLists=new ArrayList<>();
        mAdapter=new TaskInfoListAdapter(taskDataLists,this,taskId);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getTaskInfoList(taskId);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskData taskData= (TaskData) parent.getItemAtPosition(position);

                if(taskData.getType().equals("1")||taskData.getType().equals("4")){
                    Intent intent=new Intent(TaskInfoActivity.this, CourseInfoActivity.class);
                    intent.putExtra("courseId",taskData.getId());
                    intent.putExtra("isCourseTaskInfo",false);
                    startActivity(intent);
                }else if(taskData.getType().equals("3")){
                    if(taskData.getComplete().equals("100"))
                        return;
                    ToActivityUtil.newInsance().toNextActivity(TaskInfoActivity.this, CommonTestActivity.class,new String[][]{{"testId",taskData.getId()},{"taskId",taskId},{"testType",taskData.getType().equals("3")?"1":"2"},{"testName",taskData.getName()}});
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        task_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(TopBarView.ACTION_LEFT_BUTTON==action){
                    finish();
                }
            }
        });
    }

    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {

    }

    @Override
    public void closeLoadV() {

    }

    @Override
    public void SuccessData(Object o) {
        isWc=true;
        TaskInfoListBean taskInfoListBean= (TaskInfoListBean) o;
        if(taskInfoListBean.getTask_info()==null){

        }else {
            end_time_tv.setText(taskInfoListBean.getTask_info().getEnd_date()+"结束");
            int complete = (int) taskInfoListBean.getTask_info().getComplete();
            task_pross_tv.setText("完成"+complete+"%");
            if(taskInfoListBean.getTask_info().getTask_states().equals("1")){
                task_status_tv.setText("未开始");
            }else if(taskInfoListBean.getTask_info().getTask_states().equals("2")){
                task_status_tv.setText("已结束");
            }else{
                task_status_tv.setText("待完成");
            }
            if (complete >= 100) {
                task_status_tv.setText("已完成");
            }
            task_tb.getCenterTextView().setText(taskInfoListBean.getTask_info().getName());
        }
        taskDataLists.clear();
        taskDataLists.addAll(taskInfoListBean.getTask_data());
        mAdapter.notifyDataSetChanged();
        multiplestatusview.showContent();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(taskDataLists==null)
            return;
        if(!isWc)
            return;
        ShowLoadView();
        mPresenter.getTaskInfoList(taskId);
    }
}
