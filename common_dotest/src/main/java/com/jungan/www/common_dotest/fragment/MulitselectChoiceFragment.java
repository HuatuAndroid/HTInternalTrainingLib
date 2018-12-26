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

import java.util.HashSet;
import java.util.Set;

/**
 * 普通多选业务
 */
public class MulitselectChoiceFragment extends LazyFragment {
    private ListView mListView;
    private RadioChoiceAdapter mAdapter;
    private QuestionBankBean questionBankBean;
    private ViewPageCall mCall;
    private Set<String> stringSet;
    private boolean isAnalisys;
    private TextView test_type_tv;
    public static MulitselectChoiceFragment newInstance(QuestionBankBean questionBankBean,boolean isAnalisys){
        MulitselectChoiceFragment mulitselectChoiceFragment=new MulitselectChoiceFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("questionBankBean",questionBankBean);
        bundle.putBoolean("isAnalisys",isAnalisys);
        mulitselectChoiceFragment.setArguments(bundle);
        return mulitselectChoiceFragment;
    }
    @Override
    public boolean isLazyFragment() {
        return true;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.layout_listview);
        stringSet=new HashSet<>();
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
                    if(stringSet.contains(option)){
                        stringSet.remove(option);
                    }else {
                        stringSet.add(option);
                    }
                    if(stringSet.size()==0){
                        mAdapter.updateItem(mListView,option,false, QuestTestConfig.testTime+"");
                        mCall.CutCurrentViewPage("");
                    }else {
                        String o=StrUtils.Instance().SetToString(stringSet);
                        mAdapter.updateItem(mListView,o,true,QuestTestConfig.testTime+"");
                        mCall.CutCurrentViewPage(o);
                    }

            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCall= (ViewPageCall) context;
    }
}
