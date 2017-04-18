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
     * 这里的消息派发都是用户发过来的消息， 1.发消息到直播间。 2.发消息给用户（私聊）。 3. 主播禁言（解除禁言）。 4.主播踢出用户。（解除踢出）
     *
     * 其他消息是由系统派发的，不经过dispatchMessage。 用户进入（离开）直播间。 主播进入（离开）直播间。
     *
     * @param session 当前的发送者的session
     * @param textMessage 发送的信息
     */
    public static void dispatchMessage(WebSocketSession session, TextMessage textMessage) {

        Message message  = JsonUtils.fromJson(textMessage.getPayload(), Message.class) ;

        String destination = message.getDestination() ;     //消息发往的目的地

        switch (message.getMessageType()) {
            case MessageType.SEND_TO_USER_MESSAGE_TYPE: {   //发消息给某个用户
                String[] destinations = resolveDestination(destination);
                message.setDestination(destinations[1]) ;   //消息目的地是用户，
                chatHall.dispatchMessageToChatRoom(destinations[0], message) ;
                break ;
            }

            case MessageType.SHUTUP_USER_MESSAGE_TYPE: {   //禁言用户
                String[] destinations = resolveDestination(destination);
                message.setDestination(destinations[0]) ;   //设置发往的直播间
                message.setContent(destinations[1]) ;   //用户账号暂存到content中， 在chatHall获取用户账号，并重新设置message的content
                if(listener != null) listener.onShutupUserOnChatRoom(destinations[0], destinations[1]) ;//通知调用者。 存库
                chatHall.dispatchMessageToChatRoom(destinations[0], message) ;
                break ;
            }

            case MessageType.RELIEVE_SHUTUP_USER_MESSAGE_TYPE: {    //解除禁言
                String[] destinations = resolveDestination(destination);
                message.setDestination(destinations[0]) ;   //设置发往的直播间
                message.setContent(destinations[1]) ;   //用户账号暂存到content中， 在chatHall获取用户账号，并重新设置message的content
                if(listener != null) listener.onRelieveShutupUserOnChatRoom(destinations[0], destinations[1]) ;//通知调用者。 存库
                chatHall.dispatchMessageToChatRoom(destinations[0], message);
                break ;
            }

            case MessageType.KICKOUT_USER_MESSAGE_TYPE: {   //踢出用户
                String[] destinations = resolveDestination(destination);
                message.setDestination(destinations[0]) ;   //设置发往的直播间
                message.setContent(destinations[1]) ;   //用户账号暂存到content中， 在chatHall获取用户账号，并重新设置message的content
                if(listener != null) listener.onKickoutUserOnChatRoom(destinations[0], destinations[1]) ;//通知调用者。 存库
                chatHall.dispatchMessageToChatRoom(destinations[0], message);
                break ;
            }

            case MessageType.RELIEVE_KICKOUT_USER_MESSAGE_TYPE: {   //解除踢出
                String[] destinations = resolveDestination(destination);
                message.setDestination(destinations[0]) ;   //设置发往的直播间
                message.setContent(destinations[1]) ;   //用户账号暂存到content中， 在chatHall获取用户账号，并重新设置message的content
                if(listener != null) listener.onRelieveKickoutUserOnChatRoom(destinations[0], destinations[1]) ;//通知调用者。 存库
                chatHall.dispatchMessageToChatRoom(destinations[0], message);
            }
            default: chatHall.dispatchMessageToChatRoom(destination, message) ; //其他消息
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
            if(listener != null) listener.onAnchorOpenChatRoom(chatRoomNum) ;   //通知调用者，主播开启直播间。
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
            if(listener != null) listener.onAnchorDissolveChatRoom(chatroomNum) ; //通知调用者。
        }
    }

    /**
     * 获取所有房间的在线人数
     * @return
     */
    public static Map<String, Integer> listChatroomOnlineCount() {
        return chatHall.listChatRoomOnlineCount() ;
    }


    /**
     *  解析消息目的地
     * @param destination 特定目的地 ， 格式： 格式："主播间号-用户账号"
     * @return String[]  0. 主播间号，1.用户账号
     */
    private static String[] resolveDestination(String destination) {
        if(destination == null || "".equals(destination)) {
            return new String[]{"", ""} ;
        }
        String[] destinations = destination.split("-") ;
        return destinations ;
    }



}
