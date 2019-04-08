package com.example.module_employees_world.ui.topic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baijiayun.glide.Glide;
import com.example.module_employees_world.R;
import com.example.module_employees_world.adapter.ViewPagerAdapter;
import com.example.module_employees_world.common.CommonUtils;
import com.example.module_employees_world.common.LocalImageHelper;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.view.TopBarView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhe
 * @date 2019/3/26
 * <p>
 * 选择相册页面
 */
public class LocalAlbumDetailActicity extends MvpActivity implements View.OnClickListener
        , CompoundButton.OnCheckedChangeListener {

    private TopBarView topBarView;

    private RecyclerView mRecyclerView;
    private TextView mTvConfirm;

    private RelativeLayout mRlLargePic;
    private TopBarView mPicTopBarView;
    private ViewPager mViewpager;
    private CheckBox mCheckBox;

    private List<LocalImageHelper.LocalFile> currentFolder = null;
    private LocalImageHelper helper = LocalImageHelper.getInstance();
    private List<LocalImageHelper.LocalFile> checkedItems;

    private CameraAdapter adapter;
    private String folder;
    //前面传的参数：插入图片的个数
    private int pic_size = 0;
    //插入图片的最大数量
    private int maxicSize = 9;

    private File mFileTemp;

    @Override
    protected BasePreaenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_local_album_detail);
        if (!LocalImageHelper.getInstance().isInited()) {
            finish();
            return;
        }

        topBarView = findViewById(R.id.mTopBarView);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mTvConfirm = findViewById(R.id.mTvConfirm);

        mRlLargePic = findViewById(R.id.mRlLargePic);
        mPicTopBarView = findViewById(R.id.mPicTopBarView);
        mViewpager = findViewById(R.id.mViewpager);
        mCheckBox = findViewById(R.id.mCheckBox);

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        folder = intent.getStringExtra(CommonUtils.LOCAL_FOLDER_NAME);
        pic_size = intent.getIntExtra("pic_size", 0);
        maxicSize = intent.getIntExtra("maxicSize", 9);

        if (folder == null) {
            folder = getResourcesImage(this, helper.getFolderMap());
        }

        new Thread(() -> {
            //防止停留在本界面时切换到桌面，导致应用被回收，图片数组被清空，在此处做一个初始化处理
            helper.initImage();
            //获取该文件夹下地所有文件
            final List<LocalImageHelper.LocalFile> folders = helper.getFolder(folder);

            setGrideAdapter(folders);

        }).start();

        checkedItems = helper.getCheckedItems();
        LocalImageHelper.getInstance().setResultOk(false);

    }

    public void setGrideAdapter(List<LocalImageHelper.LocalFile> currentFolder) {

        runOnUiThread(() -> {
            if (currentFolder != null) {
                this.currentFolder = currentFolder;
                if (adapter == null) {
                    adapter = new CameraAdapter(LocalAlbumDetailActicity.this, currentFolder);
                    mRecyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void setListener() {

        topBarView.setListener((v, action, extra) -> {
            //点击back键退出时，按键响应
            if (action == TopBarView.ACTION_LEFT_BUTTON) {

                finish();

            } else if (action == TopBarView.ACTION_RIGHT_TEXT) {     //点确定时，按键响应

                finish();

            }
        });

        mPicTopBarView.settvRight("确定(0/" + maxicSize + ")");

        mPicTopBarView.setListener((v, action, extra) -> {
            //点击back键退出时，按键响应
            if (action == TopBarView.ACTION_LEFT_BUTTON) {

                mRlLargePic.setVisibility(View.GONE);
                setGrideAdapter(currentFolder);

            } else if (action == TopBarView.ACTION_RIGHT_TEXT) {     //点确定时，按键响应

                mRlLargePic.setVisibility(View.GONE);
                setGrideAdapter(currentFolder);

            }
        });

        mTvConfirm.setOnClickListener(this);
        mCheckBox.setOnCheckedChangeListener(this);
        mViewpager.setOnPageChangeListener(pageChangeListener);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public void scanPhotos(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        this.sendBroadcast(intent);
    }

    List<String> folderNames;

    private String getResourcesImage(Context context, Map<String, List<LocalImageHelper.LocalFile>> folders) {

        folderNames = new ArrayList<>();

        Iterator iter = folders.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            folderNames.add(key);
        }

        int num = collectionsSort(folderNames);

        return folderNames.get(num);

    }

    private int collectionsSort(List<String> folderNames) {

        Collections.sort(folderNames, (arg0, arg1) -> {
            Integer num1 = helper.getFolder(arg0).size();
            Integer num2 = helper.getFolder(arg1).size();
            return num2.compareTo(num1);
        });
        return 0;
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        if (viewId == R.id.mTvConfirm) {
            LocalImageHelper.getInstance().setResultOk(true);
            finish();
        }
    }

    /**
     * checked 事件点击响应
     *
     * @param compoundButton
     * @param b
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
            if (checkedItems.contains(compoundButton.getTag())) {
                checkedItems.remove(compoundButton.getTag());
            }
        } else {
            if (!checkedItems.contains(compoundButton.getTag())) {
                if (checkedItems.size() + LocalImageHelper.getInstance().getCurrentSize() >= maxicSize - pic_size) {
                    Toast.makeText(this, "图片最多插入" + maxicSize + "张", Toast.LENGTH_SHORT).show();
                    compoundButton.setChecked(false);
                    return;
                }
                checkedItems.add((LocalImageHelper.LocalFile) compoundButton.getTag());
//                checkedSelectItems = checkedItems;
            }
        }

        if (checkedItems.size() != 0) {
            mTvConfirm.setText("确定" + "(" + checkedItems.size() + ")");
        } else {
            mTvConfirm.setText("确定");
        }
        mPicTopBarView.getRightTextView().setText("确定" + "(" + checkedItems.size() + "/" + maxicSize + ")");

    }

    private void showViewPager(int index) {

        mRlLargePic.post(() -> {

            mRlLargePic.setVisibility(View.VISIBLE);
            mViewpager.setAdapter(new ViewPagerAdapter(this, currentFolder, null));
            mViewpager.setCurrentItem(index);

//        mCountView.setText((index + 1) + "/" + currentFolder.size());
            //第一次载入第一张图时，需要手动修改
            if (index == 0) {
                mCheckBox.setTag(currentFolder.get(index));
                mCheckBox.setChecked(checkedItems.contains(currentFolder.get(index)));
            }
            AnimationSet set = new AnimationSet(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0.9, 1, (float) 0.9, 1, mRlLargePic.getWidth() / 2, mRlLargePic.getHeight() / 2);
            scaleAnimation.setDuration(300);
            set.addAnimation(scaleAnimation);
            AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
            alphaAnimation.setDuration(200);
            set.addAnimation(alphaAnimation);
            mRlLargePic.startAnimation(set);

        });
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            mCheckBox.setTag(currentFolder.get(position));
            mCheckBox.setChecked(checkedItems.contains(currentFolder.get(position)));

            mPicTopBarView.getCenterTextView().setText(position + 1 + "/" + currentFolder.size());

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };

    class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ViewHolder> {

        LayoutInflater inflater;
        Context mContext;
        List<LocalImageHelper.LocalFile> localFiles;

        public CameraAdapter(Context mContext, List<LocalImageHelper.LocalFile> localFiles) {
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
            this.localFiles = localFiles;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_gride_pic, parent, false);
            return new CameraAdapter.ViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

           final int nposition;

            if (position == 0){

                nposition = 0;
                holder.mIvCamera.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.GONE);
                holder.checkbox.setVisibility(View.GONE);

            }else {

                holder.mIvCamera.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.checkbox.setVisibility(View.VISIBLE);

                holder.checkbox.setOnCheckedChangeListener(LocalAlbumDetailActicity.this);

                nposition = position - 1;

                LocalImageHelper.LocalFile localFile = localFiles.get(nposition);

                Glide.with(mContext).asBitmap().load(localFile.getThumbnailUri()).into(holder.imageView);
                holder.checkbox.setTag(localFile);
                holder.checkbox.setChecked(checkedItems.contains(localFile));

            }

            holder.mIvCamera.setOnClickListener(view -> {

                if (position == 0) {
                    photoEven();
                }
            });

            holder.imageView.setOnClickListener(view -> {

                if (position != 0) {
                    showViewPager(nposition);
                }
            });

        }

        @Override
        public int getItemCount() {
            return localFiles.size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private CheckBox checkbox;
            private ImageView imageView, mIvCamera;

            public ViewHolder(View itemView, int viewType) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                mIvCamera = itemView.findViewById(R.id.mIvCamera);
                checkbox = itemView.findViewById(R.id.checkbox);
            }
        }
    }

    public void photoEven() {
        pictureInIt();
        takePicture();
    }

    public void pictureInIt() {

        // 创建项目所需文件夹
        CommonUtils.createFile();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(CommonUtils.cache_path, getLinPhotokName());
        } else {
            mFileTemp = new File(getFilesDir(), getLinPhotokName());
        }

    }

    public void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {

                if (Build.VERSION.SDK_INT >= 24) {
                    mImageCaptureUri = FileProvider.getUriForFile(this, "com.ekao123.manmachine.fileprovider", mFileTemp);
                } else {
                    mImageCaptureUri = Uri.fromFile(mFileTemp);
                }
            } else {
                /*
                 * The solution is taken from here:
                 * http://stackoverflow.com/questions
                 * /10042695/how-to-get-camera-result-as-a-uri-in-data-folder
                 */
                mImageCaptureUri = Uri.parse("content://eu.janmuller.android.simplecropimage.example/");;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CommonUtils.REQUEST_CODE_GETIMAGE_BYCROP);

        } catch (ActivityNotFoundException e) {

        }

    }

    private String getLinPhotokName() {
        return "htjy_photo" + CommonUtils.getTime() + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case CommonUtils.REQUEST_CODE_GETIMAGE_BYCROP:

                Log.i("onActivityResult", "-----" + mFileTemp.getPath());

                Intent intent1 = new Intent();
                intent1.putExtra("mFileTemp", mFileTemp.getPath());
                setResult(CommonUtils.REQUEST_CODE_GETIMAGE_BYCAMERA, intent1);
                finish();
                break;
            default:
                break;

        }
    }
}