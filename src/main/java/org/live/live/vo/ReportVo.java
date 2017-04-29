package org.live.live.vo;


import java.util.Date;

/**
 * Created by wang on 2017/4/29.
 */
public class ReportVo {

    private String id;
    /**
     * 记录号
     */
    private String reportNum;

    /**
     * 举报人的账号
     */
    private String reporterNum;

    /**
     * 举报人的昵称
     */
    private String reportName;

    /**
     * 被举报的直播间号
     */
    private String liveRoomNum;

    /**
     * 被举报的房间名
     */
    private String liveRoomName;

    /**
     * 举报时间
     */
    private Date createTime;

    /**
     * 处理状态：true: 已处理，false：未处理
     */
    private boolean handleType;

    public ReportVo() {
    }

    public ReportVo(String id, String reportNum, String reporterNum, String reportName, String liveRoomNum, String liveRoomName, Date createTime, boolean handleType) {
        this.id = id;
        this.reportNum = reportNum;
        this.reporterNum = reporterNum;
        this.reportName = reportName;
        this.liveRoomNum = liveRoomNum;
        this.liveRoomName = liveRoomName;
        this.createTime = createTime;
        this.handleType = handleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }

    public String getReporterNum() {
        return reporterNum;
    }

    public void setReporterNum(String reporterNum) {
        this.reporterNum = reporterNum;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getLiveRoomNum() {
        return liveRoomNum;
    }

    public void setLiveRoomNum(String liveRoomNum) {
        this.liveRoomNum = liveRoomNum;
    }

    public String getLiveRoomName() {
        return liveRoomName;
    }

    public void setLiveRoomName(String liveRoomName) {
        this.liveRoomName = liveRoomName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isHandleType() {
        return handleType;
    }

    public void setHandleType(boolean handleType) {
        this.handleType = handleType;
    }
}
