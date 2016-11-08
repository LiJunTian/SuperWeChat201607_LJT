package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.R;

public class AddFriendActivity extends BaseActivity {

    @BindView(R.id.register_and_login_title_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.title_send)
    ImageView titleSend;
    @BindView(R.id.tv_add_text)
    EditText tvAddText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitleText.setVisibility(View.VISIBLE);
        tvTitleText.setText(R.string.add_friend);
        titleSend.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.register_and_login_title_back, R.id.title_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_and_login_title_back:
                break;
            case R.id.title_send:
                break;
        }
    }
}
