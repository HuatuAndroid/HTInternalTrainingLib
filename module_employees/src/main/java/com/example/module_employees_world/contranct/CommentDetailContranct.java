package com.example.module_employees_world.contranct;

import android.widget.TextView;

import com.example.module_employees_world.bean.CommentChildrenBean;
import com.example.module_employees_world.bean.CommentDetailBean;
import com.example.module_employees_world.bean.CommentLikeBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;

import java.util.List;

import io.reactivex.Observable;

/**
 * author:LIENLIN
 * date:2019/3/30
 */
public interface CommentDetailContranct {

    interface CommentDetialView extends MvpView{
        void commentChildrenList(List<CommentChildrenBean> childrenBeans);
        void commentLike(CommentLikeBean commentLikeBean, TextView tvZan);
        void deleteComment(int position);
        void isLoadMore(boolean isLoadMore);
    }

    interface CommentDetailModel extends BaseModel{
        Observable<Result<CommentDetailBean>> commentDetail(int commentId);
        Observable<Result<List<CommentChildrenBean>>> commentChildrenList(int commentId,int page,int limit,int st);
        Observable<Result<CommentLikeBean>> commentLike(String comment_id);
        Observable<Result> deleteComment(String commentId);
    }

    abstract class CommentDetailPresenter extends BasePreaenter<CommentDetialView,CommentDetailModel>{
        public abstract void commentDetail(int commentId);
        public abstract void commentChildrenList(int commentId,int page,int limit,int st);
        public abstract void commentLike(String comment_id, TextView tvZan);
        public abstract void deleteComment(String deleteComment,int position);
    }
}
