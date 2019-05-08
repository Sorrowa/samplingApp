package com.example.samplingapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.GlideTool.RotateTransformation;

import java.io.ByteArrayOutputStream;

public class GlideUtil {

    //加载指定大小
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Glide.with(mContext)
                .load(path)
                .override(width, height)
                .skipMemoryCache(true)
                .into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLodingSize(Context mContext, String path,
                                               int width, int height,ImageView mImageView) {
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .override(width, height)
                .into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path,
                                               ImageView mImageView) {
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(mImageView);
    }

    //旋转角度图片
    public static void loadImageViewLodingRotate(Context mContext, String path,
                                           ImageView mImageView,float rot) {
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .transform(new RotateTransformation(rot))
                .into(mImageView);
    }

    //设置加载中以及加载失败图片圆形
    public static void loadRoundImageViewLoding(Context mContext, String path, ImageView mImageView
            , int loadingDrawable, int errorDrawable) {
        Glide.with(mContext)
                .load(path)
                .placeholder(loadingDrawable)
                .error(errorDrawable)
                .dontAnimate()
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mImageView);
    }

    //设置加载中以及加载失败圆角图片
    public static void loadImageViewYJ(Context mContext, String path, ImageView mImageView
            , int loadingDrawable) {
        Glide.with(mContext)
                .load(path)
                .error(loadingDrawable)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))//圆角半径)
                .into(mImageView);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }
    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
    }

    //加载bitmap
    public static void loadBitmapSize(Context mContext, Bitmap bitmap
            ,int width, int height,ImageView mImageView){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(mContext).load(bytes).into(mImageView);
    }

    /**
     * 加载第4000000=四秒的帧数作为封面
     *  url就是视频的地址
     */
    public static void loadCover(ImageView imageView, String url, Context context) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(200)
                                .centerCrop()
                                .error(R.drawable.placeholder)//可以忽略
                                .placeholder(R.drawable.placeholder)//可以忽略
                )
                .load(url)
                .into(imageView);
    }

}
