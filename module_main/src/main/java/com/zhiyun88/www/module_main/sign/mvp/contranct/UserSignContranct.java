package com.zhiyun88.www.module_main.sign.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.sign.bean.SignBean;

import io.reactivex.Observable;

public interface UserSignContranct {
    interface UserSignView extends MvpView{
        void showErrorMsg(String s, final boolean isclose,boolean isSign);
    }
    interface UserSignModel extends BaseModel{
        Observable<Result<SignBean>> getUserSign(String url);
        Observable<Result> userSign(String basis_id,String  chapter_id);
    }
    abstract class UserSignPresenter extends BasePreaenter<UserSignView,UserSignModel>{
            public abstract void userSign(String basis_id,String  chapter_id);
            public abstract void getUserSign(String url);
    }
}
