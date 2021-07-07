package com.cims.service;

import com.cims.pojo.ClientInfo;
import com.cims.pojo.ManagerInfo;
import com.cims.pojo.ServiceInfo;


import java.util.HashMap;
import java.util.List;

public interface ServiceInfoService {

    //添加 服务记录方法
    HashMap<String, Object> addServiceInfo(ServiceInfo serviceInfo);

    //查询客户邮箱
    String selectClientEmail(ClientInfo clientInfo);

    //给客户通过邮件发送意见链接
    HashMap<String, Object> sendFeedbackLink(String email, int SId);

    //判断是否已经提交反馈
    String  judgeSFbackIsSubmit(ServiceInfo serviceInfo);

    //更新插入客户反馈
    String updateServiceInfoBack(ServiceInfo serviceInfo);

    //查找经理负责的客户信息方法
    List<ClientInfo> addClientInfoByManagerId(ManagerInfo managerInfo);

    //查询
    HashMap<String,Object> select(ServiceInfo serviceInfo);

    //分页查询
    HashMap<String,Object> page(ServiceInfo serviceInfo);

    //删除 服务记录方法
    String del(ServiceInfo serviceInfo);

    //通过服务编号查询
    ServiceInfo selectBySId(ServiceInfo serviceInfo);

    //修改 服务记录方法
    String update(ServiceInfo serviceInfo);

    //查询所有服务记录
    List selectServiceInfoAll(ServiceInfo serviceInfo);


}
