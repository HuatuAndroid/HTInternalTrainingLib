package com.example.module_employees_world.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.bean.TopicContentItem;
import com.example.module_employees_world.ui.ExtendImageView;
import com.example.module_employees_world.utils.ImgUtils;
import com.example.module_employees_world.utils.Rgba;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.wb.baselib.utils.VerticalImageSpan;
import com.wb.baselib.view.NCommontPopw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author liuzhe
 * @date 2019/3/26
 */
    public class TopicEditView extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener, EditText.OnClickListener, EditText.OnFocusChangeListener {

    public static final String TAG = "ContentEditor";
    public static int maxTextureWidth = 0;
    public static int maxTextureheight = 0;
    private List<TopicContentItem> datas = new ArrayList<>();
    private boolean editable = false;

    //控件相关属性
    private int textColor;
    private float fontSize;
    private float lineSpacingExtra;
    private int textpadding;
    private int imgpadding;
    private int ce_img_txt_spacing;
    private int ce_img_img_spacing;
    private int windowWidth;
    private int windowHeight;

    //删除照片，弹框
    private NCommontPopw sureBackPopw;

    public int getMaxImgCount() {
        return maxImgCount;
    }

    public void setMaxImgCount(int maxImgCount) {
        this.maxImgCount = maxImgCount;
    }

    private int maxImgCount = 9;   //图片插入的最大个数
    private String hint;

    public void setHint(String hint) {
        this.hint = hint;
    }

    public TopicEditView(Context context) {
        this(context, null);
    }

    public TopicEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TopicEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ContentEditor, defStyleAttr, 0);
        textColor = a.getColor(R.styleable.ContentEditor_ce_text_color, Color.argb(0, 0, 0, 0));
        fontSize = a.getDimensionPixelSize(R.styleable.ContentEditor_ce_text_size, 0);
        lineSpacingExtra = a.getDimensionPixelSize(R.styleable.ContentEditor_ce_text_lineSpacingExtra, 0);
        textpadding = a.getDimensionPixelSize(R.styleable.ContentEditor_ce_text_padding, 0);
        imgpadding = a.getDimensionPixelOffset(R.styleable.ContentEditor_ce_img_padding, 0);
        ce_img_txt_spacing = a.getDimensionPixelSize(R.styleable.ContentEditor_ce_img_txt_spacing, 0);
        ce_img_img_spacing = a.getDimensionPixelSize(R.styleable.ContentEditor_ce_img_img_spacing, 0);
        hint = a.getString(R.styleable.ContentEditor_ce_hint);
        a.recycle();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();
        windowHeight = wm.getDefaultDisplay().getHeight();

        if (maxTextureWidth == 0) {
            maxTextureWidth = windowWidth * 2;
        }
        if (maxTextureheight == 0) {
            maxTextureheight = windowHeight * 2;
        }
    }

    int measureWidth = 0;
    int measureHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.measureWidth = getMeasuredWidth();
        this.measureHeight = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 添加数据
     *
     * @param beans
     */
    public void addDatas(int index, List<TopicContentItem> beans) {
        for (int i = 0; i < beans.size(); i++) {
            addData(index + i, beans.get(i));
        }
    }

    /**
     * 添加数据
     *
     * @param beans
     */
    public void setDatas(List<TopicContentItem> beans) {
        if (this.datas != null && this.datas.equals(beans)) {
            return;
        }
        this.datas.clear();
        this.removeAllViews();
        addDatas(0, beans);
        //去掉显示模式首行和尾行空白
        if (!editable) {
            int cc = getChildCount();
            for (int i = 0; i < cc; i++) {
                View v = getChildAt(i);
                if (v instanceof TextView) {
                    TextView mTv = (TextView) v;
                    String content = mTv.getText().toString();
                    if (TextUtils.isEmpty(content.trim())) {
                        mTv.setVisibility(i == 0 || i == cc - 1 ? View.GONE : View.VISIBLE);
                    }
                }
                if (i == cc - 1) {
                    TextView mtv = new TextView(getContext());
                    mtv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ce_img_txt_spacing));
                    addView(mtv);
                }
            }
        }
    }

    private boolean isShowHint = false;

    /**
     * edittext hint设置
     *
     * @param showHint
     */
    public void setShowHint(boolean showHint) {
        isShowHint = showHint;
    }

    /**
     * 添加数据  没种数据判断类型
     *
     * @param index
     * @param bean
     */
    public void addData(final int index, final TopicContentItem bean) {
        this.datas.add(index, bean);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 0;//textpadding;
        layoutParams.rightMargin = 0;//textpadding;

        int preIndex = index - 1;
        TopicContentItem preBean = null;
        if (preIndex >= 0) {
            preBean = this.datas.get(preIndex);
            View preView = getChildAt(preIndex);
            if (preView instanceof EditText) {
                ((EditText) preView).setHint("");
            }
        }

        switch (bean.type) {
            case TXT:     //文本类型
                if (editable) {    //文本可编辑
                    //参加文本控件，初始化相关属性
                    final EditText editText = new EditText(getContext());
                    editText.setOnFocusChangeListener(this);
                    editText.setOnClickListener(this);
                    editText.setText(bean.content);
                    editText.setBackground(null);
                    editText.addTextChangedListener(new MyTextWatch(bean, editText));
                    editText.setBackground(null);
                    editText.setGravity(Gravity.CENTER_VERTICAL);
                    editText.setPadding(0, 0, 0, 0);
                    editText.requestFocus();
                    editText.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, lineSpacingExtra, getResources().getDisplayMetrics()), 1.0f);
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    editText.setTextColor(textColor);
                    editText.setHint(index == 0 && isShowHint ? hint : "");
                    editText.setHintTextColor(Color.parseColor("#50403f3f"));

                    if (preBean != null && preBean.type == TopicContentItem.ContentType.IMG) {
                        layoutParams.topMargin = ce_img_txt_spacing;
                    }

                    this.addView(editText, index, layoutParams);
                } else {    //文本不可编辑
                    //创建TextView，初始化
                    TextView tv = new TextView(getContext());
                    tv.setText(bean.content);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    tv.setTextColor(textColor);
                    tv.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, lineSpacingExtra, getResources().getDisplayMetrics()), 1.0f);

                    //判断数据是否合法
                    if (preBean != null && preBean.type == TopicContentItem.ContentType.IMG && TextUtils.isEmpty(bean.content.trim())) {
                        layoutParams.height = ce_img_img_spacing;
                        layoutParams.topMargin = 0;
                    }

                    //判断数据是否是img如果是，特殊处理
                    if (preBean != null && preBean.type == TopicContentItem.ContentType.IMG && !TextUtils.isEmpty(bean.content.trim())) {
                        layoutParams.topMargin = ce_img_txt_spacing;
                    }

                    tv.setTextIsSelectable(true);
                    this.addView(tv, index, layoutParams);
                }
                break;
            case IMG:    //图片类型

                LayoutParams imgLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imgLp.leftMargin = imgpadding;
                imgLp.rightMargin = imgpadding;

                final View iv;
                //判断此图片是本地还是网络
                final String url = editable ? bean.localUrl : bean.remoteUrl;

                if (editable) {       //本地图片，可编辑状态
                    int[] decodeSize = ImgUtils.decodeImageSize(Uri.parse(bean.localUrl).getPath());
                    boolean isSmall;
                    if (decodeSize != null && decodeSize.length == 2) {
                        if (decodeSize[0] >= maxTextureWidth || decodeSize[1] >= maxTextureheight) {
                            isSmall = false;
                        } else if (decodeSize[0] != 0 && decodeSize[1] != 0) {
                            isSmall = true;
                        } else {
                            isSmall = false;
                        }
                    } else {
                        isSmall = false;
                    }
                    if (isSmall) {

                        ExtendImageView imageView = new ExtendImageView(getContext());
                        imgLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        imgLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        iv = imageView;
                        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
                        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                        imageView.setAspectRatioBySize(bean.w, bean.h);
                        imageView.loadToLimitScreenSize(bean.localUrl);

                    } else {
                        SubsamplingScaleImageView scaleImageView = new SubsamplingScaleImageView(getContext());
                        scaleImageView.setZoomEnabled(false);
                        scaleImageView.setMaxScale(5.0f);
                        scaleImageView.setImage(ImageSource.uri(bean.localUrl));
                        iv = scaleImageView;
                    }

                } else {    //网络图片， 不可编辑，点击放大

                    ExtendImageView imageView = new ExtendImageView(getContext());
                    imgLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    imgLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    iv = imageView;
                    GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
                    hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                    if (bean.color == null) {
                        hierarchy.setPlaceholderImage(R.drawable.sis_hardware_default, ScalingUtils.ScaleType.CENTER_CROP);
                    } else {
                        hierarchy.setPlaceholderImage(new ColorDrawable(bean.color.toColor()));
                    }
                    imageView.setAspectRatioBySize(bean.w, bean.h);
                    imageView.loadToLimitScreenSize(url);

                }

                iv.setOnClickListener(view -> {
                    if (mItemClickListener != null) {
                        mContentWatch.deletePic(iv);
                    } else if (!editable) {
                        if (!TextUtils.isEmpty(url)) {
                            navToPhotoView(bean);
                        }
                    }
                });

                if (editable || (preBean != null && preBean.type == TopicContentItem.ContentType.TXT && !TextUtils.isEmpty(preBean.content.trim()))) {
                    imgLp.topMargin = ce_img_txt_spacing;
                }

                this.addView(iv, index, imgLp);
                break;
            default:
                break;
        }
    }

    public String getScaleImgUrl(String imgUrl, int[] desSize) {
        String url = null;
        try {
            Uri uri = Uri.parse(imgUrl);
            File compressFile = ImgUtils.supportCompressImage(getContext(), uri.getPath(), desSize);
            if (compressFile == null) {
                return null;
            }
            url = Uri.fromFile(compressFile).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return url;
    }

    /**
     * 图片点击放大图
     *
     * @param item
     */
    public void navToPhotoView(TopicContentItem item) {

    }

    public static int state = 0;

    public static void setState(int state1) {
        state = state1;
    }

    public int getIndexFromImages(TopicContentItem item) {
        int currentIndex = 0;
        if (datas != null) {
            for (TopicContentItem data : datas) {
                if (TopicContentItem.ContentType.IMG.equals(data.type)) {
                    if (item == data) {
                        return currentIndex;
                    }
                    currentIndex++;
                }
            }
        }
        return 0;
    }

    public List<TopicContentItem> getImgItems() {
        List<TopicContentItem> items = new ArrayList<>();

        if (datas != null) {
            for (TopicContentItem data : datas) {
                if (TopicContentItem.ContentType.IMG.equals(data.type)) {
                    items.add(data);
                }
            }
        }

        return items;
    }

    public String[] getImgUrls() {
        List<String> urls = new ArrayList<>();
        if (datas != null) {
            for (TopicContentItem data : datas) {
                if (TopicContentItem.ContentType.IMG.equals(data.type)) {
                    urls.add(editable ? data.localUrl : data.remoteUrl);
                }
            }
        }
        return urls.toArray(new String[]{});
    }


    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<TopicContentItem> getDatas() {
        return datas;
    }

    int stateBarHeight = 0;

    @Override
    public void onGlobalLayout() {

        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            Rect wR = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(wR);
            stateBarHeight = wR.top;
        }

    }

    class MyTextWatch implements TextWatcher {

        TopicContentItem bean;
        EditText editText;

        public MyTextWatch(TopicContentItem bean, EditText editText) {
            this.bean = bean;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        /**
         * 用于判断编辑时：输入字符的字数
         *
         * @param editable
         */
        @Override
        public void afterTextChanged(Editable editable) {

            String content = editText.getText().toString();
            bean.content = content;
            bean.mEditable = editText.getText();
            long tmpCount = getTxtCount();
            if (maxTxtCount > 0 && tmpCount > maxTxtCount) {

                long needRemoveCount = tmpCount - maxTxtCount;
                long tmpDelete = 0;
                int delLen = content.length() - 1;
                for (int length = content.length() - 1; length >= 0; length--) {
                    char schar = content.charAt(length);
                    if (isCh(schar)) {
                        tmpDelete += 2;
                    } else {
                        tmpDelete += 1;
                    }
                    if (tmpDelete >= needRemoveCount) {
                        delLen = length;
                        break;
                    }
                }
                content = content.substring(0, delLen);
                bean.content = content;

                editText.setText(content);
                editText.setSelection(editText.getText().length());
                return;
            }

            if (mContentWatch != null) {
                mContentWatch.onEmpty(!hasTextContent());
            }
        }
    }

    public boolean hasTextContent() {
        for (TopicContentItem data : datas) {
            if (data.type == TopicContentItem.ContentType.TXT && !TextUtils.isEmpty(data.content) && data.content.trim().length() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isDataEmpty() {
        for (TopicContentItem data : datas) {
            if (data.type == TopicContentItem.ContentType.TXT && !TextUtils.isEmpty(data.content) && data.content.trim().length() > 0) {
                return false;
            }
            if (data.type == TopicContentItem.ContentType.IMG && !TextUtils.isEmpty(data.localUrl) && data.localUrl.trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    int maxTxtCount = -1;

    public void setMaxTxtCount(int maxTxtCount) {
        this.maxTxtCount = maxTxtCount;
    }

    public int getMaxTxtCount() {
        return maxTxtCount;
    }

    public int getImageCount() {
        if (datas == null) {
            return 0;
        }
        int ic = 0;
        for (TopicContentItem data : datas) {
            if (data.type == TopicContentItem.ContentType.IMG) {
                ic++;
            }
        }
        return ic;
    }

    public TopicContentItem[] getImageItems() {
        List<TopicContentItem> items = new ArrayList<>();
        for (TopicContentItem data : datas) {
            if (data.type == TopicContentItem.ContentType.IMG) {
                items.add(data);
            }
        }
        return items.toArray(new TopicContentItem[]{});
    }

    public long getTxtCount() {
        if (datas == null) {
            return 0;
        }
        long txtCount = 0;
        float txtCountSingle = 0;
        long txtCountDouble = 0;

        for (TopicContentItem data : datas) {
            if (data.type == TopicContentItem.ContentType.TXT && !TextUtils.isEmpty(data.content)) {
                for (int j = 0; j < data.content.length(); j++) {
                    char schar = data.content.charAt(j);
                    if (isCh(schar)) {
                        txtCount = txtCount + 2;
                        txtCountDouble++;
                    } else {
                        txtCount = txtCount + 1;
                        txtCountSingle++;
                    }
                }
            }
        }
        if (mContentWatch != null) {
            mContentWatch.onTypedCount(txtCountSingle * .5f + txtCountDouble);
        }
        return txtCount;
    }

    public static boolean isCh(char schar) {
        return (schar & 0xFF80) != 0;
    }

    public void delete(View view) {
        int index;
        removeViewAt(index = indexOfChild(view));
        if (index != -1) {

            String text = datas.get(index - 1).content + (TextUtils.isEmpty(datas.get(index + 1).content) ? "" : "\n") + datas.get(index + 1).content;
            datas.remove(index);
            datas.remove(index);
            datas.remove(index - 1);
            removeViewAt(index);
            removeViewAt(index - 1);
            addData(index - 1, new TopicContentItem(text));
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int index);
    }

    ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public static final Rgba diffColorM = new Rgba(50, 50, 50, 255);

    public void rxImg(Observable<String> observable) {

        observable.flatMap(path -> Observable.create((Observable.OnSubscribe<TopicContentItem>) subscriber -> {

            final int[] desSize = new int[2];
            final String localPath;

            try {
                localPath = getScaleImgUrl(path, desSize);
            } catch (Exception e) {
                e.printStackTrace();
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
                return;
            }

            if (TextUtils.isEmpty(localPath)) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                }
                return;
            }

            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(localPath))
                    .setProgressiveRenderingEnabled(false)
                    .build();

            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, getContext());

            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                     @Override
                                     public void onNewResultImpl(@Nullable Bitmap bitmap) {

                                         Rgba color = null;
                                         try {
                                             Palette generate = Palette.from(bitmap).generate();
                                             Palette.Swatch mutedSwatch = generate.getMutedSwatch();
                                             color = Rgba.fromSwitch(mutedSwatch, diffColorM);
                                         } catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                         if (!subscriber.isUnsubscribed()) {
                                             subscriber.onNext(new TopicContentItem(localPath, null, color, desSize[0], desSize[1]));
                                             subscriber.onCompleted();
                                         }
                                     }

                                     @Override
                                     public void onFailureImpl(DataSource dataSource) {
                                         if (!subscriber.isUnsubscribed()) {
                                             subscriber.onError(new Exception("onFailureImpl"));
                                         }
                                     }
                                 },
                    CallerThreadExecutor.getInstance());
        }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {

                    if (item != null) {
                        addImgItem(item);
                    }
                }, throwable -> throwable.printStackTrace());
    }

    public void addImgs(final String[] imgs) {
        if (imgs == null || imgs.length == 0) {
            return;
        }
        rxImg(Observable.from(imgs));
    }

    public void addImg(String path) {
        rxImg(Observable.just(path));
    }

    /**
     * 添加图片
     *
     * @param item
     * @return
     */
    private int addImgItem(TopicContentItem item) {

        if (item == null) {
            return -1;
        }

        //图片最大的数量
        if (getImageCount() >= getMaxImgCount()) {
            return -1;
        }

        //判断图片的是否为null
        if (TextUtils.isEmpty(item.localUrl) || !ImgUtils.isImage(Uri.parse(item.localUrl).getPath())) {
            return -1;
        }

        //获取当前有焦点的view
        View view = this.findFocus();
        //获取容器孩子的数量
        int index = this.indexOfChild(view);
        String newContent = "";

        if (index >= 0) {
            //判断此view是否是EditText
            if (view != null && view instanceof EditText) {

                EditText et = (EditText) view;
                int cindex = et.getSelectionStart();
                if (cindex >= 0) {
                    //EditText获取内容，并且删除此view
                    //插入图片时，如要移除插入点的控件来放图片，此控件向下移位
                    Editable editable = et.getText();
                    newContent = editable.subSequence(cindex, editable.length()).toString();
                    editable.delete(cindex, editable.length());
                }
            }
        } else {
            index = this.getChildCount() - 1;
        }

        List<TopicContentItem> beans = new ArrayList<>();
        beans.add(item);
        beans.add(new TopicContentItem(newContent));
        this.addDatas(index + 1, beans);
        return index + 2;
    }

    /**
     * 添加换行
     *
     * @param state 0:插入换行  1：插入表情
     */
    public void AddLineFeed(int state, EmojiconBean emojicon) {

        View view = this.findFocus();

        if (view != null && view instanceof EditText) {

            EditText editText = (EditText) view;

            Editable editable = editText.getText();
            int index = editText.getSelectionStart();

            if (state == 0) {
                editable.insert(index, "\n");
            } else if (state == 1) {
                editable.insert(index, emojicon.emojiChart);
            }

        }
    }

    /**
     * 添加超链接
     */
    public void AddConnect(SpannableString spannableString) {

        View view = this.findFocus();

        if (view != null && view instanceof EditText) {

            EditText editText = (EditText) view;

            Editable editable = editText.getText();
            int index = editText.getSelectionStart();

            editable.insert(index, spannableString);

        }

    }

    /**
     * 点击会隐藏表情视图
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        mContentWatch.hideEmojiKeyboard();
    }

    /**
     * 点击会隐藏表情视图
     *
     * @param v
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mContentWatch.hideEmojiKeyboard();
        }
    }

    /**
     * 向外暴露回调
     */
    public interface ContentWatch {
        void onEmpty(boolean empty);

        void onTypedCount(float count);

        void hideEmojiKeyboard();

        void deletePic(View view);
    }

    ContentWatch mContentWatch;

    public void setContentWatch(ContentWatch mContentWatch) {
        this.mContentWatch = mContentWatch;
    }

    public void addTutuImg(int res, String name) {

        View view = this.findFocus();

        if (view != null && view instanceof EditText) {

            EditText editText = (EditText) view;

            Editable editable = editText.getText();
            int index = editText.getSelectionStart();

            Drawable drawable = getContext().getResources().getDrawable(res);

//            LogUtil.e("addTutuImg -- " + drawable.getIntrinsicWidth() + " --- " + drawable.getIntrinsicHeight());

            drawable.setBounds(0, 0, 200, 200);
            SpannableString spannable = new SpannableString(name);
            VerticalImageSpan span = new VerticalImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannable.setSpan(span, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            editable.insert(index, spannable);

        }
    }

}
