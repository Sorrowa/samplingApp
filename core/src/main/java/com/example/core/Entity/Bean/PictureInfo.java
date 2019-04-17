package com.example.core.Entity.Bean;

/**
 * 记载上传的picture内容
 */
public class PictureInfo {
    private boolean isFromNet=false;//是否来自网络
    private String url=null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFromNet() {
        return isFromNet;
    }

    public void setFromNet(boolean fromNet) {
        isFromNet = fromNet;
    }
}
