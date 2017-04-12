package org.live.app.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.bcel.Const;
import org.live.app.vo.LiveCategoryVo;
import org.live.app.vo.MobileUserVo;
import org.live.common.constants.Constants;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.utils.EncryptUtils;
import org.live.common.utils.HttpServletUtils;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.service.AnchorService;
import org.live.live.service.LiveCategoryService;
import org.live.live.service.LiveRoomService;
import org.live.live.service.MobileUserService;
import org.live.school.entity.Member;
import org.live.school.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

    @Resource
    private MemberService memberService ;


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
    public ResponseModel<Object> liveLogin(HttpServletRequest request, String account, String password) {
        ResponseModel<Object> model = new SimpleResponseModel() ;
        try {
           MobileUser mobileUser = mobileUserService.findMobileUserByAccount(account) ;
           if(mobileUser == null || !StringUtils.equals(mobileUser.getPassword(), EncryptUtils.encryptToBase64(password)) ) {
               model.error() ;
               model.setMessage("账号或密码错误！") ;
               return model ;
           }
           if(mobileUser.isLockFlag()) {    //账号被锁
               model.error() ;
               model.setMessage("账号被锁定！") ;
               return model ;
           }
           if(mobileUser.isOutDateFlag()) {
               model.error() ;
               model.setMessage("账号已过期！") ;
               return model ;
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

           mobileUser.setLastLoginTime(new Date()) ;
           mobileUser.setLastLoginIp(HttpServletUtils.getIpAddr(request)) ;
           mobileUserService.save(mobileUser) ;

           model.success() ;
           model.setData(userVo) ;
        } catch (Exception e) {
            LOGGER.error("移动端登录异常", e) ;
            model.error() ;
            model.setMessage("服务器忙！");

        }
        return model ;
    }

    /**
     *
     *  注册
     * @param account 账号
     * @param password 密码
     * @param nickname 昵称
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> register(String account, String password, String nickname) {
        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            Member member = memberService.findMemberBymemberNo(account) ;
            if(member == null) {
                model.error() ;
                model.setMessage("请输入您的学号或工号!") ;
                return model ;
            }
            if(mobileUserService.findMobileUserByAccount(account) != null) {
                model.error() ;
                model.setMessage("该账号已注册!") ;
                return model ;
            }
            MobileUser user = new MobileUser() ;
            user.setAccount(account) ;
            user.setPassword(EncryptUtils.encryptToBase64(password)) ;
            user.setAnchorFlag(false) ;
            user.setOutDateFlag(false) ;
            user.setLockFlag(false) ;
            user.setHeadImgUrl(Constants.DEFAULT_HEAD_IMG_URL) ;
            user.setMember(member) ;
            user.setNickname(nickname) ;
            user.setRegisterTime(new Date()) ;
            mobileUserService.save(user) ;

            model.success() ;
        } catch (Exception e){
            LOGGER.error("移动端用户注册异常", e) ;
            model.error() ;
            model.setMessage("服务器忙!") ;
        }
        return model ;
    }

    /**
     * 重置密码
     * @param account
     * @param realName
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/password/resetting", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> resetPassword(String account, String realName, String newPassword) {
        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
           MobileUser user =  mobileUserService.findMobileUserByAccount(account) ;
           if(user == null) {
               model.error() ;
               model.setMessage("重置密码失败，信息填写有误!") ;
               return model ;
           }
           if(!StringUtils.equals(user.getMember().getRealName(), realName)) {
               model.error() ;
               model.setMessage("重置密码失败，信息填写有误!") ;
               return model ;
           }
           user.setPassword(EncryptUtils.encryptToBase64(newPassword)) ;
           mobileUserService.save(user) ;

           model.success() ;
           model.setMessage("密码重置成功!") ;
        } catch (Exception e) {
            LOGGER.error("移动端密码重置失败！", e) ;
            model.error() ;
            model.setMessage("服务器忙!") ;
        }
        return model ;
    }

}
