package com.example.module_employees_world.ui.emoji;

import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.bean.TutuIconBean;

/**
 * @author liuzhe
 * @date 2019/3/28
 *
 * 表情页 点击/删除回调
 */
public interface EmojiItemClickListener {

    void onItemClick(EmojiconBean emojicon);

    void onDeleteClick();

    void onItemClick(TutuIconBean tutuIconBean);

    void onDeleteTutuClick();
}
