package com.zhiyun88.www.module_main.main.mvp.model;

import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.main.api.MainServiceApi;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyTaskContranct;

import java.util.HashMap;

import io.reactivex.Observable;

public class MyTaskModel implements MyTaskContranct.MyTaskModel {

    @Override
    public Observable getMyTaskData(String complete_type,String type,int page) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", HttpManager.newInstance().getHttpConfig().getmMapHeader().get("uid"));
      //  map.put("type", type+"");
        map.put("is_complete", complete_type+"");
        map.put("page", page+"");
        return HttpManager.newInstance().getService(MainServiceApi.class).getMyTaskData(map);
    }
}
