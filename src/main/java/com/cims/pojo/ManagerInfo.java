package com.cims.pojo;

import java.io.Serializable;

public class ManagerInfo  extends MyPage implements Serializable {

    //描述经理id
    private int MId;
   //描述经理登录名
    private String MLogin;
    //描述经理密码
    private String MPwd;
    //描述经理盐值
    private String MSalt;
    //描述经理姓名
    private String MName;
    //描述经理照片
    private String MPic;
    //描述经理性别
    private String MSex;
    //描述经理生日
    private  String MBirth;
    //描述经理电话
    private String MTel;
    //描述经理邮箱
    private String MEmail;
    //描述经理学历
    private String MDegree;
    //描述经理身份证号码
    private  String MNo;
    //描述经理备注信息
    private String MRemark;

    //描述修改密码的旧密码
    private String oldMPwd;
    //描述修改密码的新密码
    private String newMPwd;


    //描述查询条件codition的参数
    private String condition="";
    //描述查询条件conValue的参数
    private String conValue="";

    public String getOldMPwd() {
        return oldMPwd;
    }

    public void setOldMPwd(String oldMPwd) {
        this.oldMPwd = oldMPwd;
    }

    public String getNewMPwd() {
        return newMPwd;
    }

    public void setNewMPwd(String newMPwd) {
        this.newMPwd = newMPwd;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

    public String getMLogin() {
        return MLogin;
    }

    public void setMLogin(String MLogin) {
        this.MLogin = MLogin;
    }

    public int getMId() {
        return MId;
    }

    public void setMId(int MId) {
        this.MId = MId;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getMPwd() {
        return MPwd;
    }

    public void setMPwd(String MPwd) {
        this.MPwd = MPwd;
    }

    public String getMSalt() {
        return MSalt;
    }

    public void setMSalt(String MSalt) {
        this.MSalt = MSalt;
    }

    public String getMPic() {
        return MPic;
    }

    public void setMPic(String MPic) {
        this.MPic = MPic;
    }

    public String getMSex() {
        return MSex;
    }

    public void setMSex(String MSex) {
        this.MSex = MSex;
    }

    public String getMBirth() {
        return MBirth;
    }

    public void setMBirth(String MBirth) {
        this.MBirth = MBirth;
    }

    public String getMTel() {
        return MTel;
    }

    public void setMTel(String MTel) {
        this.MTel = MTel;
    }

    public String getMEmail() {
        return MEmail;
    }

    public void setMEmail(String MEmail) {
        this.MEmail = MEmail;
    }

    public String getMDegree() {
        return MDegree;
    }

    public void setMDegree(String MDegree) {
        this.MDegree = MDegree;
    }

    public String getMNo() {
        return MNo;
    }

    public void setMNo(String MNo) {
        this.MNo = MNo;
    }

    public String getMRemark() {
        return MRemark;
    }

    public void setMRemark(String MRemark) {
        this.MRemark = MRemark;
    }
}
