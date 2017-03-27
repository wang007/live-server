package org.live.websocket.chat;

import org.live.common.utils.JsonUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  直播间
 * Created by Mr.wang on 2017/3/16.
 */
public class ChatRoom {

    private String chatRoomNum ;     //直播间号

    private String anchorAccount ;  //主播号

    private AtomicInteger onlineCount ;     //在线人数

    /**
     *  直播间状态，true：正在直播。 false：关闭直播
     *  防止关闭的时候，有用户进来
     */
    private volatile boolean chatFlag ;

    /**
     *  在线用户的session
     *  key：用户账号
     */
    private ConcurrentHashMap<String,WebSocketSession> onlineUserSessions ;

    /**
     *
     * @param chatRoomNum   直播间号
     * @param anchorAccount 主播号
     */
    public ChatRoom(String chatRoomNum, String anchorAccount, WebSocketSession session) {

        this.chatRoomNum = chatRoomNum ;
        this.anchorAccount = anchorAccount ;
        chatFlag = true ;
        onlineCount = new AtomicInteger(1) ;
        onlineUserSessions = new ConcurrentHashMap<>() ;
        onlineUserSessions.put(anchorAccount, session) ;
    }

    /**
     *  此时，直播间号 == 主播号
     * @param chatRoomNum 直播间号
     */
    public ChatRoom(String chatRoomNum, WebSocketSession session) {

        this(chatRoomNum, chatRoomNum, session) ;
    }

    /**
     * 打扫屋子再请客
     * @param chatRoomNum
     * @param anchorAccount
     * @return
     */
    public ChatRoom resetChatRoom(String chatRoomNum, String anchorAccount, WebSocketSession session) {

        this.chatRoomNum = chatRoomNum ;
        this.anchorAccount = anchorAccount ;
        chatFlag = true ;
        onlineCount = new AtomicInteger(1) ;
        onlineUserSessions = new ConcurrentHashMap<>() ;
        onlineUserSessions.put(anchorAccount, session) ;
        return this ;
    }

    /**
     * 打扫屋子再请客
     * @param chatRoomNum
     * @return
     */
    public ChatRoom resetChatRoom(String chatRoomNum, WebSocketSession session) {
        return this.resetChatRoom(chatRoomNum, chatRoomNum, session) ;
    }

    /**
     * 打扫屋子再请客
     * @param session
     * @return
     */
    public ChatRoom resetChatRoom(WebSocketSession session) {

        Map<String, Object> map = session.getAttributes() ;

        String chatroomNum = (String) map.get(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY);
        String anchorAccount = (String) map.get(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY);
        return this.resetChatRoom(chatroomNum, anchorAccount, session) ;
    }

    /**
     * 用户进入直播间，保存到chatRoom中
     * @param session
     * @return chatFlag true： 进入直播间成功， false： 进入直播间失败
     */
    public boolean addUserSession(WebSocketSession session) {

        if(!chatFlag) return false ;
        //获取用户的账号
        String usernameNum = (String) session.getAttributes().get(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY);
        onlineUserSessions.put(usernameNum, session) ;
        onlineCount.incrementAndGet() ;
        return true ;
    }

    /**
     * 用户离开直播间，从chatRoom删除掉
     * @param session
     */
    public int removeUserSession(WebSocketSession session) {
        //获取用户的账号
        String usernameNum = (String) session.getAttributes().get(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY);
        onlineUserSessions.remove(usernameNum) ;
        return onlineCount.decrementAndGet() ;
    }

    /**
     *  发送信息给直播间的所有用户
     */
    public void sendMessageToCurrentChatRoom(Message message) {
        TextMessage springTextMessage = new TextMessage(JsonUtils.toJson(message)) ;
        //Collection<WebSocketSession> sessions = onlineUserSessions.values() ;
        Set<Map.Entry<String, WebSocketSession>> entryWebSessions = onlineUserSessions.entrySet() ;
        Iterator<Map.Entry<String, WebSocketSession>> iterator = entryWebSessions.iterator() ;
        try {
            while (iterator.hasNext()) {
                WebSocketSession session = iterator.next().getValue() ;
                if (session.isOpen()) {     //session是否打开
                    session.sendMessage(springTextMessage) ;
                } else {    //清除当前的session
                    iterator.remove() ;
                    onlineCount.decrementAndGet() ;
                }
            }
        } catch (IOException e) {
            //TODO 换成日志框架
            e.printStackTrace() ;
        }
    }

    /**
     *  发送信息直播间的某个用户
     */
    public void sendMessageToUser(Message message) {
        String usernameNum = message.getDestination() ;  //某用户的账号
        WebSocketSession session = onlineUserSessions.get(usernameNum) ;
        if(session == null) return ;
        TextMessage springTextMessage = new TextMessage(JsonUtils.toJson(message)) ;
        try {
            if(session.isOpen()) {
                session.sendMessage(springTextMessage) ;
            } else {
                onlineUserSessions.remove(usernameNum) ;
                onlineCount.decrementAndGet() ;
            }

        } catch (IOException e) {
            //TODO 换成日志框架
            e.printStackTrace() ;
        }
    }


    /**
     *  直播间的状态改成 关闭。
     */
    private void closeChatRoom() {
        this.chatFlag = false ;
    }

    /**
     *  关闭当前直播间
     * @param message
     */
    public void dissolveChatRoom(Message message) {
        TextMessage springTextMessage = new TextMessage(JsonUtils.toJson(message)) ;
        this.closeChatRoom() ;
        Collection<WebSocketSession> sessions = onlineUserSessions.values() ;
        try {
            for (WebSocketSession session : sessions) {
                if(session.isOpen()) {
                    //此时用户被动的离开，存一个标志，用于在关闭session的时候（afterConnectionClosed方法），就不用在进入直播间进行处理
                    session.getAttributes().put(ChatConstants.USER_PASSIVE_EXIT_FLAG_WEBSOCKET_SESSION_KEY, new Object()) ; //
                    session.sendMessage(springTextMessage);    //websocketSession还打开的就发送信息，关闭的就不用管了。
                    session.close();
                }

            }
        } catch (IOException e) {
            //TODO 换成日志框架
            e.printStackTrace() ;
        }
        onlineUserSessions.clear() ;    //清空chatRoom的session
        onlineUserSessions = null ;
    }


    public boolean getChatroomFlag() {
        return chatFlag ;
    }

    public String getChatRoomNum() {
        return chatRoomNum;
    }

    public String getanchorAccount() {
        return anchorAccount;
    }

    public int getOnlineCount() {
        int localCount = onlineCount.intValue() ;
        if(localCount <= 1) {   //如果小于1，那就设置回去为1 ;
            onlineCount.set(1) ;
        }
        return onlineCount.intValue() ;
    }

}
