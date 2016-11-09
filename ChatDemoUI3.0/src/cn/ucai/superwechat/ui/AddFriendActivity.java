package cn.ucai.superwechat.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.easeui.utils.EaseUserUtils;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.utils.MFGT;

public class AddFriendActivity extends BaseActivity {

    @BindView(R.id.register_and_login_title_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.title_send)
    Button titleSend;
    @BindView(R.id.tv_add_text)
    EditText tvAddText;
    private ProgressDialog progressDialog;
    String username;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        username = getIntent().getStringExtra(I.User.USER_NAME);
        if(username==null){
            MFGT.finish(this);
        }
        initView();
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitleText.setVisibility(View.VISIBLE);
        tvTitleText.setText(R.string.add_friend);
        titleSend.setVisibility(View.VISIBLE);
        msg = getString(R.string.addcontact_send_msg_prefix)
                    + EaseUserUtils.getAppCurrentUserInfo().getMUserNick();
        tvAddText.setText(msg);

    }

    @OnClick({R.id.register_and_login_title_back, R.id.title_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_and_login_title_back:
                MFGT.finish(this);
                break;
            case R.id.title_send:
                sendAddRequest();
                break;
        }
    }

    private void sendAddRequest() {
        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.addcontact_search);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(username,msg);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                            MFGT.finish(AddFriendActivity.this);
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                            MFGT.finish(AddFriendActivity.this);
                        }
                    });
                }
            }
        }).start();
    }
}
