package com.baijiahulian.live.ui.rightmenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.baijiahulian.live.ui.R;
import com.baijiahulian.live.ui.base.BaseFragment;
import com.baijiahulian.live.ui.utils.RxUtils;
import com.baijiahulian.live.ui.viewsupport.CountdownCircleView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Shubo on 2017/2/15.
 */

public class RightMenuFragment extends BaseFragment implements RightMenuContract.View {

    private RightMenuContract.Presenter presenter;

    @SuppressLint("CheckResult")
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        $.id(R.id.fragment_right_pen).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeDrawing();
            }
        });
        $.id(R.id.fragment_right_ppt).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.managePPT();
            }
        });
        $.id(R.id.fragment_right_online_user).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.visitOnlineUser();
            }
        });
        $.id(R.id.fragment_right_speak_apply).clicked().throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (!clickableCheck()) {
                        showToast(getString(R.string.live_frequent_error));
                        return;
                    }
                    if (!presenter.isWaitingRecordOpen())
                        presenter.speakApply();
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_right_menu;
    }

    @Override
    public void showDrawingStatus(boolean isEnable) {
        if (isEnable)
            $.id(R.id.fragment_right_pen).image(R.drawable.live_ic_lightpen_on);
        else
            $.id(R.id.fragment_right_pen).image(R.drawable.live_ic_lightpen);
    }

    @Override
    public void showSpeakApplyCountDown(int countDownTime, int total) {
        $.id(R.id.fragment_right_hand_countdown).visible();
        ((CountdownCircleView) $.id(R.id.fragment_right_hand_countdown).view()).setRatio(countDownTime / (float) total);
        $.id(R.id.fragment_right_hand_countdown).view().invalidate();
    }

    @Override
    public void showSpeakApplyAgreed(boolean isEnableDrawing) {
        showToast(getString(R.string.live_media_speak_apply_agree));
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup_on);
        if (isEnableDrawing)
            $.id(R.id.fragment_right_pen).visible();
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }

    @Override
    public void showSpeakClosedByTeacher(boolean isSmallGroup) {
        showToast(getString(R.string.live_media_speak_closed_by_teacher));
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup);
        if (!isSmallGroup)
            $.id(R.id.fragment_right_pen).gone();
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }

    @Override
    public void showSpeakClosedByServer() {
        showToast(getString(R.string.live_media_speak_closed_by_server));
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup);
        $.id(R.id.fragment_right_pen).gone();
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }

    @Override
    public void showForceSpeakDenyByServer() {
        showToast(getString(R.string.live_force_speak_closed_by_server));
//        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup);
//        $.id(R.id.fragment_right_pen).gone();
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }


    @Override
    public void showSpeakApplyDisagreed() {
        $.id(R.id.fragment_right_speak_apply).enable(true);
        showToast(getString(R.string.live_media_speak_apply_disagree));
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup);
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }

    @Override
    public void showSpeakApplyCanceled() {
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup);
        $.id(R.id.fragment_right_pen).gone();
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }

    @Override
    public void showTeacherRightMenu() {
        $.id(R.id.fragment_right_pen).visible();
        $.id(R.id.fragment_right_ppt).visible();
        $.id(R.id.fragment_right_speak_wrapper).gone();
    }

    @Override
    public void showStudentRightMenu() {
        $.id(R.id.fragment_right_pen).gone();
        $.id(R.id.fragment_right_ppt).gone();
        $.id(R.id.fragment_right_speak_wrapper).visible();
    }

    @Override
    public void showForbiddenHand() {
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup_forbid);
        $.id(R.id.fragment_right_speak_apply).enable(false);
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }

    @Override
    public void showNotForbiddenHand() {
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup);
        $.id(R.id.fragment_right_speak_apply).enable(true);
    }

    @Override
    public void hidePPTDrawBtn() {
        showToast(getString(R.string.live_student_no_auth_drawing));
        $.id(R.id.fragment_right_pen).gone();
    }

    @Override
    public void showPPTDrawBtn() {
        showToast(getString(R.string.live_student_auth_drawing));
        $.id(R.id.fragment_right_pen).visible();
    }

    @Override
    public void showHandUpError() {
        showToast(getString(R.string.live_hand_up_error));
    }

    @Override
    public void showHandUpForbid() {
        showToast(getString(R.string.live_forbid_send_message));
    }

    @Override
    public void showCantDraw() {
        showToast(getString(R.string.live_cant_draw));
    }

    @Override
    public void showCantDrawCauseClassNotStart() {
        showToast(getString(R.string.live_cant_draw_class_not_start));
    }

    @Override
    public void showWaitingTeacherAgree() {
        showToast(getString(R.string.live_waiting_speak_apply_agree));
    }

    @Override
    public void showAutoSpeak(boolean isDrawingEnable) {
        if (isDrawingEnable)
            $.id(R.id.fragment_right_pen).visible();
        $.id(R.id.fragment_right_speak_apply).gone();
    }

    @Override
    public void showForceSpeak(boolean isDrawingEnable) {
        $.id(R.id.fragment_right_speak_apply).image(R.drawable.live_ic_handup_on);
        if (isDrawingEnable)
            $.id(R.id.fragment_right_pen).visible();
        $.id(R.id.fragment_right_hand_countdown).invisible();
    }

    @Override
    public void hideUserList() {
        $.id(R.id.fragment_right_online_user).gone();
    }

    @Override
    public void hideSpeakApply() {
        $.id(R.id.fragment_right_speak_apply).gone();
    }

    @Override
    public void showHandUpTimeout() {
        showToast(getString(R.string.live_media_speak_apply_timeout));
    }

    @Override
    public void setPresenter(RightMenuContract.Presenter presenter) {
        super.setBasePresenter(presenter);
        this.presenter = presenter;
    }

    private Disposable subscriptionOfClickable;

    private boolean clickableCheck() {
        if (subscriptionOfClickable != null && !subscriptionOfClickable.isDisposed()) {
            return false;
        }
        subscriptionOfClickable = Observable.timer(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                RxUtils.dispose(subscriptionOfClickable);
            }
        });
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter = null;
    }
}
