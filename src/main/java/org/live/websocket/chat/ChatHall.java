package org.live.websocket.chat;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 *  聊天大厅
 * Created by Mr.wang on 2017/3/17.
 */
public interface ChatHall {

    /**
     * 生成一个直播间
     * @param chatRoomNum  直播间号
     * @param session 主播的webSocketSession
     * @return
     */
    public ChatRoom generateChatRoom(String chatRoomNum, WebSocketSession session) ;

    /**
     *
     * @param chatRoomNum 直播间号
     * @param anchorAccount  主播号
     * @param session 主播的webSocketSession
    * @return
     */
    public ChatRoom generateChatRoom(String chatRoomNum, String anchorAccount, WebSocketSession session) ;


    /**
     * 解散一个直播间
     * @param chatRoom 直播间
     */
    public void dissolveChatRoom(ChatRoom chatRoom) ;

    public void dissolveChatRoom(ChatRoom chatRoom, Message message) ;

    /**
     *  获取一个直播间
     * @param chatRoomNum 直播间号
     * @return
     */
    public ChatRoom getChatRoom(String chatRoomNum) ;

    /**
     * 处理一条信息到特定的直播间
     * @param chatRoomNum 直播间号
     * @param message 消息
     */
    public void dispatchMessageToChatRoom(String chatRoomNum, Message message) ;

    /**
     * 处理一条信息到特定的直播间，用于广播某个事件。例如：主播离开，用户进入等等。。不能发送具体的消息
     * @param chatRoomNum 直播间号
     * @param messageType 消息类型 , 参考{@link MessageType}
     */
    public void dispatchMessageToChatRoom(String chatRoomNum, int messageType) ;

    /**
     *  获取直播间的在线人数
     * @param chatroomNum 直播间号
     * @return
     */
    public int getChatRoomOnLineCount(String chatroomNum) ;

    /**
     *  获取直播间的状态
     * @param chatroomNum 直播间号
     * @return chatFlag true: 正在直播  false: 直播间关闭
     */
    public boolean getChatRoomChatFlag(String chatroomNum) ;

    /**
     * 往直播间添加用户的session
     * @param session
     */
    public void addWebSocketSessionToChatRoom(WebSocketSession session) ;

    /**
     *  移除直播间中用户的session
     * @param session
     * @return boolean true: 移除的是直播的session，即关闭直播间。 false: 观众
     */
    public boolean removeWebSocketSessionToChatRoom(WebSocketSession session) ;

    /**
     * 获取所有房间的在线人数
     *
     * @return
     */
    public Map<String, Integer> listChatRoomOnlineCount() ;


}
