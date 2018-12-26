package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordBean;
import com.zhiyun88.www.module_main.commonality.bean.RecordListBean;

import java.util.ArrayList;

public class RecordAdapter extends BaseAdapter {
    private ArrayList<RecordListBean> recordListBeans;
    private Context mContext;

    public RecordAdapter(Context context, ArrayList<RecordListBean> recordListBeans) {
        this.mContext = context;
        this.recordListBeans = recordListBeans;
    }

    @Override
    public int getCount() {
        return recordListBeans.size();
    }

    @Override
    public RecordListBean getItem(int position) {
        return recordListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordListBean listBean = getItem(position);
        RecordHolder recordHolder;
            if (convertView == null) {
                recordHolder = new RecordHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.main_fragment_integral_record, null);
                recordHolder.title = convertView.findViewById(R.id.record_title);
                recordHolder.time = convertView.findViewById(R.id.record_time);
                recordHolder.content = convertView.findViewById(R.id.record_score);
                convertView.setTag(recordHolder);
            }else {
                recordHolder = (RecordHolder) convertView.getTag();
            }
            recordHolder.title.setText(listBean.getName());
            recordHolder.time.setText(listBean.getCreated_at()+"完成");
            if (listBean.getIs_increase() == 0) {
                recordHolder.content.setText("+"+listBean.getIntegral());
                recordHolder.content.setTextColor(mContext.getResources().getColor(R.color.main_text_red_1));
            }else {
                recordHolder.content.setText("-"+listBean.getIntegral());
                recordHolder.content.setTextColor(mContext.getResources().getColor(R.color.main_text_cyan_13));
            }
            return convertView;
    }
    class RecordHolder {
        TextView title,time,content;
    }
}

