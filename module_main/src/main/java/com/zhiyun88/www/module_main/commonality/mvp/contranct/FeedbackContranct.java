package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.main.bean.MyTrainBean;

import io.reactivex.Observable;

public interface FeedbackContranct {
    interface FeedbackView extends BaseView{
    }
    interface FeedbackModel extends BaseModel{
        Observable<Result<MyTrainBean>> getFeedback(String msg);
    }
    abstract class FeedbackPresenter extends BasePreaenter<FeedbackView,FeedbackModel>{
            public abstract void getFeedback(String msg);
    }
}
