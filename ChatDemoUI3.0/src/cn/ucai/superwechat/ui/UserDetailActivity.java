package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.easeui.domain.User;
import cn.ucai.easeui.utils.EaseUserUtils;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.utils.MFGT;

public class UserDetailActivity extends BaseActivity {
    User user;
    @BindView(R.id.register_and_login_title_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.iv_detail_avatar)
    ImageView ivDetailAvatar;
    @BindView(R.id.tv_detail_nick)
    TextView tvDetailNick;
    @BindView(R.id.tv_detail_userName)
    TextView tvDetailUserName;
    @BindView(R.id.btn_detail_add)
    Button btnDetailAdd;
    @BindView(R.id.btn_detail_sendMsg)
    Button btnDetailSendMsg;
    @BindView(R.id.btn_detail_video)
    Button btnDetailVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        if (user == null) {
            MFGT.finish(this);
            return;
        }
        initView();
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitleText.setVisibility(View.VISIBLE);
        tvTitleText.setText(getString(R.string.userinfo_txt_profile));
        setUserInfo();
        isFriend();
    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(this, user.getMUserName(), ivDetailAvatar);
        EaseUserUtils.setAppUserNick(user.getMUserNick(), tvDetailNick);
        EaseUserUtils.setAppUserNameWithNo(user.getMUserName(), tvDetailUserName);
    }

    private void isFriend() {
        if (SuperWeChatHelper.getInstance().getAppContactList().containsKey(user.getMUserName())) {
            btnDetailSendMsg.setVisibility(View.VISIBLE);
            btnDetailVideo.setVisibility(View.VISIBLE);
        } else {
            btnDetailAdd.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.register_and_login_title_back, R.id.btn_detail_add, R.id.btn_detail_sendMsg, R.id.btn_detail_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_and_login_title_back:
                MFGT.finish(this);
                break;
            case R.id.btn_detail_add:
                MFGT.gotoAddFriendActivity(this,user.getMUserName());
                break;
            case R.id.btn_detail_sendMsg:
                MFGT.gotoChatActivity(this,user.getMUserName());
                break;
            case R.id.btn_detail_video:
                break;
        }
    }
}