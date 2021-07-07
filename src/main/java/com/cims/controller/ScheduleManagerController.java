package com.cims.controller;

import com.cims.pojo.ManagerInfo;
import com.cims.pojo.ScheduleInfo;
import com.cims.service.ScheduleInfoService;
import com.cims.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
@RequestMapping("/schedule")
public class ScheduleManagerController {


    //创建一个serviceInfoService的实现类对象
    @Autowired
    ServiceInfoService serviceInfoService;

    @Autowired
    ScheduleInfoService scheduleInfoService;

    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //访问日程记录 列表页面
    @RequestMapping("/scheduleList")
    public String ServiceListPage(ScheduleInfo scheduleInfo, ModelMap m, HttpServletRequest request) {

        //从session中获取当前经理信息
        HttpSession session = request.getSession();
        //把经理信息从session中取出
        ManagerInfo managerInfo= (ManagerInfo)session.getAttribute("manager");
        //设置日程记录经理编号
       scheduleInfo.setScheduleMId(managerInfo.getMId());

        //查询分页数据
        HashMap<String, Object> map = scheduleInfoService.select(scheduleInfo);
        //把数据传到前端
        m.put("info", map);
        m.put("startDay",scheduleInfo.getStartDay());
        m.put("endDay",scheduleInfo.getEndDay());
        m.put("ScheduleSType",scheduleInfo.getScheduleSType());
        return "/manager/schedule-list";

    }


    //访问日程 增加页面
    @RequestMapping("/scheduleAdd")
    public String scheduleAddPage()
    {
        return "/manager/schedule-add";
    }

    //处理增加 日程记录
    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, Object> addAjax(ScheduleInfo scheduleInfo, HttpServletRequest request) {

        return scheduleInfoService.addScheduleInfo(scheduleInfo);
    }

    //处理删除 日程记录
    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String, Object> delAjax(ScheduleInfo scheduleInfo, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<>();
        String info = scheduleInfoService.del(scheduleInfo);
        map.put("info",info);
        return map;
    }

    //访问日程记录 修改页面
    @RequestMapping("/scheduleUpdate")
    public String scheduleUpdate(ScheduleInfo scheduleInfo,ModelMap modelMap)
    {
        //根据ScheduleId查询
        ScheduleInfo s= scheduleInfoService.selectByScheduleId(scheduleInfo);
        //把数据传递到前端
        modelMap.addAttribute("scheduleInfo",s);
        return "/manager/schedule-update";
    }

    //处理修改日程记录信息
    @RequestMapping("/update")
    @ResponseBody
    public HashMap<String, Object> updateAjax(ScheduleInfo scheduleInfo, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<>();
        String info =  scheduleInfoService.update(scheduleInfo);
        map.put("info",info);
        return map;
    }
}
