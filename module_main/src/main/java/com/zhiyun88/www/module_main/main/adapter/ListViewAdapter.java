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
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.RoundTransform;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.HomeCourseBean;

import java.util.List;

public class ListViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<HomeCourseBean> courseBeanList;

    public ListViewAdapter(Context context, List<HomeCourseBean> courseBeanList) {
        this.mContext = context;
        this.courseBeanList = courseBeanList;
    }

    @Override
    public int getCount() {
        return courseBeanList.size();
    }

    @Override
    public HomeCourseBean getItem(int position) {
        return courseBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HomeCourseBean homeCourseBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_home_listview, null);
            viewHolder.imageView = convertView.findViewById(R.id.home_image);
            viewHolder.image_type=convertView.findViewById(R.id.image_type);
            viewHolder.title = convertView.findViewById(R.id.home_title);
            viewHolder.home_teacher = convertView.findViewById(R.id.home_teacher);
            viewHolder.num = convertView.findViewById(R.id.home_num);
            viewHolder.cardview = convertView.findViewById(R.id.cardview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image_type.setVisibility(View.GONE);
        if (homeCourseBean.getCover()==null||homeCourseBean.getCover().equals("")) {
            Picasso.with(mContext).load("http://ww.baid.com").error(R.drawable.course_image).placeholder(R.drawable.course_image).transform(new RoundTransform(4)).into(viewHolder.imageView);
//            GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , "http://ww.baid.com", 4);
        }else {
            Picasso.with(mContext).load(homeCourseBean.getCover()).error(R.drawable.course_image).placeholder(R.drawable.course_image).transform(new RoundTransform(4)).into(viewHolder.imageView);
//            GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , homeCourseBean.getCover(), 4);
        }
        viewHolder.title.setText(homeCourseBean.getTitle());
        viewHolder.home_teacher.setText(homeCourseBean.getTeacher());
        viewHolder.num.setText(homeCourseBean.getStudy_count()+"人在学");
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("isCourseTaskInfo",false);
                intent.putExtra("courseId", homeCourseBean.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        CardView cardview;
        ImageView imageView,image_type;
        TextView title,num,home_teacher;
    }
}
