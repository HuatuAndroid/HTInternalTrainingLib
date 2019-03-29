package com.example.module_employees_world.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.ListBean;

import java.util.List;

public class SelectGroupAdapter extends BaseAdapter {

    private List<ListBean> listBeans;
    private Context mContext;
    private OnItemJoinListener onItemJoinListener;

    public SelectGroupAdapter(Context context, List<ListBean> listBeans, OnItemJoinListener onItemJoinListener) {
        this.mContext = context;
        this.listBeans = listBeans;
        this.onItemJoinListener = onItemJoinListener;
    }

    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public ListBean getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ListBean listBean = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_group, null);
            viewHolder.mTvGroupName = convertView.findViewById(R.id.mTvGroupName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTvGroupName.setText(listBean.getName()+"");

        viewHolder.mTvGroupName.setOnClickListener(v -> {
            if (onItemJoinListener == null) return;
            onItemJoinListener.onItemJoinListener(listBean);
        });

        return convertView;
    }

    class ViewHolder {
        TextView mTvGroupName;
    }

    public interface OnItemJoinListener{
        void onItemJoinListener(ListBean listBean);
    }

}
