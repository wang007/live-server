package org.live.app.controller;

import org.apache.commons.lang3.StringUtils;
import org.live.app.vo.LiveCategoryVo;
import org.live.app.vo.LoginResponseVo;
import org.live.app.vo.MobileUserVo;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.utils.EncryptUtils;
import org.live.live.entity.Anchor;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.service.AnchorService;
import org.live.live.service.LiveCategoryService;
import org.live.live.service.LiveRoomService;
import org.live.live.service.MobileUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 *  移动端访问的直播分类接口
 * Created by Mr.wang on 2017/4/4.
 */
@Controller
@RequestMapping("/app")
public class AppLiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLiveController.class) ;

    @Resource
    private LiveCategoryService categoryService ;

    @Resource
    private MobileUserService mobileUserService ;

    @Resource
    private AnchorService anchorService ;

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
     *  登录
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponseVo liveLogin(String account, String password) {
        LoginResponseVo responseVo = new LoginResponseVo() ;
        try {
           MobileUser mobileUser = mobileUserService.findMobileUserByAccount(account) ;
           if(mobileUser == null || !StringUtils.equals(mobileUser.getPassword(), EncryptUtils.encryptToBase64(password)) ) {
               responseVo.error() ;
               responseVo.setMessage("账号或密码错误！") ;
               return responseVo ;
           }
           if(mobileUser.isLockFlag()) {    //账号被锁
               responseVo.error() ;
               responseVo.setMessage("账号被锁定！") ;
               return responseVo ;
           }
           if(mobileUser.isOutDateFlag()) {
               responseVo.error() ;
               responseVo.setMessage("账号已过期！") ;
               return responseVo ;
           }

            MobileUserVo userVo = new MobileUserVo() ;

           if(mobileUser.isAnchorFlag()) {  //当前用户是主播, 封装主播的信息
               LiveRoom liveRoom = liveRoomService.findLiveRoomByMobileUser(mobileUser) ;
               if(liveRoom != null) {
                   MobileUserVo.LiveRoomInUserVo liveRoomVo = userVo.newInstantLiveRoomVo() ;
                   liveRoomVo.setCategoryId(liveRoom.getLiveCategory().getId()) ;
                   liveRoomVo.setCategoryName(liveRoom.getLiveCategory().getCategoryName()) ;
                   liveRoomVo.setDescription(liveRoom.getAnchor().getDescription()) ;
                   liveRoomVo.setRoomCoverUrl(liveRoom.getCoverUrl()) ;
                   liveRoomVo.setRoomId(liveRoom.getId()) ;
                   liveRoomVo.setRoomLabel(liveRoom.getRoomLabel()) ;
                   liveRoomVo.setRoomName(liveRoom.getRoomName()) ;
                   userVo.setLiveRoomVo(liveRoomVo) ;
               }
           }
           userVo.setAccount(account) ;
           userVo.setPassword(password) ;
           userVo.setAnchorFlag(mobileUser.isAnchorFlag()) ;    //主播标记
           userVo.setBirthday(mobileUser.getMember().getBirthday()) ;
           userVo.setEmail(mobileUser.getEmail()) ;
           userVo.setHeadImgUrl(mobileUser.getHeadImgUrl()) ;
           userVo.setMobileNumber(mobileUser.getMobileNumber()) ;
           userVo.setNickname(mobileUser.getNickname()) ;
           userVo.setRealName(mobileUser.getMember().getRealName()) ;
           userVo.setSex(mobileUser.getMember().getSex()) ;

           responseVo.success() ;
           responseVo.setData(userVo) ;
        } catch (Exception e) {
            LOGGER.error("移动端登录异常", e) ;
            responseVo.error() ;
            responseVo.setMessage("服务器忙！");

        }
        return responseVo ;
    }

}
