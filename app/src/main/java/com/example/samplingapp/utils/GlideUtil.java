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
                .into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView
            ,int loadingDrawable,int errorDrawable) {
        Glide.with(mContext)
                .load(path)
                .placeholder(loadingDrawable)
                .error(errorDrawable)
                .dontAnimate()
                .into(mImageView);
    }

    //设置加载中以及加载失败图片圆形
    public static void loadRoundImageViewLoding(Context mContext, String path, ImageView mImageView
            ,int loadingDrawable,int errorDrawable) {
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
            ,int loadingDrawable) {
        Glide.with(mContext)
                .load(path)
                .error(loadingDrawable)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))//圆角半径)
                .into(mImageView);
    }
}
