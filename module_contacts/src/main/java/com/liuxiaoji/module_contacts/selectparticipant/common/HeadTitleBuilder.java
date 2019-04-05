package com.liuxiaoji.module_contacts.selectparticipant.common;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class HeadTitleBuilder extends BaseViewHolder {
    private ImageView backImageView, rightImageView;
    private ImageView rightImageView2;
    private TextView leftTextView, midTextView, rightTextView;
    private RelativeLayout relativeLayout;
    private RelativeLayout screenShotLl;
    private TextView screenShotTv;
    private View seperateView;

    public View getSeperateView() {
        return seperateView;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public ImageView getRightImageView() {
        return rightImageView;
    }

    public TextView getMidTextView() {
        return midTextView;
    }

    public TextView getLeftTextView() {
        return leftTextView;
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public ImageView getBackImageView() {
        return backImageView;
    }

    public RelativeLayout getScreenShotLl() {
        return screenShotLl;
    }

    public HeadTitleBuilder(View view) {
        super(view);
        initView();
    }

    private void initView() {
        try {
            backImageView = $$(R.id.common_title_ui_back_imageview);
            rightImageView = $$(R.id.common_title_ui_right_imageview);
            rightImageView2 = $$(R.id.common_title_ui_right_imageview2);
            leftTextView = $$(R.id.common_title_ui_left_title);
            rightTextView = $$(R.id.common_title_ui_right_title);
            midTextView = $$(R.id.common_title_ui_mid_title);
            relativeLayout = $$(R.id.common_title_ui_rl);
            seperateView = $$(R.id.common_title_ui_seperate_view);
            screenShotLl = $$(R.id.approval_shot_ll);
            screenShotTv = $$(R.id.common_title_ui_shot_mid_title);
        } catch (Exception e) {
            Log.e("HeadTitleBuilder", e.getMessage().toString());
        }

    }

    public void setRightTextViewViewListener(View.OnClickListener listener) {
        if (rightTextView != null) {
            rightTextView.setOnClickListener(listener);
        }
    }


    public void setRightImageViewListener(View.OnClickListener listener) {
        if (rightImageView != null) {
            rightImageView.setOnClickListener(listener);
        }
    }

    public void setRightImageView2Listener(View.OnClickListener listener) {
        if (rightImageView2 != null) {
            rightImageView2.setOnClickListener(listener);
        }
    }

    /**
     * 设置head不可见
     */
    public void setHeadLayoutInVisible() {
        if (relativeLayout != null) {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public HeadTitleBuilder setLeftText(String leftText) {
        if (leftTextView != null) {
            leftTextView.setText(leftText);
        }
        return this;
    }

    public HeadTitleBuilder setRightText(String rightText) {
        if (rightTextView != null) {
            rightTextView.setText(rightText);
        }
        return this;
    }

    public HeadTitleBuilder setMidtText(String contentText) {
        if (midTextView != null) {
            midTextView.setText(contentText);
        }
        if (screenShotTv != null) {
            screenShotTv.setText(contentText);
        }
        return this;
    }

    public HeadTitleBuilder setBackImageViewInVisibility() {
        if (backImageView != null) {
            backImageView.setVisibility(View.GONE);
        }
        return this;
    }

    public HeadTitleBuilder setRightImageViewInVisibility() {
        if (rightImageView != null) {
            rightImageView.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public HeadTitleBuilder setLeftTextInVisibility() {
        if (leftTextView != null) {
            leftTextView.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public HeadTitleBuilder setMidTextInVisibility() {
        if (midTextView != null) {
            midTextView.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public HeadTitleBuilder setRightTextInVisibility() {
        if (rightTextView != null) {
            rightTextView.setVisibility(View.INVISIBLE);
        }
        return this;
    }


    public HeadTitleBuilder setRightImage(int resId) {
        if (rightImageView != null) {
            rightImageView.setImageResource(resId);
        }
        return this;
    }

    public HeadTitleBuilder setRightImage2(int resId) {
        if (rightImageView2 != null) {
            rightImageView2.setImageResource(resId);
        }
        return this;
    }

    public HeadTitleBuilder setBackImageViewListener(View.OnClickListener listener) {
        if (backImageView != null) {
            backImageView.setOnClickListener(listener);
        }
        return this;
    }

    public HeadTitleBuilder setLeftTextViewListener(View.OnClickListener listener) {
        if (leftTextView != null) {
            leftTextView.setOnClickListener(listener);
        }
        return this;
    }

    public static class HeadTitleBuilderInner {

        private HeadTitleBuilder mHeadTitleBuilder;

        public HeadTitleBuilderInner(View view) {
            mHeadTitleBuilder = new HeadTitleBuilder(view);
        }

        public void setBackImageViewListener(View.OnClickListener listener) {
            mHeadTitleBuilder.backImageView.setOnClickListener(listener);
            mHeadTitleBuilder.leftTextView.setOnClickListener(listener);
        }

        public HeadTitleBuilder getHeadTitleBuilder() {
            return mHeadTitleBuilder;
        }


    }
}