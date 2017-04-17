package org.live.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.live.common.response.DataTableModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * dataTable订制工具
 * Created by KAM on 2017/3/25.
 */
public class DataTableUtils {
    /**
     * 解析出dataTable的相关参数，并将多个参数封装至Map中
     * * @param jsonObject json格式参数
     *
     * @return
     */
    public static Map<String, Object> parseMap(HttpServletRequest request) {
        JSONObject jsonObject = readJsonObject(request); // 将request映射成jsonObject
        Map<String, Object> params = new HashMap<String, Object>();
        Integer draw = jsonObject.getInteger("draw"); // 出于安全考虑所携带的参数，响应请求时应返回此参数
        JSONObject order = jsonObject.getJSONArray("order").getJSONObject(0); // 用于排序的相关参数
        Integer start = jsonObject.getInteger("start"); // 记录起始下标
        Integer length = jsonObject.getInteger("length"); // 单页记录长度
        JSONObject search = jsonObject.getJSONObject("search"); // 应用于所有列的过虑条件
        JSONArray columns = jsonObject.getJSONArray("columns"); // 字段基本信息数据

        Integer currentPage = (start + length - 1) / length; // 当前页数
        Sort.Direction direction = (StringUtils.equals(order.getString("dir"), "asc")) ? Sort.Direction.ASC : Sort.Direction.DESC; // 排序类型
        String sortBy = columns.getJSONObject(order.getInteger("column")).getString("name"); // 排序根据
        Sort sort = new Sort(direction, sortBy);

        PageRequest pageRequest = new PageRequest(currentPage, length, sort); // 映射请求分页请求参数
        Map<String, Object> filter = new LinkedHashMap<String, Object>(); // 有序map
        String searchVal = search.getString("value"); // 过滤参数

        if (StringUtils.isNotEmpty(searchVal)) {
            /** 有过滤参数 **/
            for (Object object : columns) {
                JSONObject column = (JSONObject) object;
                String name = column.getString("name");
               int index = name.indexOf(".");
                if (index != -1) {
                    name = name.substring(index+1);
                }
                filter.put(name, searchVal);
            }
        }
        params.put("searchVal",searchVal);
        params.put("draw", draw);
        params.put("pageRequest", pageRequest);
        params.put("filter", filter);

        return params;
    }

    /**
     * 解析Page,并将解析出的参数及dataTables相关约定参数封装至respose model中
     *
     * @param page
     * @param draw
     * @param recordsTotal
     * @return
     */
    public static DataTableModel parseDataTableModel(Page<?> page, Integer draw, Long recordsTotal) {
        Long recordsFiltered = page.getTotalElements(); // 过滤后的总记录数
        List<?> data = page.getContent(); // 数据
        DataTableModel model = new DataTableModel(draw, recordsTotal, recordsFiltered, data);
        return model;
    }

    /**
     * @return
     */
    public static JSONObject parseJSONObject(HttpServletRequest request) {
        return readJsonObject(request); // 请求参数映射成JSONObject类型
    }

    /**
     * 读取jsonObject
     *
     * @param request
     * @return
     */
    private static JSONObject readJsonObject(HttpServletRequest request) {
        StringBuilder jsonStr = new StringBuilder();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (JSONObject) JSON.parse(jsonStr.toString());
    }
}
