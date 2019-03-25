package com.example.samplingapp.Base;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BaseFragment extends Fragment implements BaseView {



    public static Fragment getInstance(Bundle bundle){
        Fragment fragment=new BaseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //另一个方法已经在fragment中重写了
    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

}
