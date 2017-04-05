package org.live.school.controller;

import org.live.common.response.DataTableModel;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.common.utils.CopyPropertiesUtils;
import org.live.school.entity.Grade;
import org.live.school.entity.Major;
import org.live.school.service.GradeService;
import org.live.school.service.MajorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 班级控制器
 * Created by KAM on 2017/4/5.
 */
@RestController
@RequestMapping("school/")
public class GradeControlller {
    private static final String MODULE = "school";
    private static final Logger LOGGER = LoggerFactory.getLogger(GradeControlller.class);
    @Autowired
    private MajorService majorService;
    @Autowired
    private GradeService gradeService;

    /**
     * 跳转至班级管理页面
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "grade/page", method = RequestMethod.GET)
    public ModelAndView page(ModelAndView mv) {
        List<Major> majors = majorService.findAll();
        mv.addObject("majors", majors);
        mv.setViewName(MODULE + "/grade");
        return mv;
    }

    /**
     * 请求班级数据
     *
     * @param request
     * @return
     */
    @SystemLog(description = "请求班级数据", logLevel = LogLevel.WARN, operateType = OperateType.QUERY)
    @RequestMapping(value = "grade/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel data(HttpServletRequest request) {
        return gradeService.findPage(request);
    }


    /**
     * 添加班级记录
     *
     * @param grade
     * @return
     */
    @SystemLog(description = "添加班级记录", logLevel = LogLevel.WARN, operateType = OperateType.ADD)
    @RequestMapping(value = "grade", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> save(Grade grade) {
        grade.setCreateTime(new Date()); // 添加创建时间
        /** 保存班级记录 **/
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            model.setData(gradeService.save(grade));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加班级记录异常", e);
            model.error();
        }
        return model;
    }

    /**
     * 修改班级记录
     */
    @SystemLog(description = "修改班级记录", logLevel = LogLevel.WARN, operateType = OperateType.UPDATE)
    @RequestMapping(value = "grade", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> update(Grade grade) {
        Grade entity = null;
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            if (grade.getId() != null) {
                entity = gradeService.get(grade.getId()); // 取得原始记录
                CopyPropertiesUtils.copyPropertiesIgnoreNull(entity, grade); // 更新记录
            } else {
                /** id为空异常 **/
                model.error();
                return model;
            }

            /** 保存班级记录 **/
            model.setData(gradeService.save(entity));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加班级记录异常", e);
            model.error();
        }

        return model;
    }

    /**
     * 删除多个班级记录
     *
     * @return
     */
    @SystemLog(description = "删除多个班级记录", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
    @RequestMapping(value = "grade", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel del(@RequestParam(value = "ids[]") List<String> ids) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            for (String id : ids) {
                gradeService.delete(id);
            }
            model.success();
        } catch (Exception e) {
            LOGGER.error("删除班级记录异常", e);
            model.error();
        }
        return model;
    }
}
