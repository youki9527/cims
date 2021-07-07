package com.cims.service;


import com.cims.dao.ContactInfoDao;
import com.cims.pojo.ClientInfo;
import com.cims.pojo.ContactInfo;
import com.cims.pojo.ManagerInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ContactInfoServicelmpl implements ContactInfoService {

    @Autowired(required = false)
    ContactInfoDao contactInfoDao;


    //获取发件人邮箱
    @Value("${spring.mail.username}")
    String sendEmail;

    //创建发送邮件的对象
    @Autowired
    JavaMailSender javaMailSender;


    //分页
    @Override
    public HashMap<String, Object> select(ContactInfo contact, HttpServletRequest request) {

//        HttpSession session = request.getSession();
//        ManagerInfo manager = (ManagerInfo)session.getAttribute("manager");
//        contact.setCMid(manager.getMId());
//        System.out.println("service:"+contact.getCMid());

        HashMap<String, Object> map = new HashMap<String, Object>();
        //设置分页参数
        PageHelper.startPage(contact.getPage(), contact.getRow());
        //根据用户选择的查询条件，判断用户需要查询的
        List<ContactInfo> list = new ArrayList<>();

        //判断用户输入的查询条件是否有值
        if (contact.getConValue().equals("")) {
            list = contactInfoDao.select(contact);
        } else {
            if (contact.getCondition().equals("编号")) {
                //设置用户输入的查询条件
                contact.setCId(Integer.parseInt(contact.getConValue()));
                list = contactInfoDao.findByCId(contact);
            } else if (contact.getCondition().equals("客户名")) {

                contact.setCName(contact.getConValue());
                list = contactInfoDao.findByCName(contact);
            } else {
                list = contactInfoDao.select(contact);
            }
        }


        //把查询出来的数据转换成分页对象
        PageInfo<ContactInfo> page = new PageInfo<ContactInfo>(list);
        //获取当前页的集合
        map.put("list", page.getList());
        //总条数
        map.put("total", page.getTotal());
        //总页数
        map.put("totalPage", page.getPages());
        //上一页
        if (page.getPrePage() == 0) {
            map.put("pre", 1);
        } else {
            map.put("pre", page.getPrePage());
        }
        //下一页
        //判断下一页的值是否大于等于最后一页
        if (page.getNextPage() == 0) {
            map.put("next", page.getPages());
        } else {
            map.put("next", page.getNextPage());
        }
        //当前页
        map.put("cur", page.getPageNum());


        return map;
    }

    //按id查询
    @Override
    public ContactInfo selectByCId(ContactInfo contact) {
        return contactInfoDao.selectByCId(contact);
    }

    //修改
    @Override
    public String update(ContactInfo contact) {
        int num = contactInfoDao.update(contact);
        if (num > 0) {
            return "修改成功";
        }
        return "修改失败";
    }

    @Override
    public String del(ContactInfo contact) {
        int num = contactInfoDao.del(contact);
        if (num > 0) {
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public String saveAdd(ContactInfo contact) {
        int num = contactInfoDao.saveAdd(contact);
        if (num > 0) {
            return "新增成功";
        }
        return "新增失败";
    }


    //查询生日
    @Override
    public HashMap<String, Object> selectByBirth(ClientInfo client, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        //1 设置分页参数
        PageHelper.startPage(client.getPage(), client.getRow());
        //根据用户选择的查询条件，判断用户需要查询的
        List<ClientInfo> cList = new ArrayList<>();

        //取出存入session中获取manager的Mid
        HttpSession session = request.getSession();
        ManagerInfo managerInfo=(ManagerInfo)session.getAttribute("manager");
        client.setCMid(managerInfo.getMId());

        cList = contactInfoDao.selectByBirthCMid(client);

        //3.把查询的数据转换成分页对象
        PageInfo<ClientInfo> page = new PageInfo<ClientInfo>(cList);

        //获取分页的当前页的集合
        map.put("clientListZhufu", page.getList());
        //总条数
        map.put("clientTotalZhufu", page.getTotal());
        if (page.getTotal() == 0) {
            //总页数
            map.put("clientTotalPageZhufu", page.getPages() + 1);
        } else {
            //总页数
            map.put("clientTotalPageZhufu", page.getPages());
        }
        //上一页
        if (page.getPrePage() == 0) {
            map.put("clientPreZhufu", 1);
        } else {
            map.put("clientPreZhufu", page.getPrePage());
        }

        //下一页
        //保持在最后一页
        if (page.getNextPage() == 0) {
            map.put("clientNextZhufu", page.getPages());
        } else {
            map.put("clientNextZhufu", page.getNextPage());
        }

        if (page.getPageNum() == 0) {
            //当前页
            map.put("clientCurZhufu", page.getPageNum() + 1);
        } else {
            //当前页
            map.put("clientCurZhufu", page.getPageNum());
        }

        return map;
    }

    //邮件发送
    @Override
    public HashMap<String, Object> sendEmail(String email, String name, String value, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            //创建普通邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //设置发件人邮箱
            message.setFrom(sendEmail);
            //设置收件人邮箱
            message.setTo(email);
            //设置邮件标题
            message.setSubject("亲爱的"+name+"，祝您生日快乐");
            //设置邮件正文
            message.setText(value);
            //发送邮件
            javaMailSender.send(message);
            //发送成功
            map.put("info","发送成功");
            return map;

        } catch (Exception e) {
            System.out.println("发送邮件时发生异常！");
            e.printStackTrace();
        }
        map.put("info","发送邮件异常");
        return map;
    }

}
