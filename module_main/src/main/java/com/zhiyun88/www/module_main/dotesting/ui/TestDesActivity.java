package com.zhiyun88.www.module_main.dotesting.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.utils.SharedPrefsUtil;
import com.zhiyun88.www.module_main.R;

@Route(path = "/dotesting/againtest")
public class TestDesActivity extends BaseActivity {
    private ImageView back_left;
    private TextView star_test_tv;
    private CheckBox again_cbx;
    private String classId,testTypeName;
    private int testType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarColor(Color.WHITE,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dotest_testdes_layout);
        classId=getIntent().getStringExtra("classId");
        testTypeName=getIntent().getStringExtra("testTypeName");
        testType=getIntent().getIntExtra("classId",1);
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        back_left=getViewById(R.id.back_left);
        star_test_tv=getViewById(R.id.star_test_tv);
        again_cbx=getViewById(R.id.again_cbx);
    }

    @Override
    protected void setListener() {
        back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        star_test_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TestDesActivity.this,CommonTestActivity.class);
                intent.putExtra("classId",classId);
                intent.putExtra("testType",testType);
                intent.putExtra("testTypeName","快速智能刷题");
                startActivity(intent);
                finish();
//                ARouter.getInstance().build("/test/againtest").withString("",classId).withInt("testType",testType).withString("testTypeName","快速智能刷题").navigation();
            }
        });
        again_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPrefsUtil.putValue(TestDesActivity.this,"again","again",true);
                }else {
                    SharedPrefsUtil.putValue(TestDesActivity.this,"again","again",false);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
