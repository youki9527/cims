package com.cims.service;


import com.cims.pojo.AdminInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;




public interface AdminInfoService {


        //管理员登录方法
        String adminloginSure(AdminInfo admin, HttpServletRequest request);

        //检查邮箱和用户名是否一致
        String loginAdminEmail(AdminInfo admin, HttpServletRequest request);

        //注册
        //String zhuceAdmin(AdminInfo admin);

        //邮件发送
        HashMap<String, Object> sendCode(String email, HttpServletRequest request);

        //查询
        HashMap<String, Object> select(AdminInfo admin);

        //根据AId查询
        AdminInfo selectByAId(AdminInfo admin);

        //修改
        String updateA(AdminInfo admin);

        //删除
        String delA(AdminInfo admin);

        //修改密码
        String updateAPwd(AdminInfo admin, HttpServletRequest request);

        //新增客户经理
        //String saveAdd(ManagerInfo manager);

}
