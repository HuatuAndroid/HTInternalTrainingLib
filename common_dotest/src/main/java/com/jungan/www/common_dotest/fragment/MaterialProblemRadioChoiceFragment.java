package com.jungan.www.common_dotest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.adapter.RadioChoiceAdapter;
import com.jungan.www.common_dotest.base.LazyFragment;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.call.OptionCall;
import com.jungan.www.common_dotest.call.ViewPageCall;
import com.jungan.www.common_dotest.config.QuestTestConfig;
import com.jungan.www.common_dotest.config.QuestionTypeConfig;
import com.jungan.www.common_dotest.view.HtmlTextView;

/**
 * 材料单项选择业务
 */
public class MaterialProblemRadioChoiceFragment extends LazyFragment {
    private HtmlTextView mt_tv;
//    private ListView bottom_lv;
    private QuestionBankBean questionBankBean;
//    private ViewPageCall mCall;
//    private RadioChoiceAdapter mAdapter;
    private boolean isAnalisys;

    public static MaterialProblemRadioChoiceFragment newInstance(QuestionBankBean questionBankBean,boolean isAnalisys){
        MaterialProblemRadioChoiceFragment materialProblemFragment=new MaterialProblemRadioChoiceFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("questionBankBean",questionBankBean);
        bundle.putBoolean("isAnalisys",isAnalisys);
        materialProblemFragment.setArguments(bundle);
        return materialProblemFragment;
    }
    @Override
    public boolean isLazyFragment() {
        return true;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.layout_main);
        questionBankBean=getArguments().getParcelable("questionBankBean");
        isAnalisys=getArguments().getBoolean("isAnalisys",false);
        mt_tv=getViewById(R.id.mt_tv);
        mt_tv.showTxt(questionBankBean.getParent_stem()==null?"":questionBankBean.getParent_stem());
//        bottom_lv=getViewById(R.id.bottom_lv);
//        bottom_lv.setDivider(null);
//        mAdapter=new RadioChoiceAdapter(questionBankBean,getActivity(),isAnalisys);
//        bottom_lv.setAdapter(mAdapter);
        setListener();
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        // 注意这里是调用getChildFragmentManager()方法
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // 把碎片添加到碎片中
//        if(questionBankBean.getQuestionType()== QuestionTypeConfig.RADIO_CHOICE||questionBankBean.getQuestionType()== QuestionTypeConfig.MATERIAL_RADIO_CHOICE||questionBankBean.getQuestionType()== QuestionTypeConfig.TEST_PDT){
//            //单选
//            transaction.replace(R.id.bottom_lv, RadioChoiceFragment.newInstance(questionBankBean,isAnalisys));
//        }else if(questionBankBean.getQuestionType()== QuestionTypeConfig.MULITSELECT_CHOICE||questionBankBean.getQuestionType()== QuestionTypeConfig.MATERIAL_MULITSELECT_CHOICE||questionBankBean.getQuestionType()== QuestionTypeConfig.MULITSELECT_CHOICE_BDX){
//            //多选
//            transaction.replace(R.id.bottom_lv, MulitselectChoiceFragment.newInstance(questionBankBean,isAnalisys));
//        }else if(questionBankBean.getQuestionType()== QuestionTypeConfig.ESSAY_QUESTION){
//            //问答
//            transaction.replace(R.id.bottom_lv, EssayQuestionFragment.newInstance(questionBankBean,isAnalisys));
//        }else {
//            transaction.replace(R.id.bottom_lv,new ErrorQuestionTypeFragment());
//        }
        if(questionBankBean.getMaterial_type()== QuestionTypeConfig.RADIO_CHOICE){
            //单选
            transaction.replace(R.id.bottom_lv, RadioChoiceFragment.newInstance(questionBankBean,isAnalisys));
        }else if(questionBankBean.getMaterial_type()== QuestionTypeConfig.MULITSELECT_CHOICE){
            //多选
            transaction.replace(R.id.bottom_lv, MulitselectChoiceFragment.newInstance(questionBankBean,isAnalisys));
        }else if(questionBankBean.getMaterial_type()== QuestionTypeConfig.ESSAY_QUESTION){
            //问答
            transaction.replace(R.id.bottom_lv, EssayQuestionFragment.newInstance(questionBankBean,isAnalisys));
        }else if(questionBankBean.getMaterial_type()== QuestionTypeConfig.MULITSELECT_CHOICE_BDX){
            //不定项
            transaction.replace(R.id.bottom_lv, MulitselectChoiceFragment.newInstance(questionBankBean,isAnalisys));
        }else {
            transaction.replace(R.id.bottom_lv,new ErrorQuestionTypeFragment());
        }
        transaction.commit();
    }

    @Override
    protected void setListener() {
        super.setListener();
//        mAdapter.setmCall(new OptionCall() {
//            @Override
//            public void getUserSelectOption(String option,int type) {
//                mAdapter.updateItem(bottom_lv,option,true, QuestTestConfig.testTime+"");
//                mCall.CutCurrentViewPage(option);
//            }
//        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mCall= (ViewPageCall) context;
    }
}
