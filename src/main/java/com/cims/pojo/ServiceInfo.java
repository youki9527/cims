package com.cims.pojo;
import java.io.Serializable;
public class ServiceInfo extends MyPage  implements Serializable {

    //描述服务编号
    private  int SId;
    //描述服务类型
    private  String SType="";
    //描述服务日期
    private String SDate;
    //描述服务日期
    private String STime;
    //描述服务的客户名
    private String SUserName;
    //描述经理姓名
    private String SManagerName;
    //描述服务的客户id
    private int SUserId;
    //描述经理id
    private int SManagerId;
    //描述服务预计花费
    private String SCost;
    //描述服务内容
    private String SContent;
    //描述客户反馈意见
    private String SFback;
    //描述客户满意度
    private  String SGrade;
    //描述客户意见是否反馈
    private int SFbackStatus;

    //开始日期
    private  String startDay="";
    //结束日期
    private  String endDay="";
    //按客户姓名还是服务编号查询
    private  String idOrName="";
    //描述查询条件conValue的参数
    private String SIdOrUserName="";

    public int getSFbackStatus() {
        return SFbackStatus;
    }

    public void setSFbackStatus(int SFbackStatus) {
        this.SFbackStatus = SFbackStatus;
    }

    public String getIdOrName() {
        return idOrName;
    }

    public void setIdOrName(String idOrName) {
        this.idOrName = idOrName;
    }

    public String getSIdOrUserName() {
        return SIdOrUserName;
    }

    public void setSIdOrUserName(String SIdOrUserName) {
        this.SIdOrUserName = SIdOrUserName;
    }



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

    public String getSTime() {
        return STime;
    }

    public void setSTime(String STime) {
        this.STime = STime;
    }

    public int getSUserId() {
        return SUserId;
    }

    public void setSUserId(int SUserId) {
        this.SUserId = SUserId;
    }

    public int getSManagerId() {
        return SManagerId;
    }

    public void setSManagerId(int SManagerId) {
        this.SManagerId = SManagerId;
    }

    public String getSUserName() {
        return SUserName;
    }

    public void setSUserName(String SUserName) {
        this.SUserName = SUserName;
    }

    public String getSManagerName() {
        return SManagerName;
    }

    public void setSManagerName(String SManagerName) {
        this.SManagerName = SManagerName;
    }

    public int getSId() {
        return SId;
    }

    public void setSId(int SId) {
        this.SId = SId;
    }

    public String getSType() {
        if (SType==null)
            return "";
        return SType;
    }

    public void setSType(String SType) {
        this.SType = SType;
    }

    public String getSDate() {
        return SDate;
    }

    public void setSDate(String SDate) {
        this.SDate = SDate;
    }

    public String getSCost() {
        return SCost;
    }

    public void setSCost(String SCost) {
        this.SCost = SCost;
    }

    public String getSContent() {
        return SContent;
    }

    public void setSContent(String SContent) {
        this.SContent = SContent;
    }

    public String getSFback() {
        return SFback;
    }

    public void setSFback(String SFback) {
        this.SFback = SFback;
    }

    public String getSGrade() {
        return SGrade;
    }

    public void setSGrade(String SGrade) {
        this.SGrade = SGrade;
    }
}
