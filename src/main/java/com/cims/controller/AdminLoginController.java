package com.cims.controller;


import com.cims.pojo.AdminInfo;
import com.cims.pojo.ManagerInfo;
import com.cims.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.logging.Logger;


//初始登录界面

@Controller
@RequestMapping("/loginAdmin")
public class AdminLoginController {


    //创建一个adminInfoService的实现类对象
    @Autowired
    AdminInfoService adminInfoService;


    //处理邮件发送的请求
    @RequestMapping("/sendEmail")
    @ResponseBody
    public HashMap<String,Object> sendEmail(String email, HttpServletRequest request){
        return adminInfoService.sendCode(email,request);
    }

    //管理员ajax登录
    @RequestMapping("/adminSurelogin")
    @ResponseBody
    public HashMap<String,Object> adminSurelogin(AdminInfo admin, HttpServletRequest request){

        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = adminInfoService.adminloginSure(admin,request);
        map.put("info",info);
        return map;
    }

    //处理邮件登录的请求
    @RequestMapping("/emailLogin")
    @ResponseBody
    public HashMap<String,Object> emailLogin(String code,HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();

        //获取session对象
        HttpSession session = request.getSession();
        //取出存到session中的验证码的值
        String valCode = session.getAttribute("valCode")+"";
        //判断用户输入的验证码和邮箱接收的验证码是否一致

        if(code.equals(valCode)){
            map.put("info","邮箱登录成功");

        }else{
            map.put("info","验证码输入错误");

        }
        return map;

    }



}
