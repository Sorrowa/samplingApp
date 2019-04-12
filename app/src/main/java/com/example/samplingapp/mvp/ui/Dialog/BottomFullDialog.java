package com.example.samplingapp.mvp.ui.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.samplingapp.R;
import com.weigan.loopview.LoopView;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * 底部显示的dialog
 */
public class BottomFullDialog extends Dialog {

    private LoopView loopView;
    private TextView cancel;
    private TextView confirm;

    public BottomFullDialog(@NonNull Context context) {
        super(context);
    }
    @SuppressLint("ClickableViewAccessibility")
    public BottomFullDialog(@NonNull Context context
            , int themeResId
            , List<String> items
            , onClickListener listener
            , int curItem) {
        super(context, themeResId);
        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_bottom_show, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        loopView=contentView.findViewById(R.id.loopView);
        cancel=contentView.findViewById(R.id.cancel);
        confirm=contentView.findViewById(R.id.confirm);
        loopView.setItems(items);
        loopView.setCurrentPosition(curItem);
        cancel.setOnClickListener(view -> dismiss());
        confirm.setOnClickListener(view ->
        {
            listener.doConfirm(loopView.getSelectedItem());
            dismiss();
        });

        contentView.setOnTouchListener((v, event) -> {
            BottomFullDialog.this.dismiss();
            return true;
        });
        super.setContentView(contentView);
    }

    protected BottomFullDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.BOTTOM);//设置显示在底部
        WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;//设置Dialog的宽度为屏幕宽度
        getWindow().setAttributes(layoutParams);
    }

    public interface onClickListener{
        void doConfirm(int item_num);
    }
}
