package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.UserDataBean;

import io.reactivex.Observable;

public interface UserInfoContranct {
    interface UserInfoView extends BaseView{
    }
    interface UserInfoModel extends BaseModel{
        Observable<Result<UserDataBean>> getUserData(String id);
    }
    abstract class UserInfoPresenter extends BasePreaenter<UserInfoView,UserInfoModel>{
            public abstract void getUserData(String id);
    }
}
