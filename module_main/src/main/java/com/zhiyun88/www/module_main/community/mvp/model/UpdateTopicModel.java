package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.ImageListBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.UpdateTopicContranct;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/2/25
 */
public class UpdateTopicModel implements UpdateTopicContranct.UpdateTopicModel{
    @Override
    public Observable<Result> sendUpdateTopic(String topicId, String title, String content, String is_anonymity) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).editTopic(topicId,title,content,is_anonymity);
    }

    @Override
    public Observable<Result<ImageListBean>> commitImage(Map<String, RequestBody> map) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commitImage(map);
    }
}
