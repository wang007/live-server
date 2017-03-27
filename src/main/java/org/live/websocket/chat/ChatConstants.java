package org.live.websocket.chat;

/**
 *  chat模块的常量
 * Created by Mr.wang on 2017/3/17.
 */
public class ChatConstants {

    /**
     *  用户账号在websocketSession的key
     */
    public static final String USER_ACCOUNT_IN_WEBSOCKET_SESSION_KEY = "accountKey" ;

    /**
     *  直播间号在websocketSession的key
     */
    public static final String CHATROOM_NUMBER_IN_WEBSOCKET_SESSION_KEY = "chatRoomNumKey" ;

    /**
     *  昵称在websocketsession的key
     */
    public static final String NICKNAME_IN_WEBSOCKET_SESSION_KEY = "nicknameKey" ;

    /**
     *  是否是主播的标记在websocketSession的key
     */
    public static final String ANCHOR_FLAG_IN_WEBSOCKET_SESSION_KEY = "anchorFlagKey" ;

    /**
     *  系统的账号，发消息的时候，代表这条消息是系统发的
     */
    public static final String SYSTEM_NUM = "10000" ;

    /**
     *  系统的名称
     */
    public static final String SYSTEM_NAME = "system" ;

    /**
     *  账号在request中的key
     */
    public static final String ACCOUNT_KEY_IN_REQUEST = "account" ;


    /**
     *  昵称在request中的key
     */
    public static final String NICKNAME_KEY_IN_REQUEST = "nickname" ;

    /**
     * 直播间号在request中的key
     */
    public static final String CHATROOM_NUMBER_KEY_IN_REQUEST = "chatroom" ;

    /**
     * 是否主播的标记在request中的key
     */
    public static final String ANCHOR_FLAG_IN_REQUEST = "anchor" ;

    /**
     *  用户进入直播间失败，（例如：用户进入时，直播间未开启这种情况）
     */
    public static final String USER_ENTER_FAIL_WEBSOCKET_SESSION_KEY = "enterFlagKey" ;

    /**
     *  用户被动的离开， 例如：当主播关闭聊天室时，用户就要被动的离开。
     */
    public static final String USER_PASSIVE_EXIT_FLAG_WEBSOCKET_SESSION_KEY = "passiveExitKey" ;

}
