package com.zhiyun88.www.module_main.library.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.library.api.LibraryServiceApi;
import com.zhiyun88.www.module_main.library.bean.LibraryTypeBean;
import com.zhiyun88.www.module_main.library.mvp.contranct.LibraryContranct;

import io.reactivex.Observable;

public class LibraryModel implements LibraryContranct.LibraryModel {

    @Override
    public Observable<Result<LibraryTypeBean>> getLibraryType() {
        return HttpManager.newInstance().getService(LibraryServiceApi.class).getLibraryType(1);
    }
}
