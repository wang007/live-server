package org.live.common.response;

import java.util.List;

/**
 * 为dataTable插件订制的response model
 * Created by KAM on 2017/3/25.
 */
public class DataTableModel {


    private Integer draw; // 必要属性；与dataTable请求时发送的draw参数一致，出于安全考虑
    private Long recordsTotal; // 必要属性；没有过滤的记录数，表的总记录数
    private Long recordsFiltered; // 必要属性；过滤后的记录数，若无过滤则与recordsTotal一致
    private List<?> data; // 必要属性；返回至前台显示的数据记录
    private String error; // 可选属性；用于描述后台发送的错误

    public DataTableModel(Integer draw, Long recordsTotal, Long recordsFiltered, List<?> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }


    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
