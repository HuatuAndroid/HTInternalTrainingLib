package com.zhiyun88.www.module_main.dotesting.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wb.baselib.view.MyGrideView;
import com.wb.baselib.view.MyListView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.dotesting.bean.CountBean;
import com.zhiyun88.www.module_main.dotesting.bean.CountInfoData;
import com.zhiyun88.www.module_main.dotesting.bean.CuntQuesData;

import java.util.List;

public class CuntAdapter extends BaseAdapter {
    private CountBean countBean;
    private Context mContext;

    public CuntAdapter(CountBean countBean, Context mContext) {
        this.countBean = countBean;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 2;
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
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type=getItemViewType(position);
        if(type==0){
            return getTopView(countBean.getInfo(),convertView);
        }else {
            return getBottomView(countBean.getQues_data(),convertView);
        }
    }
    private View getTopView(CountInfoData countInfoData,View mView){
        TopCuntHolder holder=null;
        if(mView==null){
            holder=new TopCuntHolder();
            mView= LayoutInflater.from(mContext).inflate(R.layout.dotest_top_layout,null);
            holder.title_tv=mView.findViewById(R.id.title_tv);
            holder.date_tv=mView.findViewById(R.id.date_tv);
            holder.time_tv=mView.findViewById(R.id.time_tv);
            holder.right_cunt_tv=mView.findViewById(R.id.right_cunt_tv);
            holder.zcunt_tv=mView.findViewById(R.id.zcunt_tv);
            holder.df_tv=mView.findViewById(R.id.df_tv);
            mView.setTag(mView);
        }else {
            holder= (TopCuntHolder) mView.getTag();
        }
        holder.title_tv.setText(countInfoData.getPaper_name());
        holder.date_tv.setText("交卷时间："+countInfoData.getUpdated_at());
        String reportTime = countInfoData.getReport_time_long();
        if (!TextUtils.isEmpty(reportTime)) {
            if (reportTime.startsWith("0"))
                reportTime = reportTime.substring(1, reportTime.length());
            reportTime = reportTime.replace("分", "′");
            reportTime = reportTime.replace("秒", "″");
            holder.time_tv.setText("考试时长："+reportTime);
        }

        holder.right_cunt_tv.setText(countInfoData.getRight_count());
        holder.zcunt_tv.setText("/"+countInfoData.getQues_count() + "道");
        holder.df_tv.setText(countInfoData.getPoint());
        return mView;
    }
    class TopCuntHolder{
        TextView title_tv,date_tv,time_tv,right_cunt_tv,zcunt_tv,df_tv;
    }

    private View getBottomView(List<CuntQuesData> countInfoData, View mView){
        BottomCuntHolder holder=null;
        if(mView==null){
            holder=new BottomCuntHolder();
            mView= LayoutInflater.from(mContext).inflate(R.layout.dotest_bottom_layout,null);
            holder.myListView=mView.findViewById(R.id.mlist_lv);
            holder.myListView.setAdapter(new QuestAdapter(countInfoData,mContext));
            mView.setTag(mView);
        }else {
            holder= (BottomCuntHolder) mView.getTag();
        }

        return mView;
    }
    class BottomCuntHolder{
            MyGrideView myListView;
    }
}
