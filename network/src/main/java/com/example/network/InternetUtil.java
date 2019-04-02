package com.example.network;

/**
 * 记录所有的连接信息
 */
public class InternetUtil {
    public static final String SERVER_IP="http://192.168.1.124:129/";

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
}
