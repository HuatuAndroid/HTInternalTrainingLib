package com.jungan.www.common_dotest.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.bean.WdBean;
import com.jungan.www.common_dotest.call.OptionCall;
import com.jungan.www.common_dotest.call.UserLookAnalisysCall;
import com.jungan.www.common_dotest.utils.GlideCircleTransform;
import com.jungan.www.common_dotest.view.HtmlTextView;

import me.next.tagview.TagCloudView;

public class EssayQuestionAdapter extends BaseAdapter {
    private boolean analisys;
    private Context mContext;
    private QuestionBankBean questionBankBean;
    private UserLookAnalisysCall userLookAnalisysCall;
    private OptionCall mCall;
    private boolean isSelect;
    private String question;


    public EssayQuestionAdapter(Context context, QuestionBankBean questionBankBean, boolean isAnalisys) {
        this.mContext = context;
        this.analisys=isAnalisys;
        this.questionBankBean = questionBankBean;
        this.userLookAnalisysCall= (UserLookAnalisysCall) context;
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
        return position;
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
            return getOneView(position,convertView);
        }else if(getItemViewType(position)==1){
            return getTwoView(position,convertView);
        }
        return null;
    }
    private View getOneView(int position, View convertView) {
        QuestionHolder questionHolder = null;
        if (convertView == null) {
            questionHolder = new QuestionHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_essay, null);
            questionHolder.html_htv = convertView.findViewById(R.id.html_htv);
            questionHolder.html_htv.setOption(false);
            questionHolder.question_et = convertView.findViewById(R.id.question_et);
            convertView.setTag(questionHolder);
        }else {
            questionHolder = (QuestionHolder) convertView.getTag();
        }
        question = questionHolder.question_et.getText().toString().trim();
       /* if (question != null) {
            isSelect = true;
        }else {
            isSelect = false;
        }*/
        setmCall(new OptionCall() {
            @Override
            public void getUserSelectOption(String option, int type) {
                if (mCall == null) {
                    return;
                }
                    mCall.getUserSelectOption(question,type);
            }
        });
        questionHolder.html_htv.showTxt(questionBankBean.getQuestionStem());
        return convertView;
    }
    private View getTwoView(int position, View convertView) {
        AnalysisHolder analysisHolder = null;
        if(convertView==null){
            analysisHolder=new AnalysisHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_essay_question,null);
            analysisHolder.look_jx_tv=convertView.findViewById(R.id.look_jx_tv);
            analysisHolder.main_ll=convertView.findViewById(R.id.main_ll);
            analysisHolder.jx_answer=convertView.findViewById(R.id.jx_answer);
            analysisHolder.jx_tv=convertView.findViewById(R.id.jx_tv);
            analysisHolder.known_main=convertView.findViewById(R.id.known_main);
            analysisHolder.tag_cloud_view=convertView.findViewById(R.id.tag_cloud_view);
            analysisHolder.right_more_tv=convertView.findViewById(R.id.right_more_tv);
            analysisHolder.user_tx_img=convertView.findViewById(R.id.user_tx_img);
            analysisHolder.username_tv=convertView.findViewById(R.id.username_tv);
            analysisHolder.time_tv=convertView.findViewById(R.id.time_tv);
            analysisHolder.dn_tv=convertView.findViewById(R.id.dn_tv);
            analysisHolder.wd_rel=convertView.findViewById(R.id.wd_rel);
            analysisHolder.look_jx_tv.setEnabled(false);
            convertView.setTag(analysisHolder);
        }else {
            analysisHolder= (AnalysisHolder) convertView.getTag();
        }

        final AnalysisHolder finalAnalysisHolder = analysisHolder;
        analysisHolder.look_jx_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalAnalysisHolder.look_jx_tv.setEnabled(false);
                finalAnalysisHolder.look_jx_tv.setVisibility(View.GONE);
                finalAnalysisHolder.main_ll.setVisibility(View.VISIBLE);
                userLookAnalisysCall.lookAnasysli(true);

            }
        });
        analysisHolder.jx_answer.showTxt(questionBankBean.getRight_answer());
        analysisHolder.jx_tv.showTxt(questionBankBean.getQues_analysis());
        if(questionBankBean.getWd()==null||questionBankBean.getWd().size()==0){
            analysisHolder.dn_tv.setVisibility(View.GONE);
            analysisHolder.wd_rel.setVisibility(View.GONE);
        }else {
            WdBean wdBean=questionBankBean.getWd().get(0);
            analysisHolder.dn_tv.setVisibility(View.VISIBLE);
            analysisHolder.wd_rel.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(wdBean.getUser_avatar()).error(R.drawable.touxiang).placeholder(R.drawable.touxiang).transform(new GlideCircleTransform(mContext)).into(analysisHolder.user_tx_img);
            analysisHolder.time_tv.setText(wdBean.getCreated_at());
            analysisHolder.username_tv.setText(wdBean.getUser_name());
            analysisHolder.dn_tv.setText(wdBean.getProblem());

        }

        analysisHolder.right_more_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLookAnalisysCall==null)
                    return;
                userLookAnalisysCall.lookMoreAnswer(questionBankBean.getQuestId());
            }
        });

        if(questionBankBean.getKnows_name()==null||questionBankBean.getKnows_name().size()==0){
            analysisHolder.known_main.setVisibility(View.GONE);
        }else {
            analysisHolder.known_main.setVisibility(View.VISIBLE);
            analysisHolder.tag_cloud_view.setTags(questionBankBean.getKnows_name());
        }

        if(question == null || question.length() == 0){
            analysisHolder.look_jx_tv.setEnabled(false);
            analysisHolder.look_jx_tv.setBackgroundResource(false?R.drawable.look_jx_yes_bg:R.drawable.look_jx_no_bg);
        }else {
                analysisHolder.look_jx_tv.setEnabled(true);
                analysisHolder.look_jx_tv.setBackgroundResource(true?R.drawable.look_jx_yes_bg:R.drawable.look_jx_no_bg);

        }
        if(analisys){
            analysisHolder.look_jx_tv.setVisibility(View.GONE);
            analysisHolder.main_ll.setVisibility(View.VISIBLE);
        }else {
            analysisHolder.look_jx_tv.setVisibility(View.VISIBLE);
            analysisHolder.main_ll.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setmCall(OptionCall mCall) {
        this.mCall = mCall;
    }

    class QuestionHolder{
        HtmlTextView html_htv;
        EditText question_et;
    }
    class AnalysisHolder{
        TextView look_jx_tv,dn_tv,time_tv,username_tv,right_more_tv;
        ImageView user_tx_img;
        TagCloudView tag_cloud_view;
        HtmlTextView jx_tv,jx_answer;
        LinearLayout main_ll,known_main;
        RelativeLayout wd_rel;
    }


}
