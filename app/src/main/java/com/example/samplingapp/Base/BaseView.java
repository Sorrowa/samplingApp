package com.example.samplingapp.Base;

import android.content.Context;

public interface BaseView {
    /**
     * 显示提示
     * @param msg
     */
    void showToast(String msg);

    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();
}
