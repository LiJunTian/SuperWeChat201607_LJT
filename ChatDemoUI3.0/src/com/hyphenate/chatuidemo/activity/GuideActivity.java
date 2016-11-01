package com.hyphenate.chatuidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.ui.BaseActivity;
import com.hyphenate.chatuidemo.ui.LoginActivity;
import com.hyphenate.chatuidemo.ui.RegisterActivity;

public class GuideActivity extends BaseActivity {
    Button mbtnLogin,mbtnsignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        setListener();
    }

    private void initView() {
        mbtnLogin = (Button) findViewById(R.id.btn_main_login);
        mbtnsignUp = (Button) findViewById(R.id.btn_main_signUp);
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
