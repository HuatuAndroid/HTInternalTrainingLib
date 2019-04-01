package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.contranct.TopicEditContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class TopicEditModel implements TopicEditContranct.Model {


    @Override
    public Observable<Result> sendUpdateTopic(String topicId, String title, String content, String is_anonymity) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).editTopic(topicId,title,content,is_anonymity);
    }

    @Override
    public Observable<Result<NImageListsBean>> commitImage(Map<String, RequestBody> map) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commitImage(map);
    }

    @Override
    public Observable<Result> commitTopicData(String group_id, String title, String content, String is_anonymity, String type) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commitTopicData(group_id, title, content, is_anonymity, type);
    }
}
