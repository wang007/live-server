package org.live.websocket.chat;

import org.live.common.constants.Constants;
import org.live.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHallImpl.class) ;

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
        chatRoomMap.remove(chatRoom.getChatRoomNum()) ;   //从聊天大厅中删除这个直播间
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

            //先发消息给这个用户，设置他不能发言。 然后再把这件事广播给直播间的所有用户
            case MessageType.SHUTUP_USER_MESSAGE_TYPE: {    //主播禁言用户
                String userAccount = message.getContent() ;
                WebSocketSession session = chatRoom.getSessionByAccount(userAccount);
                if(session != null) {
                    Message specialMessage = new Message() ;    //先禁言该用户，
                    specialMessage.setAccount(ChatConstants.SYSTEM_NUM) ;  //代表系统账号
                    specialMessage.setMessageType(MessageType.SHUTUP_USER_MESSAGE_TYPE) ;
                    specialMessage.setDestination(userAccount) ;
                    chatRoom.sendMessageToUser(specialMessage) ;

                    //再把禁言这事，广播到这个直播间。
                    //获取昵称
                    String nickname  = (String) session.getAttributes().get(ChatConstants.NICKNAME_IN_WEBSOCKET_SESSION_KEY) ;
                    Message broadcastMessage = new Message() ;
                    broadcastMessage.setFromChatRoomNum(chatRoomNum) ;
                    broadcastMessage.setDestination(chatRoomNum) ;
                    broadcastMessage.setAccount(ChatConstants.SYSTEM_NUM) ;
                    broadcastMessage.setNickname(nickname) ;
                    broadcastMessage.setContent(nickname+"被禁言了") ;
                    broadcastMessage.setMessageType(MessageType.SEND_TO_CHATROOM_MESSAGE_TYPE) ;
                    chatRoom.sendMessageToCurrentChatRoom(broadcastMessage) ;   //广播到直播间，
                }
                break ;
            }

            //解除禁言，如果用户在线就告诉他。 不用广播到直播间中
            case MessageType.RELIEVE_SHUTUP_USER_MESSAGE_TYPE: {    //主播解除禁言
                String userAccount = message.getContent() ;
                WebSocketSession session = chatRoom.getSessionByAccount(userAccount) ;
                if(session != null) {
                    //获取昵称
                    String nickname  = (String) session.getAttributes().get(ChatConstants.NICKNAME_IN_WEBSOCKET_SESSION_KEY);
                    message.setDestination(userAccount) ;
                    message.setContent(nickname) ;
                    chatRoom.sendMessageToUser(message) ;
                }
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

            //先踢出这个用户，再把这事广播到直播间中
            case MessageType.KICKOUT_USER_MESSAGE_TYPE: {   //主播踢出用户
                String userAccount = message.getContent() ;
                WebSocketSession session = chatRoom.getSessionByAccount(userAccount) ;
                //踢出用户
                if(session != null) {
                    Message specialMessage = new Message() ;
                    specialMessage.setAccount(ChatConstants.SYSTEM_NUM) ;
                    specialMessage.setDestination(userAccount) ;
                    specialMessage.setMessageType(MessageType.KICKOUT_USER_MESSAGE_TYPE) ;
                    chatRoom.sendMessageToUser(specialMessage) ;   //发消息给这个用户
                    //把这个session从chatRoom中移除， 先存标记，afterConnectionClosed方法中判断要不要做处理。
                    session.getAttributes().put(ChatConstants.USER_PASSIVE_EXIT_FLAG_WEBSOCKET_SESSION_KEY, ChatConstants.FLAG_DELEGATE_VALUE) ;
                    removeWebSocketSessionToChatRoom(session) ;

                    //广播踢出用户消息
                    //获取昵称
                    String nickname  = (String) session.getAttributes().get(ChatConstants.NICKNAME_IN_WEBSOCKET_SESSION_KEY);
                    Message broadcastMessage = new Message() ;
                    broadcastMessage.setAccount(ChatConstants.SYSTEM_NUM) ;
                    broadcastMessage.setNickname(nickname) ;
                    broadcastMessage.setContent(nickname+ "被踢出直播间")  ;
                    broadcastMessage.setDestination(chatRoomNum) ;
                    broadcastMessage.setMessageType(MessageType.SEND_TO_CHATROOM_MESSAGE_TYPE) ;
                    chatRoom.sendMessageToCurrentChatRoom(broadcastMessage) ;
                }
                break ;
            }

            //解除踢出时，此时用户并不在直播间， 所以不用做任何处理
            case MessageType.RELIEVE_KICKOUT_USER_MESSAGE_TYPE: {   //主播解除用户踢出
                /*String userAccount = message.getContent() ;
                WebSocketSession session = chatRoom.getSessionByAccount(userAccount);
                if(session != null) {
                    //获取昵称
                    String nickname  = (String) session.getAttributes().get(ChatConstants.NICKNAME_IN_WEBSOCKET_SESSION_KEY);
                    message.setContent(nickname) ;
                    chatRoom.sendMessageToCurrentChatRoom(message) ;
                }*/
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
            session.getAttributes().put(ChatConstants.USER_ENTER_FAIL_WEBSOCKET_SESSION_KEY, ChatConstants.FLAG_DELEGATE_VALUE) ;
            try {
                session.sendMessage(springTextMessage) ;
                session.close() ;   //关闭连接
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e) ;
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
        message.setNickname(nickname);
        chatRoom.sendMessageToCurrentChatRoom(message) ;

    }

    @Override
    public boolean removeWebSocketSessionToChatRoom(WebSocketSession session) {

        Map<String, Object> map = session.getAttributes() ;
        //直播间号
        String chatroomNum = (String) map.get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
        //用户账号
        String userAccount = (String) map.get(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY);
        //
        String nickname = (String) map.get(ChatConstants.NICKNAME_IN_WEBSOCKET_SESSION_KEY);

        //主播标记
        Object anchorFlag = map.get(ChatConstants.ANCHOR_FLAG_IN_WEBSOCKET_SESSION_KEY) ;

        ChatRoom chatRoom = this.getChatRoom(chatroomNum) ;

        Message message = new Message() ;
        message.setFromChatRoomNum(chatroomNum) ;
        message.setDestination(chatroomNum) ;
        message.setNickname(nickname) ;
        message.setAccount(ChatConstants.SYSTEM_NUM) ;

        String anchorInChatroom = chatRoom.getanchorAccount() ; //直播间的主播账号
        if(anchorFlag != null || userAccount.equals(anchorInChatroom)) {
            message.setMessageType(MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE) ;
            dispatchMessageToChatRoom(chatroomNum, message) ;
            return true ;
        }

        message.setMessageType(MessageType.USER_EXIT_CHATROOM_MESSAGE_TYPE) ;
        chatRoom.removeUserSession(session) ;   //先移除这个websocketSession
        message.setContent(chatRoom.getOnlineCount() +"") ; //再统计人数
        dispatchMessageToChatRoom(chatroomNum, message) ;
        return false ;

    }

    /**
     *  缓冲大小，尽可能的防止map扩容
     */
    private static final int BUFFER_SIZE = 4 ;
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
