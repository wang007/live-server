package org.live.live.vo;

import java.util.Date;

/**
 * Created by KAM on 2017/5/10.
 */
public class LiveRecordVo {
    private String id;
    private String recordNum;
    private String roomName;
    private String roomNum;
    private int maxOnlineCount;
    private Date startTime;
    private Date endTime;

    public LiveRecordVo() {
    }

    public LiveRecordVo(String id, String recordNum, String roomName, String roomNum, int maxOnlineCount, Date startTime, Date endTime) {
        this.id = id;
        this.recordNum = recordNum;
        this.roomName = roomName;
        this.roomNum = roomNum;
        this.maxOnlineCount = maxOnlineCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public int getMaxOnlineCount() {
        return maxOnlineCount;
    }

    public void setMaxOnlineCount(int maxOnlineCount) {
        this.maxOnlineCount = maxOnlineCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
