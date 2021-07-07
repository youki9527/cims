package com.cims.controller;

import com.cims.pojo.ClientInfo;
import com.cims.pojo.ManagerInfo;
import com.cims.pojo.ServiceInfo;
import com.cims.service.ServiceInfoService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/serviceInfoManage")
public class ServiceManageController {

    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //创建一个serviceInfoService的实现类对象
    @Autowired
    ServiceInfoService serviceInfoService;

    //访问服务记录 列表页面
    @RequestMapping("/serviceList")
    public String ServiceListPage(ServiceInfo serviceInfo, ModelMap m, HttpServletRequest request)
    {

        //从session中获取当前经理信息
        HttpSession session = request.getSession();
        //把经理信息从session中取出
        ManagerInfo managerInfo= (ManagerInfo)session.getAttribute("manager");
        //设置serviceInfo经理编号
        serviceInfo.setSManagerId(managerInfo.getMId());

        //查询分页数据
        HashMap<String, Object> map = serviceInfoService.select(serviceInfo);
        //把数据传到前端
        m.put("info", map);
        m.put("startDay",serviceInfo.getStartDay());
        m.put("endDay",serviceInfo.getEndDay());
        m.put("SType",serviceInfo.getSType());
        m.put("idOrName",serviceInfo.getIdOrName());
        m.put("SIdOrUserName",serviceInfo.getSIdOrUserName());


//        System.out.println("startDay"+serviceInfo.getStartDay());
//        System.out.println("endDay"+serviceInfo.getEndDay());
//        System.out.println("SType"+serviceInfo.getSType());
//        System.out.println("SIdOrUserName"+serviceInfo.getSIdOrUserName());

        return "/manager/service-list";
    }

    //访问服务记录 增加页面
    @RequestMapping("/serviceAdd")
    public String ServiceAddPage()
    {
        return "/manager/service-add";
    }

    //访问服务记录 反馈页面
    @RequestMapping("/serviceFeedback")
    public String ServiceFeedback(ServiceInfo serviceInfo,ModelMap modelMap)
    {
        //根据SId查询服务记录表
        ServiceInfo s=serviceInfoService.selectBySId(serviceInfo);
        //把数据传递到前端
        modelMap.addAttribute("serviceInfo",s);
        return "/manager/service-feedback";
    }

    //访问服务记录 修改页面
    @RequestMapping("/serviceUpdate")
    public String ServiceUpdate(ServiceInfo serviceInfo,ModelMap modelMap)
    {
        //根据SId查询
        ServiceInfo s=serviceInfoService.selectBySId(serviceInfo);
        //把数据传递到前端
        modelMap.addAttribute("serviceInfo",s);
        return "/manager/service-update";
    }

    //加载客户经理信息到添加页面和客户经理负责的客户列表信息
    @RequestMapping("/addInitialize")
    @ResponseBody
    public HashMap<String,Object> loadManager(HttpServletRequest request) {

        HashMap<String,Object> map=new HashMap<>();
        //获取session中客户经理信息
        HttpSession session=request.getSession();
        ManagerInfo manager=(ManagerInfo) session.getAttribute("manager");

//        //获取redis中客户经理信息
//        ManagerInfo managerInfo= (ManagerInfo)redisTemplate.opsForValue().get("managerInfo"+manager.getMId());
//        System.out.println(managerInfo+"");
//        map.put("managerInfo",managerInfo);

        //获取客户经理负责的客户的信息
        List<ClientInfo> clientInfoList=serviceInfoService.addClientInfoByManagerId(manager);
        map.put("clientInfoList",clientInfoList);
        map.put("managerInfo",manager);
        return map;
    }

    //处理添加服务记录信息
    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, Object> addAjax(ServiceInfo serviceInfo, HttpServletRequest request) {

        return serviceInfoService.addServiceInfo(serviceInfo);
    }

    //处理邮件发送的请求
    @RequestMapping("/sendEmail")
    @ResponseBody
    public HashMap<String,Object> sendEmail(ServiceInfo serviceInfo, HttpServletRequest request){

        HashMap<String, Object> map = new HashMap<>();
        //根据服务记录SUserId查询获取客户邮箱地址
        ClientInfo clientInfo=new ClientInfo();
        clientInfo.setCId(serviceInfo.getSUserId());
        String email=serviceInfoService.selectClientEmail(clientInfo);

        return serviceInfoService.sendFeedbackLink(email,serviceInfo.getSId());

    }

    //处理插入客户反馈请求
    @RequestMapping("/updateServiceInfoBack")
    @ResponseBody
    public HashMap<String,Object> updateServiceInfoBack(ServiceInfo serviceInfo, HttpServletRequest request){

        HashMap<String, Object> map = new HashMap<>();
        String info = serviceInfoService.updateServiceInfoBack(serviceInfo);
        map.put("info",info);
        return map;
    }

    //处理插入客户反馈请求
    @RequestMapping("/SFbackIsSubmit")
    @ResponseBody
    public HashMap<String,Object> SFbackIsSubmit(ServiceInfo serviceInfo, HttpServletRequest request){

        HashMap<String, Object> map = new HashMap<>();
        String info = serviceInfoService.judgeSFbackIsSubmit(serviceInfo);
        map.put("info",info);
        return map;
    }

    //处理删除服务记录信息
    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String, Object> delAjax(ServiceInfo serviceInfo, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<>();
        String info = serviceInfoService.del(serviceInfo);
        map.put("info",info);
        return map;
    }

    //处理修改除服务记录信息
    @RequestMapping("/update")
    @ResponseBody
    public HashMap<String, Object> updateAjax(ServiceInfo serviceInfo, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<>();
        String info = serviceInfoService.update(serviceInfo);
        map.put("info",info);
        return map;
    }


    //导出serviceInfo
    @RequestMapping("/exportServiceInfo")
    public void Export(HttpServletResponse response,HttpServletRequest request) throws IOException {

        //从session中获取当前用户信息
        HttpSession session = request.getSession();
        //把用户id从session中取出
        ManagerInfo managerInfo= (ManagerInfo)session.getAttribute("manager");
        //创建serviceInfo对象并设置SMId值
        ServiceInfo serviceInfo=new ServiceInfo();
        serviceInfo.setSManagerId(managerInfo.getMId());

        //开始日期
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<ServiceInfo> classmateList =serviceInfoService.selectServiceInfoAll(serviceInfo);

        // 设置要导出的文件的名字
        String fileName = "serviceInfo"  + new Date() + ".xls";

        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = { "服务标号", "服务类型", "服务日期", "用户编号","用户姓名","经理编号","经理姓名","服务花费","服务时间",
        "服务内容","客户反馈","客户满意度"};
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (ServiceInfo item : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(item.getSId());
            row1.createCell(1).setCellValue(item.getSType());
            row1.createCell(2).setCellValue(item.getSDate());
            row1.createCell(3).setCellValue(item.getSUserId());
            row1.createCell(4).setCellValue(item.getSUserName());
            row1.createCell(5).setCellValue(item.getSManagerId());
            row1.createCell(6).setCellValue(item.getSManagerName());
            row1.createCell(7).setCellValue(item.getSCost());
            row1.createCell(8).setCellValue(item.getSTime());
            row1.createCell(9).setCellValue(item.getSContent());
            row1.createCell(10).setCellValue(item.getSFback());
            row1.createCell(11).setCellValue(item.getSGrade());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
