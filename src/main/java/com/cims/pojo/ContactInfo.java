package com.cims.pojo;

public class ContactInfo extends MyPage{
    private int CId;
    private String CName;
    private  String CBirth;
    private String CTel;
    private String CAdd;
    private String CSex;
    private String CEmail;
    private int CMid;
    //描述查询条件codition的参数
    private String condition="";
    //描述查询条件conValue的参数
    private String conValue="";

    public String getCBirth() {
        return CBirth;
    }

    public void setCBirth(String CBirth) {
        this.CBirth = CBirth;
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

    public String getCTel() {
        return CTel;
    }

    public void setCTel(String CTel) {
        this.CTel = CTel;
    }

    public String getCAdd() {
        return CAdd;
    }

    public void setCAdd(String CAdd) {
        this.CAdd = CAdd;
    }

    public String getCSex() {
        return CSex;
    }

    public void setCSex(String CSex) {
        this.CSex = CSex;
    }

    public String getCEmail() {
        return CEmail;
    }

    public void setCEmail(String CEmail) {
        this.CEmail = CEmail;
    }

    public int getCMid() {
        return CMid;
    }

    public void setCMid(int CMid) {
        this.CMid = CMid;
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
}
