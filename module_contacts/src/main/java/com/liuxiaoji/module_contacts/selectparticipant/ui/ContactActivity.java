package com.liuxiaoji.module_contacts.selectparticipant.ui;//package com.huatu.htmoa.ui.home.work.sitemanagement.selectparticipant;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.View;
//
//import com.huatu.htmoa.R;
//import com.huatu.htmoa.common.eventbus.EventBusEntity;
//import com.huatu.htmoa.ui.base.BaseActivity;
//import com.huatu.htmoa.ui.base.BaseViewHolder;
//import com.huatu.htmoa.ui.home.work.sitemanagement.bean.ContactsBean;
//import com.huatu.htmoa.ui.view.headview.HeadTitleBuilder;
//import com.wb.baselib.utils.ToastUtils;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import butterknife.OnClick;
//
///**
// * @author liuzhe
// * @date 2018/11/29
// *
// * 组织架构首页
// */
//public class ContactActivity extends BaseActivity<ContactContract.View, Contactresenter> implements ContactContract.View,
//        SelectParticipantRecycleViewAdapter.ItemNodesClickListener, SelectParticipantRecycleViewAdapter.ItemStaffsClickListener{
//
//    //下级传入的参数---跳转
//    public static final String StaffsBean_ID = "StaffsBean_ID";
//
//    private String headTitleStr;
//    private Contactresenter contactresenter;
//    private ContactViewHolder contactViewHolder;
//
//    private SelectParticipantRecycleViewAdapter selectParticipantRecycleViewAdapter;
//
//    @Override
//    protected void handleEventBus(EventBusEntity eventBusMsg) {
//
//        //当选择完联系人, 点击确定， 此界面会finish
//        if (eventBusMsg.getTypeStr().equals(SelectParticipantActivity.IntentSelectPrarticipant)) {
//            finish();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle bundle) {
//        headTitleStr = "选择参与人";
//        super.onCreate(bundle);
//        initData();
//    }
//
//    private void initData() {
//
//        contactViewHolder.mTvTitle.setText(headTitleStr);
//        contactViewHolder.mTvRightTextView.setVisibility(View.VISIBLE);
//        contactViewHolder.mIvSearch.setVisibility(View.GONE);
//
//        contactViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        contactViewHolder.mRecyclerView.setHasFixedSize(true);
//
//        getContacts(0);
//    }
//
//    public void getContacts(int nodes_id) {
//
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        mPresenter.getContacts(nodes_id, simpleDateFormat.format(date));
//
//    }
//
//    @Override
//    protected Contactresenter initPresenter() {
//        contactresenter = new Contactresenter(this);
//        return contactresenter;
//    }
//
//    @Override
//    protected BaseViewHolder initViewBean(View view) {
//        contactViewHolder = new ContactViewHolder(view);
//        return contactViewHolder;
//    }
//
//    @Override
//    protected void setHeadTitle(HeadTitleBuilder headTitleBuilder) {
//
//    }
//
//    @Override
//    protected int setLayoutResouceID() {
//        return R.layout.activity_contract;
//    }
//
//    @Override
//    public void getDataSuccess(ContactsBean contactsBean, int nodeId) {
//
//        List<ContactsBean.DataBean.StaffsBean> staffs = null;
//        List<ContactsBean.DataBean.NodesBean> nodes = null;
//        if (contactsBean != null && contactsBean.data != null) {
//
//            if (contactsBean.data.staffs != null) {
//                staffs = contactsBean.data.staffs;
//            }
//            if (contactsBean.data.nodes != null) {
//                nodes = contactsBean.data.nodes;
//            }
//        }
//
//        setSelectParticipantRecycleViewAdapter(staffs, nodes);
//
//    }
//
//    /**
//     * 组织下结构的数据展示
//     *
//     * @param staffs
//     * @param nodes
//     */
//    public void setSelectParticipantRecycleViewAdapter(List<ContactsBean.DataBean.StaffsBean> staffs, List<ContactsBean.DataBean.NodesBean> nodes){
//
//        if (selectParticipantRecycleViewAdapter == null){
//            selectParticipantRecycleViewAdapter = new SelectParticipantRecycleViewAdapter(this, staffs, nodes, this, this, FirstContact);
//            contactViewHolder.mRecyclerView.setAdapter(selectParticipantRecycleViewAdapter);
//        }else{
//            selectParticipantRecycleViewAdapter.setSelectParticipantRecycleViewAdapter(staffs, nodes, 0);
//        }
//
//    }
//
//    @Override
//    public void getDataFailure(String errorMsg) {
//
//        ToastUtils.showToast(this, "网络请求错误：" + errorMsg);
//
//    }
//
//    @OnClick({R.id.iv_reserver_back})
//    public void onClick(View view){
//
//        switch (view.getId()){
//            case R.id.iv_reserver_back:
//                finish();
//                break;
//            default:
//                break;
//        }
//
//    }
//
//    @Override
//    public void onClick(View view, ContactsBean.DataBean.NodesBean nodesBean, int position) {
//        Intent intent = new Intent(mContext, SelectParticipantActivity.class);
//        intent.putExtra(StaffsBean_ID, nodesBean.id);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onClick(View view, List<ContactsBean.DataBean.StaffsBean> selectStaffsBeans, int position) {
//
//    }
//}
