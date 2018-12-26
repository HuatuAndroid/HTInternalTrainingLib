package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.MessageDetailsBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private List<MessageDetailsBean> messageDetailsBeans;
    private Context mContext;

    public  MessageAdapter(Context context, List<MessageDetailsBean> messageDetailsBeans) {
        this.mContext = context;
        this.messageDetailsBeans = messageDetailsBeans;
    }

    @Override
    public int getCount() {
        return messageDetailsBeans.size();
    }

    @Override
    public MessageDetailsBean getItem(int position) {
        return messageDetailsBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageDetailsBean detailsBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_message, null);
            viewHolder.imageView = convertView.findViewById(R.id.user_message_image);
            viewHolder.title = convertView.findViewById(R.id.user_message_title);
            viewHolder.time = convertView.findViewById(R.id.user_message_time);
            viewHolder.content = convertView.findViewById(R.id.user_message_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (detailsBean.getMessage_type() == 1) {
            viewHolder.title.setText("系统通知");
            if (detailsBean.getIs_read() == 0) {
                viewHolder.imageView.setImageResource(R.drawable.user_notify_main_system_red);
            }else {
                viewHolder.imageView.setImageResource(R.drawable.user_notify_main_system);
            }
        }else if (detailsBean.getMessage_type() == 2) {
            viewHolder.title.setText("课程通知");
            if (detailsBean.getIs_read() == 0) {
                viewHolder.imageView.setImageResource(R.drawable.user_notify_main_course_red);
            }else {
                viewHolder.imageView.setImageResource(R.drawable.user_notify_main_course);
            }
        }else {
            viewHolder.title.setText("任务通知");
            if (detailsBean.getIs_read() == 0) {
                viewHolder.imageView.setImageResource(R.drawable.user_notify_main_task_red);
            }else {
                viewHolder.imageView.setImageResource(R.drawable.user_notify_main_task);
            }
        }
        viewHolder.time.setText(getDateToString(detailsBean.getApp_time_created_at(),"MM月dd日"));
        viewHolder.content.setText(detailsBean.getMessage_desc());
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView title,time,content;
    }

    public static String getDateToString(String milSecond, String pattern) {
        Date date = new Date(Long.parseLong(milSecond+"000"));
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
    public void updateItem(int index, ListView mListView){
        int postion=mListView.getFirstVisiblePosition();
        messageDetailsBeans.get(index).setIs_read(1);
        if(index-postion>=0){
            View mView=mListView.getChildAt(index-postion);
            ViewHolder viewHolder= (ViewHolder) mView.getTag();
            if (messageDetailsBeans.get(index).getMessage_type() == 1) {
                viewHolder.title.setText("系统通知");
                if (messageDetailsBeans.get(index).getIs_read() == 0) {
                    viewHolder.imageView.setImageResource(R.drawable.user_notify_main_system_red);
                }else {
                    viewHolder.imageView.setImageResource(R.drawable.user_notify_main_system);
                }
            }else if (messageDetailsBeans.get(index).getMessage_type() == 2) {
                viewHolder.title.setText("课程通知");
                if (messageDetailsBeans.get(index).getIs_read() == 0) {
                    viewHolder.imageView.setImageResource(R.drawable.user_notify_main_course_red);
                }else {
                    viewHolder.imageView.setImageResource(R.drawable.user_notify_main_course);
                }
            }else {
                viewHolder.title.setText("任务通知");
                if (messageDetailsBeans.get(index).getIs_read() == 0) {
                    viewHolder.imageView.setImageResource(R.drawable.user_notify_main_task_red);
                }else {
                    viewHolder.imageView.setImageResource(R.drawable.user_notify_main_task);
                }
            }
        }
    }
}
