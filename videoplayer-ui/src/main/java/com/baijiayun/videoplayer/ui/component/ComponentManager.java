package com.baijiayun.videoplayer.ui.component;

import android.content.Context;

import com.baijiayun.BJYPlayerSDK;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.listener.IComponent;
import com.baijiayun.videoplayer.ui.listener.IComponentChangeListener;
import com.baijiayun.videoplayer.ui.listener.IFilter;
import com.baijiayun.videoplayer.ui.listener.OnLoopListener;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by yongjiaming on 2018/8/7
 * 播放器ui组件管理器
 */

public class ComponentManager {
    //component 集合
    private Map<String, IComponent> componentMap;
    private List<IComponentChangeListener> componentChangeListeners;
    private Context context;

    public ComponentManager(Context context){
        this.context = context;
        componentMap = Collections.synchronizedMap(new LinkedHashMap<String, IComponent>());
        componentChangeListeners = new CopyOnWriteArrayList<>();
        generateDefaultComponentList();
    }

    public void addComponent(String key, BaseComponent component){
        componentMap.put(key, component);
        for(IComponentChangeListener changeListener : componentChangeListeners){
            changeListener.onComponentAdd(component);
        }
    }

    public void removeComponent(String key){
        IComponent removedComponent = componentMap.remove(key);
        for(IComponentChangeListener changeListener : componentChangeListeners){
            changeListener.onComponentRemove(removedComponent);
        }
    }

    public void addOnComponentChangeListener(IComponentChangeListener componentChangeListener){
        if(componentChangeListener != null && !componentChangeListeners.contains(componentChangeListener)){
            componentChangeListeners.add(componentChangeListener);
        }
    }

    public void removeOnComponentChangeListener(IComponentChangeListener componentChangeListener){
        componentChangeListeners.remove(componentChangeListener);
    }


    public void forEach(OnLoopListener loopListener){
        for(IComponent component : componentMap.values()){
            loopListener.onEach(component);
        }
    }

    public void forEach(IFilter filter, OnLoopListener loopListener){
        for(IComponent component : componentMap.values()){
            if(filter == null || filter.filter(component)){
                loopListener.onEach(component);
            }
        }
    }

    /**
     * 释放资源
     */
    public void release(){
        componentMap.clear();
        componentChangeListeners.clear();
    }

    /**
     * 默认组合的组件
     */
    public void generateDefaultComponentList(){
        componentMap.clear();
        componentChangeListeners.clear();
        addComponent(UIEventKey.KEY_LOADING_COMPONENT, new LoadingComponent(context));
        addComponent(UIEventKey.KEY_GESTURE_COMPONENT, new GestureComponent(context));
        //controller 需在gesture布局上方，否则会有事件冲突
        addComponent(UIEventKey.KEY_CONTROLLER_COMPONENT, new ControllerComponent(context));
        addComponent(UIEventKey.KEY_ERROR_COMPONENT, new ErrorComponent(context));
        addComponent(UIEventKey.KEY_MENU_COMPONENT, new MenuComponent(context));
        if(BJYPlayerSDK.IS_DEVELOP_MODE){
            addComponent(UIEventKey.KEY_VIDEO_INFO_COMPONENT, new MediaPlayerDebugInfoComponent(context));
        }
    }
}
