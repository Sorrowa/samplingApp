package com.example.samplingapp;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        saveUrlAs("http://192.168.1.124:129/Upload/20190416/PictureSelector.temp-副本(6).jpg"
                , "/Users/zhangzihao/Documents/test"
                , "GET"
                , "test.png");
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
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(true);
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

            byte[] buf = new byte[Integer.MAX_VALUE/2];
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
        return s_chin;
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

}