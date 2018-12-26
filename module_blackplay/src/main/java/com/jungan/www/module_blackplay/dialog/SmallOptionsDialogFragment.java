package com.jungan.www.module_blackplay.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jungan.www.module_blackplay.R;


/**
 * Created by szw on 17/8/20.
 */

public class SmallOptionsDialogFragment extends DialogFragment implements View.OnClickListener {
    private OptionsClickableListener listener;
    private TextView fullScreen;
    private TextView closeVideo;
    private TextView cancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setWindowAnimations(R.style.bottom_menu_animation);
        View view = inflater.inflate(R.layout.fragment_samll_options_dialog, container);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        cancel = (TextView) view.findViewById(R.id.fragment_small_options_cancel);
        fullScreen = (TextView) view.findViewById(R.id.fragment_small_options_full_screen);
        closeVideo = (TextView) view.findViewById(R.id.fragment_small_options_close_video);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.fullScreen();
                dismiss();
            }
        });

        closeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.closeVideo();
                dismiss();
            }
        });
    }

    public void setCreateClickableListener(OptionsClickableListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        //移动弹出菜单到底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.50f;
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    @Override
    public void onStop() {
        this.getView().setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_menu_disappear));
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public interface OptionsClickableListener {
        void fullScreen();

        void closeVideo();
    }
}
