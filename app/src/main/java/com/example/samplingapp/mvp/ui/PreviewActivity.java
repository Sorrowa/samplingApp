package com.example.samplingapp.mvp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.GlideUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * 预览图片
 */
public class PreviewActivity extends BaseActivity {

    public static final int PREVIEWACTIVITY=1000;

    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.text_back)
    TextView textBack;
    @BindView(R.id.text_delete)
    TextView textDelete;
    private String path;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        path=getIntent().getStringExtra("path");//文件路径
        type=getIntent().getStringExtra("type");//删除照片的种类
        textDelete.setVisibility(View.GONE);
        if (!SamplingFormActivity.canDelete){
            textDelete.setVisibility(View.GONE);
        }
        initView();

    }

    private void initView() {
        textBack.setOnClickListener(view -> doBack());
        textDelete.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.putExtra("path",path);
            intent.putExtra("type",type);
            setResult(PREVIEWACTIVITY,intent);
            finish();
        });

//        File file = new File(path);
//        if(file.exists()){
//            Bitmap bm = BitmapFactory.decodeFile(path);
//            photoView.setImageBitmap(bm);
//        }
        GlideUtil.loadImageViewLoding(this
                ,path
                ,photoView
        );
    }


    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack() {
        finish();
    }
}
