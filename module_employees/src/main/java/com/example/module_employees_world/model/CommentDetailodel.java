package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommentChildrenBean;
import com.example.module_employees_world.bean.CommentDetailBean;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.contranct.CommentDetailContranct;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;

import java.util.List;

import io.reactivex.Observable;

/**
 * author:LIENLIN
 * date:2019/3/30
 */
public class CommentDetailodel implements CommentDetailContranct.CommentDetailModel {
    @Override
    public Observable<Result<CommentDetailBean>> commentDetail(int commentId) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).comment_detail(commentId);
    }

    @Override
    public Observable<Result<List<CommentChildrenBean>>> commentChildrenList(int commentId, int page, int limit, int st) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commentChildrenList(commentId,page,limit,st);
    }

    @Override
    public Observable<Result<CommentLikeBean>> commentLike(String comment_id) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commentLike(comment_id);
    }

    @Override
    public Observable<Result> deleteComment(String commentId) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).deleteComment(commentId);
    }
}
