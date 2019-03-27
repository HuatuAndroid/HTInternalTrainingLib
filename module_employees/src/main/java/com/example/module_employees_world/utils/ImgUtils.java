package com.example.module_employees_world.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import com.baijiayun.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @author liuzhe
 * @date 2019/3/22
 */
public class ImgUtils {

    public static final String TAG = "ImgUtils";
    public static final int IMG_MAX_WIDTH = 1280;
    public static final int IMG_COMPRESS_QUA = 100;


    /**
     * 默认图片压缩（width->500px）
     * @return
     */
    public static File supportCompressImage(Context mContext, String path, int[] desSize) throws IOException {

        File tempFile = decodeSupportBitmapFromResource(mContext,path,IMG_MAX_WIDTH,desSize);
        return tempFile;
    }

    /**
     * 计算inSampleSize，用于压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // 源图片的宽度
        int width = options.outWidth;
        int inSampleSize = 1;

        if (width > reqWidth)
        {
            // 计算出实际宽度和目标宽度的比率
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        return inSampleSize;
    }

    public static boolean isImage(String inputPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(inputPath, options);

        if (options.outWidth == -1 || options.outHeight == -1 || options.outWidth == 0 || options.outHeight == 0) {
            return false;
        }
        return true;

    }

//    /**
//     * 根据计算的inSampleSize，得到压缩后图片
//     *
//     */
//    public static File decodeSampledBitmapFromResource(String inputPath,int reqWidth) throws IOException {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(inputPath, options);
//
//        if (!isImage(inputPath)) {
//            return null;
//        }
//
//        int desW = reqWidth;
//        int desH = (int) (reqWidth*(options.outHeight*1.0f/(options.outWidth == 0 ? 1 : options.outWidth)));
//
//        File tempFile = new File(App.getAppContext().getExternalCacheDir(), UUID.randomUUID().toString()+".jpg");
//        LogUtil.d(TAG,"des->"+desW+","+desH+" --->"+tempFile);
//
//        Bitmap mBitmap = Picasso.with(App.getAppContext()).load(new File(inputPath)).resize(desW,desH).get();
//        mBitmap.compress(Bitmap.CompressFormat.JPEG,IMG_COMPRESS_QUA,new FileOutputStream(tempFile));
//        return tempFile;
//    }

    public static File decodeSupportBitmapFromResource(Context mContext,String inputPath, int reqWidth,int[] desSize){
        return decodeSupportBitmapFromResource(mContext,inputPath,reqWidth,desSize,true);
    }

    public static File decodeSupportBitmapFromResource(Context mContext,String inputPath, int reqWidth,int[] desSize,boolean keepGif){

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(inputPath, options);

        if (!isImage(inputPath)) {
            return null;
        }

        int desW = reqWidth;
        int desH = (int) (reqWidth*(options.outHeight*1.0f/(options.outWidth == 0 ? 1 : options.outWidth)));

        if(desSize!=null && desSize.length > 1){
            desSize[0] = desW;
            desSize[1] = desH;
        }

        String tmpFileName = UUID.randomUUID().toString();
        File tmpFile = null;

        try {
//            if (!keepGif) {
                tmpFile = new File(mContext.getExternalCacheDir(),tmpFileName+".jpg");
                createScaleBitmap(Glide.with(mContext).asBitmap().load(inputPath).into(desW,desH).get(),desW,desH).compress(Bitmap.CompressFormat.JPEG,IMG_COMPRESS_QUA,new FileOutputStream(tmpFile));
//            }else{
//                GlideDrawable glideDrawable = Glide.with(mContext).load(inputPath).into(desW, desH).get();
//                if(glideDrawable instanceof GifDrawable){
//                    tmpFile = new File(mContext.getExternalCacheDir(),tmpFileName+".gif");
//                    FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
//                    fileOutputStream.write(((GifDrawable)glideDrawable).getData());
//                }else if(glideDrawable instanceof GlideBitmapDrawable){
//                    tmpFile = new File(mContext.getExternalCacheDir(),tmpFileName+".jpg");
//                    createScaleBitmap(((GlideBitmapDrawable)glideDrawable).getBitmap(),desW,desH).compress(Bitmap.CompressFormat.JPEG,IMG_COMPRESS_QUA,new FileOutputStream(tmpFile));
//                }else{
////                tmpFile = decodeSampledBitmapFromResource(inputPath,reqWidth);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmpFile;
    }

    /**
     * @description 通过传入的bitmap，进行压缩，得到符合标准的bitmap
     *
     * @param tempBitmap
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    private static Bitmap createScaleBitmap(Bitmap tempBitmap, int desiredWidth, int desiredHeight) {
        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null && tempBitmap.getWidth() > desiredWidth) {
            // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
            Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
            tempBitmap.recycle(); // 释放Bitmap的native像素数组
            return bitmap;
        } else {
            return tempBitmap; // 如果没有缩放，那么不回收
        }
    }

    public static void copy(File src, File dst) throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try{
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(in!=null)in.close();
                if(out!=null)out.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }



    /**
     * 写图片文件到SD卡
     *
     * @throws IOException
     */
    public static void saveImageToSD(String filePath,Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0,filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        }
    }


    public static int[] decodeImageSize(String inputPath){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(inputPath, options);
        return new int[]{options.outWidth,options.outHeight};
    }

    public static int[] decodeImageSize(@DrawableRes int res, Context mContext){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(),res,options);
        return new int[]{options.outWidth,options.outHeight};
    }

}
