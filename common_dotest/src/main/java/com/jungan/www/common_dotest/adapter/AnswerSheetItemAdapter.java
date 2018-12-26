package com.jungan.www.common_dotest.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.bean.AnswerSheetBean;
import com.jungan.www.common_dotest.utils.StrUtils;
import com.jungan.www.common_dotest.view.mGriview;

import java.util.List;

public class AnswerSheetItemAdapter extends BaseAdapter{
    private List<AnswerSheetBean> answerSheetBeans;
    private Context mContext;

    public AnswerSheetItemAdapter(List<AnswerSheetBean> answerSheetBeans, Context mContext) {
        this.answerSheetBeans = answerSheetBeans;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return answerSheetBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return answerSheetBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnswerSheetItemHolder holder=null;
        AnswerSheetBean answerSheetBean= (AnswerSheetBean) getItem(position);
        if(convertView==null){
            holder=new AnswerSheetItemHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_answersheet_title,null);
            holder.mgv=convertView.findViewById(R.id.mgv);
            holder.title=convertView.findViewById(R.id.sheet_tv);
            convertView.setTag(holder);
        }else {
            holder= (AnswerSheetItemHolder) convertView.getTag();
        }
        holder.title.setText(StrUtils.Instance().getTestTypeStr(Integer.parseInt(answerSheetBean.getGroup())));
        holder.mgv.setAdapter(new AnswerSheetAdapter(answerSheetBean.getQuestionBankBeanList(),mContext));
        return convertView;
    }
    class AnswerSheetItemHolder{
         mGriview mgv;
         TextView title;
    }
}
