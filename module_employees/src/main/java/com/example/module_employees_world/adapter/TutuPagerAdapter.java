package com.example.module_employees_world.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.module_employees_world.common.config.EmojiConfig;
import com.example.module_employees_world.ui.emoji.EmojiItemClickListener;
import com.example.module_employees_world.ui.emoji.EmojiPageFragment;
import com.example.module_employees_world.ui.emoji.TutuPageFragment;

/**
 * @author liuzhe
 * @date 2019/3/28
 *
 * viewpager表情页适配器
 */
public class TutuPagerAdapter extends FragmentPagerAdapter {

    EmojiItemClickListener emojiItemClickListener;

    public TutuPagerAdapter(FragmentManager fm, EmojiItemClickListener itemClickListener) {
        super(fm);
        this.emojiItemClickListener = itemClickListener;
    }

    /**
     * 显示模式：如果只有一种Emoji表情，则像QQ表情一样左右滑动分页显示<br>
     * 如果有多种Emoji表情，每页显示一种，Emoji筛选时上下滑动筛选。
     */
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public TutuPageFragment getItem(int pos) {
        return TutuPageFragment.newInstance(pos, emojiItemClickListener);
    }
}