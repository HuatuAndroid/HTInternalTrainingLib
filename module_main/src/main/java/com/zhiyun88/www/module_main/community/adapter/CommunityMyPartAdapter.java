package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.MyPartListBean;

import java.util.List;

public class CommunityMyPartAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyPartListBean> myPartListBeans;

    public CommunityMyPartAdapter(Context context, List<MyPartListBean> myPartListBeans) {
        this.mContext = context;
        this.myPartListBeans = myPartListBeans;
    }

    @Override
    public int getCount() {
        return myPartListBeans.size();
    }

    @Override
    public MyPartListBean getItem(int position) {
        return myPartListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyPartListBean listBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_community_myjoin, null);
            viewHolder.title = convertView.findViewById(R.id.myjoin_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText((position+1)+". "+listBean.getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView title;
    }

}
