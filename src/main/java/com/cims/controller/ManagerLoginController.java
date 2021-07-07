package com.cims.controller;


import com.cims.pojo.ManagerInfo;
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


//客户经理登录界面

@Controller
@RequestMapping("/loginManager")
public class ManagerLoginController {



    //创建一个managerInfoService的实现类对象
    @Autowired
    ManagerInfoService managerInfoService;

    //客户经理ajax登录
    @RequestMapping("/loginAjaxM")
    @ResponseBody
    public HashMap<String,Object> loginAjaxM(ManagerInfo manager, HttpServletRequest request){

        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = managerInfoService.loginManager(manager,request);
        map.put("info",info);
        return map;
    }


}

