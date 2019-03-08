package com.baijiayun.videoplayer.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;

import com.baijiayun.videoplayer.ui.playback.BaseDialogFragment;

public class BaseActivity extends AppCompatActivity {

    protected boolean isLandscape;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90;
    }

    @Override
    public void onBackPressed() {
        if (isLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }

    /**
     * 直接退出
     */
    protected void onBackPressedExitImmediately(){
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
            requestLayout(true);
        } else {
            isLandscape = false;
            requestLayout(false);
        }
    }

    protected void requestLayout(boolean isLandscape){

    }

    protected void addFragment(int layoutId, Fragment fragment, boolean addToBackStack, String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragmentTag == null) {
            transaction.add(layoutId, fragment);
        } else {
            transaction.add(layoutId, fragment, fragmentTag);
        }
        transaction.commitAllowingStateLoss();
    }

    protected void showDialogFragment(final BaseDialogFragment dialogFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dialogFragment.show(ft, dialogFragment.getClass().getSimpleName() + dialogFragment.hashCode());
        getSupportFragmentManager().executePendingTransactions();
        dialogFragment.getDialog().setOnDismissListener(dialog -> {
            if (isFinishing()) return;
            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag(dialogFragment.getClass().getSimpleName() + dialogFragment.hashCode());
            if (prev != null)
                ft1.remove(prev);
            ft1.commitAllowingStateLoss();
        });
    }

}
