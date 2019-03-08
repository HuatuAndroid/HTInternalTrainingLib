package com.baijiahulian.live.ui.loading;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;

import com.baijiahulian.live.ui.R;
import com.baijiahulian.live.ui.base.BaseFragment;
import com.baijiayun.livecore.LiveSDK;
import com.baijiayun.livecore.context.LPError;
import com.baijiayun.livecore.context.LiveRoom;

import static com.baijiahulian.live.ui.utils.Precondition.checkNotNull;

/**
 * Created by Shubo on 2017/2/14.
 */

public class LoadingFragment extends BaseFragment implements LoadingContract.View {

    private LoadingContract.Presenter presenter;
    private ProgressBar progressBar;

    @Override
    public void setPresenter(LoadingContract.Presenter presenter) {
        super.setBasePresenter(presenter);
        this.presenter = presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loading;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        checkNotNull(presenter);
        progressBar = (ProgressBar) $.id(R.id.fragment_loading_pb).view();
        $.id(R.id.fragment_loading_back).clicked(lis -> getActivity().finish());
        Bundle args = getArguments();
        if (args != null) {
            setTechSupportVisibility(args.getBoolean("show_tech_support", true));
        } else {
            setTechSupportVisibility(true);
        }
        LiveRoom room;
        if (presenter.isJoinCode()) {
            room = LiveSDK.enterRoom(getActivity(), presenter.getCode(), presenter.getName(), null, presenter.getAvatar(), presenter.getLaunchListener());
        } else {
            room = LiveSDK.enterRoom(getActivity(), presenter.getRoomId(), presenter.getUser().getNumber(),
                    presenter.getUser().getName(), presenter.getUser().getType(), presenter.getUser().getAvatar(),
                    presenter.getSign(), presenter.getLaunchListener());
        }
        presenter.setLiveRoom(room);
    }

    private ObjectAnimator animator;

    @Override
    public void showLoadingSteps(int currentStep, int totalSteps) {
        int start = progressBar.getProgress();
        int end = currentStep * 100 / totalSteps;
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = ObjectAnimator.ofInt(progressBar, "progress", start, end);
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    @Override
    public void showLaunchError(LPError lpError) {
//        showToast(lpError.getMessage());
    }

    @Override
    public void setTechSupportVisibility(boolean shouldShow) {
        if (shouldShow) {
            $.id(R.id.tv_fragment_loading_tech_support).visible();
        } else {
            $.id(R.id.tv_fragment_loading_tech_support).invisible();
        }
    }
}
