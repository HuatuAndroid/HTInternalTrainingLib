package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.IntegralBean;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;

import io.reactivex.Observable;

public interface IntegralContranct {
    interface IntegralView extends BaseView {
    }
    interface IntegralModel extends BaseModel {
        Observable<Result<IntegralBean>> getIntegral(String id);
    }
    abstract class IntegralPresenter extends BasePreaenter<IntegralView,IntegralModel> {
        public abstract void getIntegral(String id);
    }
}
