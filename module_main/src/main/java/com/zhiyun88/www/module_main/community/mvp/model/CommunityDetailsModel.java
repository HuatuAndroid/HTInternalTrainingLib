package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.CommunityDetailsBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentBean;
import com.zhiyun88.www.module_main.community.bean.DetailsLikeBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDetailsContranct;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class CommunityDetailsModel implements CommunityDetailsContranct.CommunityDetailsModel {
    @Override
    public Observable<Result<CommunityDetailsBean>> getCommunityDetails(String question_id) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getCommunityDetails(question_id, "1");
    }

    @Override
    public Observable<Result<DetailsCommentBean>> getCommentList(String question_id, String st, int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getCommentList(question_id, "1", page);
    }

    @Override
    public Observable<Result> sendComment(String question_id, String content, String is_anonymity, String comment_id) {
        Map<String, String> map = new HashMap<>();
        map.put("question_id", question_id);
        map.put("content",content );
        map.put("is_anonymity", is_anonymity);
        map.put("comment_id",comment_id );
        return HttpManager.newInstance().getService(CommunityServiceApi.class).sendComment(map);
    }

    @Override
    public Observable<Result<DetailsLikeBean>> setLike(String question_id) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).setDetailsLike(question_id);
    }
}
