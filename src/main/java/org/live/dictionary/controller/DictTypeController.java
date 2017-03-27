package org.live.dictionary.controller;


import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.service.DictTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/dict")
@Controller
public class DictTypeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DictTypeController.class);

	@Resource
	private DictTypeService dictTypeService;

	/**
	 * 跳转至字典类型管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dictType/page", method = RequestMethod.GET)
	public ModelAndView page(ModelAndView model) {
		model.addObject("dictTypes", dictTypeService.findAll()); // 查询所有字典类型
		model.setViewName("dict/dict_type");
		return model;
	}

	/**
	 * 保存字典类型
	 */
	@SystemLog(description = "添加字典类型", logLevel = LogLevel.WARN, operateType = OperateType.ADD)
	@RequestMapping(value = "/dictType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Object> save(DictType dt) {
		/** 需要保存的参数 **/
		DictType entity = new DictType();
		entity.setTypeName(dt.getTypeName());
		entity.setDictTypeMark(dt.getDictTypeMark());
		entity.setDescription(dt.getDescription());

		/** 保存字典类型 **/
		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			model.setData(dictTypeService.save(entity));
			model.success();
		} catch (Exception e) {
			LOGGER.error("添加字典类型异常", e);
			model.error();
		}

		return model;
	}

	/**
	 * 删除字典信息
	 * 
	 * @param id
	 * @return
	 */
	@SystemLog(description = "删除字典类型", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
	@RequestMapping(value = "/dictType/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseModel<Object> del(@PathVariable("id") String id) {

		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			dictTypeService.delete(id);
			model.success();
		} catch (Exception e) {
			LOGGER.error("删除字典类型异常", e);
			model.error();
		}
		return model;
	}

	/**
	 * 修改字典类型
	 */
	@SystemLog(description = "修改字典类型", logLevel = LogLevel.WARN, operateType = OperateType.UPDATE)
	@RequestMapping(value = "/dictType", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseModel<Object> update(HttpServletRequest request, DictType dt) {
		DictType entity = null;
		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			/** 需要保存的参数 **/
			if (dt.getId() != null) {
				entity = dictTypeService.get(dt.getId()); // 取得原始记录

				/** 更新记录 **/
				entity.setTypeName(dt.getTypeName());
				entity.setDictTypeMark(dt.getDictTypeMark());
				entity.setDescription(dt.getDescription());
			} else {
				/** id为空异常 **/
				model.error();
				return model;
			}

			/** 保存字典类型 **/
			model.setData(dictTypeService.save(entity));
			model.success();
		} catch (Exception e) {
			LOGGER.error("添加字典类型异常", e);
			model.error();
		}

		return model;
	}

	/**
	 * 删除所有字典类型信息
	 * 
	 * @param ids
	 * @return
	 */
	@SystemLog(description = "删除多个字典类型", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
	@RequestMapping(value = "/dictType/all", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseModel<Object> del(@RequestParam(value = "ids[]") String[] ids) {
		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			for (String id : ids) {
				dictTypeService.delete(id);
			}
			model.success();
		} catch (Exception e) {
			LOGGER.error("删除字典类型异常", e);
			model.error();
		}
		return model;
	}
}
