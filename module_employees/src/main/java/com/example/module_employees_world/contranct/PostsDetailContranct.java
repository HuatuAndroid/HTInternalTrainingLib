package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.CommentListBean;
import com.example.module_employees_world.bean.PostDetailBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;

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
    }

    interface PostsDetailModel extends BaseModel{
        Observable<Result<CommentListBean>> getCommentList(String question_id,String st,String page,String limit);
        Observable<Result<PostDetailBean>> getPostDetail(String question_id, String st);
    }

    abstract class PostDetailPresenter extends BasePreaenter<PostsDetailView,PostsDetailModel>{
        public abstract void getCommentList(String question_id,String st,String page,String limit);
        public abstract void getPostDetail(String question_id,String st);
    }

}
