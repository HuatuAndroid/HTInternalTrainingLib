package com.example.seele.newht;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wb.baselib.view.MyListView;

public class Test1Adapter extends BaseAdapter {
    private Context mContext;

    public Test1Adapter(Context mContext) {
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
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position)==0){
            return getOneView(convertView);
        }else if(getItemViewType(position)==1){
            return getTwoView(convertView);
        }
        return null;
    }
    private View getOneView(View mView){
        OneHolder holder;
        if(mView==null){
            holder=new OneHolder();
            mView= LayoutInflater.from(mContext).inflate(R.layout.test1_layout,null);
            mView.setTag(holder);
        }else {
            holder= (OneHolder) mView.getTag();
        }
        return mView;
    }
    class OneHolder{
        TextView textView;
    }
    private View getTwoView(View mView){
        TwoHolder holder;
        mView=null;
        if(mView==null){
            holder=new TwoHolder();
            mView= LayoutInflater.from(mContext).inflate(R.layout.test1_item,null);
            holder.myListView=mView.findViewById(R.id.mlist);
            mView.setTag(holder);
        }else {
            holder= (TwoHolder) mView.getTag();
        }
        holder.myListView.setAdapter(new TestAdapter(mContext));
        return mView;
    }
    class TwoHolder{
         MyListView myListView;
    }
}
