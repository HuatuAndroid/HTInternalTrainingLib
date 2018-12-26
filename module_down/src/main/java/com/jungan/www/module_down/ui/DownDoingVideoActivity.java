package com.jungan.www.module_down.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baijia.player.playback.downloader.PlaybackDownloader;
import com.baijiahulian.downloader.download.VideoDownloadManager;
import com.baijiayun.download.DownloadManager;
import com.baijiayun.download.DownloadTask;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.jungan.www.common_down.BjyBackPlayDownManager;
import com.jungan.www.common_down.config.DownVideoMessageTypeConfig;
import com.jungan.www.module_down.R;
import com.jungan.www.module_down.adapter.DodingItemAdapter;
import com.jungan.www.module_down.call.DoingDownCall;
import com.jungan.www.module_down.call.DownHaveVideoCall;
import com.jungan.www.module_down.mvp.contranct.DownDoingVideoContranct;
import com.jungan.www.module_down.mvp.presenter.DownDoingVideoPresenter;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.wb.rxbus.taskBean.RxBus;

import java.util.ArrayList;
import java.util.List;

public class DownDoingVideoActivity extends MvpActivity<DownDoingVideoPresenter> implements DownDoingVideoContranct.DownDoingVideoView,DoingDownCall{
    private MultipleStatusView multipleStatusView;
    private ListView mListView;
    private List<DownloadTask> downloadTasks;
    private DodingItemAdapter mAdapter;
    private TopBarView mTopBarView;
    private LinearLayout bottom_ll,status_ll;
    private TextView delect_select_tv,delect_num_tv,all_pase_tv,all_start_tv;
    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.down_havedown_layout);
        multipleStatusView=getViewById(R.id.multiplestatusview);
        multipleStatusView.showContent();
        multipleStatusView.showLoading();
        mListView=getViewById(R.id.p_lv);
        mTopBarView=getViewById(R.id.top_tb);
        delect_select_tv=getViewById(R.id.delect_select_tv);
        delect_num_tv=getViewById(R.id.delect_num_tv);
        bottom_ll=getViewById(R.id.bottom_ll);
        all_pase_tv=getViewById(R.id.all_pase_tv);
        all_start_tv=getViewById(R.id.all_start_tv);
        status_ll=getViewById(R.id.status_ll);
        downloadTasks=new ArrayList<>();
        status_ll.setVisibility(View.VISIBLE);
        mAdapter=new DodingItemAdapter(downloadTasks,DownDoingVideoActivity.this);
        mListView.setAdapter(mAdapter);
        mPresenter.getDownDoingVideo();
    }
    @Override
    public void getDoingDownVideList(List<DownloadTask> lists) {
        if(lists==null||lists.size()==0){
            multipleStatusView.showEmpty();
        }else {
            downloadTasks.clear();
            downloadTasks.addAll(lists);
            mAdapter.notifyDataSetChanged();
            multipleStatusView.showContent();
        }

    }

    @Override
    public void userDelectVideo(Boolean is) {
        RxBus.getIntanceBus().post(DownVideoMessageTypeConfig.RESHVIDEO);
        mPresenter.getDownDoingVideo();
    }

    @Override
    protected DownDoingVideoPresenter onCreatePresenter() {
        return new DownDoingVideoPresenter(this);
    }

    @Override
    protected void setListener() {
        delect_select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delect_select_tv.getText().toString().equals(getResources().getString(R.string.down_all_select))){
                    //全选
                    mAdapter.setAllSelect(true);
                }else {
                    //反选
                    mAdapter.setAllSelect(false);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mTopBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View view, int i, String s) {
                if (i == TopBarView.ACTION_RIGHT_TEXT) {
                    TextView textView=mTopBarView.getRightTextView();
                    if(textView.getText().toString().equals("编辑")){
                        mAdapter.setVist(true);
                        textView.setText("完成");
                        bottom_ll.setVisibility(View.VISIBLE);
                        status_ll.setVisibility(View.GONE);
                    }else {
                        mAdapter.setVist(false);
                        textView.setText("编辑");
                        bottom_ll.setVisibility(View.GONE);
                        status_ll.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                }else if(i==TopBarView.ACTION_LEFT_BUTTON){
                    finish();
                }
            }
        });
        mAdapter.setCall(new DownHaveVideoCall() {
            @Override
            public void selectVideo() {
                delect_num_tv.setText(getResources().getString(R.string.down_delect));
                if(mAdapter.getSelectDown().size()==downloadTasks.size()){
                    delect_select_tv.setText("取消全选");
                }else {
                    delect_select_tv.setText(getResources().getString(R.string.down_all_select));
                }
            }
        });
        delect_num_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMdDialog("提示", getResources().getString(R.string.down_agin_delect), getResources().getString(R.string.down_ok), getResources().getString(R.string.down_cancel), new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        mPresenter.userDelectVideo(mAdapter.getSelectDown());
                    }

                    @Override
                    public void onSecond() {

                    }
                });
            }
        });
        all_start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DownloadTask> tasks=BjyBackPlayDownManager.Instance().getDownloadManager().getManager().getAllTasks();
                for(DownloadTask downloadTask:tasks){
                    downloadTask.start();
                }
            }
        });
        all_pase_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DownloadTask> tasks=BjyBackPlayDownManager.Instance().getDownloadManager().getManager().getAllTasks();
                for(DownloadTask downloadTask:tasks){
                    downloadTask.pause();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle bundle) {
        mTopBarView.getCenterTextView().setText("正在缓存");
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
    public void showErrorMsg(String s) {
        showLongToast(s);
    }

    @Override
    public void showLoadV(String s) {

    }

    @Override
    public void closeLoadV() {

    }

    @Override
    public void SuccessData(Object o) {

    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void doingFinsh() {
        mPresenter.getDownDoingVideo();
        RxBus.getIntanceBus().post(DownVideoMessageTypeConfig.RESHVIDEO);
    }
}
