package com.baijiayun.videoplayer.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.baijiayun.videoplayer.event.EventKey;
import com.baijiayun.videoplayer.ui.component.ComponentManager;
import com.baijiayun.videoplayer.ui.event.EventDispatcher;
import com.baijiayun.videoplayer.ui.listener.IComponent;
import com.baijiayun.videoplayer.ui.listener.IComponentEventListener;
import com.baijiayun.videoplayer.ui.listener.IFilter;
import com.baijiayun.videoplayer.ui.listener.PlayerStateGetter;

/**
 * Created by yongjiaming on 2018/8/7
 * 组件容器类
 */

public class ComponentContainer extends FrameLayout {
    private ComponentManager componentManager;
    private IComponentEventListener onComponentEventListener;
    private String key;

    private IComponentEventListener internalComponentEventListener =
            new IComponentEventListener() {
                @Override
                public void onReceiverEvent(int eventCode, Bundle bundle) {
                    if (bundle != null) {
                        key = bundle.getString(EventKey.KEY_PRIVATE_EVENT);
                    }
                    //通知外部监听
                    if (onComponentEventListener != null) {
                        onComponentEventListener.onReceiverEvent(eventCode, bundle);
                    }
                    //通知其它component
                    if (eventDispatcher != null) {
                        eventDispatcher.dispatchComponentEvent(component ->
                                TextUtils.isEmpty(key) || component.getKey().equals(key), eventCode, bundle);
                    }
                }
            };
    private EventDispatcher eventDispatcher;
    private GestureDetector mGestureDetector;
    private PlayerStateGetter stateGetter;

    private boolean mGestureEnable = true;

    public ComponentContainer(@NonNull Context context) {
        this(context, null);
    }

    public ComponentContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComponentContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGestureDetector(context);
    }

    private void initGestureDetector(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                eventDispatcher.dispatchTouchEventOnSingleTabUp(e);
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                eventDispatcher.dispatchTouchEventOnDoubleTabUp(e);
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                eventDispatcher.dispatchTouchEventOnDown(e);
                return mGestureEnable;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                eventDispatcher.dispatchTouchEventOnScroll(e1, e2, distanceX, distanceY);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            eventDispatcher.dispatchTouchEventOnEndGesture();
        }
        return mGestureEnable && mGestureDetector.onTouchEvent(event);
    }

    private void addComponent(IComponent component) {
        addView(component.getView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        component.setComponentEventListener(internalComponentEventListener);
    }

    private void removeComponent(IComponent component) {
        removeView(component.getView());
        component.destroy();
    }

    public void setOnComponentEventListener(IComponentEventListener componentEventListener) {
        onComponentEventListener = componentEventListener;
    }

    public final void dispatchPlayEvent(int eventCode, Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchPlayEvent(eventCode, bundle);
        }
    }

    public final void dispatchPlayEvent(IFilter filter, int eventCode, Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchPlayEvent(filter, eventCode, bundle);
        }
    }

    public final void dispatchErrorEvent(int eventCode, Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchErrorEvent(eventCode, bundle);
        }
    }

    public final void dispatchErrorEvent(IFilter filter, int eventCode, Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchErrorEvent(filter, eventCode, bundle);
        }
    }

    public final void dispatchCustomEvent(int eventCode, Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchCustomEvent(eventCode, bundle);
        }
    }

    public final void dispatchCustomEvent(IFilter filter, int eventCode, Bundle bundle) {
        if (eventDispatcher != null) {
            eventDispatcher.dispatchCustomEvent(filter, eventCode, bundle);
        }
    }

    public void destroy() {
        onComponentEventListener = null;
        internalComponentEventListener = null;
        eventDispatcher = null;
        stateGetter = null;
        componentManager.forEach(this::removeComponent);
        componentManager.release();
    }

    public boolean isGestureEnable() {
        return mGestureEnable;
    }

    public void setGestureEnable(boolean gestureEnable) {
        this.mGestureEnable = gestureEnable;
    }

    public PlayerStateGetter getStateGetter() {
        return stateGetter;
    }

    public void init(final PlayerStateGetter stateGetter, ComponentManager componentManager) {
        this.stateGetter = stateGetter;
        this.componentManager = componentManager;
        eventDispatcher = new EventDispatcher(componentManager);
        componentManager.forEach(component -> {
            component.bindStateGetter(stateGetter);
            addComponent(component);
        });
    }
}
