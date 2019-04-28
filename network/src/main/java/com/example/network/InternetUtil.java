package com.example.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 记录所有的连接信息
 */
public class InternetUtil {
    public static String SERVER_IP="http://192.168.1.124:129/";

    /**
     * 登录接口
     * 参数：loginId 与 pwd
     * post
     * **/
    public static final String LOGIN="api/Login";

    /**
     * 获取任务列表
     * 参数：type（1 待采样，2 已采样），keyWord
     * post
     */
    public static final String PROJECT="api/GetProjects";
    public final static String UNDOPROJECTTYPE="1";
    public final static String HAVEDONEPROJECTTYPE="2";

    /**
     * 获取采样点位列表
     * 参数 : type,projectId,keyWord
     */
    public static final String POINTLIST="api/GetSampForms";

    /**
     * 获取对应于项目的点位列表
     * 参数：type，projectId
     */
    public static final String PROJECTPOINTLIST="api/GetProjectPoint";

    /**
     * 获取样品列表
     * 参数： projectPointId 项目ID
     */
    public static final String SAMPLINGSTATUSLIST="api/GetSampByPoint";

    /**
     * 获取点位列表
     * 参数：projectId
     */
    public static final String GETPOINTLIST="api/PointList";

    /**
     * 保存表单
     */
    public static final String SAVEFORM="api/SaveForm";

    /**
     * 文件上传接口
     */
    public static final String UOLOADFILE="api/UploadFile";

    /**
     * 删除表单的接口
     */
    public static final String DELETEFORM="api/DelSampForm";

    /**
     * 获得方法列表
     */
    public static final String GETMETHODLIST="api/GetMethodList";

    /**
     * 获取表单信息详细
     * 传递formId
     */
    public static final String GETFORMDETAIL="api/GetFormDetail";

    /**
     * 获取点位统计信息
     */
    public static final String GETPOINTCOUNT="api/GetPointCount";


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
}
