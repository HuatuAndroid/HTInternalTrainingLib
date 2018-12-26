package com.jungan.www.common_dotest.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.jungan.www.common_dotest.utils.StrUtils;
import com.jungan.www.common_dotest.view.MyListView;

/**
 * 普通单选业务
 */
public class RadioChoiceFragment extends LazyFragment {
    private ListView mListView;
    private RadioChoiceAdapter mAdapter;
    private QuestionBankBean questionBankBean;
    private ViewPageCall mCall;
    private boolean isAnalisys;
    private TextView test_type_tv;
    public static RadioChoiceFragment newInstance(QuestionBankBean questionBankBean,boolean isAnalisys){
        RadioChoiceFragment radioChoiceFragment=new RadioChoiceFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("questionBankBean",questionBankBean);
        bundle.putBoolean("isAnalisys",isAnalisys);
        radioChoiceFragment.setArguments(bundle);
        return radioChoiceFragment;
    }
    @Override
    public boolean isLazyFragment() {
        return true;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.layout_listview);
        questionBankBean=getArguments().getParcelable("questionBankBean");
        isAnalisys=getArguments().getBoolean("isAnalisys",false);
        mListView=getViewById(R.id.question_lv);
        test_type_tv=getViewById(R.id.test_type_tv);
        mAdapter=new RadioChoiceAdapter(questionBankBean,getActivity(),isAnalisys);
        mListView.setAdapter(mAdapter);
        test_type_tv.setText(StrUtils.Instance().getTestTypeStr(questionBankBean.getQuestionType()));




        setListener();
    }
    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setmCall(new OptionCall() {
            @Override
            public void getUserSelectOption(String option,int type) {
                    mAdapter.updateItem(mListView,option,true, QuestTestConfig.testTime+"");
                     mCall.CutCurrentViewPage(option);
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCall= (ViewPageCall) context;
    }
}
