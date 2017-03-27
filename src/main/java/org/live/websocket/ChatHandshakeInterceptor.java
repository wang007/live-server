package org.live.websocket;


import org.live.websocket.chat.ChatConstants;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 客户端与服务端握手前的http请求
 *
 * 此websocket连接，必选3个参数 chatroom（直播间号），account（用户账号）, nickname(昵称),  1个可选参数 anchor（是否主播开启直播间的flag）
 *
 *  anchor可选值。 1. 代表是主播开启主播间。 不存在此参数就是普通观众.
 *  如果直播进入某个主播间，不能携带此参数。
 *
 * Created by Mr.wang on 2017/3/18.
 */
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if(request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest() ;
            //直播间号
            String chatRoomNum = httpRequest.getParameter(ChatConstants.CHATROOM_NUMBER_KEY_IN_REQUEST) ;
            //用户的账号
            String account = httpRequest.getParameter(ChatConstants.ACCOUNT_KEY_IN_REQUEST) ;
            //是否是主播的标记
            String anchorFlag = httpRequest.getParameter(ChatConstants.ANCHOR_FLAG_IN_REQUEST) ;

            String nickname = httpRequest.getParameter(ChatConstants.NICKNAME_KEY_IN_REQUEST) ;

            //TODO 当握手失败的时候，响应什么内容
            if(chatRoomNum == null || "".equals(chatRoomNum)
                    || account == null || "".equals(account)
                    || nickname == null || "".equals(nickname)) {

                return false ;
            }
            attributes.put(ChatConstants.CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY, chatRoomNum) ;
            attributes.put(ChatConstants.USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY, account) ;
            attributes.put(ChatConstants.NICKNAME_IN_WEBSOCKET_SESSION_KEY, nickname) ;
            if(anchorFlag != null) attributes.put(ChatConstants.ANCHOR_FLAG_IN_WEBSOCKET_SESSION_KEY, new Object()) ;
        }

        return true ;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
