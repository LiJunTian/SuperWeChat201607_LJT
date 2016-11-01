package cn.ucai.superwechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.ucai.superwechat.ui.BaseActivity;
import cn.ucai.superwechat.ui.LoginActivity;
import cn.ucai.superwechat.ui.RegisterActivity;

public class GuideActivity extends BaseActivity {
    Button mbtnLogin,mbtnsignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.ucai.superwechat.R.layout.activity_guide);
        initView();
        setListener();
    }

    private void initView() {
        mbtnLogin = (Button) findViewById(cn.ucai.superwechat.R.id.btn_main_login);
        mbtnsignUp = (Button) findViewById(cn.ucai.superwechat.R.id.btn_main_signUp);
    }

    private void setListener() {
        final Intent intent = new Intent();
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(GuideActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        mbtnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(GuideActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
