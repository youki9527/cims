package com.cims.pojo;


import java.io.Serializable;

public class ClientInfo extends MyPage implements  Serializable {

    private int CId;
    private  String CName;
    private String CPic;
    private String CSex;
    private  String CBirth;
    private  String CTel;
    private  String CEmail;
    private  String CDegree;
    private String CNo;
    private String CAdd;
    private String CRemark;
    private int CMid;


    //描述查询条件conValue的参数
    private String conValue="";
    //描述查询条件condition的参数
    private String condition="";




    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    public int getCMid() {
        return CMid;
    }

    public void setCMid(int CMid) {
        this.CMid = CMid;
    }

    public int getCId() {
        return CId;
    }

    public void setCId(int CId) {
        this.CId = CId;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getCPic() {
        return CPic;
    }

    public void setCPic(String CPic) {
        this.CPic = CPic;
    }

    public String getCSex() {
        return CSex;
    }

    public void setCSex(String CSex) {
        this.CSex = CSex;
    }

    public String getCBirth() {
        return CBirth;
    }

    public void setCBirth(String CBirth) {
        this.CBirth = CBirth;
    }

    public String getCTel() {
        return CTel;
    }

    public void setCTel(String CTel) {
        this.CTel = CTel;
    }

    public String getCEmail() {
        return CEmail;
    }

    public void setCEmail(String CEmail) {
        this.CEmail = CEmail;
    }

    public String getCDegree() {
        return CDegree;
    }

    public void setCDegree(String CDegree) {
        this.CDegree = CDegree;
    }

    public String getCNo() {
        return CNo;
    }

    public void setCNo(String CNo) {
        this.CNo = CNo;
    }

    public String getCAdd() {
        return CAdd;
    }

    public void setCAdd(String CAdd) {
        this.CAdd = CAdd;
    }

    public String getCRemark() {
        return CRemark;
    }

    public void setCRemark(String CRemark) {
        this.CRemark = CRemark;
    }
}
