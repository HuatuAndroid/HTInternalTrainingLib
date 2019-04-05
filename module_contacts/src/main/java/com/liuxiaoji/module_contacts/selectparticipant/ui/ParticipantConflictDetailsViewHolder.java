package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author liuzhe
 * @date 2018/12/3
 *
 * 搜索联系人
 */
public class ParticipantConflictDetailsViewHolder extends BaseViewHolder {

    public TextView mTvTitle, mTvRightTextView;
    public ImageView mIvSearch;
    public ImageView mIvBack;

    public CircleImageView mCircleImageView;
    public TextView mTvPersonalName;
    public TextView mTvPersonalId;
    public RecyclerView mRecyclerView;

    protected ParticipantConflictDetailsViewHolder(View view) {
        super(view);
        initView();
    }

    private void initView() {

        mTvTitle = $$(R.id.tv_reserver_title);
        mIvSearch = $$(R.id.tv_reserver_search);
        mIvBack = $$(R.id.iv_reserver_back);
        mTvRightTextView = $$(R.id.mTvRightTextView);

        mTvPersonalName = $$(R.id.mTvPersonalName);
        mTvPersonalId = $$(R.id.mTvPersonalId);

        mRecyclerView = $$(R.id.mRecyclerView);

    }
}
