package org.live.app.controller;

import org.live.app.vo.LiveCategoryVo;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.live.service.LiveCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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


}
