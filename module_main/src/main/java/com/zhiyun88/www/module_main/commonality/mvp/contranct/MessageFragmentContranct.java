package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.MessageBean;

import io.reactivex.Observable;

public interface MessageFragmentContranct {
    interface MessageFragmentView extends MvpView{
        void loadMore(boolean isLoadMore);
        void setIsRead();
    }
    interface MessageFragmentModel extends BaseModel{
        Observable<Result<MessageBean>> getMessageData(String user_id, int message_type, int is_app, int page);
        Observable<Result> setMessageState(String user_id, String message_id);
    }
    abstract class MessageFragmentPresenter extends BasePreaenter<MessageFragmentView,MessageFragmentModel>{
            public abstract void getMessageData(String user_id,int message_type,int is_app,int page);
            public abstract void setMessageState(String user_id,String message_id);
    }
}
