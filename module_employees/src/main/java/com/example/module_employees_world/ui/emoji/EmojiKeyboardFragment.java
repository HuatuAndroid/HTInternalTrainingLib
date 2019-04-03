package com.example.module_employees_world.ui.emoji;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.EmojiPagerAdapter;
import com.example.module_employees_world.adapter.TutuPagerAdapter;
import com.example.module_employees_world.view.ExCirclePageIndicator;
import com.wb.baselib.app.AppUtils;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
public class EmojiKeyboardFragment extends Fragment implements SoftKeyboardStateHelper.SoftKeyboardStateListener, View.OnClickListener {

    public static final String TAG = "EmojiKeyboardFragment";

    View mRootView;
    LinearLayout flEmojiContent, mViewEmoji, mViewTu;
    ViewPager mViewpager_Emoji, mViewpager_tu;
    ImageView mIvEmoji, mIvTutu;
    ExCirclePageIndicator mExCirclePageIndicator_Emoji, mExCirclePageIndicator_tu;
    SoftKeyboardStateHelper mKeyboardHelper;
    View.OnClickListener onClickListener;

    public boolean softkeyBoardOpen = false;

    EmojiPagerAdapter emojiPagerAdapter;
    TutuPagerAdapter tutuPagerAdapter;

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
        mExCirclePageIndicator_Emoji = mRootView.findViewById(R.id.mExCirclePageIndicator_Emoji);
        mViewpager_Emoji = mRootView.findViewById(R.id.mViewpager_Emoji);
        mIvEmoji = mRootView.findViewById(R.id.mIvEmoji);
        mIvTutu = mRootView.findViewById(R.id.mIvTutu);
        flEmojiContent = mRootView.findViewById(R.id.flEmojiContent);
        mViewEmoji = mRootView.findViewById(R.id.mViewEmoji);
        mViewTu = mRootView.findViewById(R.id.mViewTu);
        mViewpager_tu = mRootView.findViewById(R.id.mViewpager_tu);
        mExCirclePageIndicator_tu = mRootView.findViewById(R.id.mExCirclePageIndicator_tu);

        mViewpager_tu.setAdapter(tutuPagerAdapter = new TutuPagerAdapter(getFragmentManager(), itemClickListener));
        mExCirclePageIndicator_tu.setViewPager(mViewpager_tu);

        mViewpager_Emoji.setAdapter(emojiPagerAdapter = new EmojiPagerAdapter(getFragmentManager(), itemClickListener));
        mExCirclePageIndicator_Emoji.setViewPager(mViewpager_Emoji);

        mKeyboardHelper = new SoftKeyboardStateHelper(getActivity().getWindow()
                .getDecorView());
        mKeyboardHelper.addSoftKeyboardStateListener(this);

        mIvEmoji.setOnClickListener(this);
        mIvTutu.setOnClickListener(this);

        return mRootView;
    }

    /**
     * 表情视图 隐藏/显示
     *
     * @param emojiKeyboardOpen
     */
    public void setflEmojiContentShow(boolean emojiKeyboardOpen) {
        if (flEmojiContent == null) return;
        if (emojiKeyboardOpen) {
            flEmojiContent.setVisibility(View.VISIBLE);
        } else {
            flEmojiContent.setVisibility(View.GONE);
        }
    }

    EmojiItemClickListener itemClickListener;

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {

        if (mRootView != null) {
            ViewGroup.LayoutParams layoutParams = mViewpager_Emoji.getLayoutParams();
            layoutParams.height = (int) __getResources().getDimension(R.dimen.emoji_keyboard_padding_top);
            mViewpager_Emoji.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams1 = mViewpager_tu.getLayoutParams();
            layoutParams1.height = (int) __getResources().getDimension(R.dimen.emoji_keyboard_padding_top);
            mViewpager_tu.setLayoutParams(layoutParams1);
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

    @Override
    public void onClick(View v) {
        if (v == mIvEmoji) {
            mViewEmoji.setVisibility(View.VISIBLE);
            mViewTu.setVisibility(View.GONE);
            mIvEmoji.setBackgroundColor(getResources().getColor(R.color.color_FFf6f6f6));
            mIvTutu.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (v == mIvTutu) {
            mViewEmoji.setVisibility(View.GONE);
            mViewTu.setVisibility(View.VISIBLE);
            mIvTutu.setBackgroundColor(getResources().getColor(R.color.color_FFf6f6f6));
            mIvEmoji.setBackgroundColor(getResources().getColor(R.color.white));
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int tag);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public View getmRootView(){
        return mRootView;
    }
}
