package com.zhiyun88.www.module_main.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jungan.www.module_down.ui.DownManagerActivity;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.base.fragment.LazyFragment;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.user.AppLoginUserInfoUtils;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.MyListView;
import com.wb.rxbus.taskBean.RxBus;
import com.wb.rxbus.taskBean.RxLoginBean;
import com.wb.rxbus.taskBean.RxTaskBean;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.UserDataBean;
import com.zhiyun88.www.module_main.commonality.bean.UserInfoBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.UserInfoContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.UserInfoPresenter;
import com.zhiyun88.www.module_main.commonality.ui.CertificateActivity;
import com.zhiyun88.www.module_main.commonality.ui.DownloadManagerActivity;
import com.zhiyun88.www.module_main.commonality.ui.FeedBackActivity;
import com.zhiyun88.www.module_main.commonality.ui.IntegralActivity;
import com.zhiyun88.www.module_main.commonality.ui.MessageActivity;
import com.zhiyun88.www.module_main.commonality.ui.MyLibraryActivity;
import com.zhiyun88.www.module_main.commonality.ui.UserInfoActivity;
import com.zhiyun88.www.module_main.main.adapter.UserListAdapter;
import com.zhiyun88.www.module_main.main.bean.UserMainBean;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MineFragment extends MvpFragment<UserInfoPresenter> implements UserInfoContranct.UserInfoView {
    private MyListView mlv;
    private UserListAdapter mAdapter;
    private List<UserMainBean> userMainBeanList;
    private RelativeLayout havelogin_rel;
    private RelativeLayout nologin_rel;
    private TextView userName_tv;
    private ImageView usertx_img;
    private RelativeLayout user_main_rel;
    private TextView btn_signin,btn_login;
    private UserInfoBean userInfoBean;

    @Override
    public boolean isLazyFragment() {
        return false;
    }

    @Override
    protected UserInfoPresenter onCreatePresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_usermain_layout);
        mlv=getViewById(R.id.mlv);
        havelogin_rel=getViewById(R.id.havelogin_rel);
        nologin_rel=getViewById(R.id.nologin_rel);
        userName_tv=getViewById(R.id.userName_tv);
        usertx_img=getViewById(R.id.usertx_img);
        user_main_rel=getViewById(R.id.user_main_rel);
        btn_signin=getViewById(R.id.btn_signin);
        btn_login=getViewById(R.id.btn_login);
        mlv.setDivider(null);
        userMainBeanList=getUserMainBeanList();
        mAdapter= new UserListAdapter(getActivity(),userMainBeanList);
        mlv.setAdapter(mAdapter);
        ReshData();
        RxBus.getIntanceBus().registerRxBus(RxTaskBean.class, new Consumer<RxTaskBean>() {
            @Override
            public void accept(RxTaskBean rxTaskBean) throws Exception {
                if(rxTaskBean.getTaskType()==901){
                    mAdapter.updateItem(mlv,true);
                }else if(rxTaskBean.getTaskType()==902){
                    mAdapter.updateItem(mlv,false);
                }
            }
        });
        setListener();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(getActivity(),MessageActivity.class));
                }else if (position == 1) {
                    startActivity(new Intent(getActivity(),DownManagerActivity.class));
                }else if (position == 2) {
                    startActivity(new Intent(getActivity(),IntegralActivity.class));
                }else if (position == 3) {
                    startActivity(new Intent(getActivity(),CertificateActivity.class));
                }else if (position == 4) {
                    startActivity(new Intent(getActivity(),MyLibraryActivity.class));
                }else if (position == 5) {
                    startActivity(new Intent(getActivity(),FeedBackActivity.class));
                }
            }
        });
        havelogin_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfoBean == null) return;
                Intent intent = new Intent(getActivity(),UserInfoActivity.class);
                intent.putExtra("userInfoBean", userInfoBean);
                startActivity(intent);
            }
        });
    }

    private void ReshData(){
     //   if(AppLoginUserInfoUtils.getInstance().getUserLoginInfo()==null){
        if(false){
            //未登录
            havelogin_rel.setVisibility(View.GONE);
            nologin_rel.setVisibility(View.VISIBLE);
        }else {
            //已登录
            havelogin_rel.setVisibility(View.VISIBLE);
            nologin_rel.setVisibility(View.GONE);
            mPresenter.getUserData(HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"));
        }
    }
    private List<UserMainBean> getUserMainBeanList(){
        List<UserMainBean> userMainBeans=new ArrayList<>();
        UserMainBean userMainBean1=new UserMainBean(R.drawable.user_main_notify,R.string.main_my_message,null,"");
        UserMainBean userMainBean2=new UserMainBean(R.drawable.user_main_download,R.string.main_my_download,null,"");
        UserMainBean userMainBean3=new UserMainBean(R.drawable.user_main_integral,R.string.main_my_integral,null, "");
        UserMainBean userMainBean4=new UserMainBean(R.drawable.user_main_credential,R.string.main_my_certificate,null,"");
        UserMainBean userMainBean5=new UserMainBean(R.drawable.user_main_library,R.string.main_my_library,null,"");
        UserMainBean userMainBean6=new UserMainBean(R.drawable.user_feedback,R.string.main_problem_feedback,null,"");
        userMainBeans.add(userMainBean1);
        userMainBeans.add(userMainBean2);
        userMainBeans.add(userMainBean3);
        userMainBeans.add(userMainBean4);
        userMainBeans.add(userMainBean5);
        userMainBeans.add(userMainBean6);
        return userMainBeans;
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
        userInfoBean = (UserInfoBean) o;
        userName_tv.setText(userInfoBean.getName());
//        GlideManager.getInstance().setGlideRoundTransImage(usertx_img,R.drawable.user_head,getActivity(), userInfoBean.getAvatar());
        Picasso.with(getActivity()).load(userInfoBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(usertx_img);
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        RxBus.getIntanceBus().unSubscribe(this);
    }
    public void uoda(boolean is){
        if(userName_tv==null){
            Log.e("----->>","yes");
        }else {
            if(mAdapter==null)
                return;
            mAdapter.updateItem(mlv,is);
        }

    }
}
