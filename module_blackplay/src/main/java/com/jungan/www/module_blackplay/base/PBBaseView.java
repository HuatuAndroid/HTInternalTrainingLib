package com.jungan.www.module_blackplay.base;

/**
 * Created by wangkangfei on 17/8/17.
 */

public interface PBBaseView<T extends PBBasePresenter> {
    void setPresenter(T t);
}
