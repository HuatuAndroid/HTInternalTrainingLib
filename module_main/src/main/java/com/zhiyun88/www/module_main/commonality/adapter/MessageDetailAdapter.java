package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wb.baselib.view.MultipleStatusView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.ui.MessageDetailActivity;

public class MessageDetailAdapter extends BaseAdapter {
    private Context mContext;

    public MessageDetailAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_message_details, null);
            viewHolder.imageView = convertView.findViewById(R.id.details_image);
            viewHolder.time = convertView.findViewById(R.id.details_time);
            viewHolder.content = convertView.findViewById(R.id.details_context);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position % 3 == 0) {
            viewHolder.imageView.setImageResource(R.drawable.user_notify_main_course);
        }else if (position % 3 == 1) {
            viewHolder.imageView.setImageResource(R.drawable.user_notify_main_task);
        }else {
            viewHolder.imageView.setImageResource(R.drawable.user_notify_main_system);
        }
        viewHolder.time.setText("8月8日 20:30");
        viewHolder.content.setText("我是内容我是内容我是内容我是内容我是内容我是内容");
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView time,content;
    }
}
