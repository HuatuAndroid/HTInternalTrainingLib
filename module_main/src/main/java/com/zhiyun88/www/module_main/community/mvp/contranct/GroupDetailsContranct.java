package com.zhiyun88.www.module_main.community.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.GroupDetailsBean;

import io.reactivex.Observable;

public interface GroupDetailsContranct {
    interface GroupDetailsView extends BaseView {
        void joinGroup();
    }
    interface GroupDetailsModel extends BaseModel {
        Observable<Result<GroupDetailsBean>> getGroupDetails(String group_id, String st);
        Observable<Result> setGroup(String groupId, String states);
    }
    abstract class GroupDetailsPresenter extends BasePreaenter<GroupDetailsView,GroupDetailsModel> {
        public abstract void getGroupDetails(String group_id,String st);
        public abstract void setGroup(String groupId,String states);
    }
}
