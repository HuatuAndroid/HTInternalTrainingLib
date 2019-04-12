package com.liuxiaoji.module_contacts.selectparticipant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.bean.SelectParticipantBean;
import com.liuxiaoji.module_contacts.selectparticipant.ui.SelectParticipantActivity;
import com.liuxiaoji.module_contacts.selectparticipant.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author liuzhe`
 * @date 2018/11/22
 * <p>
 * 选择参与人 adapter
 */
public class SelectParticipantRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TypeConstruction = 0;
    public static final int TypeContacts = 1;

    //ContactActivity.FirstContact 如果是这个，说明是组织架构首页
    public int FirstContact;

    private View mView = null;
    private Context mContext;
    private LayoutInflater mInflater;
    //结构下的组织数据
    private List<ContactsBean.DataBean.NodesBean> nodesBeans;
    //结构下或组织下的人员
    private List<ContactsBean.DataBean.StaffsBean> staffsBeans;
    //选择的结构数据
    private List<SelectParticipantBean> selectStaffsBeans = new ArrayList<>();

    public SelectParticipantRecycleViewAdapter(Context mContext, List<ContactsBean.DataBean.StaffsBean> staffsBeans, List<ContactsBean.DataBean.NodesBean> nodesBeans,
                                               ItemNodesClickListener itemNodesClickListene, ItemStaffsClickListener itemStaffsClickListener, int FirstContact) {

        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.staffsBeans = staffsBeans;
        this.nodesBeans = nodesBeans;

        setItemNodesClickListener(itemNodesClickListene);
        setItemStaffsClickListener(itemStaffsClickListener);

        this.FirstContact = FirstContact;
    }

    public void setSelectParticipantRecycleViewAdapter(List<ContactsBean.DataBean.StaffsBean> staffsBeans,
                                                       List<ContactsBean.DataBean.NodesBean> nodesBeans, int FirstContact) {
        this.staffsBeans = staffsBeans;
        this.nodesBeans = nodesBeans;
        this.FirstContact = FirstContact;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case TypeConstruction:
                mView = mInflater.inflate(R.layout.select_participant_construction_nitem, parent, false);
                ViewConstructionHolderTop mViewHolderViewPager = new ViewConstructionHolderTop(mView);

                return mViewHolderViewPager;

            case TypeContacts:
                mView = mInflater.inflate(R.layout.select_participant_personal_nitem, parent, false);
                ViewContactsHolder mViewHolderNewBody = new ViewContactsHolder(mView);

                return mViewHolderNewBody;
            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TypeConstruction:
                ViewConstructionHolderTop viewConstructionHolderTop = (ViewConstructionHolderTop) holder;

                viewConstructionHolderTop.viewData(position);
                break;

            case TypeContacts:
                ViewContactsHolder viewContactsHolder = (ViewContactsHolder) holder;

                viewContactsHolder.viewData(position);
                break;

            default:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (nodesBeans != null && nodesBeans.size() != 0) {
            if (nodesBeans.size() > position) {
                return TypeConstruction;
            }
        }

        return TypeContacts;

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (nodesBeans != null && nodesBeans.size() != 0) {
            count += nodesBeans.size();
        }
        if (staffsBeans != null && staffsBeans.size() != 0) {
            count += staffsBeans.size();
        }
        return count;
    }

    private class ViewConstructionHolderTop extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private ImageView mIvApartment;

        public ViewConstructionHolderTop(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.mTvName);
            mIvApartment = itemView.findViewById(R.id.mIvApartment);
        }

        public void viewData(final int position) {

           final ContactsBean.DataBean.NodesBean nodesBean = nodesBeans.get(position);

            mTvName.setText(nodesBean.name);

            if (FirstContact == 1 && position == 0) {
                mIvApartment.setVisibility(View.VISIBLE);
            } else {
                mIvApartment.setVisibility(View.GONE);
            }

            mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemNodesClickListener.onClick(v, nodesBean, position);
                }
            });

        }
    }

    private class ViewContactsHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRlOnClick;
        private CircleImageView mCircleImageView;
        private TextView mTvPersonalName, mTvPersonalId, mTvDepartmentName, mTvEmail;
        private ImageView mIvConflict;

        public ViewContactsHolder(View itemView) {
            super(itemView);

            mRlOnClick = itemView.findViewById(R.id.mRlOnClick);
            mCircleImageView = itemView.findViewById(R.id.mCircleImageView);
            mTvPersonalName = itemView.findViewById(R.id.mTvPersonalName);
            mTvPersonalId = itemView.findViewById(R.id.mTvPersonalId);
            mIvConflict = itemView.findViewById(R.id.mIvConflict);
            mTvDepartmentName = itemView.findViewById(R.id.mTvDepartmentName);
            mTvEmail = itemView.findViewById(R.id.mTvEmail);
        }

        public void viewData(final int position) {
            int nPosition = position;
            if (nodesBeans != null && nodesBeans.size() != 0) {
                nPosition = position - nodesBeans.size();
            }
            final ContactsBean.DataBean.StaffsBean staffsBean = staffsBeans.get(nPosition);

            mTvPersonalName.setText(staffsBean.name);
            mTvPersonalId.setText("工号：" + staffsBean.id);
            mTvDepartmentName.setText(staffsBean.node.name);
            mTvEmail.setText(staffsBean.workEmail);

            GlideUtils.setNetImage(mContext, staffsBean.avatar, mCircleImageView);

            mRlOnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //冲突的不予点击
                    if (staffsBean.is_schedule == 1) {
                        return;
                    }

                    if (selectStaffsBeans != null && isData(staffsBean)) {
                        selectStaffsBeans_Del(staffsBean);
                        itemStaffsClickListener.onClick(v, selectStaffsBeans, staffsBean, position);
                    } else {
                        itemStaffsClickListener.onClick(v, selectStaffsBeans_Add(staffsBean), staffsBean, position);
                    }
                }
            });

            mIvConflict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemStaffsClickListener.onClick(v, selectStaffsBeans, staffsBean, position);
                }
            });

        }
    }

    /**
     * 添加选择的参与人
     *
     * @param staffsBean
     */
    public List<SelectParticipantBean> selectStaffsBeans_Add(ContactsBean.DataBean.StaffsBean staffsBean) {

        if (!isData(staffsBean)) {
            SelectParticipantBean selectParticipantBean = new SelectParticipantBean();
            selectParticipantBean.avatar = staffsBean.avatar;
            selectParticipantBean.id = staffsBean.id;
            selectParticipantBean.email = staffsBean.workEmail;
            selectParticipantBean.name = staffsBean.name;
            this.selectStaffsBeans.add(selectParticipantBean);
        }
        return this.selectStaffsBeans;

    }

    /**
     * 判断当前的人员是否是已经添加的
     *
     * @param staffsBean
     * @return
     */
    public boolean isData(ContactsBean.DataBean.StaffsBean staffsBean) {
        for (SelectParticipantBean staffsBean1 : selectStaffsBeans) {
            if (staffsBean1.id == staffsBean.id) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除选择的参与人
     *
     * @param staffsBean
     */
    public void selectStaffsBeans_Del(ContactsBean.DataBean.StaffsBean staffsBean) {
        for (SelectParticipantBean staffsBean1 : selectStaffsBeans) {
            if (staffsBean1.id == staffsBean.id) {
                selectStaffsBeans.remove(staffsBean1);
                break;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 删除选择的参与人
     *
     * @param staffsBean
     */
    public void selectStaffsBeans_Del(SelectParticipantBean staffsBean) {
        for (SelectParticipantBean staffsBean1 : selectStaffsBeans) {
            if (staffsBean1.id == staffsBean.id) {
                selectStaffsBeans.remove(staffsBean1);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void setSelectStaffsBeans(List<SelectParticipantBean> selectStaffsBeans) {
        this.selectStaffsBeans = selectStaffsBeans;
    }

    /**
     * 获取选择的参与人
     *
     * @return
     */
    public List<SelectParticipantBean> getSelectStaffsBeans() {
        return this.selectStaffsBeans;
    }

    /**
     * 选择所有的参与人
     */
    public void selectAllStaffs() {
        for (ContactsBean.DataBean.StaffsBean staffsBean : staffsBeans) {
            if (staffsBean.is_schedule != 1) {
                if (!isData(staffsBean)) {
                    selectStaffsBeans_Add(staffsBean);
                }
            }
        }
        setSelectParticipantHadAdapter();
    }

    /**
     * 删除所有的联系人
     */
    public void delAllStaffs() {
        for (ContactsBean.DataBean.StaffsBean staffsBean : staffsBeans) {
            if (staffsBean.is_schedule != 1) {
                if (isData(staffsBean)) {
                    selectStaffsBeans_Del(staffsBean);
                }
            }
        }
        setSelectParticipantHadAdapter();
    }

    //强制刷新，主界面的头部的数据
    public void setSelectParticipantHadAdapter() {
        if (mContext != null) {
            SelectParticipantActivity activity = (SelectParticipantActivity) mContext;
        }

    }

    /**
     * 判断，当前页面是否是全选，更新主界面
     *
     * @return  true是全选， false不是
     */
    public boolean is_allSelect(){

        if (staffsBeans == null || staffsBeans.size() == 0 || selectStaffsBeans == null  || selectStaffsBeans.size() == 0){
            return false;
        }

        for (ContactsBean.DataBean.StaffsBean staffsBean : staffsBeans) {
            if (!isNowSelectData(staffsBean)){
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是当前选择的数据
     * @param staffsBean
     * @return
     */
    public boolean isNowSelectData(ContactsBean.DataBean.StaffsBean staffsBean){
        for (SelectParticipantBean staffsBeanSelect : selectStaffsBeans) {
            if (staffsBean.id == staffsBeanSelect.id){
                return true;
            }
        }
        return false;
    }

    ItemNodesClickListener itemNodesClickListener;
    public void setItemNodesClickListener(ItemNodesClickListener itemNodesClickListener) {
        this.itemNodesClickListener = itemNodesClickListener;
    }
    public interface ItemNodesClickListener {
        void onClick(View view, ContactsBean.DataBean.NodesBean nodesBean, int position);
    }

    ItemStaffsClickListener itemStaffsClickListener;

    public void setItemStaffsClickListener(ItemStaffsClickListener itemStaffsClickListener) {
        this.itemStaffsClickListener = itemStaffsClickListener;
    }
    public interface ItemStaffsClickListener {
        void onClick(View view, List<SelectParticipantBean> selectStaffsBeans, ContactsBean.DataBean.StaffsBean staffsBean, int position);
    }
}
