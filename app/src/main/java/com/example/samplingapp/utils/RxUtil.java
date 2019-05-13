package com.example.samplingapp.utils;

import com.example.network.InternetUtil;
import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Base.BaseActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {


    /**
     * 在主线程，更新图片，或者视频
     * @param tlist
     * @param adapter
     * @param path
     * @param ac
     */
    public static void addPhoto(List<String> tlist
            , RecyclerView.Adapter adapter, String path, BaseActivity ac) {

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext(path);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        tlist.remove("1");
                        tlist.add(BaseUtil.removeLastChar(InternetUtil.SERVER_IP)+s);
                        adapter.notifyItemChanged(tlist.size()-1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
