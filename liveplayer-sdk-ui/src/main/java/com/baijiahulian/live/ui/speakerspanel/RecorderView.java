package com.baijiahulian.live.ui.speakerspanel;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baijiahulian.live.ui.R;
import com.baijiayun.livecore.wrapper.impl.LPCameraView;


/**
 * Created by Shubo on 2017/6/10.
 */

public class RecorderView extends FrameLayout {

    private LinearLayout awardAndNameLayout;
    private TextView tvName, awardTv;
    private LPCameraView cameraView;
    private String userName;

    public RecorderView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        cameraView = new LPCameraView(context);
        cameraView.setZOrderMediaOverlay(true);
        addView(cameraView);

        awardAndNameLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.video_name_award_layout, null);
        tvName = awardAndNameLayout.findViewById(R.id.live_name_tv);
        awardTv = awardAndNameLayout.findViewById(R.id.live_award_count_tv);
        tvName.setText(userName);
        FrameLayout.LayoutParams tvLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLp.gravity = Gravity.BOTTOM | Gravity.START;
        awardAndNameLayout.setLayoutParams(tvLp);
        this.addView(awardAndNameLayout);
    }

    public LPCameraView getCameraView(){
        return cameraView;
    }

    public void setZOrderMediaOverlay(boolean isZOrder){
        cameraView.setZOrderMediaOverlay(isZOrder);
    }

    public void setName(String name) {
        this.userName = name;
        if (tvName != null)
            tvName.setText(name);
    }

    public void setAwardCount(int awardCount) {
        if (awardCount > 0) {
            awardTv.setVisibility(View.VISIBLE);
            awardTv.setText(String.valueOf(awardCount));
        } else {
            awardTv.setVisibility(View.GONE);
        }
    }

    public void setAwardTvVisibility(int visibility){
        awardTv.setVisibility(visibility);
    }

    public void setAwardLayoutVisibility(int visibility){
        awardAndNameLayout.setVisibility(visibility);
    }
}
