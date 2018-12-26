package com.jungan.www.common_dotest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.call.TestViewpageCall;
import com.jungan.www.common_dotest.view.mGriview;

import java.util.List;

public class AnswerSheetAdapter extends BaseAdapter {
    private List<QuestionBankBean> questionBankBeanList;
    private Context mContext;
    private TestViewpageCall mCall;
    public AnswerSheetAdapter(List<QuestionBankBean> questionBankBeanList, Context mContext) {
        this.questionBankBeanList = questionBankBeanList;
        this.mContext = mContext;
        this.mCall= (TestViewpageCall) mContext;
    }

    @Override
    public int getCount() {
        return questionBankBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionBankBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnswerSheetHolder holder=null;
        final QuestionBankBean questionBankBean= (QuestionBankBean) getItem(position);
        if(convertView==null){
            holder=new AnswerSheetHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_answermain_item,null);
            holder.option_tv=convertView.findViewById(R.id.option_tv);
            convertView.setTag(holder);
        }else {
            holder= (AnswerSheetHolder) convertView.getTag();
        }
        holder.option_tv.setText(questionBankBean.getQuestionNum()+"");
        if(questionBankBean.getUser_answer()==null||questionBankBean.getUser_answer().equals("")||questionBankBean.getUser_answer().equals("暂无")){
                holder.option_tv.setBackgroundResource(R.drawable.nodo_bg);
            holder.option_tv.setTextColor(Color.BLACK);
        }else {
            holder.option_tv.setBackgroundResource(R.drawable.right_bg);
            holder.option_tv.setTextColor(Color.WHITE);
        }
        holder.option_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCall==null)
                    return;
                mCall.currentPage(Integer.parseInt((questionBankBean.getQuestionNum()-1)+""));
            }
        });
        return convertView;
    }
    class AnswerSheetHolder{
        TextView option_tv;
    }
}
