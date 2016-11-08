package cn.ucai.superwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.easeui.domain.User;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.ui.AddContactActivity;
import cn.ucai.superwechat.ui.LoginActivity;
import cn.ucai.superwechat.ui.MainActivity;
import cn.ucai.superwechat.ui.RegisterActivity;
import cn.ucai.superwechat.ui.SettingsActivity;
import cn.ucai.superwechat.ui.UserDetailActivity;
import cn.ucai.superwechat.ui.UserProfileActivity;


/** Activity跳转方法
 * Created by Administrator on 2016/10/14 0014.
 */
public class MFGT {//从哪来到哪去move from go to
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context){
        startActivity(context,MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoLoginActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivity(context,intent);
    }

    public static void gotoRegisterActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        startActivity(context,intent);
    }

    public static void gotoSettingsActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context,SettingsActivity.class);
        startActivity(context,intent);
    }

    public static void gotoUserProfileActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,UserProfileActivity.class);
        startActivity(context,intent);
    }

    public static void gotoAddFriend(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,AddContactActivity.class);
        startActivity(context,intent);
    }

    public static void gotoUserDetailActivity(Context context,User user){
        Intent intent = new Intent();
        intent.setClass(context,UserDetailActivity.class);
        intent.putExtra(I.User.USER_NAME,user);
        startActivity(context,intent);
    }
}
