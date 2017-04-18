package org.live.live.schedule;

import org.live.live.repository.LiveRoomRepository;
import org.live.live.service.LiveRoomService;
import org.live.websocket.chat.ChatHallManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 *  直播间的定时任务调度器
 *
 * Created by wang on 2017/4/17.
 */
@Component
public class LiveRoomSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveRoomSchedule.class) ;

    @Resource
    private LiveRoomRepository liveRoomRepository ;

    /**
     *  把内存中的直播间在线人数，刷入到数据库中，
     *  每5秒刷新一次
     */
    @Scheduled(fixedDelay= 5000)
    public void flushOnlineCountToLiveRoomTask() {
        try {
            String liveRoomNum;
            int onlineCount;
            Map<String, Integer> listChatroomOnlineCount = ChatHallManager.listChatroomOnlineCount();
            Set<Map.Entry<String, Integer>> entrySet = listChatroomOnlineCount.entrySet();
            for (Map.Entry<String, Integer> entry : entrySet) {
                liveRoomNum = entry.getKey();
                onlineCount = entry.getValue().intValue();
                liveRoomRepository.setOnlineCountByLiveRoomNum(liveRoomNum, onlineCount);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
