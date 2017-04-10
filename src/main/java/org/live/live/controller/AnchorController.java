package org.live.live.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by KAM on 2017/4/10.
 */
@RestController
@RequestMapping("/live")
public class AnchorController {
    private static final String MODULE = "live";

    /**
     * 跳转至主播管理页面
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "/anchor/page", method = RequestMethod.GET)
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName(MODULE + "/anchor");
        return mv;
    }
}
