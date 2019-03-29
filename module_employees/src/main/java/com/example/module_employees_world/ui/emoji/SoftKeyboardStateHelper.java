package com.example.module_employees_world.ui.emoji;

import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.module_employees_world.common.CommonUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
public class SoftKeyboardStateHelper implements
        ViewTreeObserver.OnGlobalLayoutListener {

    public static final String TAG = "SoftKeyboardStateHelper";
    public static final float MIN_KEYBOARD_HEIGHT = 100;

    public interface SoftKeyboardStateListener {
        void onSoftKeyboardOpened(int keyboardHeightInPx);

        void onSoftKeyboardClosed();
    }

    private final List<SoftKeyboardStateListener> listeners = new LinkedList<SoftKeyboardStateListener>();
    private final View activityRootView;
    private int lastSoftKeyboardHeightInPx;
    private boolean isSoftKeyboardOpened;
    private boolean isNavbarShowMini = false;

    public SoftKeyboardStateHelper(View activityRootView) {
        this(activityRootView, false);
    }

    public SoftKeyboardStateHelper(View activityRootView,
                                   boolean isSoftKeyboardOpened) {
        this.activityRootView = activityRootView;
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
//        if(OsDetect.isEMUI()){
//            activityRootView.getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("navigationbar_is_min"), true, mNavigationStatusObserver);
//        }
    }

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();

        int navigationBarHeight = 0;

        int resourceId = activityRootView.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && CommonUtils.checkDeviceHasNavigationBar(activityRootView.getContext(), isNavbarShowMini)) {
            navigationBarHeight = activityRootView.getContext().getResources().getDimensionPixelSize(resourceId);
        }

        int statusBarHeight = CommonUtils.getStatusBarHeight(activityRootView.getContext());
        Rect rect = new Rect();
        activityRootView.getWindowVisibleDisplayFrame(rect);
        int heightDiff = activityRootView.getHeight() - (statusBarHeight + navigationBarHeight + rect.height());

        if (!isSoftKeyboardOpened && heightDiff > CommonUtils.dip2px(activityRootView.getContext(), MIN_KEYBOARD_HEIGHT)) {
            int heightDiffEx = activityRootView.getRootView().getHeight() - activityRootView.getHeight();

            isSoftKeyboardOpened = true;
            notifyOnSoftKeyboardOpened(heightDiff);
        } else if (isSoftKeyboardOpened && heightDiff < CommonUtils.dip2px(activityRootView.getContext(), MIN_KEYBOARD_HEIGHT)) {
            isSoftKeyboardOpened = false;
            notifyOnSoftKeyboardClosed();
        }
    }


    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(activityRootView.getContext().getContentResolver(), "navigationbar_is_min", 0);
            if (navigationBarIsMin == 1) {
                isNavbarShowMini = true;
            } else {
                isNavbarShowMini = false;
            }
        }
    };

    public void setIsSoftKeyboardOpened(boolean isSoftKeyboardOpened) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
    }

    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardOpened;
    }

    /**
     * Default value is zero (0)
     *
     * @return last saved keyboard height in px
     */
    public int getLastSoftKeyboardHeightInPx() {
        return lastSoftKeyboardHeightInPx;
    }

    public void addSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(
            SoftKeyboardStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        this.lastSoftKeyboardHeightInPx = keyboardHeightInPx;

        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardOpened(keyboardHeightInPx);
            }
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }
    }
}
