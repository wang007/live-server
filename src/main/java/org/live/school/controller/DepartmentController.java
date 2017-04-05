package org.live.school.controller;

import org.live.common.response.DataTableModel;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.common.utils.CopyPropertiesUtils;
import org.live.school.entity.Department;
import org.live.school.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 系部控制器
 * Created by KAM on 2017/4/4.
 */
@RestController
@RequestMapping("school/")
public class DepartmentController {
    private static final String MODULE = "school";
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService departmentService;

    /**
     * 跳转至系部管理页面
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "department/page", method = RequestMethod.GET)
    public ModelAndView page(ModelAndView mv) {
        mv.setViewName(MODULE + "/department");
        return mv;
    }

    /**
     * 请求系部分页数据
     *
     * @param request
     * @return
     */
    @SystemLog(description = "请求系部数据", logLevel = LogLevel.WARN, operateType = OperateType.QUERY)
    @RequestMapping(value = "department/data", method = RequestMethod.POST)
    @ResponseBody
    public DataTableModel data(HttpServletRequest request) {
        return departmentService.findPage(request);
    }

    /**
     * 删除多个系部记录
     *
     * @return
     */
    @SystemLog(description = "删除多个系部记录", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
    @RequestMapping(value = "department", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel del(@RequestParam(value = "ids[]") List<String> ids) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            for (String id : ids) {
                departmentService.delete(id);
            }
            model.success();
        } catch (Exception e) {
            LOGGER.error("删除系部记录异常", e);
            model.error();
        }
        return model;
    }

    /**
     * 保存系部记录
     */
    @SystemLog(description = "添加系部记录", logLevel = LogLevel.WARN, operateType = OperateType.ADD)
    @RequestMapping(value = "department", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> save(Department department) {

        department.setCreateTime(new Date());
        /** 保存系部记录 **/
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            model.setData(departmentService.save(department));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加系部记录异常", e);
            model.error();
        }

        return model;
    }

    /**
     * 修改系部记录
     */
    @SystemLog(description = "修改系部记录", logLevel = LogLevel.WARN, operateType = OperateType.UPDATE)
    @RequestMapping(value = "department", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> update(Department department) {
        Department entity = null;
        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            /** 需要保存的参数 **/
            if (department.getId() != null) {
                entity = departmentService.get(department.getId()); // 取得原始记录
                CopyPropertiesUtils.copyPropertiesIgnoreNull(entity, department); // 更新记录
            } else {
                /** id为空异常 **/
                model.error();
                return model;
            }
            /** 保存系部记录 **/
            model.setData(departmentService.save(entity));
            model.success();
        } catch (Exception e) {
            LOGGER.error("添加系部异常", e);
            model.error();
        }
        return model;
    }
}
