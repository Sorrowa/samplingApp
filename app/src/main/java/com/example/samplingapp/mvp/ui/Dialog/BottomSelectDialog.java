package com.example.samplingapp.mvp.ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.util.Objects;

import androidx.annotation.NonNull;

public class BottomSelectDialog extends Dialog {
    private TextView open_from_camera;
    private TextView open_album;
    private TextView cancel;

    private SamplingFormActivity activity;

    public BottomSelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public BottomSelectDialog(@NonNull Context context, int themeResId, OnClickListener listener) {
        super(context, themeResId);
        activity = (SamplingFormActivity) context;

        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_bottom_select, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        cancel = contentView.findViewById(R.id.cancel);
        open_album = contentView.findViewById(R.id.open_album);
        open_from_camera = contentView.findViewById(R.id.open_from_camera);

        cancel.setOnClickListener(view -> dismiss());
        open_album.setOnClickListener(view -> {
            listener.select();
            dismiss();
        });
        open_from_camera.setOnClickListener(view -> {
            listener.shoot();
            dismiss();
        });

        super.setContentView(contentView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.BOTTOM);//设置显示在底部
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;//设置Dialog的宽度为屏幕宽度
        getWindow().setAttributes(layoutParams);
        setCanceledOnTouchOutside(true);
    }

    public interface OnClickListener {
        void shoot();

        void select();
    }
}
