package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zhiyun88.www.module_main.R;

import java.util.ArrayList;
import java.util.List;

public class UseringHistoryAdapter extends BaseAdapter {
    private  List<String> list;
    private Context mContext;

    public UseringHistoryAdapter(Context context , List<String> list) {
        this.list=list;
        this.mContext=context;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list.isEmpty())return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return  list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_history, parent, false);
        TextView title = convertView.findViewById(R.id.title);
        title.setText(list.get(position));
        return convertView;
    }

    public void setNewData(List<String> newData) {
        if(newData==null)
            return;
        for(int i=0;i<newData.size();i++){
            if(newData.get(i).equals("")){
                newData.remove(i);
            }
        }
        this.list = newData;
        notifyDataSetChanged();
    }
}
