package org.live.dictionary.controller;


import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;
import org.live.dictionary.service.DictService;
import org.live.dictionary.service.DictTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/dict")
@Controller
public class DictController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DictController.class);

	@Resource
	private DictService dictService;
	@Resource
	private DictTypeService dictTypeService;

	/**
	 * 跳转至字典管理页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dictionary/page", method = RequestMethod.GET)
	public ModelAndView page(ModelAndView model) {
		List<DictType> dictTypes = dictTypeService.findAll();// 查询所有字典类型
		List<Dictionary> dicts = dictService.findAll();// 查询所有字典记录
		model.addObject("dictTypes", dictTypes);
		model.addObject("dicts", dicts);
		model.setViewName("dict/dictionary");
		return model;
	}

	/**
	 * 保存字典
	 */
	@SystemLog(description = "添加字典", logLevel = LogLevel.WARN, operateType = OperateType.ADD)
	@RequestMapping(value = "/dictionary", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Object> save(Dictionary dict) {
		/** 需要保存的参数 **/
		Dictionary entity = new Dictionary();
		entity.setDictType(dict.getDictType());
		entity.setDictMark(dict.getDictMark());
		entity.setDictValue(dict.getDictValue());
		entity.setRemarks(dict.getRemarks());

		/** 保存字典类型 **/
		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			model.setData(dictService.save(entity));
			model.success();
		} catch (Exception e) {
			LOGGER.error("添加字典异常", e);
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
	@SystemLog(description = "删除字典", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
	@RequestMapping(value = "/dictionary/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseModel<Object> del(@PathVariable("id") String id) {

		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			dictService.delete(id);
			model.success();
		} catch (Exception e) {
			LOGGER.error("删除字典异常", e);
			model.error();
		}
		return model;
	}

	/**
	 * 修改字典
	 */
	@SystemLog(description = "修改字典", logLevel = LogLevel.WARN, operateType = OperateType.UPDATE)
	@RequestMapping(value = "/dictionary", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseModel<Object> update(HttpServletRequest request, Dictionary dict) {
		Dictionary entity = null;
		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			/** 需要保存的参数 **/
			if (dict.getId() != null) {
				entity = dictService.get(dict.getId()); // 取得原始记录

				/** 更新记录 **/
				entity.setDictType(dict.getDictType());
				entity.setDictMark(dict.getDictMark());
				entity.setDictValue(dict.getDictValue());
				entity.setRemarks(dict.getRemarks());
			} else {
				/** id为空异常 **/
				model.error();
				return model;
			}

			/** 保存字典 **/
			model.setData(dictService.save(entity));
			model.success();
		} catch (Exception e) {
			LOGGER.error("添加字典异常", e);
			model.error();
		}

		return model;
	}

	/**
	 * 删除所有字典信息
	 * 
	 * @param ids
	 * @return
	 */
	@SystemLog(description = "删除多个字典", logLevel = LogLevel.ERROR, operateType = OperateType.DELETE)
	@RequestMapping(value = "/dictionary/all", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseModel<Object> del(@RequestParam(value = "ids[]") String[] ids) {
		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			for (String id : ids) {
				dictService.delete(id);
			}
			model.success();
		} catch (Exception e) {
			LOGGER.error("删除字典类型异常", e);
			model.error();
		}
		return model;
	}

	/**
	 * 刷新字典缓存
	 * 
	 * @param id
	 * @return
	 */
	@SystemLog(description = "缓存字典", logLevel = LogLevel.INFO, operateType = OperateType.QUERY)
	@RequestMapping(value = "/dictionary/cache", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel<Object> refresh() {

		ResponseModel<Object> model = new SimpleResponseModel<Object>();
		try {
			dictService.getCacheDic(); // 缓存字典
			model.success();
		} catch (Exception e) {
			LOGGER.error("缓存字典异常", e);
			model.error();
		}
		return model;
	}
}
