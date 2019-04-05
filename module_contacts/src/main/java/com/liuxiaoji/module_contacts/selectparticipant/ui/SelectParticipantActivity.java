package com.liuxiaoji.module_contacts.selectparticipant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.adapter.SelectParticipantNodeAdapter;
import com.liuxiaoji.module_contacts.selectparticipant.adapter.SelectParticipantRecycleViewAdapter;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseActivity;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsNodeBean;
import com.liuxiaoji.module_contacts.selectparticipant.bean.SelectParticipantBean;
import com.liuxiaoji.module_contacts.selectparticipant.common.EventBusEntity;
import com.liuxiaoji.module_contacts.selectparticipant.common.HeadTitleBuilder;
import com.liuxiaoji.module_contacts.selectparticipant.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuzhe
 * @date 2018/11/22
 * <p>
 * <p>
 * 选择参与人界面
 */
public class SelectParticipantActivity extends BaseActivity<SelectParticipantContract.View, SelectParticipantPresenter> implements
        SelectParticipantContract.View, SelectParticipantNodeAdapter.ItemClickListener, SelectParticipantRecycleViewAdapter.ItemNodesClickListener,
        SelectParticipantRecycleViewAdapter.ItemStaffsClickListener, View.OnClickListener {

    public static final int intentCode = 1000;
    public static final int intentSearchCode = 2000;

    //用于判断adapter，是否是组织架构首页
    public static final int FirstContact = 1;

    public static final String IntentSelectPrarticipant = "IntentSelectPrarticipant";

    public static final String IntentSearch = "IntentSearch";

    private SelectParticipantViewHolder newSelectStaffViewHolder;
    private SelectParticipantPresenter newSelectStaffPresenter;

    private String headTitleStr;
    private SelectParticipantNodeAdapter selectParticipantNodeAdapter;
    private SelectParticipantRecycleViewAdapter selectParticipantRecycleViewAdapter;

    //用于首次进入界面时， 中间区域的展示数据
    private List<ContactsNodeBean> contactsNodeBeans;

    //上个界面传过来的数据， 用于判断是否有冲突
    public String startTime = "0";
    public String endTime = "0";
    public String dataTime = "0";

    //判断参与人是否全选变量
    public boolean isSelectAll = false;

    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        headTitleStr = "选择参与人";
        super.onCreate(bundle);
        setStatusLayout(getResources().getColor(R.color.blue_FF4A88FB));
        initData();
    }

    @Override
    protected SelectParticipantPresenter initPresenter() {
        newSelectStaffPresenter = new SelectParticipantPresenter(this);
        return newSelectStaffPresenter;
    }

    @Override
    protected SelectParticipantViewHolder initViewBean(View view) {
        newSelectStaffViewHolder = new SelectParticipantViewHolder(view);
        return newSelectStaffViewHolder;
    }

    @Override
    protected void setHeadTitle(HeadTitleBuilder headTitleBuilder) {

    }

    @Override
    protected int setLayoutResouceID() {
        return R.layout.activity_select_participannt;
    }

    private void initData() {

        newSelectStaffViewHolder.mTvTitle.setText(headTitleStr);
//        newSelectStaffViewHolder.mTvRightTextView.setText("确定");
        newSelectStaffViewHolder.mIvSearch.setVisibility(View.GONE);
//        newSelectStaffViewHolder.mTvRightTextView.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        newSelectStaffViewHolder.mNodeRecyclerView.setLayoutManager(linearLayoutManager1);
        newSelectStaffViewHolder.mNodeRecyclerView.setHasFixedSize(true);
        newSelectStaffViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        newSelectStaffViewHolder.mRecyclerView.setHasFixedSize(true);

        newSelectStaffViewHolder.mIvBack.setOnClickListener(this);
        newSelectStaffViewHolder.mTvRightTextView.setOnClickListener(this);
        newSelectStaffViewHolder.mTvResearch.setOnClickListener(this);

        getContacts(0, startTime, endTime);

        //为了给节点adapter传值
        setSelectParticipantNodeRecyclerViewAdapter(null);

    }

    public void getContacts(int nodes_id, String startTime, String endTime) {

//        showLoadingDialog();

        if (TextUtils.isEmpty(dataTime)) {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.dataTime = simpleDateFormat.format(date);
        }

        mPresenter.getContacts(nodes_id, dataTime, startTime, endTime);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.iv_reserver_back) {
            finish();
        } else if (view.getId() == R.id.mTvRightTextView) {
            List<SelectParticipantBean> selectStaffsBeans = selectParticipantRecycleViewAdapter.getSelectStaffsBeans();

            if (selectStaffsBeans != null && selectStaffsBeans.size() != 0) {
                EventBus.getDefault().post(new EventBusEntity(IntentSelectPrarticipant, selectStaffsBeans));
                finish();
            } else {
                ToastUtils.showToast(mContext, "您还没有选择参与人哦！");
            }
        } else if (view.getId() == R.id.mTvResearch) {
            Intent intent = new Intent(mContext, SearchContactActivity.class);
//                intent.putExtra(ReserveTimeRoomActivity.STAR_TTIME, startTime);
//                intent.putExtra(ReserveTimeRoomActivity.END_TIME, endTime);
//                intent.putExtra(ReserveTimeRoomActivity.DATE, dataTime);
            startActivityForResult(intent, intentSearchCode);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void handleEventBus(EventBusEntity eventBusMsg) {

        if (eventBusMsg.getTypeStr().equals(IntentSearch)) {

            ContactsBean.DataBean.StaffsBean staffsBean = eventBusMsg.getObject();

            Add_Even(staffsBean);

        }
    }

    /**
     * 添加选择联系人
     *
     * @param staffsBean
     */
    public void Add_Even(ContactsBean.DataBean.StaffsBean staffsBean) {

        //添加选择列表，给联系人的adapter
        List<SelectParticipantBean> selectStaffsBeans = selectParticipantRecycleViewAdapter.selectStaffsBeans_Add(staffsBean);
        selectParticipantRecycleViewAdapter.notifyDataSetChanged();

    }

    /**
     * 服务器请求回来的数据
     *
     * @param contactsBean
     * @param nodeId
     */
    @Override
    public void getDataSuccess(ContactsBean contactsBean, int nodeId) {
//        dismissLoadingDialog();
        List<ContactsBean.DataBean.StaffsBean> staffs = null;
        List<ContactsBean.DataBean.NodesBean> nodes = null;
        if (contactsBean != null && contactsBean.data != null) {

            if (contactsBean.data.staffs != null && contactsBean.data.staffs.size() != 0) {
                staffs = contactsBean.data.staffs;

            }
            if (contactsBean.data.nodes != null) {
                nodes = contactsBean.data.nodes;
            }
        }

        setSelectParticipantRecycleViewAdapter(staffs, nodes, nodeId);

    }

    /**
     * 组织的数据, notifyDataSetChanged
     *
     * @param bean
     */
    public void setSelectParticipantNodeRecyclerViewAdapter(ContactsNodeBean bean) {

        if (selectParticipantNodeAdapter == null) {

            contactsNodeBeans = new ArrayList<>();
            ContactsNodeBean contactsNodeBean1 = new ContactsNodeBean();
            contactsNodeBean1.id = 0;
            contactsNodeBean1.name = "华图组织架构";
            contactsNodeBeans.add(contactsNodeBean1);

            selectParticipantNodeAdapter = new SelectParticipantNodeAdapter(this, contactsNodeBeans, this);
            newSelectStaffViewHolder.mNodeRecyclerView.setAdapter(selectParticipantNodeAdapter);

        } else {

            newSelectStaffViewHolder.mNodeRecyclerView.setVisibility(View.VISIBLE);

            List<ContactsNodeBean> contactsNodeBeans = selectParticipantNodeAdapter.getContactsNodeBeans();
            contactsNodeBeans.add(bean);

            selectParticipantNodeAdapter.setData(contactsNodeBeans);
            newSelectStaffViewHolder.mNodeRecyclerView.smoothScrollToPosition(selectParticipantNodeAdapter.gettDataCount());
        }

    }

    @Override
    public void adapter_Gone() {
        newSelectStaffViewHolder.mNodeRecyclerView.setVisibility(View.GONE);
    }

    /**
     * 组织下结构的数据展示
     *
     * @param staffs
     * @param nodes
     */
    public void setSelectParticipantRecycleViewAdapter(List<ContactsBean.DataBean.StaffsBean> staffs, List<ContactsBean.DataBean.NodesBean> nodes, int nodeId) {

        if (selectParticipantRecycleViewAdapter == null) {
            selectParticipantRecycleViewAdapter = new SelectParticipantRecycleViewAdapter(this, staffs, nodes,
                    this, this, FirstContact);
            newSelectStaffViewHolder.mRecyclerView.setAdapter(selectParticipantRecycleViewAdapter);
        } else {
            if (nodeId == 0) {
                newSelectStaffViewHolder.ViewTop.setVisibility(View.VISIBLE);
                selectParticipantRecycleViewAdapter.setSelectParticipantRecycleViewAdapter(staffs, nodes, FirstContact);
            } else {
                newSelectStaffViewHolder.ViewTop.setVisibility(View.GONE);
                selectParticipantRecycleViewAdapter.setSelectParticipantRecycleViewAdapter(staffs, nodes, 0);
            }
        }

        //上个界面传过来的数据， 用于同步
//        if (intentStaffsBean != null && intentStaffsBean.selectStaffsBeans != null && intentStaffsBean.selectStaffsBeans.size() != 0) {
//            selectParticipantRecycleViewAdapter.setSelectStaffsBeans(intentStaffsBean.selectStaffsBeans);
//            selectParticipantRecycleViewAdapter.notifyDataSetChanged();
//            setSelectParticipantHadAdapter(intentStaffsBean.selectStaffsBeans);
//            intentStaffsBean = null;
//        }

    }

    @Override
    public void getDataFailure(String errorMsg) {
//        dismissLoadingDialog();
        ToastUtils.showToast(this, "网络请求错误：" + errorMsg);
    }

    @Override
    public void onClick(View view, ContactsNodeBean contactsNodeBean, int position) {

        getContacts(contactsNodeBean.id, startTime, endTime);

    }

    /**
     * 节点， 点击回调/响应事件
     *
     * @param view
     * @param nodesBean
     * @param position
     */
    @Override
    public void onClick(View view, ContactsBean.DataBean.NodesBean nodesBean, int position) {

        getContacts(nodesBean.id, startTime, endTime);

        ContactsNodeBean contactsNodeBean = new ContactsNodeBean();
        contactsNodeBean.id = nodesBean.id;
        contactsNodeBean.name = nodesBean.name;

        setSelectParticipantNodeRecyclerViewAdapter(contactsNodeBean);

    }

    /**
     * 参与人，点击回调/响应事件
     *
     * @param view
     * @param selectStaffsBeans
     * @param staffsBean
     * @param position
     */
    @Override
    public void onClick(View view, List<SelectParticipantBean> selectStaffsBeans, ContactsBean.DataBean.StaffsBean staffsBean, int position) {

        if (view.getId() == R.id.mRlOnClick) {
            Intent intent = new Intent();
            intent.putExtra("staffsBean", staffsBean);
            this.setResult(intentCode, intent);

            this.finish();

        } else if (view.getId() == R.id.mIvConflict) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == intentSearchCode) {

            Log.i("onActivityResult", "onActivityResult = " + intentSearchCode + "");

            if (data != null) {

                ContactsBean.DataBean.StaffsBean staffsBean = (ContactsBean.DataBean.StaffsBean) data.getSerializableExtra("staffsBean");

                if (staffsBean != null) {

                    Intent intent = new Intent();
                    intent.putExtra("staffsBean", staffsBean);
                    this.setResult(intentCode, intent);
                    this.finish();
                }

            }

        }

    }
}
