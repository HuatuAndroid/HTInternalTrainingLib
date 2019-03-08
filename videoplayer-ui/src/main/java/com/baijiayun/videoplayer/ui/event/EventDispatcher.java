package com.baijiayun.videoplayer.ui.event;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.baijiayun.videoplayer.event.OnPlayerEventListener;
import com.baijiayun.videoplayer.ui.component.ComponentManager;
import com.baijiayun.videoplayer.ui.listener.IFilter;
import com.baijiayun.videoplayer.ui.listener.OnLoopListener;
import com.baijiayun.videoplayer.ui.listener.OnTouchGestureListener;

/**
 * Created by yongjiaming on 2018/8/7
 * 事件分发器
 */

public class EventDispatcher {

    private ComponentManager componentManager;

    public EventDispatcher(ComponentManager manager) {
        this.componentManager = manager;
    }

    public void dispatchPlayEvent(final int eventCode, final Bundle bundle) {
        if(eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_SURFACE_UPDATE || eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_SURFACE_HOLDER_UPDATE){
            return;
        }
        componentManager.forEach(component -> component.onPlayerEvent(eventCode, bundle));
        recycleBundle(bundle);
    }

    public void dispatchPlayEvent(IFilter filter, final int eventCode, final Bundle bundle) {
        componentManager.forEach(filter, component -> component.onPlayerEvent(eventCode, bundle));
        recycleBundle(bundle);
    }

    public void dispatchErrorEvent(final int eventCode, final Bundle bundle) {
        componentManager.forEach(component -> component.onErrorEvent(eventCode, bundle));
        recycleBundle(bundle);
    }

    public void dispatchErrorEvent(IFilter filter, final int eventCode, final Bundle bundle) {
        componentManager.forEach(filter, component -> component.onErrorEvent(eventCode, bundle));
        recycleBundle(bundle);
    }

    public void dispatchComponentEvent(final int eventCode, final Bundle bundle) {
        componentManager.forEach(component -> component.onComponentEvent(eventCode, bundle));
        recycleBundle(bundle);
    }

    /**
     * 给其它component发送事件
     * @param filter
     * @param eventCode
     * @param bundle
     */
    public void dispatchComponentEvent(IFilter filter, final int eventCode, final Bundle bundle) {
        componentManager.forEach(filter, component -> component.onComponentEvent(eventCode, bundle));
        recycleBundle(bundle);
    }

    public void dispatchCustomEvent(final int eventCode, final Bundle bundle) {
        componentManager.forEach(component -> component.onCustomEvent(eventCode, bundle));
        recycleBundle(bundle);
    }

    public void dispatchCustomEvent(IFilter filter, final int eventCode, final Bundle bundle) {
        componentManager.forEach(filter, component -> component.onCustomEvent(eventCode, bundle));
        recycleBundle(bundle);
    }


    //-----------------------------------dispatch gesture touch event-----------------------------------

    public void dispatchTouchEventOnSingleTabUp(final MotionEvent event) {
        filterImplOnTouchEventListener(component -> ((OnTouchGestureListener)component).onSingleTapUp(event));
    }

    public void dispatchTouchEventOnDoubleTabUp(final MotionEvent event) {
        filterImplOnTouchEventListener(component -> ((OnTouchGestureListener)component).onDoubleTap(event));
    }

    public void dispatchTouchEventOnDown(final MotionEvent event) {
        filterImplOnTouchEventListener(component -> ((OnTouchGestureListener)component).onDown(event));
    }

    public void dispatchTouchEventOnScroll(final MotionEvent e1, final MotionEvent e2,
                                           final float distanceX, final float distanceY) {
        filterImplOnTouchEventListener(component -> ((OnTouchGestureListener)component).onScroll(e1, e2, distanceX, distanceY));
    }

    public void dispatchTouchEventOnEndGesture() {
        filterImplOnTouchEventListener(component -> ((OnTouchGestureListener)component).onEndGesture());
    }

    private void filterImplOnTouchEventListener(final OnLoopListener onLoopListener){
        componentManager.forEach(component -> component instanceof OnTouchGestureListener, onLoopListener::onEach);
    }

    private void recycleBundle(Bundle bundle) {
        if (bundle != null){
            bundle.clear();
        }
    }
}
