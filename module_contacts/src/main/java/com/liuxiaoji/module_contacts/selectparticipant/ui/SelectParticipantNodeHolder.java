package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;

/**
 * @author liuzhe
 * @date 2018/11/28
 *
 *
 */
public class SelectParticipantNodeHolder extends BaseViewHolder {

    public TextView mTvName;
    public ImageView mIvJiantou;
    public SelectParticipantNodeHolder(View view) {
        super(view);
        initView();
    }

    private void initView() {
        mTvName = $$(R.id.mTvName);
        mIvJiantou = $$(R.id.mIvJiantou);
    }
}
