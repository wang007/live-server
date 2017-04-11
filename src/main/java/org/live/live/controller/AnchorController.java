package org.live.live.controller;

import org.live.common.page.JqGridModel;
import org.live.common.page.PageUtils;
import org.live.common.systemlog.SystemLog;
import org.live.live.service.AnchorService;
import org.live.live.vo.AnchorVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wang on 2017/4/10.
 */
@Controller
@RequestMapping("/live")
public class AnchorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnchorController.class) ;

    @Resource
    private AnchorService service ;

    /**
     *  跳转到主播管理界面
     * @return
     */
    @SystemLog(description = "进入主播管理界面")
    @RequestMapping("/anchor/page")
    public String toAnchorPage() {
        return "live/anchor" ;
    }

    /**
     *  获取主播的数据
     * @param request
     * @return
     */
    @RequestMapping(value="/anchor", method = RequestMethod.GET)
    @ResponseBody
    public JqGridModel<AnchorVo> findAnchors(HttpServletRequest request, String searchStr) {
        try {
            PageRequest page = PageUtils.getPage4JqGrid(request) ;
            Page<AnchorVo> pageResult =service.findAnchors(page, searchStr) ;
            JqGridModel<AnchorVo> model = PageUtils.pageConvertJqGrid(pageResult) ;
            return model ;
        } catch (Exception e) {
            LOGGER.error("查询主播信息异常", e) ;

        }
        return null ;
    }





}
