package com.zhiyun88.www.module_main.commonality.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.commonality.api.CommonalityServiceApi;
import com.zhiyun88.www.module_main.commonality.bean.MyLibraryBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.MyLibraryContranct;
import com.zhiyun88.www.module_main.library.api.LibraryServiceApi;
import com.zhiyun88.www.module_main.library.bean.LibraryDetailsBean;

import io.reactivex.Observable;

public class MyLibraryModel implements MyLibraryContranct.MyLibraryModel {

    @Override
    public Observable<Result<MyLibraryBean>> getLibraryData(int page) {
        return HttpManager.newInstance().getService(CommonalityServiceApi.class).getLibraryData(page);
    }
    @Override
    public Observable<Result> setLibraryCollection(String libraryId, String userId, String isClick) {
        return HttpManager.newInstance().getService(LibraryServiceApi.class).setLibraryCollection(libraryId,userId,isClick);
    }

    @Override
    public Observable<Result<LibraryDetailsBean>> requestLibraryDetails(String id) {
        return HttpManager.newInstance().getService(LibraryServiceApi.class).getLibraryDetails(id);
    }
}
