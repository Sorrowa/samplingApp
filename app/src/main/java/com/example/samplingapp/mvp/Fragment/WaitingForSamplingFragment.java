package com.example.samplingapp.mvp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samplingapp.Base.BaseFragment;
import com.example.samplingapp.R;

import androidx.annotation.Nullable;

/**
 * 待采样
 */
public class WaitingForSamplingFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater
            , @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_waitingsampling
                ,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
