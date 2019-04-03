package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.NImageBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * author:LIENLIN
 * date:2019/4/1
 */
public interface CommentSendDialogContranct {

    interface ICommentSendDialogView extends MvpView{
        void sendCommment();
        void commitImage(List<NImageBean> pathList);
    }
    interface ICommentSendDialogModel extends BaseModel{
        Observable<Result<NImageListsBean>> commitImage(Map<String, RequestBody> map);
        Observable<Result> sendComment(String question_id,String content,String comment_picture,String comment_face ,String is_anonymity,String comment_id	);
    }
    abstract class CommentSendDialogPresenter extends BasePreaenter<ICommentSendDialogView,ICommentSendDialogModel>{
        public abstract void commitImage(Map<String, RequestBody> map);
        public abstract void sendComment(String question_id,String content	,String comment_picture,String comment_face ,String is_anonymity,String comment_id);
    }
}
