package org.live.module.demo.service;

import org.live.common.response.DataTableModel;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by KAM on 2017/3/28.
 */
public interface DemoService {
    DataTableModel findPage(HttpServletRequest request);
}
