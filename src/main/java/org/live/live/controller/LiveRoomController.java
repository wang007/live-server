package org.live.live.controller;

import org.live.common.getui.PushInterface;
import org.live.common.page.JqGridModel;
import org.live.common.page.PageUtils;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.SystemLog;
import org.live.live.entity.LiveRoom;
import org.live.live.service.LiveRoomService;
import org.live.live.vo.LiveRoomInfoVo;
import org.live.live.vo.LiveRoomVo;
import org.live.websocket.chat.ChatHallManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wang on 2017/4/13.
 */
@Controller
@RequestMapping("/live")
public class LiveRoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveRoomController.class) ;

    @Resource
    private LiveRoomService service ;

    /**
     * 跳转到直播间界面
     * @return
     */
    @SystemLog(description = "进入直播间管理界面")
    @RequestMapping("/liveroom/page")
    public String toLiveRoomPage() {
        return "live/live_room" ;
    }

    /**
     * 刷新页面
     * @param searchStr
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/liveroom/refresh")
    public String refresh(HttpServletRequest request, String searchStr, int page, int rows) {

        request.setAttribute("searchStr", searchStr) ;
        request.setAttribute("page", page) ;
        request.setAttribute("rows", rows) ;
        return "live/live_room" ;
    }


    /**
     *  分页查询直播间的数据
     * @param request
     * @param searchStr
     * @return
     */
    @RequestMapping(value="/liveroom", method = RequestMethod.GET)
    @ResponseBody
    public JqGridModel<LiveRoomVo> findLiveRooms(HttpServletRequest request, String searchStr) {
        try {
            PageRequest page = PageUtils.getPage4JqGrid(request) ;
            Page<LiveRoomVo> pageResult = service.findLiveRooms(page, searchStr) ;
            JqGridModel<LiveRoomVo> model = PageUtils.pageConvertJqGrid(pageResult) ;
            return model ;
        } catch (Exception e) {
            LOGGER.error("查询直播间数据异常", e) ;
        }
        return null ;
    }

    /**
     * 更新直播间的禁播状态
     *
     * @return
     */
    @RequestMapping(value="/liveroom/{liveRoomId}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseModel<Object> changeLiveRoomBanFlag(@PathVariable("liveRoomId") String liveRoomId, boolean liveRoomBanFlag) {
        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
           LiveRoom liveRoom =  service.get(liveRoomId) ;
           String account = liveRoom.getAnchor().getUser().getAccount() ;
           service.changeLiveRoomBanFlag(liveRoomId, liveRoomBanFlag) ;
           if(liveRoomBanFlag) {    //禁播
                PushInterface.getInstance().pushToSingle(account, "高校直播", "您被禁播了！") ;
            } else {
                PushInterface.getInstance().pushToSingle(account, "高校直播", "您被解除禁播，可以进行直播了！") ;
            }
            model.success() ;
        } catch (Exception e) {
            LOGGER.error("更新直播间的禁播状态发生异常", e) ;
            model.error() ;
        }
        return model ;
    }

    /**
     * 直播间详情
     * @return
     */
    @RequestMapping(value="/liveroom/{liveroomId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> getLiveRoomInfo(@PathVariable String liveroomId) {
        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            LiveRoomInfoVo liveRoomInfo = service.getLiveRoomInfo(liveroomId) ;
            model.setData(liveRoomInfo) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error("查询直播间详情异常", e) ;
            model.error() ;
        }
        return model ;
    }


}
