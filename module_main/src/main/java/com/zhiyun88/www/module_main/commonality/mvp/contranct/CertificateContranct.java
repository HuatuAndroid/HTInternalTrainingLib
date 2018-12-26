package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.CertificateBean;
import com.zhiyun88.www.module_main.commonality.bean.IntegralBean;

import io.reactivex.Observable;

public interface CertificateContranct {
    interface CertificateView extends MvpView {
        void isLoadMore(boolean isLoadMore);
    }
    interface CertificateModel extends BaseModel {
        Observable<Result<CertificateBean>> getCertificateData(int page);
    }
    abstract class CertificatePresenter extends BasePreaenter<CertificateView,CertificateModel> {
        public abstract void getCertificateData(int page);
    }
}
