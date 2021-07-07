package com.cims.service;

import com.cims.pojo.ScheduleInfo;

import java.util.HashMap;

public interface ScheduleInfoService {

    //查询
    HashMap<String,Object> select(ScheduleInfo scheduleInfo);

    //通过服务编号查询
    ScheduleInfo selectByScheduleId(ScheduleInfo scheduleInfo);

    //添加 服务记录方法
    HashMap<String, Object> addScheduleInfo(ScheduleInfo scheduleInfo);

    //修改 服务记录方法
    String update(ScheduleInfo scheduleInfo);

    //删除
    String del(ScheduleInfo scheduleInfo);

    //处理经理日程到期提醒
    HashMap<String,Object> remind(ScheduleInfo scheduleInfo);
}
