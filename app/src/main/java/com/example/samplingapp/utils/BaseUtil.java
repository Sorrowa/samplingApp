package com.example.samplingapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;

/**
 * 各种工具类
 */
public class BaseUtil {
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showDialog(Context context, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("注意");
        builder.setMessage(text);
        builder.setCancelable(true);
        builder.show();
    }

    /**
     * 判断网络是否正常
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void softInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 计算两个坐标之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离M为单位
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    /**
     * 角度转换为弧度
     *
     * @param d
     * @return 弧度
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
//        origin.recycle();
        return newBM;
    }


    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */

    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        try {
            media.setDataSource(path);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return media.getFrameAtTime();
    }

    /**
     * 保留小数点后一位
     *
     * @return
     */
    public static String FomatNumber(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(num);
    }
    /**
     * 保留小数点后二位
     *
     * @return
     */
    public static String FomatNumber2(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(num);
    }


    /**
     * 去掉最后一个字符
     *
     * @param s
     * @return
     */
    public static String removeLastChar(String s) {
        String res = s;
        res = res.substring(0, s.length() - 1);
        return res;
    }


    /**
     * @param filePath 文件将要保存的目录
     * @param method   请求方法，包括POST和GET
     * @param url      请求的路径
     * @return
     * @功能 下载临时素材接口
     */
    public static File saveUrlAs(String url, String filePath, String method, String fileName) {
        //System.out.println("fileName---->"+filePath);
        //创建不同的文件夹目录
        File dirfile = new File(filePath);
        //判断文件夹是否存在
        if (!dirfile.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            dirfile.mkdirs();
        }
        File file = new File(filePath, fileName);

        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            // 建立链接
            URL httpUrl = new URL(to_Chanese(url));
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
//            conn.setRequestMethod(method);
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            // post方式不能使用缓存
//            conn.setUseCaches(true);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
//            if (!filePath.endsWith("/")) {
//
//                filePath += "/";
//
//            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }

        return file;

    }

    //url转码
    public static String to_Chanese(String str) {
        String s_chin = "";
        String s_ch_one;
//        str=str.replace("%28","(").replace("%29",")");
        for (int i = 0; i < str.length(); i++) {
            s_ch_one = str.substring(i, i + 1);
            if (vd(s_ch_one)) {
                try {
                    s_chin = s_chin + URLEncoder.encode(s_ch_one, "utf8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                s_chin = s_chin + s_ch_one;
            }
        }
        return s_chin.replace("%28","(").replace("%29",")");
    }

    //判断是否有汉字
    public static boolean vd(String str) {
        byte[] bytes = str.getBytes();
        if (bytes.length > 1) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * 复制单个文件
     *
     * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath$Name String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     *         <code>false</code> otherwise
     */
    public static boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                Log.e("--Method--", "copyFile:  oldFile not exist.");
                return false;
            } else if (!oldFile.isFile()) {
                Log.e("--Method--", "copyFile:  oldFile not file.");
                return false;
            } else if (!oldFile.canRead()) {
                Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return false;
            }

            /* 如果不需要打log，可以使用下面的语句
            if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
                return false;
            }
            */

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 统一bitmap尺寸
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 去掉日期的时分秒
     * @param oldDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeRemoveSFM(String oldDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=sdf.parse(oldDate);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断url是本地还是网络
     * @param url
     * @return
     */
    public static boolean isNetUrl(String url) {
        boolean reault = false;
        if (url != null) {
            if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("rtsp") || url.toLowerCase().startsWith("mms")) {
                reault = true;
            }
        }
        return reault;
    }


    //从头开始删除字符的方法
    public static String TruncateHeadString(String origin, int count) {
        if (origin == null || origin.length() < count) {
            return null;
        }
        char[] arr = origin.toCharArray();
        char[] ret = new char[arr.length - count];
        for (int i = 0; i < ret.length; i ++) {
            ret[i] = arr[i + count];
        }

        return String.copyValueOf(ret);
    }


    /**
     * 根据网络地址获取第一帧图片
     * @param videoUrl
     * @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try
        {
            if (videoUrl.startsWith("http://")
                    || videoUrl.startsWith("https://")
                    || videoUrl.startsWith("widevine://"))
            {
                retriever.setDataSource(videoUrl, new Hashtable<String, String>());
            }
            else
            {
                retriever.setDataSource(videoUrl);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        }
        catch (IllegalArgumentException ex)
        {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        }
        catch (RuntimeException ex)
        {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                retriever.release();
            }
            catch (RuntimeException ex)
            {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null)
        {
            return null;
        }

        return bitmap;
    }

    /**
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        String one = str.trim();
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(one).matches();
    }


    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     * @param videoPath 视频的路径
     * @param width 指定输出视频缩略图的宽度
     * @param height 指定输出视频缩略图的高度度
     * @param kind 参照MediaStore.Images(Video).Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        if(bitmap!= null){
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        }
        return bitmap;
    }

}
