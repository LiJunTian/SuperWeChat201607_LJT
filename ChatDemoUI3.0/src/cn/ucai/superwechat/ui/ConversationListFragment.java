package cn.ucai.superwechat.ui;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.easemob.redpacketui.RedPacketConstant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.db.InviteMessgeDao;
import cn.ucai.easeui.model.EaseAtMessageHelper;
import cn.ucai.easeui.ui.EaseConversationListFragment;

import com.hyphenate.util.NetUtils;

import cn.ucai.easeui.widget.EaseConversationList;

public class ConversationListFragment extends EaseConversationListFragment {

    private TextView errorText;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), cn.ucai.superwechat.R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(cn.ucai.superwechat.R.id.tv_connect_errormsg);
    }
    
    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), cn.ucai.superwechat.R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType() == EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }
                        
                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示
        conversationListView.setConversationListHelper(new EaseConversationList.EaseConversationListHelper() {
            @Override
            public String onSetItemSecondaryText(EMMessage lastMessage) {
                if (lastMessage.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
                    String sendNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_SENDER_NAME, "");
                    String receiveNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_NAME, "");
                    String msg;
                    if (lastMessage.direct() == EMMessage.Direct.RECEIVE) {
                        msg = String.format(getResources().getString(cn.ucai.superwechat.R.string.msg_someone_take_red_packet), receiveNick);
                    } else {
                        if (sendNick.equals(receiveNick)) {
                            msg = getResources().getString(cn.ucai.superwechat.R.string.msg_take_red_packet);
                        } else {
                            msg = String.format(getResources().getString(cn.ucai.superwechat.R.string.msg_take_someone_red_packet), sendNick);
                        }
                    }
                    return msg;
                }
                return null;
            }
        });
        super.setUpView();
        //end of red packet code
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
         errorText.setText(cn.ucai.superwechat.R.string.can_not_connect_chat_server_connection);
        } else {
          errorText.setText(cn.ucai.superwechat.R.string.the_current_network);
        }
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(cn.ucai.superwechat.R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == cn.ucai.superwechat.R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == cn.ucai.superwechat.R.id.delete_conversation) {
            deleteMessage = false;
        }else{
            return false;
        }
    	EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
    	if (tobeDeleteCons == null) {
    	    return true;
    	}
        if(tobeDeleteCons.getType() == EMConversationType.GroupChat){
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.getUserName());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

}
