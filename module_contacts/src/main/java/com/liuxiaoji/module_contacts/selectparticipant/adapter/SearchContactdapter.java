package com.liuxiaoji.module_contacts.selectparticipant.adapter;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsBean;
import com.liuxiaoji.module_contacts.selectparticipant.utils.GlideUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class SearchContactdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements LifecycleObserver {

    private View mView = null;
    private final LayoutInflater mInflater;
    private Context mContext;
    private List<ContactsBean.DataBean.StaffsBean> staffs;

    public SearchContactdapter(Context mContext, List<ContactsBean.DataBean.StaffsBean> staffs, ItemClickListener itemClickListener) {

        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        setStaffsBeans(staffs);
        setItemClickListener(itemClickListener);

    }

    public void setStaffsBeans(List<ContactsBean.DataBean.StaffsBean> staffs) {
        this.staffs = staffs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        mView = mInflater.inflate(R.layout.search_contact_nitem, viewGroup, false);
        MViewHolder mViewHolderViewPager = new MViewHolder(mView);

        return mViewHolderViewPager;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        MViewHolder holderViewPage = (MViewHolder) viewHolder;

        holderViewPage.viewData(position);

    }

    public class MViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mRlClickEven;
        CircleImageView mCircleImageView;
        TextView mTvPersonalName, mTvPersonalId, mTvDepartmentName, mTvEmail;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);

            mRlClickEven = itemView.findViewById(R.id.mRlClickEven);
            mCircleImageView = itemView.findViewById(R.id.mCircleImageView);
            mTvPersonalName = itemView.findViewById(R.id.mTvPersonalName);
            mTvPersonalId = itemView.findViewById(R.id.mTvPersonalId);
            mTvDepartmentName = itemView.findViewById(R.id.mTvDepartmentName);
            mTvEmail = itemView.findViewById(R.id.mTvEmail);

        }

        public void viewData(final int position) {

            ContactsBean.DataBean.StaffsBean staffsBean = staffs.get(position);
            ContactsBean.DataBean.StaffsBean.NodeBean nodeBean = null;
            if (staffsBean.node != null) {
                nodeBean = staffsBean.node;
            }

            GlideUtils.setNetImage(mContext, staffsBean.avatar, mCircleImageView);

            mTvPersonalName.setText(staffsBean.name);
            mTvPersonalId.setText(staffsBean.id + "");

            if (nodeBean != null) {
                mTvDepartmentName.setText(nodeBean.name);
            }
            mTvEmail.setText(staffsBean.workEmail);

            mRlClickEven.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(v, staffs.get(getAdapterPosition()), getAdapterPosition());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return staffs.size();
    }

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, ContactsBean.DataBean.StaffsBean staffsBean, int position);
    }
}