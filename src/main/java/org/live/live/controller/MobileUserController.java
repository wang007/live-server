package org.live.live.controller;

import org.live.common.response.DataTableModel;
import org.live.live.service.MobileUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 移动端用户控制器
 * Created by KAM on 2017/4/6.
 */
@RestController
@RequestMapping("/live")
public class MobileUserController {

    private static final String MODULE = "live";
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
}
