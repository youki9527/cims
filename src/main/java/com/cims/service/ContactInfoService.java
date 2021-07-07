package com.cims.service;



import com.cims.pojo.ClientInfo;
import com.cims.pojo.ContactInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface ContactInfoService {


    //生日查询
    HashMap<String,Object> selectByBirth(ClientInfo client, HttpServletRequest request);

    //查询
    HashMap<String, Object> select(ContactInfo contact, HttpServletRequest request);


    //根据id查询
    ContactInfo selectByCId(ContactInfo contact);

    //修改
    String update(ContactInfo contact);

    //删除
    String del(ContactInfo contact);


    //注册
    String saveAdd(ContactInfo contact);

    //邮件发送
    HashMap<String,Object> sendEmail(String toEmail, String name, String value, HttpServletRequest request);
}
