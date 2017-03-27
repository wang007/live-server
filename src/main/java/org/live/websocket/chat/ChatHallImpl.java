package org.live.websocket.chat;

import org.live.common.utils.JsonUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 聊天大厅的实现
 * Created by Mr.wang on 2017/3/17.
 */
public class ChatHallImpl implements ChatHall {

    /**
     *  直播间的列表
     *
     *  key是直播间号
     */
    private ConcurrentHashMap<String, ChatRoom> chatRoomMap ;

    public ChatHallImpl() {
        chatRoomMap = new ConcurrentHashMap<>() ;
    }

    @Override
    public ChatRoom generateChatRoom(String chatRoomNum, WebSocketSession session) {
        return generateChatRoom(chatRoomNum, chatRoomNum, session) ;
    }

    @Override
    public ChatRoom generateChatRoom(String chatRoomNum, String anchorAccount,WebSocketSession session) {
        if(chatRoomMap.containsKey(chatRoomNum)) {
            ChatRoom chatRoom = chatRoomMap.get(chatRoomNum);
            if (chatRoom != null) return chatRoom.resetChatRoom(session) ;
        }
        ChatRoom newChatRoom = new ChatRoom(chatRoomNum, anchorAccount, session) ;
        chatRoomMap.put(chatRoomNum,newChatRoom) ;
        return newChatRoom ;
    }

    @Override
    public void dissolveChatRoom(ChatRoom chatRoom) {
        String chatRoomNum = chatRoom.getChatRoomNum() ;
        Message message = new Message() ;
        message.setFromChatRoomNum(chatRoomNum) ;
        message.setDestination(chatRoomNum) ;
        message.setNickname(ChatConstants.SYSTEM_NAME) ;
        message.setAccount(ChatConstants.SYSTEM_NUM) ;
        message.setMessageType(MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE) ; //信息事件
        dissolveChatRoom(chatRoom, message) ;

    }

    @Override
    public void dissolveChatRoom(ChatRoom chatRoom, Message message) {
        chatRoom.dissolveChatRoom(message) ;
        chatRoomMap.remove(chatRoom.getChatRoomNum()) ;   //从聊天室中删除这个直播间
    }

    @Override
    public ChatRoom getChatRoom(String chatRoomNum) {
        return chatRoomMap.get(chatRoomNum) ;
    }

    @Override
    public void dispatchMessageToChatRoom(String chatRoomNum, Message message) {
        int messageType = message.getMessageType() ;    //消息事件类型
        ChatRoom chatRoom = getChatRoom(chatRoomNum) ;  //获取直播间
        if(chatRoom == null) return ;
        switch (messageType) {
            case MessageType.SEND_TO_CHATROOM_MESSAGE_TYPE: {   //发送信息至直播间
                chatRoom.sendMessageToCurrentChatRoom(message) ;
                break ;
            }

            case MessageType.SHUTUP_USER_MESSAGE_TYPE: {    //主播禁言用户

            }

            case MessageType.RELIEVE_SHUTUP_USER_MESSAGE_TYPE: {    //主播解除禁言
                chatRoom.sendMessageToCurrentChatRoom(message) ;
                break ;
            }

            case MessageType.SEND_TO_USER_MESSAGE_TYPE: {   //发送信息至直播间的某个用户
                chatRoom.sendMessageToUser(message) ;
                break ;
            }

            case MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE: {   //主播离开，直播结束
                dissolveChatRoom(chatRoom, message) ;
                break ;
            }

            case MessageType.USER_ENTER_CHATROOM_MESSAGE_TYPE: {    //有用户进入直播间
                //chatRoom.sendMessageToCurrentChatRoom(message) ;
                //break ;
            }

            case MessageType.USER_EXIT_CHATROOM_MESSAGE_TYPE: {     //有用户离开直播间
                chatRoom.sendMessageToCurrentChatRoom(message) ;
                break ;
            }
        }

    }

    @Override
    public void dispatchMessageToChatRoom(String chatRoomNum, int messageType) {

        Message message = new Message() ;
        message.setFromChatRoomNum(chatRoomNum) ;
        message.setDestination(chatRoomNum) ;
        message.setNickname(ChatConstants.SYSTEM_NAME) ;
        message.setAccount(ChatConstants.SYSTEM_NUM) ;

        ChatRoom chatRoom = getChatRoom(chatRoomNum) ;  //获取直播间
        //TODO 考虑是否要判断chatRoom为空
        switch (messageType) {
            case MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE: {   //主播离开，直播结束
                message.setMessageType(MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE) ;
                dissolveChatRoom(chatRoom, message) ;
                break ;
            }

            case MessageType.USER_ENTER_CHATROOM_MESSAGE_TYPE: {    //有用户进入直播间
                message.setMessageType(MessageType.USER_ENTER_CHATROOM_MESSAGE_TYPE) ;
                message.setContent(chatRoom.getOnlineCount()+"") ;  //用户数量
                chatRoom.sendMessageToCurrentChatRoom(message) ;
                break ;
            }

            case MessageType.USER_EXIT_CHATROOM_MESSAGE_TYPE: {     //有用户离开直播间
                message.setMessageType(MessageType.USER_EXIT_CHATROOM_MESSAGE_TYPE) ;
                message.setContent(chatRoom.getOnlineCount()+"") ;  //用户数量
                chatRoom.sendMessageToCurrentChatRoom(message) ;
                break ;
            }

            case MessageType.SHUTUP_USER_MESSAGE_TYPE: {    //主播禁言用户
                message.setMessageType(MessageType.SHUTUP_USER_MESSAGE_TYPE) ;
                chatRoom.sendMessageToUser(message) ;
                break ;
            }

            case MessageType.RELIEVE_SHUTUP_USER_MESSAGE_TYPE: {    //主播解除禁言
                message.setMessageType(MessageType.RELIEVE_SHUTUP_USER_MESSAGE_TYPE) ;
                chatRoom.sendMessageToUser(message) ;
                break ;
            }
        }
    }

    @Override
    public int getChatRoomOnLineCount(String chatroomNum) {
        return chatRoomMap.get(chatroomNum).getOnlineCount() ;
    }

    @Override
    public boolean getChatRoomChatFlag(String chatroomNum) {
        ChatRoom chatRoom = chatRoomMap.get(chatroomNum) ;
        return chatRoom == null ? false: chatRoom.getChatroomFlag() ;
    }

    @Override
    public void addWebSocketSessionToChatRoom(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes() ;
        //获取直播间号
        String chatRoomNum = (String) attributes.get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
        ChatRoom chatRoom = getChatRoom(chatRoomNum) ;
        Message message = new Message() ;
        message.setFromChatRoomNum(chatRoomNum) ;
        message.setDestination(chatRoomNum) ;

        //1. 不存在直播间，此时直播大厅还未管理当前WebSocketSession，可以直接发消息关闭连接
        //2. 此时聊天室关闭，
        if(chatRoom == null || !chatRoom.getChatroomFlag()) {
            message.setMessageType(MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE) ; //信息事件
            TextMessage springTextMessage = new TextMessage(JsonUtils.toJson(message)) ;
            //用户进入直播间失败，存入标志，用于移除websocketSession的时候判断
            session.getAttributes().put(ChatConstants.USER_ENTER_FAIL_WEBSOCKET_SESSION_KEY, new Object()) ;
            try {
                session.sendMessage(springTextMessage) ;
            } catch (IOException e) {
                //TODO 换成日志框架
                e.printStackTrace();
            }
            return ;
        }

        String account = (String) attributes.get(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY) ; //账号
        String nickname = (String) attributes.get(ChatConstants.NICKNAME_IN_WEBSOCKET_SESSION_KEY); //昵称
        chatRoom.addUserSession(session) ;
        int onlineCount = chatRoom.getOnlineCount() ;   //在线用户数
        message.setContent(onlineCount+"") ;
        message.setMessageType(MessageType.USER_ENTER_CHATROOM_MESSAGE_TYPE) ;
        message.setAccount(account) ;
        chatRoom.sendMessageToCurrentChatRoom(message) ;

    }

    @Override
    public boolean removeWebSocketSessionToChatRoom(WebSocketSession session) {

        Map<String, Object> map = session.getAttributes() ;
        //直播间号
        String chatroomNum = (String) map.get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
        //用户账号
        String userAccount = (String) map.get(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY);
        //主播标记
        Object anchorFlag = map.get(ChatConstants.ANCHOR_FLAG_IN_WEBSOCKET_SESSION_KEY) ;
        ChatRoom chatRoom = this.getChatRoom(chatroomNum) ;

        String anchorInChatroom = chatRoom.getanchorAccount() ; //直播间的主播账号
        if(anchorFlag != null || userAccount.equals(anchorInChatroom)) {
            dispatchMessageToChatRoom(chatroomNum, MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE) ;
            return true ;
        }

        chatRoom.removeUserSession(session) ;
        dispatchMessageToChatRoom(chatroomNum, MessageType.USER_EXIT_CHATROOM_MESSAGE_TYPE) ;
        return false ;

    }

    /**
     *  缓冲大小，尽可能的防止map扩容
     */
    private static final int BUFFER_SIZE = 8 ;
    @Override
    public Map<String, Integer> listChatRoomOnlineCount() {

        int chatRoomCount = chatRoomMap.size() ;

        Map<String, Integer> map = new HashMap<>(chatRoomCount+BUFFER_SIZE) ;

        Collection<ChatRoom> chatRooms = chatRoomMap.values() ;
        for (ChatRoom chatRoom : chatRooms) {
            if(chatRoom == null) continue ;
            map.put(chatRoom.getChatRoomNum(), chatRoom.getOnlineCount()) ;
        }

        return map;
    }


}
