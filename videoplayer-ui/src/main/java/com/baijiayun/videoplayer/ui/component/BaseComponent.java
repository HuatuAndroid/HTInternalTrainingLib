package com.baijiayun.videoplayer.ui.component;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.listener.IComponent;
import com.baijiayun.videoplayer.ui.listener.IComponentEventListener;
import com.baijiayun.videoplayer.ui.listener.PlayerStateGetter;

/**
 * Created by yongjiaming on 2018/8/7
 */

public abstract class BaseComponent implements IComponent, View.OnAttachStateChangeListener{

    private View view;
    //当前组件的唯一标识
    protected String key;
    private Context mContext;
    private IComponentEventListener componentEventListener;
    private PlayerStateGetter stateGetter;

    public BaseComponent(Context context){
        mContext = context;
        setKey();
        view = onCreateComponentView(context);
        onInitView();
        view.addOnAttachStateChangeListener(this);
    }

    @Override
    public void setComponentVisibility(int visibility) {
        if(view.getVisibility() != visibility){
            view.setVisibility(visibility);
        }
    }

    @Override
    public View getView() {
        return view;
    }

    protected abstract View onCreateComponentView(Context context);

    protected abstract void onInitView();

    protected final <T extends View> T findViewById(int id){
        return view.findViewById(id);
    }

    //发射当前组件的事件
    protected void notifyComponentEvent(int eventCode, Bundle bundle){
        if(componentEventListener != null){
            componentEventListener.onReceiverEvent(eventCode, bundle);
        }
    }

    @Override
    public void setComponentEventListener(IComponentEventListener componentEventListener) {
        this.componentEventListener = componentEventListener;
    }

    @Override
    public void bindStateGetter(PlayerStateGetter stateGetter) {
        this.stateGetter = stateGetter;
    }

    @Override
    public void destroy() {
        view.removeOnAttachStateChangeListener(this);
        if(componentEventListener != null){
            componentEventListener = null;
        }
        if(stateGetter != null){
            stateGetter = null;
        }
        mContext = null;
    }

    @Override
    public PlayerStateGetter getStateGetter() {
        return stateGetter;
    }

    public final void requestPause(Bundle bundle) {
        notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_PAUSE, bundle);
    }

    public final void requestPlay(Bundle bundle) {
        notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_PLAY, bundle);
    }

    public final void requestSeek(Bundle bundle) {
        notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_SEEK, bundle);
    }

    public final void requestStop(Bundle bundle) {
        notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_STOP, bundle);
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }

    @Override
    public void onComponentEvent(int eventCode, Bundle bundle) {

    }

    /**
     * 播放器正常事件回调
     * @param eventCode
     * @param bundle
     */
    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onCustomEvent(int eventCode, Bundle bundle) {

    }

    /**
     * 播放器报错回调
     * @param eventCode 事件code
     * @param bundle
     */
    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    protected abstract void setKey();

    @Override
    public String getKey(){
        return key;
    }

    protected final Context getContext(){
        return mContext;
    }
}
