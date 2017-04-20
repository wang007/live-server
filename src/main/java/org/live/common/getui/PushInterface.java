package org.live.common.getui;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.PushSingleException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  推送的接口
 * Created by wang on 2017/4/19.
 */
public class PushInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushInterface.class) ;

    private static final String appID = "h5vhTEXk1f554YtJlkRDh4" ;

    private static final String appKey = "AhUU3DgXY47byZdhK4fr72" ;

    private static final String appSecret = "vMr70GJY798g5oaN06MA26" ;

    private static final String masterSecret = "bsv5nNxUUC8DWoOtLZo7M2" ;

    private static final String url = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * 实例
     */
    private static PushInterface instance ;

    /**
     *  推送的接口
     */
    private static IGtPush push ;

    private PushInterface() {
        push = new IGtPush(url, appKey, masterSecret) ;
    }

    /**
     * 推送到特定用户上
     * @param alias 别名， 这里指用户名
     * @param title 接收端显示的title
     * @param content 接收端显示的内容
     */
    public void  pushToSingle(String alias, String title, String content) {
        //发送的目标
        Target target = new Target() ;
        target.setAlias(alias) ;
        target.setAppId(appID) ;

        //信息
        SingleMessage message = new SingleMessage() ;
        message.setData(createNotificationTemplate(title, content)) ;
        message.setPushNetWorkType(0);
        message.setOffline(true) ;
        message.setOfflineExpireTime(24 * 3600 * 1000);

        IPushResult iPushResult ;
        try {
            iPushResult = push.pushMessageToSingle(message, target);
        } catch (PushSingleException e) {
            LOGGER.error(e.getMessage(), e) ;
            iPushResult = push.pushMessageToSingle(message, target, e.getRequestId()) ;
        }
    }




    /**
     * 接收的显示模板
     * @param title
     * @param content
     * @return
     */
    private NotificationTemplate createNotificationTemplate(String title, String content) {
        NotificationTemplate template = new NotificationTemplate() ;
        template.setAppId(appID) ;
        template.setAppkey(appKey) ;

        template.setTransmissionType(1) ;

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        // 配置通知栏图标
        style.setLogo("ic_launcher2.png") ;
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        return template ;
    }

    /**
     * 获取实例
     * @return
     */
    public static PushInterface  getInstance() {
        if(instance == null) {
            synchronized (PushInterface.class) {
                if(instance == null) {
                    instance = new PushInterface() ;
                }
            }
        }
        return instance ;
    }

    public static void main(String[] args) {

        PushInterface.getInstance().pushToSingle("201335020231", "测试33次测试33次", "我是内容3321我是内容3321我是内容3321我是内容3321我是内容3321");

    }


}
