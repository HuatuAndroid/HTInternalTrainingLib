package com.zhiyun88.www.module_main.dotesting.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wb.baselib.adapter.ListBaseAdapter;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.dotesting.bean.CuntQuesData;

import java.util.List;

public class QuestAdapter extends ListBaseAdapter<CuntQuesData> {
    private Context mContext;
    public QuestAdapter(List<CuntQuesData> list, Context context) {
        super(list, context);
        this.mContext=context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        QuestHolder holder=null;
        if(view==null){
            holder=new QuestHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.dotest_question_tv,null);
            holder.textView=view.findViewById(R.id.quest_tv);
            view.setTag(holder);
        }else {
            holder= (QuestHolder) view.getTag();
        }
        holder.textView.setText(getItem(i).getQues_number());
        if(getItem(i).getIs_right().equals("1")){
            holder.textView.setBackgroundResource(R.drawable.right_bg);
            holder.textView.setTextColor(Color.WHITE);
        }else if(getItem(i).getIs_right().equals("2")){
            holder.textView.setBackgroundResource(R.drawable.error_bg);
            holder.textView.setTextColor(Color.WHITE);
        }else {
            holder.textView.setBackgroundResource(R.drawable.nodo_bg);
            holder.textView.setTextColor(Color.BLACK);
        }
        return view;
    }
    class QuestHolder{
        TextView textView;
    }
}
