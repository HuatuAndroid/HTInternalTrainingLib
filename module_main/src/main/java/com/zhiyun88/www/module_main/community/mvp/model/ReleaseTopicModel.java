package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.ImageListBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.ReleaseTopicContranct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class ReleaseTopicModel implements ReleaseTopicContranct.ReleaseTopicModel {

    @Override
    public Observable<Result> commitTopicData(String group_id, String title, String content, String is_anonymity, String path) {
        HashMap<String, String> map = new HashMap<>();
        map.put("group_id",group_id );
        map.put("title", title);
        map.put("content", content);
        map.put("is_anonymity", is_anonymity);
        map.put("content_picture", path);
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commitTopicData(map);
    }

    @Override
    public Observable<Result<ImageListBean>> commitImage(Map<String, RequestBody> map) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commitImage(map);
    }
}
