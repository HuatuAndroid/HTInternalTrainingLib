package com.jungan.www.common_dotest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.baijiayun.glide.Glide;
//import com.baijiayun.glide.request.target.SimpleTarget;
//import com.baijiayun.glide.request.transition.Transition;
import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.bean.UserOptionBean;
import com.jungan.www.common_dotest.call.OptionCall;
import com.jungan.www.common_dotest.config.QuestionTypeConfig;
import com.jungan.www.common_dotest.utils.StrUtils;
import com.jungan.www.common_dotest.view.HtmlTextView;

import java.util.List;

import cn.droidlover.xrichtext.XRichText;
import me.wcy.htmltext.HtmlImageLoader;
import me.wcy.htmltext.HtmlText;
import me.wcy.htmltext.OnTagClickListener;

public class CommonQuestionOptionAdapter extends BaseAdapter {
    private List<UserOptionBean> stringList;
    private Context mContext;
    private OptionCall mCall;
    private int selectOption=-1;
    private int questionType;
    private boolean isClick=true;
    private boolean analisys;
    private String rightAnswer;
    private String userAnswer;
    private boolean isUserClick=false;
    public void setClick(boolean click) {
        isClick = click;
    }

    public void setSelectOption(int selectOption) {
        this.selectOption = selectOption;
    }

    public void setmCall(OptionCall mCall) {
        this.mCall = mCall;
    }
    public CommonQuestionOptionAdapter(List<UserOptionBean> stringList, Context mContext,int type,boolean analisy,String rr,String uu) {
        this.stringList = stringList;
        this.mContext = mContext;
        this.questionType=type;
        this.analisys=analisy;
        this.rightAnswer=rr;
        this.userAnswer=uu;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CommonQuestionOptionHolder holder=null;
        final UserOptionBean userOptionBean= (UserOptionBean) getItem(position);
//        if(convertView==null){
//            holder=new CommonQuestionOptionHolder();
//            convertView=LayoutInflater.from(mContext).inflate(R.layout.layout_option,null);
//            holder.tv_tv=convertView.findViewById(R.id.tv_tv);
//            convertView.setTag(holder);
//        }else {
//            holder= (CommonQuestionOptionHolder) convertView.getTag();
//        }
        if(convertView==null){
            holder=new CommonQuestionOptionHolder();
            convertView=questionType== QuestionTypeConfig.RADIO_CHOICE||questionType==QuestionTypeConfig.MATERIAL_RADIO_CHOICE? LayoutInflater.from(mContext).inflate(R.layout.layout_option,null):LayoutInflater.from(mContext).inflate(R.layout.layout_duooption,null);
            holder.html_htv=convertView.findViewById(R.id.html_htv);
            holder.main_ll=convertView.findViewById(R.id.main_ll);
            holder.html_htv.setOption(true);
            convertView.setTag(holder);
        }else {
            holder= (CommonQuestionOptionHolder) convertView.getTag();
        }
        holder.html_htv.setOption(userOptionBean.getOptionName());
        holder.html_htv.showTxt(userOptionBean.getOptionContext());
        if(analisys){
                if(userAnswer==null||userAnswer.equals("")){
                    if(rightAnswer.contains(userOptionBean.getOptionName())){
                        holder.html_htv.showRightOption();
                    }else {
                        if(rightAnswer.contains(userOptionBean.getOptionName())){
                            holder.html_htv.showRightOption();
                        }else {
                            holder.html_htv.showNoDoOption();
                        }
                    }
                }else {
                    if(rightAnswer.contains(userOptionBean.getOptionName())&&userAnswer.contains(userOptionBean.getOptionName())){
                        holder.html_htv.showRightOption();
                    }else {
                        if(rightAnswer.contains(userOptionBean.getOptionName())){
                            holder.html_htv.showRightOption();
                        }else {
                            if(userAnswer.contains(userOptionBean.getOptionName())){
                                holder.html_htv.showErrorOption();
                            }else {
                                holder.html_htv.showNoDoOption();
                            }
                        }
                    }
                }
        }else {
            if(userAnswer==null||userAnswer.equals("")){
                if(selectOption==position){
                    holder.html_htv.showRightOption();
                }else {
                    holder.html_htv.showNoDoOption();
                }
            }else {
                if(isUserClick){
                    if(selectOption==position){
                        holder.html_htv.showRightOption();
                    }else {
                        holder.html_htv.showNoDoOption();
                    }
                }else {
                    if(userAnswer.contains(userOptionBean.getOptionName())){
                        holder.html_htv.showRightOption();
                    }else {
                        holder.html_htv.showNoDoOption();
                    }
                }

            }

            final CommonQuestionOptionHolder finalHolder = holder;
            holder.html_htv.onClickOption(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isClick)
                        return;
                    isUserClick=true;
                    if(questionType==QuestionTypeConfig.RADIO_CHOICE||questionType==QuestionTypeConfig.MATERIAL_RADIO_CHOICE){
                        //单选
                        setSelectOption(position);
                        notifyDataSetChanged();
                    }else {
                        //多选
                        if(finalHolder.html_htv.getTag()==null||!(Boolean) finalHolder.html_htv.getTag()){
                            finalHolder.html_htv.showRightOption();
                            finalHolder.html_htv.setTag(true);
                        }else {
                            finalHolder.html_htv.showNoDoOption();
                            finalHolder.html_htv.setTag(false);
                        }
                    }
                    mCall.getUserSelectOption(StrUtils.Instance().numberToLetter(position+1),questionType);
                }
            });
        }

        return convertView;
    }
    class CommonQuestionOptionHolder{
        HtmlTextView html_htv;
        LinearLayout main_ll;
//        XRichText tv_tv;
    }

}
