package org.live.app.controller;

import org.live.app.vo.AppLiveRoomVo;
import org.live.app.vo.LiveCategoryVo;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.live.service.LiveCategoryService;
import org.live.live.service.LiveRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 移动端访问，关于直播间的接口
 * Created by wang on 2017/4/15.
 */
@Controller
@RequestMapping("/app")
public class AppLiveRoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLiveRoomController.class) ;


    @Resource
    private LiveCategoryService categoryService ;

    @Resource
    private LiveRoomService liveRoomService ;

    /**
     * 获取直播分类
     * @return
     */
    @RequestMapping("/category")
    @ResponseBody
    public ResponseModel<Object> findLiveCategorys(){

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            List<LiveCategoryVo> voList = categoryService.findLiveCategory4app() ;
            model.setData(voList) ;
            model.setMessage("查询成功") ;
            model.success() ;
        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e) ;
            model.setMessage("查询失败") ;
        }
        return model ;
    }


    /**
     * 1.当分类id为空时，就查询全部
     * 查询未禁播直播间的信息,
     * @return
     */
    @RequestMapping(value = "/liveroom", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findLiveRooms(String categoryId) {
        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            List<AppLiveRoomVo> voList = liveRoomService.findLiveRoomsForApp(categoryId);
            model.setData(voList) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error("移动端查询异常", e) ;
            model.error("服务器繁忙！") ;
        }
        return model ;
    }

    /**
     * 查询用户关注的直播间
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/liveroom", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findAttentionLiveRoomsByUserId(String userId) {
        ResponseModel<Object>  model = new SimpleResponseModel<>() ;
        try {
            List<AppLiveRoomVo> liveRoomVos = liveRoomService.findAttentionLiveRoomsForUser(userId) ;
            model.setData(liveRoomVos) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e) ;
            model.error("服务器繁忙！") ;
        }
        return model ;
    }


}
