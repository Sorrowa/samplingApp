package com.example.samplingapp.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.network.InternetUtil;
import com.example.network.RetrofitHelper;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.MainActivity;
import com.example.samplingapp.Presenter.LoginPresenter;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.BaseUtil;
import com.example.samplingapp.utils.ShareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.account_input)
    EditText accountText;
    @BindView(R.id.keyword_input)
    EditText keyText;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.keyword_see)
    ImageView keywordIfsee;//是否显示密码
    Boolean canYouSee = false;//默认不能显示密码
    @BindView(R.id.server_input)
    EditText serverIP;

    private LoginPresenter presenter;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter();
        presenter.attachView(this);
        //如果已经登录，那么直接跳转到下一活动
        if (checkLoadState()) {
            Intent intent = new Intent(LoginActivity.this
                    , MainActivity.class);
            startActivity(intent);
            finish();
        }
        initEdit();
    }

    private void initEdit() {

        accountText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                keyText.requestFocus();
                return true;
            }
            return false;
        });

        keyText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                keyText.clearFocus();
                return true;
            }
            return false;
        });
    }


    @OnClick({R.id.login_button, R.id.keyword_see})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                getLogin();
                break;
            case R.id.keyword_see:
                if (canYouSee) {
                    //可以看到
                    keyText
                            .setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
                                    | InputType.TYPE_CLASS_TEXT);
                    keywordIfsee
                            .setImageDrawable(getDrawable(R.drawable.login_hide_keyword));
                } else {
                    keyText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    keywordIfsee
                            .setImageDrawable(getDrawable(R.drawable.login_see_keyword));
                }
                canYouSee = !canYouSee;
                break;
        }
    }

    /**
     * 登录
     * presenter调用之前进行内容审核
     */
    private void getLogin() {

        if (!serverIP.getText().toString().equals("http://")) {
            //存储Ip，防止之后重新退出app导致数据重置
            InternetUtil.SERVER_IP = serverIP.getText().toString() + "/";
            //重置Retrofit
            RetrofitHelper.reset();
            ShareUtil.saveIp(app, InternetUtil.SERVER_IP);
        } else {
            //存储Ip，防止之后重新退出app导致数据重置
            InternetUtil.SERVER_IP = "http://192.168.1.124:129/";
            //重置Retrofit
            RetrofitHelper.reset();
            ShareUtil.saveIp(app, InternetUtil.SERVER_IP);
        }
        if (!BaseUtil.isNetworkConnected(this)) {
            showToast("请检查您的网络!");
            return;
        }

        String account;
        String passWord;
        if ((account = String.valueOf(accountText.getText())).equals("")
                || (passWord = String.valueOf(keyText.getText())).equals("")) {
            //如果有一项没有填写
            BaseUtil.showDialog(this, "密码或者用户名没有输入");
            return;
        }
        //显示一个dialog
        showDialog();

        presenter.getLogin(account, passWord, new LoginPresenter.LoginLisenter() {
            @Override
            public void onSuccess(String res, Boolean success) {
                //成功回调
                if (success) {
                    showToast(res);
                    Intent intent = new Intent(LoginActivity.this
                            , MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast(res);
                }
                dismissDialog();
            }

            @Override
            public void onFail(String error) {
                //失败
                showToast(error);
                dismissDialog();
            }
        });
    }

    private void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void showDialog() {
        View layout = getLayoutInflater().inflate(R.layout.dialog_loading, null);
        dialog = new Dialog(this);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(dialog -> {
        });
        dialog.show();
    }

    /**
     * 检测登录状态
     *
     * @return 是否已经登录
     */
    public Boolean checkLoadState() {
        return ShareUtil.getLoadState((App) getApplication());
    }

    //接触绑定内容
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
