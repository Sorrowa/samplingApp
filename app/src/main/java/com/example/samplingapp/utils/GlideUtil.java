package com.example.samplingapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.samplingapp.R;

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
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView
            , int loadingDrawable, int errorDrawable) {
        Glide.with(mContext)
                .load(path)
                .placeholder(loadingDrawable)
                .error(errorDrawable)
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

}
