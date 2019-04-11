package com.zhiyun88.www.module_main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

//import com.baijiahulian.BJVideoPlayerSDK;
import com.baijiayun.BJYPlayerSDK;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.wb.baselib.utils.ToActivityUtil;
import com.zhiyun88.www.module_main.api.AppBean;
import com.zhiyun88.www.module_main.api.AppServiceApi;
import com.zhiyun88.www.module_main.call.LoginStatusCall;
import com.zhiyun88.www.module_main.community.ui.CommunityActivity;
import com.zhiyun88.www.module_main.main.ui.MainActivity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class hApp {
    private static hApp mApp;
    public static hApp newInstance(){
        if (mApp==null){
            mApp=new hApp();
        }
        return mApp;
    }
    public void initVideoPlay(Application application){
//        BJVideoPlayerSDK.getInstance().init(application);
        new BJYPlayerSDK.Builder(application)
                .setDevelopMode(false)
                .build();
    }

    /**
     * 调起内训主界面
     * @param source
     * @param uid
     * @param token
     * @param loginStatusCall
     */
    public void toMainActivity(final Object source, final String uid, final String token, final LoginStatusCall loginStatusCall){
        if(loginStatusCall==null){
            throw new NullPointerException("loginStatusCall is not null");
        }
        if(uid==null||uid.equals("")){
            loginStatusCall.LoginError("uid is null",1001);
            return;
        }
        if(token==null||token.equals("")){
            loginStatusCall.LoginError("token is null",1002);
        }
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid);
        HttpManager.newInstance().commonRequest(HttpManager.newInstance().getService(AppServiceApi.class).getLoginInfo(map), new BaseObserver<Result<AppBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<AppBean> o) {
                if(o.getStatus()== AppConfigManager.newInstance().getAppConfig().getHttpCodeSuccess()){
                    //校检成功
                    if (source instanceof Activity) {
                        Map<String,String> map1=new HashMap<>();
                        map1.put("httoken",token);
                        map1.put("Authorization","bearer "+o.getData().getRemember_token());
                        map1.put("uid",uid);
                        HttpConfig.HttpConfigBuilder httpConfig =
                                new HttpConfig.HttpConfigBuilder()
                                .setmBaseUrl(HttpManager.newInstance().getHttpConfig().getmBaseUrl())
                                .setUseCustGson(HttpManager.newInstance().getHttpConfig().isUseCustGson())
                                .setmCacheFolder(HttpManager.newInstance().getHttpConfig().getmCacheFolder())
                                .setmCacheTimeWithNet(HttpManager.newInstance().getHttpConfig().getmCacheTimeWithoutNet())
                                .setmCacheSize(HttpManager.newInstance().getHttpConfig().getmCacheSize())
                                .setmConnectTimeout(HttpManager.newInstance().getHttpConfig().getmConnectTimeout())
                                .setmIsUseCache(HttpManager.newInstance().getHttpConfig().ismIsUseCache())
                                 .setmMapHeader(map1)
                                .setIsReshConfig(true)
                                .setmIsUseLog(HttpManager.newInstance().getHttpConfig().ismIsUseLog());
                        HttpConfig.newInstanceBuild(httpConfig);
                        MainActivity.startForResult((Activity) source);
                    }
                }else {
                    loginStatusCall.LoginError(o.getMsg(),o.getStatus());
                }
            }

            @Override
            public void onFail(ApiException e) {
                loginStatusCall.LoginError(e.getMessage(),e.getErrorCode());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 调起内训界面通用方法
     * @param source 跳转来源
     * @param cls 跳转到
     * @param uid
     * @param token
     * @param showEmployees  是否展示 员工天地
     * @param loginStatusCall
     */
    public void jumpToActivity(final Object source, final Class<?> cls, final String uid, final String token, final boolean showEmployees, final LoginStatusCall loginStatusCall){
        if(loginStatusCall==null){
            throw new NullPointerException("loginStatusCall is not null");
        }
        if(uid==null||uid.equals("")){
            loginStatusCall.LoginError("uid is null",1001);
            return;
        }
        if(token==null||token.equals("")){
            loginStatusCall.LoginError("token is null",1002);
        }
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid);
        HttpManager.newInstance().commonRequest(HttpManager.newInstance().getService(AppServiceApi.class).getLoginInfo(map), new BaseObserver<Result<AppBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<AppBean> o) {
                if(o.getStatus()== AppConfigManager.newInstance().getAppConfig().getHttpCodeSuccess()){
                    //校检成功
                    if (source instanceof Activity) {
                        Map<String,String> map1=new HashMap<>();
                        map1.put("httoken",token);
                        map1.put("Authorization","bearer "+o.getData().getRemember_token());
                        map1.put("uid",uid);
                        HttpConfig.HttpConfigBuilder httpConfig =
                                new HttpConfig.HttpConfigBuilder()
                                        .setmBaseUrl(HttpManager.newInstance().getHttpConfig().getmBaseUrl())
                                        .setUseCustGson(HttpManager.newInstance().getHttpConfig().isUseCustGson())
                                        .setmCacheFolder(HttpManager.newInstance().getHttpConfig().getmCacheFolder())
                                        .setmCacheTimeWithNet(HttpManager.newInstance().getHttpConfig().getmCacheTimeWithoutNet())
                                        .setmCacheSize(HttpManager.newInstance().getHttpConfig().getmCacheSize())
                                        .setmConnectTimeout(HttpManager.newInstance().getHttpConfig().getmConnectTimeout())
                                        .setmIsUseCache(HttpManager.newInstance().getHttpConfig().ismIsUseCache())
                                        .setmMapHeader(map1)
                                        .setIsReshConfig(true)
                                        .setmIsUseLog(HttpManager.newInstance().getHttpConfig().ismIsUseLog())
                                        .setEmployeesWorld(showEmployees);
                        HttpConfig.newInstanceBuild(httpConfig);
//                        CommunityActivity.startForResult((Activity) source);
                        Intent intent = new Intent((Activity) source,cls);
                        ((Activity) source).startActivity(intent);
                    }
                }else {
                    loginStatusCall.LoginError(o.getMsg(),o.getStatus());
                }
            }

            @Override
            public void onFail(ApiException e) {
                loginStatusCall.LoginError(e.getMessage(),e.getErrorCode());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



    /**
     * 调起Schemme协议
     * @param url
     * @param context
     */
    public void toSchemme(String url,Context context){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid) {
            context.startActivity(intent);
        } else {
            Toast.makeText(AppUtils.getContext(),"Schemme not right",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 将打开的app关闭掉
     */
    public void exitApp(){
        Map<String, String> map = new HashMap();
        HttpConfig.newInstance().setmMapHeader(map);
        Intent intent = new Intent();
        intent.setAction("ExitApp");
        AppUtils.getContext().sendBroadcast(intent);
    }

    /**
     * 通过协议调起内训
     * @param uid
     * @param token
     * @param loginStatusCall
     */
    public void toMainActivity(final String uid, final String token, final LoginStatusCall loginStatusCall, final String url, final Context mContext){
        if(loginStatusCall==null){
            throw new NullPointerException("loginStatusCall is not null");
        }
        if(uid==null||uid.equals("")){
            loginStatusCall.LoginError("uid is null",1001);
            return;
        }
        if(token==null||token.equals("")){
            loginStatusCall.LoginError("token is null",1002);
        }
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid);
        HttpManager.newInstance().commonRequest(HttpManager.newInstance().getService(AppServiceApi.class).getLoginInfo(map), new BaseObserver<Result<AppBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<AppBean> o) {
                if(o.getStatus()== AppConfigManager.newInstance().getAppConfig().getHttpCodeSuccess()){
                    //校检成功
                    Map<String,String> map1=new HashMap<>();
                    map1.put("httoken",token);
                    map1.put("Authorization","bearer "+o.getData().getRemember_token());
                    map1.put("uid",uid);
                    HttpConfig.HttpConfigBuilder httpConfig =
                            new HttpConfig.HttpConfigBuilder()
                                    .setmBaseUrl(HttpManager.newInstance().getHttpConfig().getmBaseUrl())
                                    .setUseCustGson(HttpManager.newInstance().getHttpConfig().isUseCustGson())
                                    .setmCacheFolder(HttpManager.newInstance().getHttpConfig().getmCacheFolder())
                                    .setmCacheTimeWithNet(HttpManager.newInstance().getHttpConfig().getmCacheTimeWithoutNet())
                                    .setmCacheSize(HttpManager.newInstance().getHttpConfig().getmCacheSize())
                                    .setmConnectTimeout(HttpManager.newInstance().getHttpConfig().getmConnectTimeout())
                                    .setmIsUseCache(HttpManager.newInstance().getHttpConfig().ismIsUseCache())
                                    .setmMapHeader(map1)
                                    .setIsReshConfig(true)
                                    .setmIsUseLog(HttpManager.newInstance().getHttpConfig().ismIsUseLog());
                    HttpConfig.newInstanceBuild(httpConfig);
//                    loginStatusCall.LoginError(o.getMsg(),1040);
                    toSchemme(url,mContext);
                }else {
                    loginStatusCall.LoginError(o.getMsg(),o.getStatus());
                }
            }

            @Override
            public void onFail(ApiException e) {
                loginStatusCall.LoginError(e.getMessage(),e.getErrorCode());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
