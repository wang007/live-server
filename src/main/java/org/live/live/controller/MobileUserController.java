package org.live.live.controller;

import org.live.common.constants.Constants;
import org.live.common.response.DataTableModel;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.utils.CopyPropertiesUtils;
import org.live.common.utils.EncryptUtils;
import org.live.live.entity.MobileUser;
import org.live.live.service.MobileUserService;
import org.live.school.controller.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 移动端用户控制器
 * Created by KAM on 2017/4/6.
 */
@RestController
@RequestMapping("/live")
public class MobileUserController {

    private static final String MODULE = "live";
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileUserController.class);
    @Autowired
    private MobileUserService mobileUserService;

    /**
     * 跳转至移动端用户管理页面
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "/mobileUser/page", method = RequestMethod.GET)
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName(MODULE + "/mobile_user");
        return mv;
    }

    /**
     * 请求移动端用户数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mobileUser/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel data(HttpServletRequest request) {
        return mobileUserService.findPage(request);
    }

    /**
     * 添加移动端用户
     *
     * @return
     */
    @RequestMapping(value = "/mobileUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> save(MobileUser mobileUser) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            mobileUser.setRegisterTime(new Date());
            mobileUser.setLockFlag(false);
            mobileUser.setHeadImgUrl(Constants.DEFAULT_HEAD_IMG_URL); // 默认头像地址
            mobileUser.setPassword(EncryptUtils.encryptToBase64(mobileUser.getPassword())) ;
            model.setData(mobileUserService.save(mobileUser));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加移动端用户异常", e);
            model.error();
        }
        return model;
    }

    /**
     * 修改移动端用户信息
     *
     * @return
     */
    @RequestMapping(value = "/mobileUser", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> update(MobileUser mobileUser) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        MobileUser entity = null;
        try {
            if (mobileUser.getId() != null) {
                entity = mobileUserService.get(mobileUser.getId()); // 取得原始记录
                CopyPropertiesUtils.copyPropertiesIgnoreNull(entity, mobileUser); // 更新记录
            } else {
                /** id为空异常 **/
                model.error();
                return model;
            }
            model.setData(mobileUserService.save(entity)); // 保存用户信息
            model.success();
        } catch (Exception e) {
            LOGGER.error("修改移动端用户信息异常", e);
            model.error();
        }
        return model;
    }

    /**
     * 设置多个移动端用户为过期用户
     *
     * @return
     */
    @RequestMapping(value = "/mobileUser", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel<Object> outDate(@RequestParam(value = "ids[]") List<String> ids) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            mobileUserService.setOutDate(ids); // 设置账户为过期状态
            model.success();
        } catch (Exception e) {
            LOGGER.error("设置多个移动端用户为过期用户失败", e);
            model.error();
        }

        return model;
    }
}
