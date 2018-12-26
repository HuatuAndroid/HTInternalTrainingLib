package com.zhiyun88.www.module_main.course.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.course.bean.CommentListBean;

import io.reactivex.Observable;

public interface CommentListContranct  {
    interface CommentListView extends MvpView{
        void isLoadMore(boolean b);
    }
    interface CommentListModel extends BaseModel{
        Observable<Result<CommentListBean>> getCommentListData(String comment_shop_id, int page);
    }
    abstract class CommentListPresenter extends BasePreaenter<CommentListView,CommentListModel>{
        public abstract void getCommentListData(String comment_shop_id,int page);
    }
}
