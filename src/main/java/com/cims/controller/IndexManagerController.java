package com.cims.controller;


import com.cims.pojo.ManagerInfo;
import com.cims.service.ManagerInfoService;
import com.cims.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/indexM")
public class IndexManagerController {

    @Autowired
    ServiceInfoService serviceInfoService;

    @Autowired
    ManagerInfoService managerInfoService;
    //访问经理首页
    @RequestMapping("/indexManager")
    public String indexPage()
    {
        return "/manager/index-manager";
    }

    //访问经理信息页
    @RequestMapping("/managerInfo")
    public String managerInfo()
    {
        return "/manager/manager-info";
    }

    //访问经理欢迎首页
    @RequestMapping("/welcome")
    public String welcomePage()
    {
        return "/manager/welcome";
    }

    //访问经理密码修改页面
    @RequestMapping("/updatePwd")
    public String updatePwdPage()
    {
        return "/manager/managerupdatePwd";
    }

    //处理修改密码的ajax请求
    @RequestMapping("/updatePwdAjax")
    @ResponseBody
    public HashMap<String,Object> updatePwdAjax(ManagerInfo manager, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = managerInfoService.updatePwd(manager,request);
        map.put("info",info);
        return map;
    }
}
