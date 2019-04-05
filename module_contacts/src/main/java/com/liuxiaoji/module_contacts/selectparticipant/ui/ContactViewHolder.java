package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;

/**
 * @author liuzhe
 * @date 2018/11/29
 *
 *
 */
public class ContactViewHolder extends BaseViewHolder {

    public TextView mTvTitle, mTvRightTextView;
    public ImageView mIvSearch;
    public ImageView mIvBack;


    public RecyclerView mRecyclerView;
    public TextView mTvResearch;

    protected ContactViewHolder(View view) {
        super(view);
        initView();
    }

    private void initView() {

        mTvTitle = $$(R.id.tv_reserver_title);
        mIvSearch = $$(R.id.tv_reserver_search);
        mIvBack = $$(R.id.iv_reserver_back);
        mTvRightTextView = $$(R.id.mTvRightTextView);

        mRecyclerView = $$(R.id.mRecyclerView);
        mTvResearch = $$(R.id.mTvResearch);

    }
}
