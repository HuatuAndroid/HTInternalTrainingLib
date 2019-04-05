package com.example.module_employees_world.contranct;

import android.widget.TextView;

import com.example.module_employees_world.bean.CommentLikeBean;
import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.ParentBean;
import com.example.module_employees_world.bean.PostDetailBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;

import java.util.List;

import io.reactivex.Observable;

/**
 * author:LIENLIN
 * date:2019/3/25
 * 帖子詳情
 */
public interface PostsDetailContranct {

    interface PostsDetailView extends MvpView{
        //void  getCommentList(CommentListBean commentListBean);
        void isLoadMore(boolean moreEnable);
        void getPostDetail(PostDetailBean postDetailBean);
        void commentLike(CommentLikeBean commentLikeBean, TextView tvZan);
        void postsLike(CommentLikeBean commentLikeBean);
        void deletePost();
        void deleteComment(int position,int partenPosition);
        void editQuestion();
        void acceptPosts();
        void acceptComment();
        void commentChildrenList(List<ParentBean> childrenBeans, int partenPosition);
    }

    interface PostsDetailModel extends BaseModel{
        Observable<Result<CommentListBean>> getCommentList(String question_id,String st,String page,String limit);
        Observable<Result<PostDetailBean>> getPostDetail(String question_id, String st);
        Observable<Result<CommentLikeBean>> commentLike(String comment_id);
        Observable<Result<CommentLikeBean>> postsLike(String question_id);
        Observable<Result> deletePost(String postId);
        Observable<Result> deleteComment(String commentId);
        Observable<Result> editQuestion(String type,String id);
        Observable<Result<List<ParentBean>>> commentChildrenList(int commentId,int page,int limit,int st);
        Observable<Result> acceptPosts(String id,String solve_status);
        Observable<Result> acceptComment(String comment_id);
    }

    abstract class PostDetailPresenter extends BasePreaenter<PostsDetailView,PostsDetailModel>{
        public abstract void getCommentList(String question_id,String st,String page,String limit);
        public abstract void getPostDetail(String question_id,String st);
        public abstract void commentLike(String comment_id, TextView tvZan);
        public abstract void postsLike(String question_id);
        public abstract void deletePost(String postId);
        public abstract void deleteComment(String deleteComment,int position,int partenPosition);
        public abstract void editQuestion(String type,String id);
        public abstract void commentChildrenList(int partenPosition, int commentId, int page, int limit, int st);
        public abstract void acceptPosts(String id,String solve_status);
        public abstract void acceptComment(String comment_id);
    }

}
