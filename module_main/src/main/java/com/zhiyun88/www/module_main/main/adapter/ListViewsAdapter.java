package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.ui.TopicDetailsActivity;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.HomeCourseBean;
import com.zhiyun88.www.module_main.main.bean.HomeInformationBean;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.List;

public class ListViewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeInformationBean> homeInformationBeans;

    public ListViewsAdapter(Context context, List<HomeInformationBean> homeInformationBeans) {
        this.mContext = context;
        this.homeInformationBeans = homeInformationBeans;
    }

    @Override
    public int getCount() {
        return homeInformationBeans.size();
    }

    @Override
    public HomeInformationBean getItem(int position) {
        return homeInformationBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeInformationBean informationBean = getItem(position);
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
        if (informationBean.getCover() == null || informationBean.getCover().equals("")) {
            GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , "http://ww.baid.com", 4);
        }else {
            GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , informationBean.getCover(), 4);
        }
        viewHolder.title.setText(informationBean.getTitle());
        viewHolder.time.setText(informationBean.getCreated_at());
        viewHolder.browse.setText(informationBean.getClick_rate());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView title,time,browse;
    }
}