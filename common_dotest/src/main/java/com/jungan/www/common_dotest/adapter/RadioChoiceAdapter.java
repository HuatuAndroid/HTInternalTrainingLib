package com.jungan.www.common_dotest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.bean.WdBean;
import com.jungan.www.common_dotest.call.OptionCall;
import com.jungan.www.common_dotest.call.UserLookAnalisysCall;
import com.jungan.www.common_dotest.config.QuestionTypeConfig;
import com.jungan.www.common_dotest.utils.GlideCircleTransform;
import com.jungan.www.common_dotest.view.HtmlTextView;
import com.jungan.www.common_dotest.view.MyListView;
import com.wb.baselib.utils.SpanUtil;

import java.util.ArrayList;
import java.util.List;

import me.next.tagview.TagCloudView;

public class RadioChoiceAdapter extends BaseAdapter {
    private QuestionBankBean questionBankBean;
    private Context mContext;
    private OptionCall mCall;
    CommonQuestionOptionAdapter mAdapter=null;
    private UserLookAnalisysCall userLookAnalisysCall;
    public void setmCall(OptionCall mCall) {
        this.mCall = mCall;
    }
    private boolean analisys;
    public RadioChoiceAdapter(QuestionBankBean questionBankBean, Context context,boolean analis) {
        this.analisys=analis;
        this.questionBankBean = questionBankBean;
        this.mContext = context;
        this.userLookAnalisysCall= (UserLookAnalisysCall) context;
    }

    @Override
    public int getCount() {
        if(analisys){
            return 3;
        }else {
            return 2;
        }
//        return 2;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position)==0){
            return getOneView(position,convertView);
        }else if(getItemViewType(position)==1){
            return getTwoView(position,convertView);
        }else if(getItemViewType(position)==2){
            return getThreeView(position,convertView);
        }
//        else if(getItemViewType(position)==3){
//            return getAnswerView(position,convertView);
//        }
        return null;
    }
    private View getOneView(int option,View convertView){
        RadioChoiceOneHolder holder=null;
        if(convertView==null){
            holder=new RadioChoiceOneHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_option,null);
            holder.html_htv=convertView.findViewById(R.id.html_htv);
            holder.html_htv.setOption(false);
            convertView.setTag(holder);
        }else {
            holder= (RadioChoiceOneHolder) convertView.getTag();
        }
        String httml="";
        if(questionBankBean.getQuestionType()==1){
            httml="<font color='#458bfd'>（单选题）</font>";
        }else if(questionBankBean.getQuestionType()==2){
            httml="<font color='#458bfd'>（多选题）</font>";
        }else if(questionBankBean.getQuestionType()==5){
            httml="<font color='#458bfd'>（问答题）</font>";
        }

        holder.html_htv.showTxt(httml+questionBankBean.getQuestionStem());
        return convertView;
    }
    private View getTwoView(int option,View convertView){
        RadioChoiceTwoHolder holder=null;
        if(convertView==null){
            holder=new RadioChoiceTwoHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_mylistview,null);
            holder.myListView=convertView.findViewById(R.id.mylistview);
            convertView.setTag(holder);
        }else {
            holder= (RadioChoiceTwoHolder) convertView.getTag();
        }


        mAdapter=new CommonQuestionOptionAdapter(questionBankBean.getUserOption(),mContext,questionBankBean.getQuestionType(),analisys,questionBankBean.getRight_answer(),questionBankBean.getUser_answer());
        holder.myListView.setAdapter(mAdapter);
        mAdapter.setmCall(new OptionCall() {
            @Override
            public void getUserSelectOption(String option,int type) {
                if(mCall==null)
                    return;
                mCall.getUserSelectOption(option,type);

            }
        });

        return convertView;
    }
    private View getThreeView(int option ,View convertView){
        RadioThreeHolder holder=null;
        if(convertView==null){
            holder=new RadioThreeHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_lookasyn,null);
            holder.username1_tv=convertView.findViewById(R.id.username1_tv);
            holder.time1_tv=convertView.findViewById(R.id.time1_tv);
            holder.dn1_tv=convertView.findViewById(R.id.dn1_tv);
            holder.wd1_rel=convertView.findViewById(R.id.wd1_rel);
            holder.user1_tx_img=convertView.findViewById(R.id.user1_tx_img);


            holder.look_jx_tv=convertView.findViewById(R.id.look_jx_tv);
            holder.main_ll=convertView.findViewById(R.id.main_ll);
            holder.user_right_tv=convertView.findViewById(R.id.user_right_tv);
            holder.user_answer_tv=convertView.findViewById(R.id.user_answer_tv);
            holder.dotest_cunt_tv=convertView.findViewById(R.id.dotest_cunt_tv);
            holder.user_time_tv=convertView.findViewById(R.id.user_time_tv);
            holder.right_tv=convertView.findViewById(R.id.right_tv);
            holder.yc_tv=convertView.findViewById(R.id.yc_tv);
            holder.jx_tv=convertView.findViewById(R.id.jx_tv);
            holder.nd_img=convertView.findViewById(R.id.nd_img);
            holder.known_main=convertView.findViewById(R.id.known_main);
            holder.tag_cloud_view=convertView.findViewById(R.id.tag_cloud_view);
            holder.wd_rel=convertView.findViewById(R.id.wd_rel);
            holder.dn_tv=convertView.findViewById(R.id.dn_tv);
            holder.username_tv=convertView.findViewById(R.id.username_tv);
            holder.time_tv=convertView.findViewById(R.id.time_tv);
            holder.user_tx_img=convertView.findViewById(R.id.user_tx_img);
            holder.right_more_tv=convertView.findViewById(R.id.right_more_tv);
            holder.look_jx_tv.setEnabled(false);
            convertView.setTag(holder);

            if(questionBankBean.getWd()==null||questionBankBean.getWd().size()==0){
                holder.dn_tv.setVisibility(View.GONE);
                holder.wd_rel.setVisibility(View.GONE);
            }else {
                WdBean wdBean=questionBankBean.getWd().get(0);
                holder.dn_tv.setVisibility(View.VISIBLE);
                holder.wd_rel.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(wdBean.getUser_avatar()).error(R.drawable.touxiang).placeholder(R.drawable.touxiang).transform(new GlideCircleTransform(mContext)).into(holder.user_tx_img);
                holder.time_tv.setText(wdBean.getCreated_at());
                holder.username_tv.setText(wdBean.getUser_name());
                holder.dn_tv.setText(wdBean.getProblem());
                if(wdBean.getAnswer()==null||wdBean.getAnswer().equals("")){
                    holder.wd1_rel.setVisibility(View.GONE);
                }else {
                    holder.wd1_rel.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(wdBean.getT_avatar()).error(R.drawable.touxiang).placeholder(R.drawable.touxiang).transform(new GlideCircleTransform(mContext)).into(holder.user1_tx_img);
                    holder.time1_tv.setText(wdBean.getUpdated_at());
                    holder.username1_tv.setText(wdBean.getName());
                    holder.dn1_tv.setText(wdBean.getAnswer());
                }
            }
            holder.right_more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userLookAnalisysCall==null)
                        return;
                    userLookAnalisysCall.lookMoreAnswer(questionBankBean.getQuestId());
                }
            });


            if(questionBankBean.getKnows_name()==null||questionBankBean.getKnows_name().size()==0){
                holder.known_main.setVisibility(View.GONE);
            }else {
                holder.known_main.setVisibility(View.VISIBLE);
                holder.tag_cloud_view.setTags(questionBankBean.getKnows_name());
            }

            if(analisys){
                holder.look_jx_tv.setVisibility(View.GONE);
                holder.main_ll.setVisibility(View.VISIBLE);
                if(questionBankBean.getRight_answer().equals(questionBankBean.getUser_answer())){
                    Drawable nav_up=mContext.getResources().getDrawable(R.drawable.public_ture);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    holder.user_answer_tv.setCompoundDrawables(null, null, nav_up, null);
                }else {
                    Drawable nav_up=mContext.getResources().getDrawable(R.drawable.public_error);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    holder.user_answer_tv.setCompoundDrawables(null, null, nav_up, null);
                }
            }else {
                holder.look_jx_tv.setVisibility(View.VISIBLE);
                holder.main_ll.setVisibility(View.GONE);
                if(questionBankBean.getUser_answer()==null||questionBankBean.getUser_answer().equals("")||questionBankBean.getUser_answer().equals("暂无")){
                    holder.look_jx_tv.setEnabled(false);
                    holder.look_jx_tv.setBackgroundResource(false?R.drawable.look_jx_yes_bg:R.drawable.look_jx_no_bg);
                }else {
                    holder.look_jx_tv.setEnabled(true);
                    holder.look_jx_tv.setBackgroundResource(true?R.drawable.look_jx_yes_bg:R.drawable.look_jx_no_bg);
                }

            }

            SpanUtil.create()
                    .addForeColorSection("正确答案\t", Color.BLACK)
                    .addForeColorSection(questionBankBean.getRight_answer(),Color.rgb(68,193,253))
                    .showIn(  holder.user_right_tv);

//          .setText("正确答案："+questionBankBean.getRight_answer());

            SpanUtil.create()
                    .addForeColorSection("我的答案\t", Color.BLACK)
                    .addForeColorSection(questionBankBean.getUser_answer(),Color.RED)
                    .showIn(  holder.user_answer_tv);
//            holder.user_answer_tv.setText("我的作答："+questionBankBean.getUser_answer());
            if(questionBankBean.getAnswer_difficulty().equals("3")){
                //难
                holder.nd_img.setImageResource(R.drawable.public_difficult);
            }else if(questionBankBean.getAnswer_difficulty().equals("2")){
                //中
                holder.nd_img.setImageResource(R.drawable.public_medium);
            }else {
                //易
                holder.nd_img.setImageResource(R.drawable.public_easy);
            }
            holder.dotest_cunt_tv.setText(questionBankBean.getNumber_ques());
            holder.user_time_tv.setText(questionBankBean.getUser_dotime()+"秒");
            holder.right_tv.setText(questionBankBean.getCorrect_rate()+"%");
            holder.yc_tv.setText(questionBankBean.getFallibility());
            holder.jx_tv.showTxt(questionBankBean.getQues_analysis());
            final RadioThreeHolder finalHolder = holder;
            holder.look_jx_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.setClick(false);
                    setmCall(null);
                    finalHolder.look_jx_tv.setVisibility(View.GONE);
                    finalHolder.main_ll.setVisibility(View.VISIBLE);
                    userLookAnalisysCall.lookAnasysli(true);

                }
            });

        }else {
            holder= (RadioThreeHolder) convertView.getTag();
        }
        return convertView;
    }
    class RadioChoiceOneHolder{
        HtmlTextView html_htv;
        TextView test_type_tv;
    }
    class RadioChoiceTwoHolder{
       MyListView myListView;
       TextView test_type_tv;
    }
    class RadioThreeHolder{
        LinearLayout main_ll,known_main,wd_rel,wd1_rel;
        TextView look_jx_tv,user_right_tv,user_answer_tv,dotest_cunt_tv,user_time_tv,right_tv,yc_tv,dn_tv,username_tv,time_tv,right_more_tv;
        ImageView nd_img,user_tx_img,user1_tx_img;
        HtmlTextView  jx_tv;
        TagCloudView tag_cloud_view;
        TextView username1_tv,time1_tv,dn1_tv;
    }
    public void updateItem(ListView listView,String option,boolean isSelect,String time){
        int postion=listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if(2 >= postion && 2 <= lastVisiblePosition){
            View mView=listView.getChildAt(2-postion);
            final RadioThreeHolder holder= (RadioThreeHolder) mView.getTag();
            holder.user_answer_tv.setText("我的答案："+option);
            holder.user_time_tv.setText(time+"秒");
            if(questionBankBean.getRight_answer().equals(option)){
                Drawable nav_up=mContext.getResources().getDrawable(R.drawable.public_ture);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                holder.user_answer_tv.setCompoundDrawables(null, null, nav_up, null);
            }else {
                Drawable nav_up=mContext.getResources().getDrawable(R.drawable.public_error);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                holder.user_answer_tv.setCompoundDrawables(null, null, nav_up, null);
            }
            holder.look_jx_tv.setBackgroundResource(isSelect?R.drawable.look_jx_yes_bg:R.drawable.look_jx_no_bg);
            holder.look_jx_tv.setEnabled(isSelect);

            holder.look_jx_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.setClick(false);
                    setmCall(null);
                    holder.look_jx_tv.setVisibility(View.GONE);
                    holder.main_ll.setVisibility(View.VISIBLE);
                    userLookAnalisysCall.lookAnasysli(true);

                }
            });
        }
    }
    private View getAnswerView(int option,View convertView){
        AnswerHolder holder=null;
        if(convertView==null){
            holder=new AnswerHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_answer,null);
            convertView.setTag(holder);
        }else {
            holder= (AnswerHolder) convertView.getTag();
        }
        return convertView;
    }
    class AnswerHolder{

    }
}
