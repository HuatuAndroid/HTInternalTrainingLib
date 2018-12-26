package com.zhiyun88.www.module_main.main.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.view.MyRatingBar;
import com.zhiyun88.www.module_main.main.adapter.MyCourseAdapter;
import com.zhiyun88.www.module_main.main.bean.MyCourseListBean;
import com.zhiyun88.www.module_main.main.call.MyCourseCommentCall;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyCourseContranct;
import com.zhiyun88.www.module_main.main.mvp.presenter.MyCoursePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyCourseFragment extends MvpFragment<MyCoursePresenter> implements MyCourseContranct.MyCourseView{

    private ListView listView;
    private MultipleStatusView multipleStatusView;
    private MyCourseAdapter myCourseAdapter;
    private List<MyCourseListBean> myCourseListBeans;
    private int page = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private int course_type;
    private Dialog mDiaLog;
    @Override
    protected MyCoursePresenter onCreatePresenter() {
        return new MyCoursePresenter(this);
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }
    public static MyCourseFragment newInstance(int type) {
        MyCourseFragment taskProgressFragment = new MyCourseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("course_type", type);
        taskProgressFragment.setArguments(bundle);
        return taskProgressFragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_mu_resh_listview);
        course_type = getArguments().getInt("course_type", -1);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).defaultRefreSh();
        listView = getViewById(R.id.p_lv);
        myCourseListBeans = new ArrayList<>();
        myCourseAdapter = new MyCourseAdapter(getActivity(),myCourseListBeans);
        listView.setAdapter(myCourseAdapter);
        smartRefreshLayout.autoRefresh();
        setListener();
    }

    @Override
    protected void setListener() {
        super.setListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // ToActivityUtil.newInsance().toNextActivity(getActivity(),MessageDetailActivity.class);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getMyCourseData(course_type, page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMyCourseData(course_type,page );
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                mPresenter.getMyCourseData(course_type, page);
            }
        });
        myCourseAdapter.setmCall(new MyCourseCommentCall() {
            @Override
            public void userCommentBy(String courseId,int postion) {
                showCommentLayout(courseId,postion);
            }
        });
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
        if (page == 1) {
            myCourseListBeans.clear();
        }
        myCourseListBeans.addAll((Collection<? extends MyCourseListBean>) o);
        multipleStatusView.showContent();
        myCourseAdapter.notifyDataSetChanged();
        page++;
    }


    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void loadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).isLoadData(isLoadMore);
    }

    @Override
    public void successComment(String msg, boolean isPost,int postion) {
        if(mDiaLog==null)
            return;
        if(isPost){
            if(mDiaLog.isShowing()){
                mDiaLog.dismiss();
            }
            myCourseAdapter.updateItem(postion,listView);
        }
        showErrorMsg(msg);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(myCourseListBeans==null){
                return;
            }
            smartRefreshLayout.autoRefresh();
        }
    }
    String pj="5";
    private void showCommentLayout(final String courseId, final int postion){
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.main_mycourse_comment_layout, null);
        mDiaLog=StyledDialog.buildCustom(view,Gravity.CENTER).show();
        final EditText pj_et=view.findViewById(R.id.pj_et);
        TextView post_comment_tv=view.findViewById(R.id.post_comment_tv);
        MyRatingBar myRatingBar=view.findViewById(R.id.pfxx_rb);
        ImageView right_close_img=view.findViewById(R.id.right_close_img);
        right_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDiaLog==null)
                    return;
                if(mDiaLog.isShowing()){
                    mDiaLog.dismiss();
                }
            }
        });
        myRatingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                pj=(int)ratingCount+"";
            }
        });
        post_comment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.postUserComment(courseId,pj_et.getText().toString(),pj+"",postion);
            }
        });

    }
}
