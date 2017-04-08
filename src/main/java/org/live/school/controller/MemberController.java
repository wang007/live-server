package org.live.school.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.live.common.response.DataTableModel;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.common.utils.CopyPropertiesUtils;
import org.live.common.utils.DataTableUtils;
import org.live.school.entity.Grade;
import org.live.school.entity.Member;
import org.live.school.service.GradeService;
import org.live.school.service.MemberService;
import org.live.school.vo.MemberVo;
import org.live.school.vo.SimpleMemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 成员控制器
 * Created by KAM on 2017/4/6.
 */
@RestController
@RequestMapping("school/")
public class MemberController {
    private static final String MODULE = "school";
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    private GradeService gradeService;
    @Autowired
    private MemberService memberService;

    /**
     * 跳转至成员管理页面
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "member/page", method = RequestMethod.GET)
    public ModelAndView page(ModelAndView mv) {
        List<Grade> grades = gradeService.findAll();
        mv.addObject("grades", grades);
        mv.setViewName(MODULE + "/member");
        return mv;
    }

    /**
     * 根据姓名查询记录
     *
     * @param searchVal
     * @return
     */
    @RequestMapping(value = "member/realName", method = RequestMethod.GET)
    @ResponseBody
    public List<SimpleMemberVo> find(@RequestParam(value = "searchVal") String searchVal) {
        return memberService.findByRealName(searchVal);
    }

    /**
     * 请求成员数据
     *
     * @param request
     * @return
     */
    @SystemLog(description = "请求成员数据", logLevel = LogLevel.WARN, operateType = OperateType.QUERY)
    @RequestMapping(value = "member/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel data(HttpServletRequest request) {
        return memberService.findPage(request);
    }

    /**
     * 添加成员记录
     *
     * @param member
     * @return
     */
    @SystemLog(description = "添加成员记录", logLevel = LogLevel.WARN, operateType = OperateType.ADD)
    @RequestMapping(value = "member", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> save(Member member) {
        String id = member.getGrade().getId();
        if (StringUtils.isEmpty(id)) {
            member.setGrade(null);
        }
        member.setRegisterDate(new Date()); // 添加创建时间
        /** 保存成员记录 **/
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            model.setData(memberService.save(member));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加成员记录异常", e);
            model.error();
        }
        return model;
    }

    /**
     * 修改成员记录
     */
    @SystemLog(description = "修改成员记录", logLevel = LogLevel.WARN, operateType = OperateType.UPDATE)
    @RequestMapping(value = "member", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> update(Member member) {
        String id = member.getGrade().getId();
        if (StringUtils.isEmpty(id)) {
            member.setGrade(null);
        }
        Member entity = null;
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            if (member.getId() != null) {
                entity = memberService.get(member.getId()); // 取得原始记录
                CopyPropertiesUtils.copyPropertiesIgnoreNull(entity, member); // 更新记录
            } else {
                /** id为空异常 **/
                model.error();
                return model;
            }

            /** 保存成员记录 **/
            model.setData(memberService.save(entity));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加成员记录异常", e);
            model.error();
        }

        return model;
    }

    /**
     * 删除多个成员记录
     *
     * @return
     */
    @SystemLog(description = "删除多个成员记录", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
    @RequestMapping(value = "member", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel del(@RequestParam(value = "ids[]") List<String> ids) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            for (String id : ids) {
                memberService.delete(id);
            }
            model.success();
        } catch (Exception e) {
            LOGGER.error("删除成员记录异常", e);
            model.error();
        }
        return model;
    }
}
