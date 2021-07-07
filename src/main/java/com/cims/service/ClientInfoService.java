package com.cims.service;



import com.cims.pojo.ClientInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface ClientInfoService {
    //查询
    HashMap<String,Object> select(ClientInfo client, HttpServletRequest request);

    //生日查询
    HashMap<String,Object> selectByBirth(ClientInfo client, HttpServletRequest request);

    //根据CId查询
    ClientInfo selectByCId(ClientInfo client);

    //注册
    String zhuce(ClientInfo client, HttpServletRequest request);
    //修改
    String update(ClientInfo client, HttpServletRequest request);
    //邮件发送
    HashMap<String,Object> sendEmail(String toEmail, String name, String value, HttpServletRequest request);
    //删除
    String delCMid(ClientInfo client);
    //修改密码
  //  String updatePwd(ClientInfo client, HttpServletRequest request);
    //excel导出
    void excelWrite(ClientInfo client, HttpServletResponse response, HttpServletRequest request);
}
