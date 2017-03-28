package org.live.common.component.inteceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Json拦截器，拦截携带json格式数据的ajax请求
 * Created by KAM on 2017/3/25.
 */
public class JsonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (hasJsonParam(httpServletRequest)) {
            httpServletRequest.setAttribute("jsonObject", readJsonObject(httpServletRequest)); // 请求参数映射成JSONObject类型
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 读取jsonObject
     *
     * @param request
     * @return
     */
    private JSONObject readJsonObject(HttpServletRequest request) {
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

    /**
     * 判断ajax请求contentType是否是"application/json"
     *
     * @param request
     * @return
     */
    private boolean hasJsonParam(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With"); //requestType的值为 null 则是普通的页面请求，值为 XMLHttpRequest则是ajax请求
        String contentType = request.getContentType();
        if (StringUtils.equals(requestType, "XMLHttpRequest") && StringUtils.equals(contentType, "application/json")) {
            return true;
        }
        return false;
    }
}
