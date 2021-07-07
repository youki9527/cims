package com.cims.service;



import com.cims.pojo.ManagerInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface ManagerInfoService {

    //修改密码
    String updatePwd(ManagerInfo manager, HttpServletRequest request);

    //登录方法
    String loginManager(ManagerInfo manager,HttpServletRequest request);

    //查询
    HashMap<String, Object> select(ManagerInfo manager);

    //根据id查询
    ManagerInfo selectByMId(ManagerInfo manager);

    //修改
    String update(ManagerInfo manager);

    //修改
    String updateInfo(ManagerInfo manager);

    //删除
    String del(ManagerInfo manager);

    //excel导出
    void excelWrite(HttpServletResponse response);

    //注册
    String saveAdd(ManagerInfo manager);

}
