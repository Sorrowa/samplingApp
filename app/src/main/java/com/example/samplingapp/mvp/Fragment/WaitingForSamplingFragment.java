package com.example.samplingapp.mvp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.Entity.Data.ProjectData;
import com.example.samplingapp.Adapter.RecycleViewAdapters.SamplingTaskAdapter;
import com.example.samplingapp.Presenter.MainPresenter;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.BaseUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 待采样
 */
public class WaitingForSamplingFragment extends Fragment implements MainPresenter.ProjectListener{

    private static RecyclerView r;
    private EditText editText;
    private TextView srch;

    private static MainPresenter presenter;
    private List<ProjectData> datas=new ArrayList<>();
    private static SamplingTaskAdapter adapater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater
            , @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (getContext()!=null){
            View view = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.fragment_waitingsampling
                            , container, false);
            r=view.findViewById(R.id.unsampling_recyclerview);
            editText=view.findViewById(R.id.search_edit_unsampling);
            srch=view.findViewById(R.id.search_bt);
            initRecycleView();
            initOtherView();
            return view;
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        beginGetData(null);
        BaseUtil.softInput(presenter.getView());
    }

    /**
     * 初始化editText与搜索
     */
    private void initOtherView() {
        srch.setOnClickListener(view -> {
            if (presenter==null){
                showToast("初始化未完成");
            }
            Log.d("zzh","开始搜索,搜索内容为:"+editText.getText().toString());
            presenter.getUndoProject(editText.getText().toString(),this);
        });
    }

    /**
     * 获取网络数据
     */
    private void beginGetData(String keyword) {
        presenter.getUndoProject(keyword, this);
    }

    /**
     * 设置presenter
     *
     * @param presenter
     */
    public void setPresenter(MainPresenter presenter) {
        WaitingForSamplingFragment.presenter = presenter;
    }


    private void initRecycleView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        r.setLayoutManager(manager);
        adapater=new SamplingTaskAdapter(datas);
        r.setAdapter(adapater);
    }

    private void showToast(String text){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show());
        handler=null;
    }

    @Override
    public void onSuccess(List<ProjectData> data) {
        if(datas==null){
            datas=new ArrayList<>();
        }
        datas.clear();
        datas.addAll(data);
        Log.d("zzh","内容长度为:"+datas.size());
        showChange();
//        editText.clearFocus();
        BaseUtil.softInput(presenter.getView());
//        refreash();
    }

    /**
     * 刷新
     */
    private void refreash() {
        adapater=new SamplingTaskAdapter(datas);
        r.setAdapter(adapater);
    }

    private void showChange(){
        adapater.notifyDataSetChanged();
    }

    @Override
    public void onFail() {
        showToast("请检查网络");
    }
}
