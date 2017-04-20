package org.live.module.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.live.common.response.DataTableModel;
import org.live.module.demo.entity.Demo;
import org.live.module.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by KAM on 2017/3/28.
 */
@Controller
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

    @RequestMapping(value = "/data", method = RequestMethod.DELETE)
    @ResponseBody
    public String del(@RequestParam("ids[]") List<String> ids) {
        JSONObject jsonObject = new JSONObject();
        if (demoService.del(ids)) {
            jsonObject.put("serverResponseCode", 1001);
        } else {
            jsonObject.put("serverResponseCode", 1002);
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/record", method = RequestMethod.PUT)
    @ResponseBody
    public String edit(Demo demo) {
        System.out.println(demo.getName());
        return "success";
    }

    @RequestMapping(value = "/record", method = RequestMethod.POST)
    @ResponseBody
    public String insert(Demo demo) {
        System.out.println(demo.getName());
        return "success";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam("account") String accoount, @RequestParam("password") String password) {
        System.out.println(accoount + ":" + password);
        return "{'name':'test'}";
    }

    @Value("${system.localServerIp}")
    private String localServerIp ;

    @Value("${system.rtmpAddrPrefix}")
    private String rtmpAddrPrefix ;

    @RequestMapping("/demo")
    @ResponseBody
    public String demo() {

        System.out.println("localServerIp ---> "+ localServerIp) ;
        System.out.println("rtmpAddrPrefix --->"+ rtmpAddrPrefix) ;
        return null ;
    }

    /**
     * webcosket 测试
     * @return
     */
    @RequestMapping("/websocket")
    public String toWebSocketTest(HttpServletRequest request, String chatroom, String account, String nickname, String anchor) {

        request.setAttribute("chatroom", chatroom) ;
        request.setAttribute("account", account) ;
        request.setAttribute("nickname", nickname) ;
        if(anchor != null) request.setAttribute("anchor", 1) ;



        return "websocket_test" ;
    }

}
