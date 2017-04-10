package org.live.live.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wang on 2017/4/10.
 */
@Controller
@RequestMapping("/live")
public class AnchorController {

    /**
     *  跳转到主播管理界面
     * @return
     */
    @RequestMapping("/anchor/page")
    public String toAnchorPage() {
        return "live/anchor" ;
    }

}
