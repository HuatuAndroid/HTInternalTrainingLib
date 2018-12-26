package com.jungan.www.model_liveplay.pptmanage;

/**
 * Created by Shubo on 2017/5/2.
 */

interface PPTUploadImageListener {

    void onUploadingProgressUpdated(int position, int percentage);

    void onImageUploaded(int position);

    void onImageUploadFail(int position);
}
