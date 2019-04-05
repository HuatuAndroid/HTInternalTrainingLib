package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * @author liuzhe
 * @date 2018/12/3
 *
 * 搜索联系人
 */
public class SearchContactViewHolder extends BaseViewHolder {

    public EditText mEtSearch;
    public ImageView mIvDelete;

    public TextView mTvCancel;
    public RecyclerView mRecyclerView;

    public SmartRefreshLayout refreshLayout;

    protected SearchContactViewHolder(View view) {
        super(view);
        initView();
    }

    private void initView() {

        mEtSearch = $$(R.id.mEtSearch);
        mIvDelete = $$(R.id.mIvDelete);
        mTvCancel = $$(R.id.mTvCancel);

        mRecyclerView = $$(R.id.mRecyclerView);
        refreshLayout = $$(R.id.refreshLayout);

    }
}
