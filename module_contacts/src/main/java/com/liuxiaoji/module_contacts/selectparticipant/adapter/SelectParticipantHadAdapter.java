package com.liuxiaoji.module_contacts.selectparticipant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.base.BaseViewHolder;
import com.liuxiaoji.module_contacts.selectparticipant.bean.SelectParticipantBean;
import com.liuxiaoji.module_contacts.selectparticipant.ui.SelectParticipantHadViewHolder;
import com.liuxiaoji.module_contacts.selectparticipant.utils.GlideUtils;

import java.util.List;


/**
 * @author liuzhe
 * @date 2018/11/22
 *
 * 顶部选择的头像
 */
public class SelectParticipantHadAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    private Context context;
    private List<SelectParticipantBean> selectStaffsBeans;

    public SelectParticipantHadAdapter(Context context, List<SelectParticipantBean> selectStaffsBeans, ItemClickListener itemClickListener) {
        this.context = context;
        this.selectStaffsBeans = selectStaffsBeans;
        setItemClickListener(itemClickListener);
    }

    public void setData(List<SelectParticipantBean> selectStaffsBeans) {
        this.selectStaffsBeans = selectStaffsBeans;
        notifyDataSetChanged();
    }

    public int getDataSize(){
        if (selectStaffsBeans == null || selectStaffsBeans.size() == 0) {
            return 0;
        }
        return selectStaffsBeans.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_participant_had_item, parent, false);
        return new SelectParticipantHadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (holder instanceof SelectParticipantHadViewHolder) {
            SelectParticipantHadViewHolder selectStaffHadSelectItemViewHolder = (SelectParticipantHadViewHolder) holder;
            final SelectParticipantBean staffsBean = selectStaffsBeans.get(position);
            GlideUtils.setNetImage(context, staffsBean.avatar, selectStaffHadSelectItemViewHolder.circleImageView);
            selectStaffHadSelectItemViewHolder.userNameTv.setText(staffsBean.name);

            selectStaffHadSelectItemViewHolder.mLlDelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isShow;

                    delViewEven(position);

                    if (getData().size() == 0){
                        isShow = true;
                    }else{
                        isShow = false;
                    }

                    itemClickListener.onClick(v, staffsBean, isShow);
                }
            });
        }
    }

    private void delViewEven(int position) {

        selectStaffsBeans.remove(position);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return selectStaffsBeans.size();
    }

    public List<SelectParticipantBean> getData() {
        return selectStaffsBeans;
    }

    ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public interface ItemClickListener{
        void onClick(View view, SelectParticipantBean staffsBean, boolean isShow);
    }
}
