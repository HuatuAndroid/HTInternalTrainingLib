package com.jungan.www.common_dotest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.jungan.www.common_dotest.R;
import com.jungan.www.common_dotest.adapter.RadioChoiceAdapter;
import com.jungan.www.common_dotest.base.LazyFragment;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.call.OptionCall;
import com.jungan.www.common_dotest.call.ViewPageCall;
import com.jungan.www.common_dotest.utils.StrUtils;
import com.jungan.www.common_dotest.view.HtmlTextView;

import java.util.HashSet;
import java.util.Set;

/**
 * 材料多选业务
 */
public class MaterialProblemMulitSelectChoiceFragment extends LazyFragment {
    private HtmlTextView mt_tv;
    private ListView bottom_lv;
    private QuestionBankBean questionBankBean;
    private RadioChoiceAdapter mAdapter;
    private ViewPageCall mCall;
    private Set<String> stringSet;
    private boolean isAnalisys;
    public static MaterialProblemMulitSelectChoiceFragment newInstance(QuestionBankBean questionBankBean,boolean isAnalisys){
        MaterialProblemMulitSelectChoiceFragment mulitselectChoiceFragment=new MaterialProblemMulitSelectChoiceFragment();
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
        setContentView(R.layout.layout_main);
        stringSet=new HashSet<>();
        questionBankBean=getArguments().getParcelable("questionBankBean");
        isAnalisys=getArguments().getBoolean("isAnalisys",false);
        mt_tv=getViewById(R.id.mt_tv);
        mt_tv.showTxt("fnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsbfnsfsgfsdjgkjdsjgfdjgjdjgjsgjsbjhvbjdfbvjbsjbvjdsbvjbdsjbvjdsbjvbsjbvjdsbjvbsdbjvsdbjgvbsdjbgsdbgjbsgbdsbgjbsdjbgsb");
        bottom_lv=getViewById(R.id.bottom_lv);
        mAdapter=new RadioChoiceAdapter(questionBankBean,getActivity(),isAnalisys);
        bottom_lv.setAdapter(mAdapter);
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
                if(stringSet.size()==0)
                    return;
                mCall.CutCurrentViewPage(StrUtils.Instance().SetToString(stringSet));

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCall= (ViewPageCall) context;
    }
}
