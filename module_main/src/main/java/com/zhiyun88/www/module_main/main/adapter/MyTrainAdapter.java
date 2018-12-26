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
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.MyTrainListBean;

import java.util.ArrayList;

public class MyTrainAdapter extends BaseAdapter {
    private ArrayList<MyTrainListBean> myTrainList;
    private Context mContext;

    public MyTrainAdapter(Context context, ArrayList<MyTrainListBean> myTrainBeans) {
        this.mContext = context;
        this.myTrainList = myTrainBeans;
    }

    @Override
    public int getCount() {
        return myTrainList.size();
    }

    @Override
    public MyTrainListBean getItem(int position) {
        return myTrainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyTrainListBean myTrainListBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_commom, null);
            viewHolder.imageView = convertView.findViewById(R.id.commom_image);
            viewHolder.title = convertView.findViewById(R.id.commom_title);
            viewHolder.time = convertView.findViewById(R.id.commom_time);
            viewHolder.cardview = convertView.findViewById(R.id.cardview);
            viewHolder.commom_teacher=convertView.findViewById(R.id.commom_teacher);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Picasso.with(mContext).load(myTrainListBean.getCover()).error(R.drawable.course_image).placeholder(R.drawable.course_image).into(viewHolder.imageView);
           // GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext ,myTrainListBean.getCover() , 4);
        }catch (Exception e){
            Picasso.with(mContext).load("http://www.baidu.com").error(R.drawable.course_image).placeholder(R.drawable.course_image).into(viewHolder.imageView);
           // GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext ,"http://www.baidu.com" , 4);
        }

        viewHolder.title.setText(myTrainListBean.getTitle());
        String start = myTrainListBean.getStart_date().substring(0, 16);
        viewHolder.commom_teacher.setVisibility(View.GONE);
        viewHolder.time.setVisibility(View.VISIBLE);
        viewHolder.time.setText(start+mContext.getString(R.string.main_classes));
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("courseId", myTrainListBean.getId());
                intent.putExtra("isCourseTaskInfo",true);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder {
        CardView cardview;
        ImageView imageView;
        TextView title,time,commom_teacher;
    }
}
