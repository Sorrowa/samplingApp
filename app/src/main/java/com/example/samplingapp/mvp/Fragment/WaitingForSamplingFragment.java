package com.example.samplingapp.mvp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
    private static SamplingTaskAdapter adapter;

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
        presenter.getView().showLoadingDialog();
    }

    /**
     * 初始化editText与搜索
     */
    private void initOtherView() {
        srch.setOnClickListener(view -> {
            if (presenter==null){
                showToast("初始化未完成");
            }
            editText.clearFocus();
            presenter.getUndoProject(editText.getText().toString(),this);
        });
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                editText.clearFocus();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    InputMethodManager imm = null;
                    imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }else{
                    InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(editText.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
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
    public static void setPresenter(MainPresenter presenter) {
        WaitingForSamplingFragment.presenter = presenter;
    }


    private void initRecycleView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        r.setLayoutManager(manager);
        adapter=new SamplingTaskAdapter(datas);
        r.setAdapter(adapter);
    }

    private void showToast(String text){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show());
        handler=null;
    }

    @Override
    public void onSuccess(List<ProjectData> data) {
        presenter.getView().handleRunnable(() -> {
            adapter.setDatas(data);
            BaseUtil.softInput(presenter.getView());
            showChange();
            presenter.getView().dismissLoadingDialog();
        });
        BaseUtil.softInput(presenter.getView());
    }

    private void showChange(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail() {
        showToast("请检查网络");
    }
}
