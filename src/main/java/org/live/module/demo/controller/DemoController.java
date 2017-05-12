package org.live.module.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by KAM on 2017/3/28.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam("account") String accoount, @RequestParam("password") String password) {
        System.out.println(accoount + ":" + password);
        return "{'name':'test'}";
    }


}
