package com.liuxiaoji.module_contacts.selectparticipant.common;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.liuxiaoji.module_contacts.selectparticipant.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class ActivityInstance {
    private ActivityInstance() {
    }

    private volatile static ActivityInstance activityInstance = new ActivityInstance();

    public static ActivityInstance getInstance() {
        appCompatActivities = Collections.synchronizedList(appCompatActivities);
        return activityInstance;
    }

    private static volatile List<AppCompatActivity> appCompatActivities = new ArrayList<>();

    public void addActivity(@NonNull AppCompatActivity appCompatActivity) {
        if (appCompatActivity != null) {
            appCompatActivities.add(appCompatActivity);
        }
    }

    public void removeActivity(@NonNull AppCompatActivity appCompatActivity) {
        if (appCompatActivity != null && appCompatActivities.contains(appCompatActivity)) {
            appCompatActivities.remove(appCompatActivity);
        }
    }

    /**
     * 删除所有activity除了某个activity
     *
     * @param t
     * @param <T>
     */
    public <T extends BaseActivity> void finishAllActivityExcludeOne(T t) {
        if (t != null) {
            for (int i = 0; i < appCompatActivities.size(); ++i) {
                if (appCompatActivities.get(i) != null) {
                    String activitiesLocalName = appCompatActivities.get(i).getLocalClassName();
                    String tLocalName = t.getLocalClassName();
                    if (activitiesLocalName != tLocalName) {
                        appCompatActivities.get(i).finish();
                    }
                }
            }
        }

    }

    /**
     * 删除所有activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < appCompatActivities.size(); ++i) {
            if (appCompatActivities.get(i) != null) {
                appCompatActivities.get(i).finish();
            }
        }
    }

    /**
     * dialogactivity 是否存在
     */
//    public boolean isExistDialogActivity() {
//        DialogActivity dialogActivity;
//        for (int i = 0; i < appCompatActivities.size(); ++i) {
//            if (appCompatActivities.get(i) instanceof DialogActivity) {
//                return true;
//            }
//        }
//        return false;
//    }

//    public static List<AppCompatActivity> getAppCompatActivities() {
//        return appCompatActivities;
//    }
}