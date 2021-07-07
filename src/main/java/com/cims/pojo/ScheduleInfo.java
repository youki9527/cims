package com.cims.pojo;

public class ScheduleInfo extends MyPage {

    private int ScheduleId;
    private  String  ScheduleDate;
    private  String ScheduleTime;
    private  int ScheduleMId;
    private  String ScheduleMName;
    private  int ScheduleUserId;
    private  String ScheduleUserName;
    private  String ScheduleUaddress;
    private  String ScheduleUPNumber;
    private  String ScheduleSType="";
    private  int ScheduleStatus;

    //开始日期
    private  String startDay="";
    //结束日期
    private  String endDay="";

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getScheduleDate() {
        return ScheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        ScheduleDate = scheduleDate;
    }

    public String getScheduleUaddress() {
        return ScheduleUaddress;
    }

    public void setScheduleUaddress(String scheduleUaddress) {
        ScheduleUaddress = scheduleUaddress;
    }

    public String getScheduleUPNumber() {
        return ScheduleUPNumber;
    }

    public void setScheduleUPNumber(String scheduleUPNumber) {
        ScheduleUPNumber = scheduleUPNumber;
    }

    public int getScheduleStatus() {
        return ScheduleStatus;
    }

    public void setScheduleStatus(int scheduleStatus) {
        ScheduleStatus = scheduleStatus;
    }

    public int getScheduleId() {
        return ScheduleId;
    }

    public void setScheduleId(int scheduleId) {
        ScheduleId = scheduleId;
    }

    public String getScheduleTime() {
        return ScheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        ScheduleTime = scheduleTime;
    }

    public int getScheduleMId() {
        return ScheduleMId;
    }

    public void setScheduleMId(int scheduleMId) {
        ScheduleMId = scheduleMId;
    }

    public String getScheduleMName() {
        return ScheduleMName;
    }

    public void setScheduleMName(String scheduleMName) {
        ScheduleMName = scheduleMName;
    }

    public String getScheduleUserName() {
        return ScheduleUserName;
    }

    public void setScheduleUserName(String scheduleUserName) {
        ScheduleUserName = scheduleUserName;
    }

    public int getScheduleUserId() {
        return ScheduleUserId;
    }

    public void setScheduleUserId(int scheduleUserId) {
        ScheduleUserId = scheduleUserId;
    }

    public String getScheduleSType() {
        return ScheduleSType;
    }

    public void setScheduleSType(String scheduleSType) {
        ScheduleSType = scheduleSType;
    }
}
