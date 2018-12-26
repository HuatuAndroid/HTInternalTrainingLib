package com.zhiyun88.www.module_main.train.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.baselib.adapter.ListBaseAdapter;
import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.GlideImageLoader;
import com.zhiyun88.www.module_main.train.bean.TrainListData;

import java.util.List;

public class TrainListAdapter extends ListBaseAdapter<TrainListData> {
    private List<TrainListData> trainListDataLists;
    private Context mContext;
    public TrainListAdapter(List<TrainListData> list, Context context) {
        super(list, context);
        this.trainListDataLists=list;
        this.mContext=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrainListHolder holder=null;
        final TrainListData trainListData=getItem(position);
        if(convertView==null){
            holder=new TrainListHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.main_train_trainlist,null);
            holder.train_image=convertView.findViewById(R.id.train_image);
            holder.train_title=convertView.findViewById(R.id.train_title);
            holder.train_time=convertView.findViewById(R.id.train_time);
            holder.train_address=convertView.findViewById(R.id.train_address);
            holder.train_num=convertView.findViewById(R.id.train_num);
            holder.cardview=convertView.findViewById(R.id.cardview);
            convertView.setTag(holder);
        }else {
            holder= (TrainListHolder) convertView.getTag();
        }
        holder.train_address.setText("地点："+trainListData.getAddress());
        holder.train_num.setText(trainListData.getStudy_count()+"人报名");
        holder.train_time.setText("时间："+trainListData.getStart_end_date());
        holder.train_title.setText(trainListData.getTitle());
        try {
            GlideManager.getInstance().setCommonPhoto(holder.train_image,R.drawable.course_image ,mContext , trainListData.getCover()==null||trainListData.getCover().equals("")?"http://ww.baid.com":trainListData.getCover(), false);
        }catch (Exception e){
            GlideManager.getInstance().setCommonPhoto(holder.train_image,R.drawable.course_image ,mContext , "http://ww.baid.com", false);
        }

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("isCourseTaskInfo",true);
                intent.putExtra("courseId", trainListData.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class TrainListHolder{
        CardView cardview;
        ImageView train_image;
        TextView train_title,train_time,train_address,train_num;
    }
}
