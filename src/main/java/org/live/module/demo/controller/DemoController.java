package org.live.module.demo.controller;

import org.live.common.response.DataTableModel;
import org.live.module.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by KAM on 2017/3/28.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("demo/demo");
        return mv;
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel page(HttpServletRequest request) {
        return demoService.findPage(request);
    }
}
