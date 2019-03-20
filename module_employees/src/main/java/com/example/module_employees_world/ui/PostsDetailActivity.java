package com.example.module_employees_world.ui;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.PostDetailAdapter;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.utils.StatusBarUtil;
import com.wb.baselib.view.TopBarView;

/**
 * 帖子详情
 */
public class PostsDetailActivity extends MvpActivity {

    private TopBarView topBarView;
    private RecyclerView mRvPost;

    @Override
    protected BasePreaenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setStatusLayout(this,Color.parseColor("#007AFF"));
        StatusBarUtil.StatusBarDarkMode(this, StatusBarUtil.StatusBarLightMode(this));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_posts_detail);
        topBarView = findViewById(R.id.topbarview_post_detail);
        mRvPost = findViewById(R.id.rv_post_detail);

        mRvPost.setLayoutManager(new LinearLayoutManager(this));
        mRvPost.setAdapter(new PostDetailAdapter(this));

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

}
