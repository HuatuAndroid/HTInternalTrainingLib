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

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.HomeCourseBean;
import com.zhiyun88.www.module_main.main.bean.SearchListBean;

import java.util.List;

public class SearchAdapter extends BaseAdapter{
    private Context mContext;
    private List<SearchListBean> searchListBeans;

    public SearchAdapter(Context context, List<SearchListBean> searchListBeans) {
        this.mContext = context;
        this.searchListBeans = searchListBeans;
    }

    @Override
    public int getCount() {
        return searchListBeans.size();
    }

    @Override
    public SearchListBean getItem(int position) {
        return searchListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SearchListBean searchListBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_home_listview, null);
            viewHolder.imageView = convertView.findViewById(R.id.home_image);
            viewHolder.image_type = convertView.findViewById(R.id.image_type);
            viewHolder.title = convertView.findViewById(R.id.home_title);
            viewHolder.num = convertView.findViewById(R.id.home_num);
            viewHolder.cardview = convertView.findViewById(R.id.cardview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , searchListBean.getCover(), 4);
        }catch (Exception e){
            GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , "http://www.bai.com", 4);
        }

        viewHolder.title.setText(searchListBean.getTitle());
        viewHolder.image_type.setVisibility(searchListBean.getType().equals("1")?View.VISIBLE:View.GONE);
        viewHolder.num.setText(searchListBean.getStudy_num()+"人在学");
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("courseId", searchListBean.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        CardView cardview;
        ImageView imageView,image_type;
        TextView title,num;
    }
}
