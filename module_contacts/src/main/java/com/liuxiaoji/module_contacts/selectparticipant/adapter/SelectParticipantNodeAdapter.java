package com.liuxiaoji.module_contacts.selectparticipant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;
import com.liuxiaoji.module_contacts.selectparticipant.bean.ContactsNodeBean;
import com.liuxiaoji.module_contacts.selectparticipant.ui.SelectParticipantNodeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhe
 * @date 2018/11/22
 * <p>
 * 中间节点的adapter
 */
public class SelectParticipantNodeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<ContactsNodeBean> contactsNodeBeans;

    public SelectParticipantNodeAdapter(Context mContext, List<ContactsNodeBean> contactsNodeBeans, ItemClickListener itemClickListener) {
        this.contactsNodeBeans = contactsNodeBeans;
        this.mContext = mContext;
        setItemClickListener(itemClickListener);
    }

    public void setData(List<ContactsNodeBean> contactsNodeBeans) {

        this.contactsNodeBeans = contactsNodeBeans;

        notifyDataSetChanged();

        if (contactsNodeBeans.size() == 1){
            itemClickListener.adapter_Gone();
        }


    }

    public List<ContactsNodeBean> getContactsNodeBeans() {
        return contactsNodeBeans;
    }

    public int gettDataCount() {
        return contactsNodeBeans.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_participant_node_item, parent, false);
        return new SelectParticipantNodeHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {

       final ContactsNodeBean contactsNodeBean = contactsNodeBeans.get(position);

        if (holder instanceof SelectParticipantNodeHolder) {
            SelectParticipantNodeHolder selectParticipantNodeHolder = (SelectParticipantNodeHolder) holder;

            if (position == contactsNodeBeans.size() - 1) {

                selectParticipantNodeHolder.mTvName.setTextColor(mContext.getResources().getColor(R.color.black_3D3E43));
                selectParticipantNodeHolder.mIvJiantou.setVisibility(View.GONE);

            } else {

                selectParticipantNodeHolder.mTvName.setTextColor(mContext.getResources().getColor(R.color.blue_4A88FB));
                selectParticipantNodeHolder.mIvJiantou.setVisibility(View.VISIBLE);

            }

            selectParticipantNodeHolder.mTvName.setText(contactsNodeBean.name);

            selectParticipantNodeHolder.mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    delViewEven(position);

                    itemClickListener.onClick(v, contactsNodeBean, position);
                }
            });
        }
    }

    private void delViewEven(int position) {

        List<ContactsNodeBean> nContactsNodeBeans = new ArrayList<>();

        for (int i = 0; i < contactsNodeBeans.size(); i++) {

            if (i <= position){
                nContactsNodeBeans.add(contactsNodeBeans.get(i));
            }
        }

       setData(nContactsNodeBeans);

    }

    @Override
    public int getItemCount() {
        return contactsNodeBeans.size();
    }

    public List<ContactsNodeBean> getData() {
        return contactsNodeBeans;
    }

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, ContactsNodeBean followDataItem, int position);

        void adapter_Gone();
    }
}
