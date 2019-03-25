package com.example.samplingapp.Activities;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.LoginPresenter;
import com.example.samplingapp.R;

import androidx.appcompat.app.AlertDialog;
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
    Boolean canYouSee=false;//默认不能显示密码

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter=new LoginPresenter();
        presenter.attachView(this);
        //提醒输入内容为空
    }


    @OnClick({R.id.login_button,R.id.keyword_see})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.login_button:
                showToast("点击了登录按钮");
                getLogin();
                break;
            case R.id.keyword_see:
                if (canYouSee){
                    //可以看到
                    keyText
                            .setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    keywordIfsee
                            .setImageDrawable(getDrawable(R.drawable.login_hide_keyword));
                }else{
                    keyText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    keywordIfsee
                            .setImageDrawable(getDrawable(R.drawable.login_see_keyword));
                }
                canYouSee=!canYouSee;
                break;
        }
    }

    /**
     * 登录
     * presenter调用之前进行内容审核
     */
    private void getLogin() {
        String account= "";
        String passWord="";
        if ((account= String.valueOf(accountText.getText())).equals("")
                ||(passWord= String.valueOf(keyText.getText())).equals("")){
            //如果有一项没有填写
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("注意！");
            builder.setMessage("账户或者密码不能为空！");
            builder.setCancelable(true);
            builder.show();
        }

        presenter.getLogin(new LoginPresenter.LoginLisenter() {
            @Override
            public void onSuccess(String res) {
                //成功回调
            }

            @Override
            public void onFail(String error) {
                //失败
            }
        });
    }

    //接触绑定内容
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
