package com.zhiyun88.www.module_main.dotesting.mvp.contranct;

import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitBean;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitTestBean;

import java.util.List;

import io.reactivex.Observable;

public interface CommonTestContranct {
    interface CommonTestView extends MvpView {
        void submitSuccess(SubmitBean msg);
        void showDidlogMsg(String msg);
    }

    interface CommonTestModel extends BaseModel {
        Observable<List<QuestionBankBean>> getCommonTest(String id, String taskId, int testType);
        Observable<Result<SubmitBean>> submitTest(SubmitTestBean submitTestBean);
    }

    abstract class CommonTestPresenter extends BasePreaenter<CommonTestView, CommonTestModel> {
        public abstract void getCommonTest(String id, String taskId,int testType);
        public abstract void submitTest(SubmitTestBean submitTestBean);
    }
}
