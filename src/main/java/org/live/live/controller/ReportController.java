package org.live.live.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wang on 2017/4/29.
 */
@Controller
@RequestMapping("/live")
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class) ;

    /**
     * 进入举报页面
     * @return
     */
    @RequestMapping("/report/page")
    public String toReportPage() {

        return "live/report" ;
    }

    /**
     * 进入举报归档页面
     * @return
     */
    @RequestMapping("/report/page2")
    public String toReportHandledPage() {

        return "/live/report_handled" ;
    }


}
