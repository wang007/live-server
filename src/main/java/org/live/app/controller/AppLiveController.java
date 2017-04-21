package org.live.app.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.bcel.Const;
import org.live.app.vo.ApplyAnchorVo;
import org.live.app.vo.LiveCategoryVo;
import org.live.app.vo.MobileUserVo;
import org.live.common.constants.Constants;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.support.UploadFilePathConfig;
import org.live.common.utils.*;
import org.live.live.entity.*;
import org.live.live.service.*;
import org.live.school.entity.Member;
import org.live.school.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *  移动端访问的综合功能接口
 * Created by Mr.wang on 2017/4/4.
 */
@Controller
@RequestMapping("/app")
public class AppLiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLiveController.class) ;

    @Resource
    private MobileUserService mobileUserService ;

    @Resource
    private AnchorService anchorService ;

    @Resource
    private LiveRoomService liveRoomService ;

    @Resource
    private LiveCategoryService categoryService ;

    @Resource
    private MemberService memberService ;

    @Resource
    private ApplyAnchorService applyAnchorService ;

    /**
     * 申请成为主播的最大申请数
     */
    @Value("${system.applyAnchorMaxCount}")
    private int applyAnchorMaxCount ;

    /**
     * 申请主播的时间跨度
     */
    @Value("${system.applyAnchorTimeSpan}")
    private int applyAnchorTimeSpan ;

    @Resource
    private UploadFilePathConfig pathConfig ;


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

                   liveRoomVo.setRoomNum(liveRoom.getRoomNum()) ;
                   liveRoomVo.setBanLiveFlag(liveRoom.isBanLiveFlag()) ;
                   liveRoomVo.setLiveRoomUrl(liveRoom.getLiveRoomUrl()) ;
                   userVo.setLiveRoomVo(liveRoomVo) ;
               }
           }
            userVo.setUserId(mobileUser.getId()) ;
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


    /**
     * 申请成为主播的入口
     * @param applyAnchorVo
     * @return
     */
    @RequestMapping(value="/applyAnchor", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> applyAnchor(MultipartFile file, ApplyAnchorVo applyAnchorVo) {

        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            MobileUser mobileUser = mobileUserService.get(applyAnchorVo.getUserId());
            if(mobileUser == null) {
                model.error() ;
                model.setMessage("信息填写错误，重新登录后再申请！") ;  //移动端的userId失效的情况
                return model ;
            }
            if(StringUtils.isEmpty(mobileUser.getEmail()) || StringUtils.isEmpty(mobileUser.getMobileNumber())) {
                model.error() ;
                model.setMessage("申请主播，必须先填写好邮箱和手机号") ;
                return model ;
            }
            if(!applyAnchorService.judgeUserApplyCount(applyAnchorVo.getUserId(), applyAnchorMaxCount)) {    //判断次数
                model.error() ;
                model.setMessage("您已申请过了，并超过系统规定的申请限制数！") ;
                return model ;
            }
            //最后申请时间
            Date lastApplyDate = applyAnchorService.getLastApplyAnchorDate(applyAnchorVo.getUserId()) ;
            //时间跨度
            if(lastApplyDate != null) {
                double timeSpan = DateUtil.differenceDay(new Date(), lastApplyDate);
                int timeSpanInt = (int) timeSpan;
                if(timeSpanInt <= applyAnchorTimeSpan) {
                    model.error() ;
                    model.setMessage("您已申请过了，请"+(applyAnchorTimeSpan-timeSpanInt)+"天后再申请！") ;
                    return model ;
                }
            }
            LiveCategory liveCategory = categoryService.get(applyAnchorVo.getCategoryId()) ;
            if(liveCategory == null) {
                model.error() ;
                model.setMessage("请选择您想直播的分类！") ;
                return model ;
            }

            if(file == null) {
                model.error() ;
                model.setMessage("申请失败，请上传身份证照！") ;
                return model ;
            }
            String fileSuffix = UploadUtils.getFileSuffix(file.getOriginalFilename()) ; //文件后缀

            //路径： 相对于项目的 /projectDir/upload/系统日期/系统时间+6位随机数.xxx
            String dateStr = CreateOrderNoUtils.getDate() ;
            String fileName = CreateOrderNoUtils.getCreateOrderNo()+fileSuffix ;

            String targetPathSuffix = dateStr + File.separator + fileName ;

            File targetFile = UploadUtils.createFile(pathConfig.getUploadFilePath(), targetPathSuffix) ;
            file.transferTo(targetFile);

            ApplyAnchor applyAnchor = new ApplyAnchor() ;
            applyAnchor.setUser(mobileUser) ;
            applyAnchor.setRealName(applyAnchorVo.getRealName()) ;
            applyAnchor.setIdCard(applyAnchorVo.getIdCard()) ;
            applyAnchor.setCreateTime(new Date()) ;
            applyAnchor.setIdImgUrl(pathConfig.getUploadFilePathPrefix() + "/" + dateStr+ "/" + fileName) ;
            applyAnchorService.save(applyAnchor) ;
            model.success() ;
            model.setMessage("申请成功！") ;
        } catch (Exception e) {
            LOGGER.error("申请主播发生异常", e) ;
            model.error() ;
            model.setMessage("服务器繁忙，请稍后再试！") ;
        }
        return model ;
    }

    /**
     * 更换头像
     * @return
     */
    @RequestMapping(value="/headImg/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> changeUserHeadImg(MultipartFile file, @PathVariable String userId) {

        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            MobileUser mobileUser = mobileUserService.get(userId);
            String headImgUrl = mobileUser.getHeadImgUrl();
            if(!StringUtils.equals(headImgUrl, Constants.DEFAULT_HEAD_IMG_URL)) {   //不等于默认头像，
                File oldFile = new File(pathConfig.getUploadFileRootPath(), headImgUrl) ;
                if(oldFile.exists()) oldFile.delete() ; //删除之前的图片
            }
            String fileSuffix = UploadUtils.getFileSuffix(file.getOriginalFilename()) ; //文件后缀

            //路径： 相对于项目的 /projectDir/upload/系统日期/系统时间+6位随机数.xxx
            String dateStr = CreateOrderNoUtils.getDate() ;
            String fileName = CreateOrderNoUtils.getCreateOrderNo()+fileSuffix ;

            String targetPathSuffix = dateStr + File.separator + fileName ;

            File targetFile = UploadUtils.createFile(pathConfig.getUploadFilePath(), targetPathSuffix) ;
            file.transferTo(targetFile);

            mobileUser.setHeadImgUrl(pathConfig.getUploadFilePathPrefix() + "/" + dateStr+ "/" + fileName);
            mobileUserService.save(mobileUser) ;
            model.setData(mobileUser.getHeadImgUrl());
            model.setMessage("设置成功！") ;
            model.success();
        } catch (Exception e) {
            LOGGER.error("设置移动端用户头像异常", e) ;
            model.error() ;
            model.setMessage("设置失败！") ;
        }
        return model ;
    }

    /**
     * 关注
     * @param userId 移动端用户id
     * @param roomNum 主播间号
     * @return
     */
    @RequestMapping(value="/attention/{userId}", method = RequestMethod.POST)
    public ResponseModel<Object> attentionLiveRoom(@PathVariable String userId, String roomNum) {

        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            MobileUser mobileUser = mobileUserService.get(userId) ;
            LiveRoom liveRoom = liveRoomService.getLiveRoomByRoomNum(roomNum);
            if(mobileUser == null || liveRoom == null) {
                model.error() ;
                model.setMessage("服务器繁忙！") ;
                return model ;
            }
            mobileUserService.attentionLiveRoom(mobileUser, liveRoom) ;
            model.success() ;
            model.setMessage("关注成功！") ;
        } catch (Exception e) {
            LOGGER.error("关注直播间异常", e) ;
            model.error() ;
            model.setMessage("服务器繁忙！") ;
        }
        return model ;
    }

    /**
     * 修改用户的信息
     * @param nickname
     * @param email
     * @param mobileNumber
     * @return
     */
    @RequestMapping(value ="/user/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateMoberUserInfo(@PathVariable String userId, String nickname, String email, String mobileNumber) {

        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            MobileUser mobileUser = mobileUserService.get(userId) ;
            if(mobileUser == null) {
                model.error("修改失败！") ;
                return model ;
            }
            if(nickname != null && !"".equals(nickname)) {
                mobileUser.setNickname(nickname) ;
            }
            if(email != null && !"".equals(email)) {
                mobileUser.setEmail(email) ;
            }
            if(mobileNumber != null && !"".equals(mobileNumber)) {
                mobileUser.setMobileNumber(mobileNumber) ;
            }
            mobileUserService.save(mobileUser) ;
            model.success("修改成功！") ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e) ;
            model.error("服务器繁忙！") ;
        }
        return model ;
    }


}
