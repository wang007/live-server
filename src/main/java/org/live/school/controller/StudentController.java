package org.live.school.controller;

import org.live.common.response.DataTableModel;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.common.utils.CopyPropertiesUtils;
import org.live.school.entity.Grade;
import org.live.school.entity.Student;
import org.live.school.service.GradeService;
import org.live.school.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by KAM on 2017/4/6.
 */
@RestController
@RequestMapping("school/")
public class StudentController {
    private static final String MODULE = "school";
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private GradeService gradeService;
    @Autowired
    private StudentService studentService;

    /**
     * 跳转至学生管理页面
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "student/page", method = RequestMethod.GET)
    public ModelAndView page(ModelAndView mv) {
        List<Grade> grades = gradeService.findAll();
        mv.addObject("grades", grades);
        mv.setViewName(MODULE + "/student");
        return mv;
    }

    /**
     * 请求学生数据
     *
     * @param request
     * @return
     */
    @SystemLog(description = "请求学生数据", logLevel = LogLevel.WARN, operateType = OperateType.QUERY)
    @RequestMapping(value = "student/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel data(HttpServletRequest request) {
        return studentService.findPage(request);
    }

    /**
     * 添加学生记录
     *
     * @param student
     * @return
     */
    @SystemLog(description = "添加学生记录", logLevel = LogLevel.WARN, operateType = OperateType.ADD)
    @RequestMapping(value = "student", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> save(Student student) {
        student.setRegisterDate(new Date()); // 添加创建时间
        /** 保存学生记录 **/
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            model.setData(studentService.save(student));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加学生记录异常", e);
            model.error();
        }
        return model;
    }

    /**
     * 修改学生记录
     */
    @SystemLog(description = "修改学生记录", logLevel = LogLevel.WARN, operateType = OperateType.UPDATE)
    @RequestMapping(value = "student", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> update(Student student) {
        Student entity = null;
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            if (student.getId() != null) {
                entity = studentService.get(student.getId()); // 取得原始记录
                CopyPropertiesUtils.copyPropertiesIgnoreNull(entity, student); // 更新记录
            } else {
                /** id为空异常 **/
                model.error();
                return model;
            }

            /** 保存学生记录 **/
            model.setData(studentService.save(entity));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加学生记录异常", e);
            model.error();
        }

        return model;
    }

    /**
     * 删除多个学生记录
     *
     * @return
     */
    @SystemLog(description = "删除多个学生记录", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
    @RequestMapping(value = "student", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel del(@RequestParam(value = "ids[]") List<String> ids) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            for (String id : ids) {
                studentService.delete(id);
            }
            model.success();
        } catch (Exception e) {
            LOGGER.error("删除学生记录异常", e);
            model.error();
        }
        return model;
    }
}
