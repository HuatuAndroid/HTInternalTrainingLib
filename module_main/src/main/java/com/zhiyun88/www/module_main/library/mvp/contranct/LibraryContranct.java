package com.zhiyun88.www.module_main.library.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.library.bean.LibraryTypeBean;

import io.reactivex.Observable;

public interface LibraryContranct {
    interface LibraryView extends BaseView{
    }
    interface LibraryModel extends BaseModel{
        Observable<Result<LibraryTypeBean>> getLibraryType();
    }
    abstract class LibraryPresenter extends BasePreaenter<LibraryView,LibraryModel>{
            public abstract void getLibraryType();
    }
}
