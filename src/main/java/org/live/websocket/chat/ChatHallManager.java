package org.live.websocket.chat;

import org.live.common.utils.JsonUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 *  聊天大厅的管理者
 *
 * Created by Mr.wang on 2017/3/18.
 */
public class ChatHallManager {

    /**
     *  聊天大厅
     */
    private static ChatHall chatHall ;

    /**
     *  监听器
     */
    private static OnChatListener listener ;

    static {
        chatHall = new ChatHallImpl() ;
        //TODO 集成到项目中的时候，去掉。并调用实际的监听器
        setupListener(new OnChatListener() {
            @Override
            public void onAnchorDissolveChatRoom(String chatRoomNum) {
                System.out.println("通知调用者，主播解散");
            }

            @Override
            public void onShutupUserOnChatRoom(String chatRoomNum, String userAccount) {
                System.out.println("通知调用者，主播禁言用户");
            }
            @Override
            public void onRelieveShutupUserOnChatRoom(String chatRoomNum, String userAccount) {
                System.out.println("通知调用者，主播解除禁言") ;

            }
        });
    }

    private ChatHallManager(){}

    /**
     *  设置监听器
     */
    public static void setupListener(OnChatListener listener) {
        if(ChatHallManager.listener != null) throw new IllegalStateException("已经设置了监听器，不要重复设置") ;
        ChatHallManager.listener = listener ;
    }

    /**
     * 消息处理中心
     *
     * @param session 当前的发送者的session
     * @param textMessage 发送的信息
     */
    public static void dispatchMessage(WebSocketSession session, TextMessage textMessage) {

        Message message  = JsonUtils.fromJson(textMessage.getPayload(), Message.class) ;

        switch (message.getMessageType()) {
            case MessageType.SEND_TO_CHATROOM_MESSAGE_TYPE: {   //发消息给直播间
                chatHall.dispatchMessageToChatRoom(message.getDestination(), message) ;
                break ;
            }

            case MessageType.SHUTUP_USER_MESSAGE_TYPE: {    //禁言用户
                String destination = message.getDestination() ;  //消息的目的地，格式："主播间号-用户账号"
                if(destination == null || "".equals(destination)) return ;
                String[] destinations = destination.split("-") ;
                message.setDestination(destinations[1]) ;   //
                listener.onShutupUserOnChatRoom(destinations[0], destinations[1]) ;//通知调用者。
                chatHall.dispatchMessageToChatRoom(destinations[0], message) ;
                break ;
            }

            case MessageType.RELIEVE_SHUTUP_USER_MESSAGE_TYPE: {    //解除禁言

                String destination = message.getDestination() ;  //消息的目的地，格式："主播间号-用户账号"
                if(destination == null || "".equals(destination)) return ;
                String[] destinations = destination.split("-") ;
                message.setDestination(destinations[1]) ;   //
                listener.onRelieveShutupUserOnChatRoom(destinations[0], destinations[1]) ;//通知调用者。
                chatHall.dispatchMessageToChatRoom(destinations[0], message) ;
                break ;
            }

            case MessageType.SEND_TO_USER_MESSAGE_TYPE: {   //发消息给用户
                String destination = message.getDestination() ;  //消息的目的地，格式："主播间号-用户账号"
                if(destination == null || "".equals(destination)) return ;
                String[] destinations = destination.split("-") ;
                message.setDestination(destinations[1]) ;   //
                chatHall.dispatchMessageToChatRoom(destinations[0], message) ;
                break ;
            }

            case MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE: {   //主播离开

                //直播间号
                String chatroomNum = (String) session.getAttributes().get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);

                listener.onAnchorDissolveChatRoom(chatroomNum) ; //通知调用者。

                chatHall.dispatchMessageToChatRoom(chatroomNum, MessageType.ANCHOR_EXIT_CHATROOM_MESSAGE_TYPE) ;
                break ;
            }

            case MessageType.USER_ENTER_CHATROOM_MESSAGE_TYPE: {    //观众进入直播间
                //直播间号
                String chatRoomNum = (String) session.getAttributes().get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
                chatHall.dispatchMessageToChatRoom(chatRoomNum, MessageType.USER_ENTER_CHATROOM_MESSAGE_TYPE) ;
                break ;

            }

            case MessageType.USER_EXIT_CHATROOM_MESSAGE_TYPE: {     //用户离开直播间
                //直播间号
                String chatRoomNum = (String) session.getAttributes().get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
                chatHall.dispatchMessageToChatRoom(chatRoomNum, MessageType.USER_EXIT_CHATROOM_MESSAGE_TYPE) ;
                break ;
            }
        }
    }

    /**
     * 进入直播间
     *
     * @param session
     */
    public static void enterChatRoom(WebSocketSession session) {

        Map<String, Object> map = session.getAttributes() ;
        //用户名
        String userAccount = (String) map.get(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY) ;
        //直播间号
        String chatRoomNum = (String) map.get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
        //chatHall.getChatRoom(chatRoomNum) ;
        if(map.get(ChatConstants.ANCHOR_FLAG_IN_WEBSOCKET_SESSION_KEY) != null) {   //主播开启直播间
            chatHall.generateChatRoom(chatRoomNum, userAccount, session) ;
        } else {    //观众进入直播间
            chatHall.addWebSocketSessionToChatRoom(session);
        }
    }

    /**
     *  离开直播间。
     * @param session
     */
    public static void exitChatRoom(WebSocketSession session) {

        if(chatHall.removeWebSocketSessionToChatRoom(session)) {    //离开的是主播
            String chatroomNum = (String) session.getAttributes().get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
            listener.onAnchorDissolveChatRoom(chatroomNum) ; //通知调用者。
        }
    }

    /**
     * 获取所有房间的在线人数
     * @return
     */
    public static Map<String, Integer> listChatroomOnlineCount() {
        return chatHall.listChatRoomOnlineCount() ;
    }



}
