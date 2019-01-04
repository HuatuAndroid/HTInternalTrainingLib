package com.jungan.www.common_dotest.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Trace;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.adapter.EssayQuestionAdapter;
import com.jungan.www.common_dotest.adapter.RadioChoiceAdapter;
import com.jungan.www.common_dotest.base.LazyFragment;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.bean.WdBean;
import com.jungan.www.common_dotest.call.OptionCall;
import com.jungan.www.common_dotest.call.UserLookAnalisysCall;
import com.jungan.www.common_dotest.call.ViewPageCall;
import com.jungan.www.common_dotest.utils.GlideCircleTransform;
import com.jungan.www.common_dotest.utils.StrUtils;
import com.jungan.www.common_dotest.view.HtmlTextView;

import me.next.tagview.TagCloudView;

/**
 * 问答业务
 */
public class EssayQuestionFragment extends LazyFragment {
    private HtmlTextView html_htv;
    private QuestionBankBean questionBankBean;
    private boolean analisys;
    private UserLookAnalisysCall userLookAnalisysCall;

    private LinearLayout main_ll,known_main;
    private HtmlTextView da_tv,jx_tv;
    private TagCloudView tag_cloud_view;
    private TextView look_jx_tv,dn_tv,time_tv,username_tv,right_more_tv;
    private LinearLayout wd_rel;
    private ImageView user_tx_img;

    private EditText question_et;

    private ViewPageCall mCall;
    public static EssayQuestionFragment newInstance(QuestionBankBean questionBankBean, boolean analisys) {
        EssayQuestionFragment essayQuestionFragment = new EssayQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("questionBankBean", questionBankBean);
        bundle.putBoolean("isAnalisys",analisys);
        essayQuestionFragment.setArguments(bundle);
        return essayQuestionFragment;
    }

    @Override
    public boolean isLazyFragment() {
        return true;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.layout_essay);
        questionBankBean=getArguments().getParcelable("questionBankBean");
        analisys=getArguments().getBoolean("isAnalisys");
        html_htv=getViewById(R.id.html_htv);
        html_htv.setOption(false);
        // KaelLi, 2018/12/28
        String httml = "<font color='#458bfd'>（问答题）</font>";
        html_htv.showTxt(httml + questionBankBean.getQuestionStem());
        initView();
        setListener();
    }

    @Override
    public void initView() {
        super.initView();
        question_et=getViewById(R.id.question_et);
        right_more_tv=getViewById(R.id.right_more_tv);
        known_main=getViewById(R.id.known_main);
        user_tx_img=getViewById(R.id.user_tx_img);
        username_tv=getViewById(R.id.username_tv);
        time_tv=getViewById(R.id.time_tv);
        dn_tv=getViewById(R.id.dn_tv);
        wd_rel=getViewById(R.id.wd_rel);
        main_ll=getViewById(R.id.main_ll);
        da_tv=getViewById(R.id.da_tv);
        tag_cloud_view=getViewById(R.id.tag_cloud_view);
        jx_tv=getViewById(R.id.jx_tv);
        look_jx_tv=getViewById(R.id.look_jx_tv);
        look_jx_tv.setEnabled(false);
        question_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if ((view.getId() == R.id.question_et && canVerticalScroll(question_et))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
        if(analisys){
            //解析
            main_ll.setVisibility(View.VISIBLE);
//            look_jx_tv.setVisibility(View.GONE);
//            question_et.setEnabled(false);
            enableEditText(false);
            question_et.setText(questionBankBean.getUser_answer()==null?"":questionBankBean.getUser_answer());
        }else {
            //非解析
//            question_et.setEnabled(true);
            enableEditText(true);
            main_ll.setVisibility(View.GONE);
//            look_jx_tv.setVisibility(View.VISIBLE);
        }
        da_tv.showTxt(questionBankBean.getRight_answer());
        jx_tv.showTxt(questionBankBean.getQues_analysis());
        look_jx_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                question_et.setEnabled(false);
                enableEditText(false);
                main_ll.setVisibility(View.VISIBLE);
                look_jx_tv.setVisibility(View.GONE);
            }
        });
        right_more_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLookAnalisysCall==null)
                    return;
                userLookAnalisysCall.lookMoreAnswer(questionBankBean.getQuestId());
            }
        });
        if(questionBankBean.getWd()==null||questionBankBean.getWd().size()==0){
            dn_tv.setVisibility(View.GONE);
            wd_rel.setVisibility(View.GONE);
        }else {
            WdBean wdBean=questionBankBean.getWd().get(0);
            dn_tv.setVisibility(View.VISIBLE);
            wd_rel.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(wdBean.getUser_avatar()).error(R.drawable.touxiang).placeholder(R.drawable.touxiang).transform(new GlideCircleTransform(getActivity())).into(user_tx_img);
            time_tv.setText(wdBean.getCreated_at());
            username_tv.setText(wdBean.getUser_name());
            dn_tv.setText(wdBean.getProblem());
        }
        if(questionBankBean.getKnows_name()==null||questionBankBean.getKnows_name().size()==0){
            known_main.setVisibility(View.GONE);
        }else {
            known_main.setVisibility(View.VISIBLE);
            tag_cloud_view.setTags(questionBankBean.getKnows_name());
        }

    }

    @Override
    protected void setListener() {
        question_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("输入当前",count+"---");
                if(s.toString().length()>0){
                    look_jx_tv.setBackgroundResource(true?R.drawable.look_jx_yes_bg:R.drawable.look_jx_no_bg);
                    look_jx_tv.setEnabled(true);
                }else {
                    look_jx_tv.setBackgroundResource(false?R.drawable.look_jx_yes_bg:R.drawable.look_jx_no_bg);
                    look_jx_tv.setEnabled(false);
                }
                if(s.toString()==null||s.toString().equals("")){
                    mCall.CutCurrentViewPage("");
                }else {
                    mCall.CutCurrentViewPage(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userLookAnalisysCall= (UserLookAnalisysCall) context;
        mCall= (ViewPageCall) context;
    }

    private void enableEditText(boolean enable) {
        if (question_et == null) return;
        question_et.setFocusableInTouchMode(enable);
        question_et.setCursorVisible(enable);
    }

    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
}
