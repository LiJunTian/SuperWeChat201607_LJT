package cn.ucai.superwechat.data;

import android.content.Context;

import com.hyphenate.chat.EMGroup;

import java.io.File;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.utils.L;
import cn.ucai.superwechat.utils.MD5;
import cn.ucai.superwechat.bean.Result;

/**NetDao方法，用于网络请求，下载数据
 * Created by Administrator on 2016/10/17 0017.
 */
public class NetDao {
    public static final String TAG = NetDao.class.getSimpleName();
    /**
     * 登录
     * @param context 上下文
     * @param userName 用户名
     * @param password 密码
     * @param listener 监听器
     */
    public static void Login(Context context, String userName, String password, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 注册
     * @param context 上下文
     * @param userName 用户名
     * @param nick     昵称
     * @param password 密码
     * @param listener 监听器
     */
    public static void Register(Context context,String userName,String nick,String password,OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.NICK,nick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }

    public static void unRegister(Context context, String userNme, OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UNREGISTER)
                .addParam(I.User.USER_NAME,userNme)
                .targetClass(Result.class)
                .execute(listener);
    }
    /**
     * 更新用户昵称
     * @param context 上下文
     * @param userName 用户名
     * @param nick    昵称
     * @param listener 监听器
     */
    public static void updateNick(Context context, String userName, String nick, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.NICK,nick)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 更新用户头像
     * @param context 上下文
     * @param userName 用户名
     * @param file    要上传的头像文件
     * @param listener 监听器
     */
    public static void updateAvatar(Context context,String userName,File file,OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,userName)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    /**
     * 根据用户名查找用户信息，包括用户头像信息
     * @param context 上下文
     * @param userName 用户名
     * @param listener 监听器
     */
    public static void syncUserInfo(Context context,String userName,OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,userName)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 根据用户名查找用户
     * @param context  上下文
     * @param userName 用户名
     * @param listener 监听
     */
    public static void searchUser(Context context, String userName, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,userName)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 根据用户名添加好友
     * @param context  上下文
     * @param userName 用户名
     * @param listener 监听
     */
    public static void addContact(Context context, String userName, String cUserName,OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CONTACT)
                .addParam(I.Contact.USER_NAME,userName)
                .addParam(I.Contact.CU_NAME,cUserName)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 根据用户名删除好友
     * @param context 上下文
     * @param userName 用户名
     * @param cUserName 添加的联系人
     * @param listener 监听
     */
    public static void deleteContact(Context context, String userName, String cUserName,OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CONTACT)
                .addParam(I.Contact.USER_NAME,userName)
                .addParam(I.Contact.CU_NAME,cUserName)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 下载所有好友联系人列表
     * @param context 上下文
     * @param listener 监听
     */
    public static void loadContact(Context context,OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        String userName = SuperWeChatHelper.getInstance().getCurrentUsernName();
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME,userName)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 新建群组(上传图片)
     * @param context 上下文
     * @param listener 监听
     */
    public static void CreateGroup(Context context, EMGroup emGroup,File file, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
                .addParam(I.Group.HX_ID,emGroup.getGroupId())
                .addParam(I.Group.NAME,emGroup.getGroupName())
                .addParam(I.Group.DESCRIPTION,emGroup.getDescription())
                .addParam(I.Group.OWNER,emGroup.getOwner())
                .addParam(I.Group.IS_PUBLIC,String.valueOf(emGroup.isPublic()))
                .addParam(I.Group.ALLOW_INVITES,String.valueOf(emGroup.isAllowInvites()))
                .targetClass(String.class)
                .addFile2(file)
                .post()
                .execute(listener);
    }

    /**
     * 新建群组(不上传图片)
     * @param context 上下文
     * @param listener 监听
     */
    public static void CreateGroup(Context context, EMGroup emGroup, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
                .addParam(I.Group.HX_ID,emGroup.getGroupId())
                .addParam(I.Group.NAME,emGroup.getGroupName())
                .addParam(I.Group.DESCRIPTION,emGroup.getDescription())
                .addParam(I.Group.OWNER,emGroup.getOwner())
                .addParam(I.Group.IS_PUBLIC,String.valueOf(emGroup.isPublic()))
                .addParam(I.Group.ALLOW_INVITES,String.valueOf(emGroup.isAllowInvites()))
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    /**
     * 批量添加组员
     * @param context 上下文
     * @param emGroup 组员
     * @param listener 监听
     */
    public static void addGroupMembers(Context context,EMGroup emGroup,OkHttpUtils.OnCompleteListener<String> listener){
        String memberArr = "";
        for(String m:emGroup.getMembers()){
            if(!m.equals(SuperWeChatHelper.getInstance().getCurrentUsernName())) {
                memberArr += m+",";
            }
        }
        memberArr = memberArr.substring(0,memberArr.length()-1);
        L.e("addGroupMembers","memberArr="+memberArr);
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_GROUP_MEMBERS)
                .addParam(I.Member.USER_NAME,memberArr)
                .addParam(I.Member.GROUP_HX_ID,emGroup.getGroupId())
                .targetClass(String.class)
                .execute(listener);
    }
}