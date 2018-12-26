package com.jungan.www.common_dotest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.jungan.www.common_dotest.base.BaseFragment;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.config.QuestionTypeConfig;
import com.jungan.www.common_dotest.fragment.AnswerSheetFragment;
import com.jungan.www.common_dotest.fragment.ErrorQuestionTypeFragment;
import com.jungan.www.common_dotest.fragment.EssayQuestionFragment;
import com.jungan.www.common_dotest.fragment.MaterialProblemMulitSelectChoiceFragment;
import com.jungan.www.common_dotest.fragment.MaterialProblemRadioChoiceFragment;
import com.jungan.www.common_dotest.fragment.MulitselectChoiceFragment;
import com.jungan.www.common_dotest.fragment.RadioChoiceFragment;

import java.util.List;

/**
 * Created by desin on 2017/2/9.
 */

public class CommonQuestionAdapter extends FragmentStatePagerAdapter {
    private List<QuestionBankBean> mLists;
    private boolean analisys;
    private boolean isShowPost;
    public CommonQuestionAdapter(FragmentManager fm, List<QuestionBankBean> questionBankBeanList,boolean analisys,Boolean showPost) {
        super(fm);
        this.analisys=analisys;
        this.mLists=questionBankBeanList;
        this.isShowPost=showPost;
    }
    @Override
    public Fragment getItem(int position) {
        if(position<mLists.size()){
            if(mLists.get(position).getQuestionType()== QuestionTypeConfig.RADIO_CHOICE){
                //单选
                return RadioChoiceFragment.newInstance(mLists.get(position),analisys);
            }else if(mLists.get(position).getQuestionType()== QuestionTypeConfig.MULITSELECT_CHOICE){
                //多选
                return MulitselectChoiceFragment.newInstance(mLists.get(position),analisys);
            }else if(mLists.get(position).getQuestionType()== QuestionTypeConfig.ESSAY_QUESTION){
                //问答
                return EssayQuestionFragment.newInstance(mLists.get(position),analisys);
            }else if(mLists.get(position).getQuestionType()== QuestionTypeConfig.MULITSELECT_CHOICE_BDX){
                //不定项
                return MulitselectChoiceFragment.newInstance(mLists.get(position),analisys);
            }else if(mLists.get(position).getQuestionType()== QuestionTypeConfig.MATERIAL_MULITSELECT_CHOICE){
                //材料多选
                return MaterialProblemMulitSelectChoiceFragment.newInstance(mLists.get(position),analisys);
            }else if(mLists.get(position).getQuestionType()== QuestionTypeConfig.MATERIAL_RADIO_CHOICE){
                //材料单选
                return MaterialProblemRadioChoiceFragment.newInstance(mLists.get(position),analisys);
            }else if(mLists.get(position).getQuestionType()== QuestionTypeConfig.TEST_PDT){
                //判断题
                return RadioChoiceFragment.newInstance(mLists.get(position),analisys);
            }else if(mLists.get(position).getQuestionType()== QuestionTypeConfig.MATERIAL_RADIO_CHOICE){
                    return MaterialProblemRadioChoiceFragment.newInstance(mLists.get(position),analisys);

            }else {
                return new ErrorQuestionTypeFragment();
            }
        }else {
          //加一页 作为答题卡
            return  AnswerSheetFragment.newInstance(mLists,isShowPost);
        }
    }

    @Override
    public int getCount() {
        if(analisys){
            return mLists.size();
        }else {
            return mLists.size()+1;
        }
    }
}
