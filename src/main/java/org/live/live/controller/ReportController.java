package org.live.live.controller;

import org.live.common.response.DataTableModel;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.utils.DataTableUtils;
import org.live.live.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wang on 2017/4/29.
 */
@Controller
@RequestMapping("/live")
public class ReportController {

    @Autowired
    private ReportService reportService;


    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    /**
     * 进入举报页面
     *
     * @return
     */
    @RequestMapping("/report/page")
    public String toReportPage() {
        return "live/report";
    }

    /**
     * 查询举报数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/report/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel getData(HttpServletRequest request) {
        Map<String, Object> params = DataTableUtils.parseMap(request);
        return reportService.findReportByPage(params, false); // 查询记录
    }

    /**
     * 查询归档举报数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/report/handled/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel getData2(HttpServletRequest request) {
        Map<String, Object> params = DataTableUtils.parseMap(request);
        return reportService.findReportByPage(params, true); // 查询记录
    }

    /**
     * 进入举报归档页面
     *
     * @return
     */
    @RequestMapping("/report/page2")
    public String toReportHandledPage() {
        return "/live/report_handled";
    }

    /**
     * 处理举报
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/report", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel handlerReport(String id) {
        ResponseModel model = new SimpleResponseModel();
        try {
            reportService.updateReport(id);
            model.success();
        } catch (Exception e) {
            model.error();
            model.setMessage(e.getMessage());
        }
        return model;
    }

}
