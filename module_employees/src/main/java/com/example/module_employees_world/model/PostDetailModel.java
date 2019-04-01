package com.example.module_employees_world.model;

import com.example.module_employees_world.CommunityServiceApi;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.PostDetailBean;
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
    public Observable<Result<CommentListBean>> getCommentList(String question_id, String st,String page,String limit) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getCommentList(question_id, st,page,limit);
    }

    @Override
    public Observable<Result<PostDetailBean>> getPostDetail(String question_id, String st) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getPostDetail(question_id,st);
    }

    @Override
    public Observable<Result<CommentLikeBean>> commentLike(String comment_id) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).commentLike(comment_id);
    }

    @Override
    public Observable<Result<CommentLikeBean>> postsLike(String question_id) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).postsLike(question_id);
    }

    @Override
    public Observable<Result> deletePost(String postId) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).deletePost(postId);
    }

    @Override
    public Observable<Result> deleteComment(String commentId) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).deleteComment(commentId);
    }

}
