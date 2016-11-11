package cn.ucai.superwechat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.easeui.domain.EaseUser;
import cn.ucai.easeui.domain.User;
import cn.ucai.easeui.utils.EaseUserUtils;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.data.NetDao;
import cn.ucai.superwechat.data.OkHttpUtils;
import cn.ucai.superwechat.utils.MFGT;
import cn.ucai.superwechat.utils.ResultUtils;

public class UserDetailActivity extends BaseActivity {
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

    String username = null;
    User user = null;
    boolean isFriend = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        username = getIntent().getStringExtra(I.User.USER_NAME);
        if (username == null) {
            MFGT.finish(this);
            return;
        }
        initView();
        user = SuperWeChatHelper.getInstance().getAppContactList().get(username);
        if(user==null){
            isFriend = false;
        }else{
            setUserInfo();
            isFriend = true;
        }
        isFriend(isFriend);
        syncUserInfo();
    }

    private void syncFail(){
        MFGT.finish(this);
        return;
    }
    private void syncUserInfo() {
        NetDao.syncUserInfo(this, username, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if(s!=null){
                    Result result = ResultUtils.getResultFromJson(s,User.class);
                    if(result!=null&&result.isRetMsg()){
                        user = (User) result.getRetData();
                        if(user!=null){
                            setUserInfo();
                            if(isFriend){
                                SuperWeChatHelper.getInstance().saveAppContact(user);
                            }
                        }else{
                            syncFail();
                        }
                    }else{
                        syncFail();
                    }
                }else{
                    syncFail();
                }
            }

            @Override
            public void onError(String error) {
                syncFail();
            }
        });
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitleText.setVisibility(View.VISIBLE);
        tvTitleText.setText(getString(R.string.userinfo_txt_profile));
    }

    private void setUserInfo() {
        EaseUserUtils.setAppUserAvatar(this, user.getMUserName(), ivDetailAvatar);
        EaseUserUtils.setAppUserNick(user.getMUserNick(), tvDetailNick);
        EaseUserUtils.setAppUserNameWithNo(user.getMUserName(), tvDetailUserName);
    }

    private void isFriend(boolean isFriend) {
        if (isFriend) {
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
                //好友详情界面的视频聊天
                if (!EMClient.getInstance().isConnected())
                    Toast.makeText(this, R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
                else {
                    startActivity(new Intent(this, VideoCallActivity.class).putExtra("username", user.getMUserName())
                            .putExtra("isComingCall", false));
                }
                break;
        }
    }
}