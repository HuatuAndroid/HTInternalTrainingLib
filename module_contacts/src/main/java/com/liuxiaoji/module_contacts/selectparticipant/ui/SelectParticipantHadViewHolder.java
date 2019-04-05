package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author liuzhe
 * @date 2018/11/29
 *
 *
 */
public class SelectParticipantHadViewHolder extends BaseViewHolder {

    public CircleImageView circleImageView;
    public RelativeLayout mLlDelView;
    public TextView userNameTv;

    public SelectParticipantHadViewHolder(View view) {
        super(view);
        initView();
    }

    private void initView() {
        mLlDelView = $$(R.id.ll_del);
        circleImageView = $$(R.id.circleview);
        userNameTv = $$(R.id.user_name_tv);
    }
}
