package com.cims.pojo;
//描述表admininfo的信息
public class AdminInfo extends MyPage{
    //描述AId列的
    private int AId;
    //描述AName列的
    private String AName;
    //描述APwd列的
    private String APwd;
    //描述ASalt列的
    private String ASalt;
    //描述AEmail列的
    private String AEmail;

    //描述查询条件condition的参数
    private String condition;
    //描述查询条件conValue的参数
    private String conValue;

    //描述修改密码的旧密码
    private String oldAPwd;
    //描述修改密码的新密码
    private String newAPwd;

    public int getAId() {
        return AId;
    }

    public void setAId(int AId) {
        this.AId = AId;
    }

    public String getAName() {
        return AName;
    }

    public void setAName(String AName) {
        this.AName = AName;
    }

    public String getAPwd() {
        return APwd;
    }

    public void setAPwd(String APwd) {
        this.APwd = APwd;
    }

    public String getASalt() {
        return ASalt;
    }

    public void setASalt(String ASalt) {
        this.ASalt = ASalt;
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

    public String getOldAPwd() {
        return oldAPwd;
    }

    public void setOldAPwd(String oldAPwd) {
        this.oldAPwd = oldAPwd;
    }

    public String getNewAPwd() {
        return newAPwd;
    }

    public void setNewAPwd(String newAPwd) {
        this.newAPwd = newAPwd;
    }
}

