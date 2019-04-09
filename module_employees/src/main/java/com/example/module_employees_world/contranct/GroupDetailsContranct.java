package com.example.module_employees_world.contranct;

import com.example.module_employees_world.bean.GroupDetailsBean;
import com.example.module_employees_world.bean.IsBannedBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;

import io.reactivex.Observable;

public interface GroupDetailsContranct {
    interface GroupDetailsView extends BaseView {
        void joinGroup();
        void getIsBanned(IsBannedBean isBannedBean);
    }
    interface GroupDetailsModel extends BaseModel {
        Observable<Result<GroupDetailsBean>> getGroupDetails(String group_id, String st);
        Observable<Result> setGroup(String groupId, String states);
        Observable<Result<IsBannedBean>> getIsBanned();
    }
    abstract class GroupDetailsPresenter extends BasePreaenter<GroupDetailsView,GroupDetailsModel> {
        public abstract void getGroupDetails(String group_id,String st);
        public abstract void setGroup(String groupId,String states);
        public abstract void getIsBanned();
    }
}
