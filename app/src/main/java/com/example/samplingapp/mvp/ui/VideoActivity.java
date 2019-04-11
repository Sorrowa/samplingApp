package com.example.samplingapp.mvp.ui;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;

public class VideoActivity extends BaseActivity {

    public static final int VIDEO_ACTIVITY=1001;

    private String path=null;

    @BindView(R.id.text_back)
    TextView text_back;
    @BindView(R.id.text_delete)
    TextView text_delete;
    @BindView(R.id.video_view)
    VideoView video_view;

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
        initView();
    }

    private void initView() {
        text_back.setOnClickListener(view -> finish());
        text_delete.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.putExtra("path",path);
            setResult(VIDEO_ACTIVITY,intent);
            finish();
        });

        video_view.setVideoPath(path);
        MediaController controller=new MediaController(this);
        video_view.setMediaController(controller);
        video_view.requestFocus();
        //解决播放前黑屏
        //还没有比较ok的背景
        video_view.setOnPreparedListener(mediaPlayer -> mediaPlayer.setOnInfoListener((mp, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                video_view.setBackgroundColor(Color.TRANSPARENT);
            video_view.start();
            return true;
        }));
    }
}
