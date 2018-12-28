package com.zhiyun88.www.module_main.dotesting.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jungan.www.common_dotest.adapter.AnswerSheetItemAdapter;
import com.jungan.www.common_dotest.bean.AnswerSheetBean;
import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.jungan.www.common_dotest.call.TestViewpageCall;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AnswerSheetActivity extends BaseActivity implements TestViewpageCall{
    private List<QuestionBankBean> questionBankBeanList;
    public List<QuestionBankBean> getQuestionBankBeanList() {
        return questionBankBeanList;
    }
    private AnswerSheetItemAdapter mAdapter;
    private List<AnswerSheetBean> answerSheetBeanLists;
    private ListView griview;
    private TextView post_test_tv;
    private TopBarView dtk_tb;
    private boolean anasy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarColor(Color.WHITE,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dotest_answersheet_layout);
        questionBankBeanList=getIntent().getParcelableArrayListExtra("questionBankBeanList");
        Log.d("kaelli", "AnswerSheetActivity questionBankBeanList size:"+questionBankBeanList.size());
        anasy=getIntent().getBooleanExtra("anasy",false);
        griview=getViewById(com.jungan.www.common_dotest.R.id.mgv);
        griview.setDivider(null);
        dtk_tb=getViewById(R.id.dtk_tb);
        answerSheetBeanLists=new ArrayList<>();
        mAdapter=new AnswerSheetItemAdapter(answerSheetBeanLists,AnswerSheetActivity.this);
        griview.setAdapter(mAdapter);
        post_test_tv=getViewById(com.jungan.www.common_dotest.R.id.post_test_tv);
        post_test_tv.setVisibility(anasy?View.GONE:View.VISIBLE);
        new Thread(new reshAnswerSheet()).start();
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        dtk_tb.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if(action==TopBarView.ACTION_LEFT_BUTTON){
                    finish();
                }
            }
        });
        post_test_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("type",1);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void currentPage(int c) {
        Intent intent=new Intent();
        intent.putExtra("type",2);
        intent.putExtra("page",c);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    class  reshAnswerSheet  implements Runnable{
        @Override
        public void run() {
//            Log.e("questionBankBeanList",questionBankBeanList.size()+"---");
//            List<AnswerSheetBean> answerSheetBeans=new ArrayList<>();
//            for(int i=0;i<questionBankBeanList.size();i++){
//                Log.e("添加了1","---"+questionBankBeanList.get(i).getQuestionType());
//                if(i==0){
//                    AnswerSheetBean answerSheetBean=new AnswerSheetBean();
//                    answerSheetBean.setGroup(questionBankBeanList.get(i).getQuestionType()+"");
//                    answerSheetBeans.add(answerSheetBean);
//                }else {
//                    if(questionBankBeanList.get(i).getQuestionType()==questionBankBeanList.get(i-1).getQuestionType()){
//                        Log.e("添加了3","---");
//                    }else {
//                        AnswerSheetBean answerSheetBean=new AnswerSheetBean();
//                        answerSheetBean.setGroup(questionBankBeanList.get(i).getQuestionType()+"");
//                        answerSheetBeans.add(answerSheetBean);
//                        Log.e("添加了2","---");
//                    }
//                }
//
//            }
//            Log.e("answerSheetBeans",answerSheetBeans.size()+"****");
//            for(AnswerSheetBean answerSheetBean1:answerSheetBeans){
//                Log.e("获取到的试题类型",answerSheetBean1.getGroup());
//                List<QuestionBankBean> questionBankBeanss=new ArrayList<>();
//                for(QuestionBankBean questionBankBean:questionBankBeanList){
//                    if(answerSheetBean1.getGroup().equals(questionBankBean.getQuestionType()+"")){
//                        questionBankBeanss.add(questionBankBean);
//                    }
//                }
//                answerSheetBean1.setQuestionBankBeanList(questionBankBeanss);
//            }
            Map<String,List<QuestionBankBean>> map=queryQuestGroupList(questionBankBeanList);
            for (Map.Entry<String, List<QuestionBankBean>> entry : map.entrySet()) {
                AnswerSheetBean answerSheetBean=new AnswerSheetBean();
                String key = entry.getKey();
                Log.d("kaelli", "AnswerSheetActivity key:"+key);
//                answerSheetBean.setGroup(key.split("_")[0]);
//                answerSheetBean.setGroupName(key.split("_")[1]);
                answerSheetBean.setGroupName(entry.getKey());
                answerSheetBean.setQuestionBankBeanList( entry.getValue());
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().size());
                answerSheetBeanLists.add(answerSheetBean);
            }
            Log.d("kaelli", "AnswerSheet reshAnswerSheet");
//            Iterator<Map.Entry<String, List<QuestionBankBean>>> entries = map.entrySet().iterator();
//            List<AnswerSheetBean> answerSheetBeanLists=new ArrayList<>();
//            while (entries.hasNext()) {
//                Map.Entry<String, List<QuestionBankBean>> entry = entries.next();
//                AnswerSheetBean answerSheetBean=new AnswerSheetBean();
////                answerSheetBean.setGroup(entry.getKey());
////                answerSheetBean.setQuestionBankBeanList( entry.getValue());
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().size());
////                answerSheetBeanLists.add(answerSheetBean);
//
//            }
//            Message message=new Message();
//            message.what=1;
//            message.obj=answerSheetBeanLists;
//            handler.sendMessage(message);
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                List<AnswerSheetBean> answerSheetBeanList= (List<AnswerSheetBean>) msg.obj;
                Log.e("answerSheetBeanList",answerSheetBeanList.size()+"");
                answerSheetBeanLists.clear();
                answerSheetBeanLists.addAll(answerSheetBeanList);
                mAdapter.notifyDataSetChanged();
            }
        }
    };


    public LinkedHashMap<String, List<QuestionBankBean>> queryQuestGroupList(List<QuestionBankBean> list) {
        LinkedHashMap<String, List<QuestionBankBean>> map = new LinkedHashMap<>();
        for (QuestionBankBean li : list) {
            li.getQuestionModel();
            //将需要归类的属性与map中的key进行比较，如果map中有该key则添加bean如果没有则新增key
            if (map.containsKey(li.getQuestionModuleName())) {
                //取出map中key对应的list并将遍历出的bean放入该key对应的list中
                ArrayList<QuestionBankBean> templist = (ArrayList<QuestionBankBean>) map.get(li.getQuestionModuleName());
                Log.e("重复",templist.size()+"***");
                templist.add(li);
            }
//            if (map.containsKey(li.getQuestionType()+"")) {
//                //取出map中key对应的list并将遍历出的bean放入该key对应的list中
//                ArrayList<QuestionBankBean> templist = (ArrayList<QuestionBankBean>) map.get(li.getQuestionType()+"");
//                Log.e("重复",templist.size()+"***");
//                templist.add(li);
//            }
            else {
                //创建新的list
                ArrayList<QuestionBankBean> temlist = new ArrayList<QuestionBankBean>();
                temlist.add(li);
                map.put(li.getQuestionModuleName(), temlist);
            }
        }

        return map;
    }

}
