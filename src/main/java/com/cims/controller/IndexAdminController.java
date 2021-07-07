package com.cims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/indexA")
public class IndexAdminController {

    //访问管理员首页
    @RequestMapping("/indexAdmin")
    public String indexPage()
    {
        return "/admin/index-admin";
    }

    //访问管理员欢迎页面
    @RequestMapping("/welcome")
    public String welcomePage()
    {
        return "/admin/welcome";
    }

}