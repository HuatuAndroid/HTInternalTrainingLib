package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.adapter.SearchContactdapter;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseActivity;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.bean.SearchContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.common.EventBusEntity;
import com.liuxiaoji.module_contacts.selectparticipant.common.HeadTitleBuilder;
import com.liuxiaoji.module_contacts.selectparticipant.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhe
 * @date 2018/12/3
 * <p>
 * 搜索联系人
 */
public class SearchContactActivity extends BaseActivity<SearchContactContract.View, SearchContactContractPresenter> implements SearchContactContract.View,
        SearchContactdapter.ItemClickListener, TextView.OnEditorActionListener, View.OnClickListener {

    private SearchContactViewHolder searchContactViewHolder;
    private SearchContactContractPresenter searchContactContractPresenter;
    private SearchContactdapter searchContactdapter;

    private int page = 1;

    private String startTime = "0";
    private String endTime = "0";
    private String dataTime = "0";

    //当前点击的数据， 用于是冲突跳转
    private ContactsBean.DataBean.StaffsBean staffsBean;

    private List<ContactsBean.DataBean.StaffsBean> staffs;

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        initData();
    }

    private void initData() {

        searchContactViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        searchContactViewHolder.mRecyclerView.setHasFixedSize(true);
        searchContactViewHolder.mEtSearch.setOnEditorActionListener(this);

        searchContactViewHolder.refreshLayout.setEnableRefresh(false);
        searchContactViewHolder.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                searchContactViewHolder.refreshLayout.finishLoadMore();
                netSearchuser(page);
            }

        });

        searchContactViewHolder.mIvDelete.setOnClickListener(this);
        searchContactViewHolder.mTvCancel.setOnClickListener(this);

    }

    /**
     * 搜索，请求数据
     */
    public void netSearchuser(int page) {

        String editText = getEditText();

        if (TextUtils.isEmpty(editText)) {
            ToastUtils.showToast(mContext, "请填写关键字");
        }

//        showLoadingDialog();

        mPresenter.searchuser(editText, dataTime, startTime, endTime, page);

    }

    @Override
    protected void handleEventBus(EventBusEntity eventBusMsg) {

    }

    @Override
    protected SearchContactContractPresenter initPresenter() {
        searchContactContractPresenter = new SearchContactContractPresenter(this);
        return searchContactContractPresenter;
    }

    @Override
    protected SearchContactViewHolder initViewBean(View view) {
        searchContactViewHolder = new SearchContactViewHolder(view);
        return searchContactViewHolder;
    }

    @Override
    protected void setHeadTitle(HeadTitleBuilder headTitleBuilder) {

    }

    @Override
    protected int setLayoutResouceID() {
        return R.layout.activity_search_contact;
    }

    @Override
    public void getDataSuccess(SearchContactsBean searchContactsBean) {
//        dismissLoadingDialog();
        if (searchContactsBean != null && searchContactsBean.data != null && searchContactsBean.data.staffs != null && searchContactsBean.data.staffs.size() != 0) {
            setPage(page + 1);
        } else {
            ToastUtils.showToast(mContext, "没有更多数据了");
            return;
        }

        if (page > 2) {

            for (ContactsBean.DataBean.StaffsBean dataBean : searchContactsBean.data.staffs) {
                staffs.add(dataBean);
            }

        } else {
            if (staffs == null) {
                staffs = new ArrayList<>();
            }
            staffs.clear();
            staffs = searchContactsBean.data.staffs;
        }

        if (searchContactdapter == null) {
            searchContactdapter = new SearchContactdapter(mContext, staffs, this);
            searchContactViewHolder.mRecyclerView.setAdapter(searchContactdapter);
        } else {
            searchContactdapter.setStaffsBeans(staffs);
            searchContactdapter.notifyDataSetChanged();
        }

    }

    public String getEditText() {

        String mEtSearch = searchContactViewHolder.mEtSearch.getText().toString();

        if (TextUtils.isEmpty(mEtSearch)) {

            return null;
        }

        return mEtSearch;
    }

    @Override
    public void getDataFailure(String errorMsg) {
//        dismissLoadingDialog();
    }

    /**
     * item 点击回调
     *
     * @param view
     * @param staffsBean
     * @param position
     */
    @Override
    public void onClick(View view, ContactsBean.DataBean.StaffsBean staffsBean, int position) {

//        if (staffsBean.is_schedule == 1){
//            this.staffsBean  =  staffsBean;
//        }else{


//            EventBus.getDefault().post(new EventBusEntity(SelectParticipantActivity.IntentSearch,  staffsBean));


        Intent intent = new Intent();
        intent.putExtra("staffsBean", staffsBean);
        this.setResult(SelectParticipantActivity.intentSearchCode, intent);

        finish();
//        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH:

                setPage(1);
                netSearchuser(page);
                show_keyboard_from(searchContactViewHolder.mEtSearch);

                break;
            default:
                break;
        }

        return true;
    }

    public void show_keyboard_from(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.mIvDelete) {
            searchContactViewHolder.mEtSearch.setText("");
        } else if (view.getId() == R.id.mTvCancel) {
            finish();
        }

    }
}
