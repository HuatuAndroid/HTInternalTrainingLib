package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.MyCourseListBean;
import com.zhiyun88.www.module_main.main.call.MyCourseCommentCall;

import java.util.List;

import cn.bingoogolapple.progressbar.BGAProgressBar;

public class MyCourseAdapter extends BaseAdapter {
    private List<MyCourseListBean> myCourseListBeans;
    private Context mContext;
    private MyCourseCommentCall mCall;

    public void setmCall(MyCourseCommentCall mCall) {
        this.mCall = mCall;
    }

    public MyCourseAdapter(Context context, List<MyCourseListBean> myCourseListBeans) {
        this.mContext = context;
        this.myCourseListBeans = myCourseListBeans;
    }

    @Override
    public int getCount() {
        return myCourseListBeans.size();
    }

    @Override
    public MyCourseListBean getItem(int position) {
        return myCourseListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyCourseListBean myCourseListBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_commom, null);
            viewHolder.imageView = convertView.findViewById(R.id.commom_image);
            viewHolder.title = convertView.findViewById(R.id.commom_title);
            viewHolder.teacher = convertView.findViewById(R.id.commom_teacher);
            viewHolder.progressbar = convertView.findViewById(R.id.commom_progressbar);
            viewHolder.cardview = convertView.findViewById(R.id.cardview);
            viewHolder.course_pj_img=convertView.findViewById(R.id.course_pj_img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Picasso.with(mContext).load(myCourseListBean.getCover()).error(R.drawable.course_image).placeholder(R.drawable.course_image).into(viewHolder.imageView);
           // GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , myCourseListBean.getCover(), 4);
        }catch (Exception e){
            Picasso.with(mContext).load("http://www.baidu.com").error(R.drawable.course_image).placeholder(R.drawable.course_image).into(viewHolder.imageView);
            //GlideManager.getInstance().setRoundPhoto(viewHolder.imageView,R.drawable.course_image ,mContext , "http://www.baidu.com", 4);
        }
        viewHolder.course_pj_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCall==null)
                    return;
                mCall.userCommentBy(myCourseListBean.getId(),position);
            }
        });
        viewHolder.title.setText(myCourseListBean.getTitle());
        viewHolder.teacher.setVisibility(View.VISIBLE);
        viewHolder.teacher.setText(mContext.getString(R.string.main_be_the_speaker)+myCourseListBean.getTeacher());
        Log.e("进度",myCourseListBean.getRate_progress()+"-----");
        if(myCourseListBean.getRate_progress()==100){
            viewHolder.progressbar.setVisibility(View.GONE);
            viewHolder.course_pj_img.setVisibility(View.VISIBLE);
            if(myCourseListBean.getIs_comment()==0){
                //未评价
                viewHolder.course_pj_img.setEnabled(true);
                viewHolder.course_pj_img.setImageResource(R.drawable.pj_img);
            }else {
                viewHolder.course_pj_img.setEnabled(false);
                viewHolder.course_pj_img.setImageResource(R.drawable.pj_no_img);
            }
        }else {
            viewHolder.progressbar.setVisibility(View.VISIBLE);
            viewHolder.course_pj_img.setVisibility(View.GONE);
        }

        viewHolder.progressbar.setProgress(myCourseListBean.getRate_progress());
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("isCourseTaskInfo",false);
                intent.putExtra("courseId", myCourseListBean.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder {
        CardView cardview;
        ImageView imageView,course_pj_img;
        TextView title,teacher;
        BGAProgressBar progressbar;
    }
    public void updateItem(int index, ListView mListView){
        int postion=mListView.getFirstVisiblePosition();
        if(index-postion>=0){
            View mView=mListView.getChildAt(index-postion);
            ViewHolder holder= (ViewHolder) mView.getTag();
            myCourseListBeans.get(index).setIs_comment(1);
            holder.course_pj_img.setEnabled(false);
            holder.course_pj_img.setImageResource(R.drawable.pj_no_img);
        }
    }
}
