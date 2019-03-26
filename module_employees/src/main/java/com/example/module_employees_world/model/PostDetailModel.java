package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.contranct.PostsDetailContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import io.reactivex.Observable;

/**
 * author:LIENLIN
 * date:2019/3/25
 */
public class PostDetailModel implements PostsDetailContranct.PostsDetailModel {
    @Override
    public Observable<Result<CommentListBean>> getCommentList(String question_id, String st) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getCommentList(question_id, st);
    }
}
