package cn.ucai.superwechat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.ui.BaseActivity;
import cn.ucai.superwechat.utils.MFGT;

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
        switch(view.getId()){
            case R.id.btn_main_login:
                MFGT.gotoLoginActivity(this);
                break;
            case R.id.btn_main_signUp:
                MFGT.gotoRegisterActivity(this);
                break;
        }
    }
}
