package com.example.samplingapp.mvp.ui;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.BaseUtil;

import java.io.File;
import java.util.Objects;

public class VideoActivity extends BaseActivity {

    public static final int VIDEO_ACTIVITY=1001;

    private String path=null;

    @BindView(R.id.text_back)
    TextView text_back;
    @BindView(R.id.text_delete)
    TextView text_delete;
    @BindView(R.id.video_view)
    VideoView video_view;
    @BindView(R.id.image_play)
    ImageView image_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        path=getIntent().getStringExtra("path");
        if (path==null){
            showToast("出现未知错误");
            finish();
        }
        if (!SamplingFormActivity.canDelete){
            text_delete.setVisibility(View.GONE);
        }
        if (BaseUtil.isNetUrl(path)){
            initNetWorkData();
        }else{
            initView();
        }

        text_delete.setVisibility(View.GONE);
    }

    /**
     * 初始化网络数据
     */
    private void initNetWorkData() {
        text_back.setOnClickListener(view -> finish());
        showLoadingDialog();
        image_play.setVisibility(View.GONE);
        new Thread(() -> {
            File f = BaseUtil.saveUrlAs(BaseUtil.to_Chanese(path)
                            , Objects.requireNonNull(VideoActivity.this.getExternalCacheDir()).getAbsolutePath()
                            , "GET"
                            , "缓存视频");
            runOnUiThread(() -> {
                initVideo(f.getPath());
                dismissLoadingDialog();
                image_play.setVisibility(View.VISIBLE);
            });
        }).start();
    }

    private void initView() {
        text_back.setOnClickListener(view -> finish());
        text_delete.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.putExtra("path",path);
            setResult(VIDEO_ACTIVITY,intent);
            finish();
        });

        initVideo(path);
    }

    private void initVideo(String path){
        video_view.setVideoPath(path);
        MediaController controller=new MediaController(this);
        video_view.setMediaController(controller);
        video_view.requestFocus();
        //解决播放前黑屏
        //还没有比较ok的背景
        video_view.setOnPreparedListener(mediaPlayer -> mediaPlayer.setOnInfoListener((mp, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                video_view.setBackgroundColor(Color.TRANSPARENT);
//            video_view.start();
            return true;
        }));
        video_view.setOnClickListener(view -> {
            if (!video_view.isPlaying()){
                image_play.setVisibility(View.GONE);
                video_view.start();
            }
        });

        image_play.setOnClickListener(view -> {
            if (!video_view.isPlaying()){
                image_play.setVisibility(View.GONE);
                video_view.start();
            }
        });
    }
}
