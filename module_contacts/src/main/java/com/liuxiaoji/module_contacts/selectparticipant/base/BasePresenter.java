package com.liuxiaoji.module_contacts.selectparticipant.base;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class BasePresenter<V> {
    public V mView = null;
    protected Activity mActivity;
    public BasePresenter(@NonNull Activity activity){
        mActivity = activity;
    }
    protected void attach(V paramV) {
        this.mView = paramV;
    }

    protected void dettach() {
        this.mView = null;
        this.mActivity = null;
    }
}
