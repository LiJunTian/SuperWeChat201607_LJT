package cn.ucai.superwechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.ui.BaseActivity;
import cn.ucai.superwechat.ui.LoginActivity;
import cn.ucai.superwechat.ui.RegisterActivity;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.btn_main_login)
    Button mbtnLogin;
    @BindView(R.id.btn_main_signUp)
    Button mbtnsignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.ucai.superwechat.R.layout.activity_guide);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_main_login,R.id.btn_main_signUp})
    public void setListener(View view) {
        final Intent intent = new Intent();
        switch(view.getId()){
            case R.id.btn_main_login:
                intent.setClass(GuideActivity.this,LoginActivity.class);
                break;
            case R.id.btn_main_signUp:
                intent.setClass(GuideActivity.this,RegisterActivity.class);
                break;
        }
        startActivity(intent);
    }
}
