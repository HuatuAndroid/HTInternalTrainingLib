package com.example.module_employees_world.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.module_employees_world.R;
import com.example.module_employees_world.utils.Rgba;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.wb.baselib.app.AppUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatImageHelper;
import skin.support.widget.SkinCompatSupportable;

import static android.support.v4.widget.ExploreByTouchHelper.INVALID_ID;
import static skin.support.widget.SkinCompatHelper.checkResourceId;

/**
 * @author liuzhe
 * @date 2019/3/26
 */
public class ExtendImageView extends SimpleDraweeView implements SkinCompatSupportable {

    public static final int MAX_CACHE_AVATAR_SIZE = 300;
    public static List<String> sCacheAvatarList = new ArrayList<>();

    public static final String TAG = "ExtendImageView";
    public static final String TYPE_DIMEN = "dimen";
    private int mPlaceHolderResId = 0;
    private int mLayoutMarginTopResId = INVALID_ID;

    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private SkinCompatImageHelper mImageHelper;

    public ExtendImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ExtendImageView(Context context) {
        super(context);
    }

    public ExtendImageView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        init(context,attrs,0);
    }

    public ExtendImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs,0);
    }


    public ExtendImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr);
    }


    void init(Context context,AttributeSet attrs,int defStyleAttr){

        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);

        mImageHelper = new SkinCompatImageHelper(this);
        mImageHelper.loadFromAttributes(attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GenericDraweeHierarchy, defStyleAttr, 0);
        try{
            if(a.hasValue(com.facebook.drawee.R.styleable.GenericDraweeHierarchy_placeholderImage)){
                this.mPlaceHolderResId = a.getResourceId(com.facebook.drawee.R.styleable.GenericDraweeHierarchy_placeholderImage,0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            a.recycle();
        }
        TypedArray a2 = context.obtainStyledAttributes(attrs,R.styleable.SkinLayoutPosHelper,defStyleAttr,0);
        try{
            if (a.hasValue(R.styleable.SkinLayoutPosHelper_android_layout_marginTop)){
                mLayoutMarginTopResId = a.getResourceId(
                        R.styleable.SkinLayoutPosHelper_android_layout_marginTop, INVALID_ID);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            a2.recycle();
        }
        applySkin();
    }

    public void loadUrl(String url, Rgba rgba, @DrawableRes int defaultResId, boolean gifAutoAnim){
        GenericDraweeHierarchy hierarchy = getHierarchy();
        if(rgba!=null){
            hierarchy.setPlaceholderImage(new ColorDrawable(rgba.toColor()));
        }else{
            hierarchy.setPlaceholderImage(defaultResId, ScalingUtils.ScaleType.CENTER_CROP);
        }
        if(!TextUtils.isEmpty(url)){
            loadUrl(Uri.parse(url),null,false,gifAutoAnim);
        }
    }

    public void loadUrlToSmall(String url,Rgba rgba,@DrawableRes int defaultResId,boolean gifAutoAnim){
        GenericDraweeHierarchy hierarchy = getHierarchy();
        if(rgba!=null){
            hierarchy.setPlaceholderImage(new ColorDrawable(rgba.toColor()));
        }else{
            hierarchy.setPlaceholderImage(defaultResId, ScalingUtils.ScaleType.CENTER_CROP);
        }
        if(!TextUtils.isEmpty(url)){
            int size = AppUtils.getWindowWidth()/3;
            loadUrl(Uri.parse(url),null,false,gifAutoAnim,false,new int[]{size,size});
        }
    }

    public void loadDrawable(@DrawableRes int resId){
//        if(R.drawable.sis_common_head_default == this.mPlaceHolderResId){
//            GenericDraweeHierarchy hierarchy = getHierarchy();
//            hierarchy.setPlaceholderImage(SkinCompatResources.getInstance().getDrawable(R.drawable.sis_common_head_default), ScalingUtils.ScaleType.CENTER_CROP);
//            hierarchy.setFailureImage(SkinCompatResources.getInstance().getDrawable(R.drawable.sis_common_head_default), ScalingUtils.ScaleType.CENTER_CROP);
//            loadUrl("http://");
//        }else{
//            loadUrl(Uri.parse("res:///" + resId));
//        }
    }

    public void loadXmlDrawable(@DrawableRes int resId){
        GenericDraweeHierarchy hierarchy = getHierarchy();
        hierarchy.setPlaceholderImage(resId);
        loadUrl("http://");
    }

    public void loadDrawable(@DrawableRes int resId, ControllerListener listener, boolean useImgRatio){
        loadUrl(Uri.parse("res:///" + resId),listener,false,true,useImgRatio);
    }

    public void loadUrl(String url){
        loadUrl(url,null,false);
    }

    public void loadUrl(String url,boolean useImgSize){
        loadUrl(url,null,useImgSize);
    }

    public void loadUrl(String url,ControllerListener controllerListener){
        loadUrl(url,controllerListener,false);
    }

    public void loadUrl(String url,ControllerListener controllerListener,boolean useImgSize){
        if(TextUtils.isEmpty(url)){
            if(mPlaceHolderResId != 0){
                loadDrawable(mPlaceHolderResId);
            }
            return;
        }
        loadUrl(Uri.parse(url),controllerListener,useImgSize);
    }

    public void loadUrl(Uri url,boolean useImageSize){
        loadUrl(url,null,useImageSize);
    }

    public void loadUrl(Uri url){
        loadUrl(url,null,false);
    }

    public void loadUrl(Uri uri, ControllerListener controllerListener, final boolean useImgSize){
        loadUrl(uri,controllerListener,useImgSize,true);
    }

    public void loadUrl(Uri uri, ControllerListener controllerListener, final boolean useImgSize,final boolean gifAutoAnim){
        loadUrl(uri,controllerListener,useImgSize,gifAutoAnim,false);
    }

    public void loadUrl(Uri uri, final ControllerListener controllerListener,
                        final boolean useImgSize,boolean _gifAutoAnim,
                        final boolean useImgRatio){
        loadUrl(uri,controllerListener,useImgSize,_gifAutoAnim,useImgRatio,null);
    }

    public void loadToSmall(String uri,ControllerListener controllerListener){
        if(TextUtils.isEmpty(uri)){
            return;
        }
        int size = AppUtils.getWindowWidth()/3;
        loadUrl(Uri.parse(uri),controllerListener,false,true,false,new int[]{size,size});
    }

    public void loadToLimitScreenSize(String uri){
        if(TextUtils.isEmpty(uri)){
            return;
        }
        loadUrl(Uri.parse(uri),null,false,true,false, AppUtils.getWindowSize());
    }



    public void loadUrl(Uri uri, final ControllerListener controllerListener,
                        final boolean useImgSize,boolean _gifAutoAnim,
                        final boolean useImgRatio,int[] resizeSize){
        try {
            _loadUrl(uri,controllerListener,useImgSize,_gifAutoAnim,useImgRatio,resizeSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadWithGlide(Uri uri){
        Glide.with(getContext()).load(uri).centerCrop().into(this);
        setController(null);
    }

    /**
     * @param uri 图片uri
     * @param controllerListener 图片
     * @param useImgSize
     * @param _gifAutoAnim
     * @param
     */
    private void _loadUrl(Uri uri, final ControllerListener controllerListener,
                          final boolean useImgSize,boolean _gifAutoAnim,
                          final boolean useImgRatio,int[] resizeSize) throws Exception{

        RoundingParams roundingParams = getHierarchy().getRoundingParams();
        if(roundingParams!=null){
            boolean roundAsCircle = roundingParams.getRoundAsCircle();
            _gifAutoAnim = roundAsCircle ? false : _gifAutoAnim;
        }

        final boolean gifAutoAnim = _gifAutoAnim;

        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();

        builder.setControllerListener(new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                if(controllerListener != null){
                    controllerListener.onFailure(id,throwable);
                }
            }

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if(controllerListener != null){
                    controllerListener.onFinalImageSet(id,imageInfo,animatable);
                }

                ViewGroup.LayoutParams lp = getLayoutParams();
                if(lp != null){
                    if (updateMarginLp(lp)) {
                        setLayoutParams(lp);
                    }
                }

                if (imageInfo != null) {
                    int height = imageInfo.getHeight();
                    int width = imageInfo.getWidth();
                    if(useImgSize){
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        layoutParams.width = width;
                        layoutParams.height =  height;
                        setLayoutParams(layoutParams);
                    }else if(useImgRatio){
                        setAspectRatio(height == 0 ? 1 : width*1.0f/height);
                    }
                }

                if(animatable!=null && gifAutoAnim){
                    try {
//                        if (animatable instanceof AnimatedDrawable2) {
//                            AnimatedDrawable2 animatedDrawable = (AnimatedDrawable2) animatable;
//                            animatedDrawable.setAnimationBackend(new LoopCountModifyingBackend(animatedDrawable.getAnimationBackend(), AnimatedImage.LOOP_COUNT_INFINITE));
//                        }

                        try {
                            Field field = AbstractAnimatedDrawable.class.getDeclaredField("mLoopCount");
                            field.setAccessible(true); field.set(animatable,0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ImageRequestBuilder requestBuilder =  ImageRequestBuilder.newBuilderWithSource(uri);

        if(resizeSize != null && resizeSize.length == 2){
            int rw = resizeSize[0];
            int rh = resizeSize[1];
            if(rw > 0 && rh > 0){
                requestBuilder.setResizeOptions(new ResizeOptions(rw,rh));
            }
        }
//        else if(R.drawable.sis_common_head_default == this.mPlaceHolderResId){
//            int _avatarMaxSize = (int) SpPxUtils.dp2px(getResources(),50.0f);
//            requestBuilder.setResizeOptions(new ResizeOptions(_avatarMaxSize,_avatarMaxSize));
//            if (hasCached(uri)) {
//                sCacheAvatarList.add(uri.toString());
//            }
//        }
        else if(getLayoutParams().width > 0 && getLayoutParams().height > 0){
            requestBuilder.setResizeOptions(new ResizeOptions(getLayoutParams().width,getLayoutParams().height));
        }

        builder.setAutoPlayAnimations(gifAutoAnim).setImageRequest(requestBuilder.build());

        DraweeController controller = builder
                .setOldController(getController())
                .setTapToRetryEnabled(false)
                .build();
        setController(controller);
    }

    public static boolean hasCached(Uri uri) {
        Boolean result = Fresco.getImagePipeline().isInDiskCache(uri).getResult();
        return result != null && result;
    }

    public static void sCacheItToMem(Uri uri){
        Fresco.getImagePipeline().prefetchToBitmapCache(ImageRequest.fromUri(uri),null);
    }

    public static boolean hasCachedAndNotCacheIt(Uri uri,Runnable run) {

        Runnable runnable = () -> {
            if(run != null){
                run.run();
            }
        };

        Boolean result = hasCached(uri);
        if(!result){
            DataSource<Void> voidDataSource = Fresco.getImagePipeline().prefetchToDiskCache(ImageRequest.fromUri(uri), null);
            voidDataSource.subscribe(new DataSubscriber<Void>() {
                @Override
                public void onNewResult(DataSource<Void> dataSource) {

                    if (hasCached(uri)) {
                        runnable.run();
                    }
                }

                @Override
                public void onFailure(DataSource<Void> dataSource) {

                }

                @Override
                public void onCancellation(DataSource<Void> dataSource) {

                }

                @Override
                public void onProgressUpdate(DataSource<Void> dataSource) {

                }
            }, CallerThreadExecutor.getInstance());

        }else{
            runnable.run();
        }
        return result;
    }

    public void setAspectRatioBySize(int w,int h) {
        super.setAspectRatio(h == 0? 1: w*1.0f/h);
    }


    private boolean mFilterStyle = false;

    public void enableFilterStyle(){
        mFilterStyle = true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mFilterStyle){
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    //在按下事件中设置滤镜
                    setFilter();
                    break;
                case MotionEvent.ACTION_UP:
                    //由于捕获了Touch事件，需要手动触发Click事件
                    performClick();
                case MotionEvent.ACTION_CANCEL:
                    //在CANCEL和UP事件中清除滤镜
                    removeFilter();
                    break;
                default:
                    break;
            }
            return super.onTouchEvent(event);
        }else{
            return super.onTouchEvent(event);
        }
    }

    /**
     *   设置滤镜
     */
    private void setFilter() {
        //先获取设置的src图片
        Drawable drawable=getDrawable();
        //当src图片为Null，获取背景图片
        if (drawable==null) {
            drawable=getBackground();
        }
        if(drawable!=null){
            //设置滤镜
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);;
        }
    }
    /**
     *   清除滤镜
     */
    private void removeFilter() {
        //先获取设置的src图片
        Drawable drawable=getDrawable();
        //当src图片为Null，获取背景图片
        if (drawable==null) {
            drawable=getBackground();
        }
        if(drawable!=null){
            //清除滤镜
            drawable.clearColorFilter();
        }
    }

    @Override
    public void applySkin() {

//        if(R.drawable.sis_common_head_default == this.mPlaceHolderResId){
//
//            GenericDraweeHierarchy hierarchy = getHierarchy();
//            hierarchy.setPlaceholderImage(SkinCompatResources.getInstance().getDrawable(R.drawable.sis_common_head_default), ScalingUtils.ScaleType.CENTER_CROP);
//            hierarchy.setFailureImage(SkinCompatResources.getInstance().getDrawable(R.drawable.sis_common_head_default), ScalingUtils.ScaleType.CENTER_CROP);
//        }

        ViewGroup.LayoutParams lp = getLayoutParams();
        if(lp != null){
            updateMarginLp(lp);
            setLayoutParams(lp);
        }

        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
        if (mImageHelper != null) {
            mImageHelper.applySkin();
        }
    }


    private boolean updateMarginLp(ViewGroup.LayoutParams lp) {
        ViewGroup.MarginLayoutParams mlp;
        boolean _needUpdate = false;
        if(lp != null && lp instanceof ViewGroup.MarginLayoutParams){
            mlp = (ViewGroup.MarginLayoutParams) lp;
            if((mLayoutMarginTopResId = checkResourceId(mLayoutMarginTopResId)) != INVALID_ID){
                String typeMarginTopName = getResources().getResourceTypeName(mLayoutMarginTopResId);
                if(TYPE_DIMEN.equals(typeMarginTopName)){
                    mlp.topMargin = getDimen(mLayoutMarginTopResId);
                    _needUpdate = true;
                }
            }
        }
        return _needUpdate;
    }

    private int getDimen(int resId) {
        int dimen = (int) getResources().getDimension(resId);
        if (SkinCompatResources.getInstance().isDefaultSkin()) {
            return dimen;
        }

        Resources res = SkinCompatResources.getInstance().getSkinResources();
        String resName = getResources().getResourceEntryName(resId);

        int targetResId = res.getIdentifier(resName, "dimen",
                SkinCompatResources.getInstance().getSkinPkgName());

        return targetResId == 0 ? dimen : (int) res.getDimension(targetResId);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
//        // Intercept this call and instead retrieve the Drawable via the image helper
//        if(resId == R.drawable.sis_common_head_default){
//            getHierarchy().setPlaceholderImage(resId, ScalingUtils.ScaleType.CENTER_CROP);
//            return;
//        }
//        if (mImageHelper != null) {
//            mImageHelper.setImageResource(resId);
//        }
    }

    //    public static class LoopCountModifyingBackend extends AnimationBackendDelegate {
//
//        private int mLoopCount;
//
//        public LoopCountModifyingBackend(AnimationBackend animationBackend,
//                int loopCount) {
//            super(animationBackend);
//            mLoopCount = loopCount;
//        }
//
//        @Override
//        public int getLoopCount() {
//            return mLoopCount;
//        }
//    }


    @Override
    public String toString() {
        return hashCode()+"";
    }
}