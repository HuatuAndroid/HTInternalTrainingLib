package com.example.module_employees_world.ui.emoji;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.EmojiPagerAdapter;
import com.example.module_employees_world.view.ExCirclePageIndicator;
import com.wb.baselib.app.AppUtils;

/**
 * @author liuzhe
 * @date 2019/3/28
 *
 */
public class EmojiKeyboardFragment extends Fragment implements SoftKeyboardStateHelper.SoftKeyboardStateListener {

    public static final String TAG = "EmojiKeyboardFragment";

    View mRootView;
    ViewPager vp;
    ExCirclePageIndicator cpi;
    SoftKeyboardStateHelper mKeyboardHelper;
    View.OnClickListener onClickListener;

    public boolean softkeyBoardOpen = false;
    public boolean emojiKeyboardOpen = false;

    public static EmojiKeyboardFragment newInstance(EmojiItemClickListener itemClickListener, View.OnClickListener onClickListener) {
        EmojiKeyboardFragment f = new EmojiKeyboardFragment();
        f.itemClickListener = itemClickListener;
        f.onClickListener = onClickListener;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mRootView = inflater.inflate(R.layout.fragment_frag_keyboard, container, false);
        cpi = mRootView.findViewById(R.id.cpi);
        vp = mRootView.findViewById(R.id.jsvp);
        vp.setAdapter(new EmojiPagerAdapter(getFragmentManager(), itemClickListener));
        cpi.setViewPager(vp);

        mKeyboardHelper = new SoftKeyboardStateHelper(getActivity().getWindow()
                .getDecorView());
        mKeyboardHelper.addSoftKeyboardStateListener(this);

        return mRootView;
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        ((InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                vp.getWindowToken(), 0);
    }

    EmojiItemClickListener itemClickListener;

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {

        if (mRootView != null) {
            ViewGroup.LayoutParams layoutParams = vp.getLayoutParams();
            layoutParams.height = (int) (keyboardHeightInPx - (__getResources().getDimension(R.dimen.emoji_keyboard_padding_top) +
                    __getResources().getDimension(R.dimen.emoji_keyboard_bottom_padding_bottom) +
                    __getResources().getDimension(R.dimen.emoji_keyboard_bottom_height) +
                    __getResources().getDimension(R.dimen.emoji_keyboard_bottom_padding_top)));
            vp.setLayoutParams(layoutParams);
        }
        softkeyBoardOpen = true;
    }

    private Resources __getResources() {
        return AppUtils.getContext().getResources();
    }

    @Override
    public void onSoftKeyboardClosed() {
        softkeyBoardOpen = false;
    }

    public interface OnItemClickListener {
        void onItemClick(int tag);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
