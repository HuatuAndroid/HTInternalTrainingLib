package com.liuxiaoji.module_contacts.selectparticipant.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.liuxiaoji.module_contacts.R;
import com.liuxiaoji.module_contacts.selectparticipant.common.ActivityInstance;
import com.liuxiaoji.module_contacts.selectparticipant.common.EventBusEntity;
import com.liuxiaoji.module_contacts.selectparticipant.common.HeadTitleBuilder;
import com.liuxiaoji.module_contacts.selectparticipant.utils.KeybordUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    // activity跳转的数据载体
    protected Bundle mBundle = null;
    // 上下文
    protected Context mContext = null;
    // present对象
    protected T mPresenter = null;

    // eventBus方法处理
    protected abstract void handleEventBus(EventBusEntity eventBusMsg);

    // 初始化present对象
    protected abstract T initPresenter();

    // 初始化view对象实体
    protected abstract BaseViewHolder initViewBean(View view);

    // 设置head 布局文件
    protected abstract void setHeadTitle(HeadTitleBuilder headTitleBuilder);

    @Override
    protected void onCreate(Bundle bundle) {
        setTheme(R.style.ActivityTheme);
        ActivityInstance.getInstance().addActivity(this);

        super.onCreate(bundle);
        this.mContext = this;
        this.mBundle = getIntent().getExtras();
        setContentView(setLayoutResouceID());
        ButterKnife.bind(this);
        setStatusLayout(Color.parseColor("#21000000"));
//        if (!(this instanceof DialogActivity || this instanceof VideoListActivity || this instanceof LiveSplitScreenActivity
//                || this instanceof DialogActivity
//                || this instanceof NewsFunctionGuideActivity)) {
//            // 禁止横屏
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
        initViewBean(this.getWindow().getDecorView().findViewById(android.R.id.content));
        mPresenter = initPresenter();
        if (this.mPresenter != null) {
            this.mPresenter.attach((V) this);
        }
        initHeadView();
    }

    protected void setStatusLayout(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(colorId);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 初始化headUi
     */
    private void initHeadView() {
        try {
            RelativeLayout commonTitleUi = this.findViewById(R.id.common_title_ui_rl);
            HeadTitleBuilder.HeadTitleBuilderInner headTitleBuilder = new HeadTitleBuilder.HeadTitleBuilderInner(commonTitleUi);
            headTitleBuilder.setBackImageViewListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            headTitleBuilder.getHeadTitleBuilder().getLeftTextView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //当不设置返回文字时扩大点击区域
            headTitleBuilder.getHeadTitleBuilder().getLeftTextView().setText("        ");
            setHeadTitle(headTitleBuilder.getHeadTitleBuilder());
        } catch (Exception ex) {
            Log.e("baseActivity", ex.getMessage().toString());
        }

    }

    protected void onDestroy() {
        ActivityInstance.getInstance().removeActivity(this);
        if (this.mPresenter != null) {
            this.mPresenter.dettach();
        }

        super.onDestroy();
        hideSoftKeyword();

    }

    protected void hideSoftKeyword() {
        try {
            if (KeybordUtils.isSoftInputShow(this)) {
                View view = getWindow().peekDecorView();
                if (view != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception ex) {

        }

    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 发送事件总线
     *
     * @param paramString
     */
    protected void sendEventBusMsg(String paramString, Object object) {
        EventBus.getDefault().post(new EventBusEntity(paramString, object));
    }

    /**
     * 布局设置
     *
     * @return
     */
    protected abstract int setLayoutResouceID();

    /**
     * 带数据的跳转
     *
     * @param paramClass
     * @param paramBundle
     */
    public void startActivityWithExtras(Class<?> paramClass, Bundle paramBundle) {
        Intent intent = new Intent(this, paramClass);
        intent.putExtras(paramBundle);
        startActivity(intent);
    }

    /**
     * 不带数据的跳转
     *
     * @param paramClass
     */
    public void startActivityWithOutExtras(Class<?> paramClass) {
        startActivity(new Intent(this, paramClass));
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
