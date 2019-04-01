package com.example.samplingapp.mvp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samplingapp.Adapter.RecycleViewAdapters.SamplingTaskAdapter;
import com.example.samplingapp.Presenter.MainPresenter;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.BaseUtil;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 待采样
 */
public class WaitingForSamplingFragment extends Fragment {


    @BindView(R.id.unsampling_recyclerview)
    RecyclerView r;

    private MainPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater
            , @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater
                .from(getContext())
                .inflate(R.layout.fragment_waitingsampling
                ,container,false);
        ButterKnife.bind(view);
        initRecycleView();
        return view;
    }

    /**
     * 设置presenter
     * @param presenter
     */
    public void setPresenter(MainPresenter presenter){
        boolean isNetOK= BaseUtil.isNetworkConnected(getContext());
        if (isNetOK){
            this.presenter=presenter;
        }else{
            //没有网络
            //todo:显示取代RecycleView的内容
            BaseUtil.showDialog(getContext(),"请检查网络连接");
        }
    }

    private void initRecycleView() {
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        r.setLayoutManager(manager);

    }
}
