package com.zhiyun88.www.module_main.course.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.wb.baselib.interfaces.OnFilterDoneListener;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.DropDownMenu;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.adapter.CourseMainAdapter;
import com.zhiyun88.www.module_main.course.adapter.DropMenuAdapter;
import com.zhiyun88.www.module_main.course.api.CourseServiceApi;
import com.zhiyun88.www.module_main.course.bean.CourseMainBean;
import com.zhiyun88.www.module_main.course.bean.CourseMainClassflyBean;
import com.zhiyun88.www.module_main.course.bean.CourseMainData;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseMainContranct;
import com.zhiyun88.www.module_main.course.mvp.presenter.CourseMainPresenter;
import com.zhiyun88.www.module_main.main.ui.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class CourseMainActivity extends MvpActivity<CourseMainPresenter> implements OnFilterDoneListener,CourseMainContranct.CourseMainView{
    private DropDownMenu dropDownMenu;
    private int page=1;
    private List<CourseMainData> courseItemListDataList;
    private CourseMainAdapter mAdapter;
    private ListView mListView;
    private MultipleStatusView multipleStatusView;
    private String classId1="",classId2="",hotId="",newId="",freeId="",priceId="";
    private SmartRefreshLayout swipeRefreshLayout;
    private DropMenuAdapter dropMenuAdapter;
    private CourseMainClassflyBean courseClassflyBean;
    private TopBarView course_tb;
    @Override
    protected CourseMainPresenter onCreatePresenter() {
        return new CourseMainPresenter(this);
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_coursemain_layout);
        dropDownMenu = getViewById(R.id.dropDownMenu);
        multipleStatusView=getViewById(R.id.mFilterContentView);
        multipleStatusView.showContent();
        multipleStatusView.showLoading();
        course_tb=getViewById(R.id.course_tb);
        swipeRefreshLayout=getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(swipeRefreshLayout,this).defaultRefreSh();
        mListView=getViewById(R.id.p_lv);
        courseItemListDataList=new ArrayList<>();
        mAdapter=new CourseMainAdapter(courseItemListDataList,this);
        mListView.setAdapter(mAdapter);
        mListView.setDivider(null);
        initData();
        mPresenter.getItemData("","","","","","","",page);
    }

    private void initData(){
        Observable<Result<CourseMainClassflyBean>> observable = HttpManager.newInstance().getService(CourseServiceApi.class).getClassFlyData("1");
        HttpManager.newInstance().commonRequest(observable, new BaseObserver<Result<CourseMainClassflyBean>>(AppUtils.getContext()) {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSuccess(Result<CourseMainClassflyBean> courseSelectClassBeanResult) {
                        if(courseSelectClassBeanResult.getData()==null){

                        }else {
                            if(courseSelectClassBeanResult.getData().getList()==null||courseSelectClassBeanResult.getData().getList().size()==0){
                            }else {
                                initFilterDropDownView(courseSelectClassBeanResult.getData());
                            }
                        }
                    }

                    @Override
                    public void onFail(ApiException e) {

                    }
                }
        );
    }
    private void initFilterDropDownView(CourseMainClassflyBean courseClassflyBeans) {
        String[] titleList = new String[]{"分类", "排序", "筛选"};
        this.courseClassflyBean=courseClassflyBeans;
        dropMenuAdapter=new DropMenuAdapter(CourseMainActivity.this, titleList, this,courseClassflyBean);
        dropMenuAdapter.setSelectOption(0);
        dropDownMenu.setMenuAdapter(dropMenuAdapter);
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String id1,String id2) {
        LogTools.e("查看标题"+positionTitle);
        if(dropDownMenu==null){
        }else {
            if(position==2){
                /**
                 * 由于此监听的调用时封装在依赖库中，当position=2时positionTitle为索引值， 遂只能再此再次再做此判断
                 */
                if ("0".equals(positionTitle)){
                    positionTitle="全部";
                }else if ("1".equals(positionTitle)){
                    positionTitle="直播课";
                }else if ("2".equals(positionTitle)){
                    positionTitle="录播课";
                }else if ("3".equals(positionTitle)){
                    positionTitle="音频课";
                }else {
                    positionTitle="筛选";
                }
            }
            dropDownMenu.setPositionIndicatorText(position,positionTitle);
            dropDownMenu.close();
        }
        if(position==0){
            classId1=id1;
            classId2=id2;
        }else if(position==1){
            //排序
            if(id1.equals("0")){
                hotId="";
                newId="";
                priceId="";
            } else if(id1.equals("1")){
                newId="2";
                hotId="";
                priceId="";
            } else if(id1.equals("2")){
                hotId="2";
                newId="";
                priceId="";
            } else if(id1.equals("3")){
                priceId="1";
                hotId="";
                newId="";
            } else if(id1.equals("4")){
                priceId="2";
                hotId="";
                newId="";
            }
        }else if(position==2){
            //筛选
            freeId=positionTitle;
        }
        page=1;
        Log.e("筛选条件","标题"+classId1+"分类"+classId2+"最热"+hotId+"最新"+newId+"免费"+freeId+"价格"+priceId);
        mPresenter.getItemData("",classId1,classId2,hotId,newId,freeId,priceId,page);
    }
    @Override
    public void onClick(View view) {
    }
    @Override
    public void SuccessData(Object o) {
        CourseMainBean courseMainBean= (CourseMainBean) o;
        Log.e("课程进来了","-------");
        if(courseClassflyBean==null){
        }else {
            View view=dropDownMenu.findViewAtPosition(2);
        }
        if(page==1){
            courseItemListDataList.clear();
        }
        courseItemListDataList.addAll(courseMainBean.getList());
        mAdapter.notifyDataSetChanged();
        page++;
        multipleStatusView.showContent();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadData(boolean is) {
        RefreshUtils.getInstance(swipeRefreshLayout,this).isLoadData(is);
    }

    @Override
    protected void setListener() {
        swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getItemData("",classId1,classId2,hotId,newId,freeId,priceId,page);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mPresenter.getItemData("",classId1,classId2,hotId,newId,freeId,priceId,page);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        course_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(TopBarView.ACTION_RIGHT_BUTTON==action){
                    ToActivityUtil.newInsance().toNextActivity(CourseMainActivity.this,SearchActivity.class );
                }else  if(action==TopBarView.ACTION_LEFT_BUTTON){
                    finish();
                }
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
        Log.e("进来了","--null--");
        courseItemListDataList.clear();
        mAdapter.notifyDataSetChanged();
        multipleStatusView.showEmpty();
    }

    @Override
    public void ErrorData() {
        multipleStatusView.showError();
    }

    @Override
    public void showErrorMsg(String msg) {
            showLongToast(msg);
    }

    @Override
    public void showLoadV(String msg) {

    }

    @Override
    public void closeLoadV() {

    }
}
