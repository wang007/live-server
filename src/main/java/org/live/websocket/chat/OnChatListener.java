package org.live.websocket.chat;

/**
 *  直播的监听器
 *
 * Created by Mr.wang on 2017/3/18.
 */
public interface OnChatListener {

    /**
     * 直播解散了直播间，（停止了直播）
     *
     * @param chatRoomNum 直播间号
     */
    public void onAnchorDissolveChatRoom(String chatRoomNum) ;

    /**
     * 禁言用户
     *
     * @param chatRoomNum 直播间号
     * @param userAccount 用户账号
     */
    public void onShutupUserOnChatRoom(String chatRoomNum, String userAccount) ;

    /**
     * 解除禁言
     * @param chatRoomNum
     * @param userAccount
     */
    public void onRelieveShutupUserOnChatRoom(String chatRoomNum, String userAccount) ;
}
