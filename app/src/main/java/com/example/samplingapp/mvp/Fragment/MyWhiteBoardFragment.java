package com.example.samplingapp.mvp.Fragment;

import android.os.Bundle;

import com.yinghe.whiteboardlib.fragment.WhiteBoardFragment;

public class MyWhiteBoardFragment extends WhiteBoardFragment {
    private String path=null;

    public static MyWhiteBoardFragment newInstance() {

        Bundle args = new Bundle();

        MyWhiteBoardFragment fragment = new MyWhiteBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (path!=null){
//            setCurBackgroundByPath(path);
//        }

    }

    public void setPath(String path){
        this.path=path;
    }
}
