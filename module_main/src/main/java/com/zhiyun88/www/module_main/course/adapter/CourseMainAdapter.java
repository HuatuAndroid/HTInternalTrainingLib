package com.zhiyun88.www.module_main.course.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.baselib.adapter.ListBaseAdapter;
import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.bean.CourseMainData;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;

import java.util.List;

public class CourseMainAdapter extends ListBaseAdapter<CourseMainData> {
    private Context mContext;
    public CourseMainAdapter(List<CourseMainData> list, Context context) {
        super(list, context);
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseMainHolder holder=null;
        final CourseMainData courseMainData=getItem(position);
        if(convertView==null){
            holder=new CourseMainHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.main_course_coursemain_item,null);
            holder.coursemain_image=convertView.findViewById(R.id.coursemain_image);
            holder.coursemain_num=convertView.findViewById(R.id.coursemain_num);
            holder.coursemain_teacher=convertView.findViewById(R.id.coursemain_teacher);
            holder.coursemain_title=convertView.findViewById(R.id.coursemain_title);
            holder.image_type=convertView.findViewById(R.id.image_type);
            holder.cardview=convertView.findViewById(R.id.cardview);
            convertView.setTag(holder);
        }else {
            holder= (CourseMainHolder) convertView.getTag();
        }
        holder.image_type.setVisibility(courseMainData.getType().equals("1")?View.VISIBLE:View.GONE);
        holder.coursemain_title.setText(courseMainData.getTitle());
        holder.coursemain_teacher.setText(courseMainData.getTeacher());
        holder.coursemain_num.setText(courseMainData.getStudy_num()+"人学习");
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("courseId", courseMainData.getId());
                intent.putExtra("isCourseTaskInfo",false);
                mContext.startActivity(intent);
            }
        });
        try {
            GlideManager.getInstance().setCommonPhoto(holder.coursemain_image,R.drawable.course_image,mContext,courseMainData.getCover()==null||courseMainData.getCover().equals("")?"http://www.baidu.com":courseMainData.getCover(),false);
        }catch (Exception e){
            GlideManager.getInstance().setCommonPhoto(holder.coursemain_image,R.drawable.course_image,mContext,"http://www.baidu.com",false);
        }

        return convertView;
    }
    class CourseMainHolder{
        ImageView coursemain_image,image_type;
        CardView cardview;
        TextView coursemain_num,coursemain_teacher,coursemain_title;
    }

}
