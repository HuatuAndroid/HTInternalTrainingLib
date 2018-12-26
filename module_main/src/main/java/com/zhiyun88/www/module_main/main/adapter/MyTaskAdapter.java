package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.MyTaskListBean;
import com.zhiyun88.www.module_main.task.ui.TaskInfoActivity;

import java.util.ArrayList;

import cn.bingoogolapple.progressbar.BGAProgressBar;

public class MyTaskAdapter extends BaseAdapter {
    private ArrayList<MyTaskListBean> myTaskListBeans;
    private Context mContext;

    public MyTaskAdapter(Context context, ArrayList<MyTaskListBean> myTaskListBeans) {
        this.mContext = context;
        this.myTaskListBeans= myTaskListBeans;
    }

    @Override
    public int getCount() {
        return myTaskListBeans.size();
    }

    @Override
    public MyTaskListBean getItem(int position) {
        return myTaskListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyTaskListBean myTaskListBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_mytask, null);
            viewHolder.type = convertView.findViewById(R.id.task_type);
            viewHolder.title = convertView.findViewById(R.id.task_title);
            viewHolder.time = convertView.findViewById(R.id.task_time);
            viewHolder.credit = convertView.findViewById(R.id.task_credit);
            viewHolder.progressbar = convertView.findViewById(R.id.task_progressbar);
            viewHolder.cardview = convertView.findViewById(R.id.cardview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (myTaskListBean.getType() == 1) {
            viewHolder.type.setText("[直播]");
            viewHolder.type.setTextColor(mContext.getResources().getColor(R.color.main_live_3c));
        }else if (myTaskListBean.getType() == 2) {
            viewHolder.type.setText("[培训]");
            viewHolder.type.setTextColor(mContext.getResources().getColor(R.color.main_train_03));
        }else {
            viewHolder.type.setText("[考试]");
            viewHolder.type.setTextColor(mContext.getResources().getColor(R.color.main_test_29));
        }

        viewHolder.title.setText(myTaskListBean.getName());
        viewHolder.time.setText(myTaskListBean.getStart_date()+"到"+myTaskListBean.getEnd_date());
        viewHolder.credit.setText("学分:"+myTaskListBean.getIntegral()+"分");
        viewHolder.progressbar.setProgress(myTaskListBean.getProgress());
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TaskInfoActivity.class);
                intent.putExtra("taskId", myTaskListBean.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder {
        TextView type,title,time,credit;
        BGAProgressBar progressbar;
        CardView cardview;
    }
}
