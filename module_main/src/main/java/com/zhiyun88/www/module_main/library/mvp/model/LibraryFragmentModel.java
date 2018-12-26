package com.zhiyun88.www.module_main.library.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.library.api.LibraryServiceApi;
import com.zhiyun88.www.module_main.library.bean.LibraryDataBean;
import com.zhiyun88.www.module_main.library.bean.LibraryDetailsBean;
import com.zhiyun88.www.module_main.library.mvp.contranct.LibraryFragmentContranct;

import io.reactivex.Observable;

public class LibraryFragmentModel implements LibraryFragmentContranct.LibraryFragmentModel {

    @Override
    public Observable<Result<LibraryDataBean>> getLibraryData(String id, int page) {
        return HttpManager.newInstance().getService(LibraryServiceApi.class).getLibraryData(id,page);
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
