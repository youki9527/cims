package com.cims.controller;


import com.cims.pojo.AdminInfo;
import com.cims.service.AdminInfoService;
import com.cims.service.ManagerInfoService;
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
@RequestMapping("/login")
public class LoginController {


    //创建一个adminInfoService的实现类对象
    @Autowired
    AdminInfoService adminInfoService;

    //创建一个managerInfoService的实现类对象
    @Autowired
    ManagerInfoService managerInfoService;


    //访问登录页面login.html
    @RequestMapping("/loginPage")
    public String loginPage(){
        return "login";
    }



}
