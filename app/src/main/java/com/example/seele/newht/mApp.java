package com.example.seele.newht;

import com.wb.baselib.BaseApplication;
import com.wb.baselib.appconfig.AppConfig;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.log.LogTools;
import com.zhiyun88.www.module_main.hApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mApp extends BaseApplication {
    @Override
    public String getRootPackAge() {
        return "wwww";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        List<Integer> httpCodeOff = new ArrayList<>();
        httpCodeOff.add(201);
        AppConfig appConfig = new AppConfig.Bulider()
                .setMaxPage(10)
                .setHttpCodeOff(httpCodeOff)
                .bulider();
        AppConfigManager.newInstance().setAppConfig(appConfig);
        LogTools.setDebug(true);
        HttpConfig.HttpConfigBuilder httpConfig =
                new HttpConfig.HttpConfigBuilder()
                        .setUseCustGson(true)
                        .setmBaseUrl("http://test-px.huatu.com")
                .setmIsUseLog(true);
        HttpConfig.newInstanceBuild(httpConfig);
        hApp.newInstance().initVideoPlay(this);
//        ZXingLibrary.initDisplayOpinion(this);


    }
}
