package com.zhiyun88.www.module_main.information.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.information.bean.InformationDataListBean;

import java.util.List;

public class InformationListAdapter extends BaseAdapter{
    private Context mContext;
    private List<InformationDataListBean> dataListBeans;

    public InformationListAdapter(Context context, List<InformationDataListBean> dataListBeans) {
        this.mContext = context;
        this.dataListBeans = dataListBeans;
    }

    @Override
    public int getCount() {
        return dataListBeans.size();
    }

    @Override
    public InformationDataListBean getItem(int position) {
        return dataListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InformationDataListBean dataListBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_home_listviews, null);
            viewHolder.imageView = convertView.findViewById(R.id.item_image);
            viewHolder.title = convertView.findViewById(R.id.item_title);
            viewHolder.time = convertView.findViewById(R.id.item_time);
            viewHolder.browse = convertView.findViewById(R.id.item_browse);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , dataListBean.getPicture(), 4);
        viewHolder.title.setText(dataListBean.getInformation_title());
        viewHolder.time.setText(dataListBean.getCreated_at());
        viewHolder.browse.setText(dataListBean.getClick_rate());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView title,time,browse;
    }

}
