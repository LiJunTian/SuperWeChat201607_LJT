/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.easemob.redpacketui.utils.RedPacketUtil;
import cn.ucai.easeui.utils.EaseUserUtils;
import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.utils.MFGT;

/**
 * settings screen
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class ProfileFragment extends Fragment {

    @BindView(R.id.iv_personal_avatar)
    ImageView ivPersonalAvatar;
    @BindView(R.id.tv_personal_UserName)
    TextView tvPersonalUserName;
    @BindView(R.id.tv_personal_weiXinNum)
    TextView tvPersonalWeiXinNum;
    @BindView(R.id.tv_personal_image)
    TextView tvPersonalImage;
    @BindView(R.id.tv_personal_collect)
    TextView tvPersonalCollect;
    @BindView(R.id.tv_personal_wallet)
    TextView tvPersonalWallet;
    @BindView(R.id.tv_personal_smail)
    TextView tvPersonalSmail;
    @BindView(R.id.tv_personal_setting)
    TextView tvPersonalSetting;
    @BindView(R.id.layout_personal_view)
    RelativeLayout layoutPersonalView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalcenter, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        setUserInfo();
    }

    private void setUserInfo() {
        EaseUserUtils.setCurrentAppUserAvatar(getActivity(), ivPersonalAvatar);
        EaseUserUtils.setCurrentAppUserNick(tvPersonalUserName);
        EaseUserUtils.setCurrentAppUserNameWithNo(tvPersonalWeiXinNum);
    }

    @OnClick({R.id.layout_personal_view,R.id.tv_personal_wallet,R.id.tv_personal_setting})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_personal_view:
                MFGT.gotoUserProfileActivity(getContext());
                break;
            //red packet code : 进入零钱页面
            case R.id.tv_personal_wallet:
                RedPacketUtil.startChangeActivity(getActivity());
                break;
            //end of red packet code
            case R.id.tv_personal_setting:
                MFGT.gotoSettingsActivity(getContext());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((MainActivity) getActivity()).isConflict) {
            outState.putBoolean("isConflict", true);
        } else if (((MainActivity) getActivity()).getCurrentAccountRemoved()) {
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }
}
