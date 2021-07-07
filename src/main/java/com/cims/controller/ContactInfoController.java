package com.cims.controller;

import com.cims.pojo.ClientInfo;
import com.cims.pojo.ContactInfo;
import com.cims.pojo.ManagerInfo;
import com.cims.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
@RequestMapping("/contact")
public class ContactInfoController {

    @Autowired
    ContactInfoService contactInfoService;


    //访问client-list-zhufu页面
    @RequestMapping("/zhufu")
    public String zhufu(ClientInfo clientInfo, ModelMap m, HttpServletRequest request){

        //查询分页数据
        HashMap<String, Object> map=contactInfoService.selectByBirth(clientInfo,request);
        //把数据传到前端
        m.put("clientInfoZhufu",map);
        return "/manager/contact-zhufu";
    }


    //访问contact-list页面
    @RequestMapping("/list")
    public String list(ContactInfo contact, ModelMap m, HttpServletRequest request) {
        // 测试数据
//        ManagerInfo manager = new ManagerInfo();
//        manager.setMLogin("amy");
//        manager.setMName("amy");
//        manager.setMId(1);
//        HttpSession session = request.getSession();
//        session.setAttribute("manager",manager);

        //从session中获取当前经理信息
        HttpSession session = request.getSession();
        ManagerInfo mm = (ManagerInfo) session.getAttribute("manager");
        contact.setCMid(mm.getMId());
        System.out.println("controller:" + contact.getCMid());
        HashMap<String, Object> map = contactInfoService.select(contact, request);
        m.put("info", map);

        //把查询条件传到前端
        m.put("vv", contact.getConValue());
        m.put("conValue", contact.getConValue());
        m.put("condition", contact.getCondition());
        return "/manager/contact-list";
    }

    //处理表格ajax数据
    @RequestMapping("/listAjax")
    @ResponseBody
    public HashMap<String, Object> listAjax(ContactInfo contact, HttpServletRequest request) {
        return contactInfoService.select(contact, request);
    }

    //访问修改页面
    @RequestMapping("/editPage")
    public String editPage(ContactInfo contact, ModelMap m) {
        ContactInfo contactInfo1 = contactInfoService.selectByCId(contact);
        m.addAttribute("contact", contactInfo1);
        return "/manager/contact-edit";
    }

    //处理修改的ajax请求
    @RequestMapping("/edit")
    @ResponseBody
    public HashMap<String, Object> edit(ContactInfo contact) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String info = contactInfoService.update(contact);
        System.out.println(info);
        map.put("info", info);
        return map;
    }

    //处理删除的AJAX请求
    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String, Object> del(ContactInfo contact) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String info = contactInfoService.del(contact);
        System.out.println(info);
        map.put("info", info);
        return map;
    }

    //访问用户新增页面
    @RequestMapping("/add")
    public String add() {
        return "/manager/contact-add";
    }

    //增加客户经理,新增的ajax请求
    @RequestMapping("/saveAdd")
    @ResponseBody
    public HashMap<String, Object> saveAdd(ContactInfo contact) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String info = contactInfoService.saveAdd(contact);
        System.out.println(info);
        map.put("info", info);
        return map;
    }

    //客户经理发送邮件向客户
    @RequestMapping("/sendEmail")
    @ResponseBody
    public HashMap<String,Object> sendEmail(@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("value") String value, HttpServletRequest request){

        return contactInfoService.sendEmail(email,name,value,request);
    }
}
