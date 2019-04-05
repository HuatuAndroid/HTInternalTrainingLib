package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;

/**
 * @author liuzhe
 * @date 2018/11/20
 */
public class SelectParticipantViewHolder extends BaseViewHolder {

    public TextView mTvTitle, mTvRightTextView;
    public ImageView mIvSearch;
    public ImageView mIvBack;

    public LinearLayout ViewTop;
    public RecyclerView mNodeRecyclerView, mRecyclerView;
    public TextView mTvResearch;

    protected SelectParticipantViewHolder(View view) {
        super(view);
        initView();
    }

    private void initView() {

        mTvTitle = $$(R.id.tv_reserver_title);
        mIvSearch = $$(R.id.tv_reserver_search);
        mIvBack = $$(R.id.iv_reserver_back);
        mTvRightTextView = $$(R.id.mTvRightTextView);

        ViewTop = $$(R.id.ViewTop);
        mNodeRecyclerView = $$(R.id.mNodeRecyclerView);
        mRecyclerView = $$(R.id.mRecyclerView);
        mTvResearch = $$(R.id.mTvResearch);

    }
}
