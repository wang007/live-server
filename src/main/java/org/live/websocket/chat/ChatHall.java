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
     ChatRoom generateChatRoom(String chatRoomNum, WebSocketSession session) ;

    /**
     *
     * @param chatRoomNum 直播间号
     * @param anchorAccount  主播号
     * @param session 主播的webSocketSession
    * @return
     */
     ChatRoom generateChatRoom(String chatRoomNum, String anchorAccount, WebSocketSession session) ;


    /**
     * 解散一个直播间
     * @param chatRoom 直播间
     */
     void dissolveChatRoom(ChatRoom chatRoom) ;

     void dissolveChatRoom(ChatRoom chatRoom, Message message) ;

    /**
     *  获取一个直播间
     * @param chatRoomNum 直播间号
     * @return
     */
     ChatRoom getChatRoom(String chatRoomNum) ;

    /**
     * 处理一条信息到特定的直播间
     * @param chatRoomNum 直播间号
     * @param message 消息
     */
     void dispatchMessageToChatRoom(String chatRoomNum, Message message) ;


    /**
     *  获取直播间的在线人数
     * @param chatroomNum 直播间号
     * @return
     */
     int getChatRoomOnLineCount(String chatroomNum) ;

    /**
     *  获取直播间的状态
     * @param chatroomNum 直播间号
     * @return chatFlag true: 正在直播  false: 直播间关闭
     */
     boolean getChatRoomChatFlag(String chatroomNum) ;

    /**
     * 往直播间添加用户的session
     * @param session
     */
     void addWebSocketSessionToChatRoom(WebSocketSession session) ;

    /**
     *  移除直播间中用户的session
     * @param session
     * @return boolean true: 移除的是直播的session，即关闭直播间。 false: 观众
     */
     boolean removeWebSocketSessionToChatRoom(WebSocketSession session) ;

    /**
     * 获取所有房间的在线人数
     *
     * @return
     */
     Map<String, Integer> listChatRoomOnlineCount() ;


}
