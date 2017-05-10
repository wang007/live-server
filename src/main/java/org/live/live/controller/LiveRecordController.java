package org.live.live.controller;

import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.live.service.LiveRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 直播记录控制器
 * Created by KAM on 2017/5/10.
 */
@Controller
@RequestMapping("/live")
public class LiveRecordController {
    private final String MODULE = "live";
    private static final Logger LOGGER = LoggerFactory.getLogger(LiveRecordController.class);
    @Autowired
    private LiveRecordService liveRecordService;

    /**
     * 跳转直播记录查看页面
     *
     * @return
     */
    @RequestMapping(value = "/liveRecord/page")
    public String page() {
        return MODULE + "/live_record";
    }

    /**
     * 请求直播记录数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/liveRecord/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel data(HttpServletRequest request) {
        return liveRecordService.findLiveRecordByPage(DataTableUtils.parseMap(request));
    }
}
