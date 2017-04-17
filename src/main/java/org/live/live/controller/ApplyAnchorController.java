package org.live.live.controller;

import org.live.common.response.DataTableModel;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.support.ServletContextHolder;
import org.live.common.systemlog.SystemLog;
import org.live.live.service.ApplyAnchorService;
import org.live.live.vo.ApplyInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wang on 2017/4/15.
 */
@Controller
@RequestMapping("/live")
public class ApplyAnchorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyAnchorController.class) ;

    @Resource
    private ApplyAnchorService applyAnchorService;

    /**
     * 进入主播申请表的管理界面
     *
     * @return
     */
    @SystemLog(description = "进入主播申请的管理界面")
    @RequestMapping("/apply/page")
    public String toApplyAnchorPage() {
        return "live/apply_anchor";
    }

    /**
     * 进入主播申请表的归档页面
     * @return
     */
    @SystemLog(description = "注入主播申请表的归档界面")
    @RequestMapping("/apply/page2")
    public String toApplyAnchorAuditedPage() {
        return "/live/apply_anchor_audited" ;
    }

    /**
     * 获取未审核主播申请表数据
     *
     * @return
     */
    @RequestMapping(value = "/apply/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel applyDataNotAudit(HttpServletRequest request) {
        return applyAnchorService.findByPage(request, false);
    }

    /**
     * 获取审核过的主播申请表数据
     * @param request
     * @return
     */
    @RequestMapping(value="/apply/data2", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel applyDataAudited(HttpServletRequest request) {
        return applyAnchorService.findByPage(request, true);
    }

    /**
     * 获取主播申请的详细信息
     * @return
     */
    @RequestMapping(value="/apply/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> getApplyAnchor(@PathVariable String id) {

        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            ApplyInfoVo applyInfo = applyAnchorService.getApplyInfo(id) ;
            model.setData(applyInfo) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error("获取主播申请详情异常", e) ;
            model.error() ;
        }
        return model ;
    }

    /**
     * 更改主播表的申请状态，如果申请通过，那么创建直播间，主播。 更新mobileUser的anchorFlag字段
     * @param applyId 申请表id
     * @param passFlag 审核是否通过的标志
     * @param reason 审核不通过的理由
     * @return
     */
    @RequestMapping(value="/apply/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> saveApplyPassFlag(@PathVariable("id") String applyId, Integer passFlag, String reason) {

        ResponseModel<Object> model = new SimpleResponseModel<>() ;
        try {
            applyAnchorService.saveApplyPassFlag(applyId, passFlag, reason) ;
            model.success("保存成功！") ;
        } catch (Exception e) {
            LOGGER.error("更新主播申请表异常", e) ;
            model.error("保存失败！") ;
        }
        return model ;
    }

}
