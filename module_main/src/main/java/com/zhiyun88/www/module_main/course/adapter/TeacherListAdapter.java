package com.zhiyun88.www.module_main.course.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.adapter.ListBaseAdapter;
import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.bean.TeacherData;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.List;

public class TeacherListAdapter extends ListBaseAdapter<TeacherData> {
    private Context mContext;
    public TeacherListAdapter(List<TeacherData> list, Context context) {
        super(list, context);
        this.mContext=context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TeacherListHolder holder=null;
        TeacherData teacherData=getItem(i);
        if(view==null){
            holder=new TeacherListHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.main_course_teacher_item,null);
            holder.context_tv=view.findViewById(R.id.context_tv);
            holder.user_img=view.findViewById(R.id.user_img);
            holder.title_tv=view.findViewById(R.id.title_tv);
            view.setTag(holder);
        }else {
            holder= (TeacherListHolder) view.getTag();
        }
        try {
            Picasso.with(mContext).load(teacherData.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(holder.user_img);
        }catch (Exception e){
            Picasso.with(mContext).load("http://www.baidu.com").error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(holder.user_img);
        }

        holder.context_tv.setText(teacherData.getTeacher_info());
        holder.title_tv.setText(teacherData.getName());
        return view;
    }
    class TeacherListHolder{
        ImageView user_img;
        TextView title_tv,context_tv;
    }
}
