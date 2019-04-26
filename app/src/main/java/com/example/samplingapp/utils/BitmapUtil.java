package com.example.samplingapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

import static android.os.Environment.getExternalStorageDirectory;

public class BitmapUtil {

    //图片保存路径 sdCard的路径
//	private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getPath()+
//			File.separator+"bwf"+File.separator+"cache_img"+File.separator;
    /**
     * 创建存图片的文件夹
     */
//	private static void createCacheDir(){
//		File file = new File(CACHE_PATH);
//		if(!file.exists()){//当前文件夹不存在则创建
//			file.mkdirs();
//		}
//	}

    /**
     * 准备文件夹，文件夹若不存在，则创建
     *
     * @param filePath 文件路径
     */
    public static void prepareFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 保存图片
     *
     * @param b
     * @param file
     */
    public static void saveBitmap(byte[] b, File file) {
        String path = String.valueOf(file);
        try {
            FileOutputStream fout = new FileOutputStream(path);
//            BufferedOutputStream bos = new BufferedOutputStream(fout);
            fout.write(b);
//            bos.flush();
//            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void copyFile(File fromFile, File toFile, Boolean rewrite) {

        if (!fromFile.exists()) {
            return;
        }

        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (fromFile.exists() && rewrite) {
            fromFile.delete();
        }
    }


    /**
     * 获得图片
     *
     * @param imgUrl
     * @return
     */
    public static Bitmap getBitmapFromSDCard(String imgUrl) {
        //图片的路径
//		String img_path =CACHE_PATH+getImg_FileName(imgUrl);
        return BitmapFactory.decodeFile(imgUrl);
    }
    /**
     * 使用图片的网络地址 获取图片的文件名
     */
//	private static String getImg_FileName(String imgUrl){
//		//先要加密去掉特殊字符
//		String fileName = MD5(imgUrl)+".jpeg";
//		return fileName;
//	}

    /**
     * MD5加密
     *
     * @param msg
     * @return
     */
    private static String MD5(String msg) {
        try {
            byte[] btinput = msg.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md_by = md.digest(btinput);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md_by.length; i++) {
                int val = ((int) md_by[i]) & 0xff;
                if (val < 16) {
                    sb.append("0");
                } else {
                    sb.append(Integer.toHexString(val));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 压缩  优化 图片
     * 优化图片大小 和质量
     *
     * @param bitmap
     * @param pixelW 要优化的宽度
     * @param pixelH 要优化的高度
     * @return
     */
    public static Bitmap storeImage(Bitmap bitmap, float pixelW, float pixelH) {
        //质量压缩
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 70, os);
        int options = 100;
        while (os.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于160kb,大于继续压缩
            os.reset();//重置baos即清空baos
            bitmap.compress(CompressFormat.JPEG, options, os);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 15;//每次都减少10
        }
        //大小压缩
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        //比例压缩
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float hh = pixelH;
        float ww = pixelW;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (w / ww);
        } else if (w < h && h > hh) {
            be = (int) (h / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap1 = BitmapFactory.decodeStream(is, null, newOpts);
        return bitmap1;
    }

    public static Bitmap getimage(Bitmap bitmap) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        //现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
        float hh = 1280f;//这里设置高度为1280f
        float ww = 720f;//这里设置宽度为720f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (bitmap.getWidth() / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (bitmap.getHeight() / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inJustDecodeBounds = false;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }


    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 70, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 80;
        while (baos.toByteArray().length / 1024 > 300 && options>1) {  //循环判断如果压缩后图片是否大于280kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 15;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static void zoomBitmap(Bitmap bmp, float size) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 85, out);
        float zoom = (float) Math.sqrt(size * 1024 / (float) out.toByteArray().length);
        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);
        Bitmap result = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        out.reset();
        result.compress(CompressFormat.JPEG, 85, out);
        while (out.toByteArray().length > size * 1024) {
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
            out.reset();
            result.compress(CompressFormat.JPEG, 85, out);
        }
    }

    /**
     * 设置水印图片在左上角
     *
     * @param
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskLeftTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark,
                dp2px(context, paddingLeft), dp2px(context, paddingTop));
    }

    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                                int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
        // 存储
        canvas.restore();
        return newb;
    }

    /**
     * 设置水印图片在右下角
     *
     * @param
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskRightBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到右上角
     *
     * @param
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskRightTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingTop) {
        return createWaterMaskBitmap(src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                dp2px(context, paddingTop));
    }

    /**
     * 设置水印图片到左下角
     *
     * @param
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, dp2px(context, paddingLeft),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到中间
     *
     * @param
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark,
                (src.getWidth() - watermark.getWidth()) / 2,
                (src.getHeight() - watermark.getHeight()) / 2);
    }

    /**
     * 给图片添加文字到左上角
     *
     * @param context
     * @param bitmap
     * @param text
     * @return
     */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text,
                                           int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到右下角
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param
     * @param
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
                                               int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到右上方
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String text,
                                            int size, int color, int paddingRight, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到左下方
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text,
                                              int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到中间
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String text,
                                          int size, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                (bitmap.getWidth() - bounds.width()) / 2,
                (bitmap.getHeight() + bounds.height()) / 2);
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param w
     * @param h
     * @return
     */
    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /*
     * 得到图片字节流 数组大小
     * */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    //保存图片带名称
    public static String saveImgWithName(Bitmap bitmap, String imgName, String fileName, Context mContext) {
        if (bitmap != null) {
            File appDir = new File(getExternalStorageDirectory() + "/Pgs/");
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            if (fileName != null) {
                appDir = new File(getExternalStorageDirectory() + "/Pgs/cache/" + fileName);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
            }
            File file = null;
            file = new File(appDir, imgName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                if (null != fos) {
                    bitmap.compress(CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                }
                return file.getAbsolutePath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    /**
     * 将bitmap转化为比特流
     * @param bitmap
     * @return
     */
    public static byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        return buffer.array();
    }


    /**将bitmap对象保存成图片到sd卡中*/
    public static void saveBitmapToSDCard(Bitmap bitmap, String path) {

        File file = new File(path);
        if(file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ((OutputStream)fileOutputStream));//设置PNG的话，透明区域不会变成黑色

            fileOutputStream.close();
            System.out.println("----------save success-------------------");
        }
        catch(Exception v0) {
            v0.printStackTrace();
        }

    }


}
