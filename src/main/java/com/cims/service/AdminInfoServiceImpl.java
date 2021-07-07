package com.cims.service;

import com.cims.dao.AdminDao;
import com.cims.pojo.AdminInfo;
import com.cims.util.MdFive;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {

    @Autowired(required = false)
    AdminDao adminDao;

    //创建加密工具类对象
    @Autowired
    MdFive mdFive;

    //获取发件人邮箱
    @Value("${spring.mail.username}")
    String sendEmail;


    //创建发送邮件的对象
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public String adminloginSure(AdminInfo admin, HttpServletRequest request) {
        //查询用户名是否存在，如果存在，就取出它的盐值
        AdminInfo a = adminDao.selectByAName(admin);
        if(a!=null){
            //加密用户输入的密码
            String pwd = mdFive.encrypt(admin.getAPwd(),a.getASalt());
            //把加过密码的传到数据层中
            admin.setAPwd(pwd);
            //查询数据层的登录方法，并且拿到返回值
            AdminInfo adminInfo =adminDao.loginAdmin(admin);
            //如果查询到值，admininfo就不等于null，否则就等于null
            if(adminInfo!=null){
                //创建session对象
                HttpSession session = request.getSession();
                //存入用户对象
                session.setAttribute("admin",adminInfo);
                return "密码正确";
            }
        }else{
            return "用户不存在";
             }

        return "密码错误";
    }

    @Override
    public String loginAdminEmail(AdminInfo admin, HttpServletRequest request) {
        //查询用户名是否存在，如果存在，就校验邮箱
        AdminInfo a = adminDao.selectByAName(admin);
        if(a!=null){
        //校验邮箱
            //查询数据层的登录邮箱方法，并且拿到返回值
            AdminInfo adminInfo =adminDao.loginAdminEmail(admin);
            //如果查询到值，admininfo就不等于null，否则就等于null
            if(adminInfo!=null) {
                //创建session对象
                HttpSession session = request.getSession();
                //存入用户对象
                session.setAttribute("adminIfEmail", adminInfo);
                return "邮箱正确";
            }
        }else{
                return "邮箱输入错误";
             }
        return "邮箱正确";


        }




    @Override
    public HashMap<String, Object> sendCode(String email, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            //从session中获取当前用户信息
            HttpSession session = request.getSession();
            //创建普通邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //设置发件人邮箱
            message.setFrom(sendEmail);
            //设置收件人邮箱
            message.setTo(email);
            //设置邮件标题
            message.setSubject("客户经理管理系统验证码");
            // 生成随机验证码
            Random rd = new Random();
            String valCode = rd.nextInt(9999)+"";
            //设置邮件正文
            message.setText("你的验证码是:"+valCode);
            //发送邮件
            javaMailSender.send(message);
            //发送成功
            //把验证码存入session中
            session.setAttribute("valCode",valCode);
            map.put("info","发送成功");
            return map;

        } catch (Exception e) {
            System.out.println("发送邮件时发生异常！");
            e.printStackTrace();
        }
        map.put("info","发送邮件异常");
        return map;
    }

    @Override
    public HashMap<String, Object> select(AdminInfo admin) {

        HashMap<String,Object> map = new HashMap<String,Object>();
        //设置分页参数
        PageHelper.startPage(admin.getPage(),admin.getRow());
        //根据用户选择的查询条件，判断用户需要查询的
        List<AdminInfo> list = new ArrayList<>();

        //判断用户输入的查询条件是否有值
        if(admin.getConValue().equals("")){
            list  = adminDao.select();
        }else{
            if(admin.getCondition().equals("编号")){
                //设置用户输入的查询条件
                admin.setAId(Integer.parseInt(admin.getConValue()));
                list = adminDao.findByAId(admin);
            }else if(admin.getCondition().equals("用户名")){

                admin.setAName(admin.getConValue());
                list = adminDao.findByAName(admin);
            }else{
                list  = adminDao.select();
            }
        }


        //把查询出来的数据转换成分页对象
        PageInfo<AdminInfo> page = new PageInfo<AdminInfo>(list);
        //获取当前页的集合
        map.put("list",page.getList());
        //总条数
        map.put("total",page.getTotal());
        //总页数
        map.put("totalPage",page.getPages());
        //上一页
        if (page.getPrePage()==0){
            map.put("pre",1);
        }else{
            map.put("pre",page.getPrePage());
        }
        //下一页
        //判断下一页的值是否大于等于最后一页
        if (page.getNextPage()==0){
            map.put("next",page.getPages());
        }else{
            map.put("next",page.getNextPage());
        }
        //当前页
        map.put("cur",page.getPageNum());
        return map;
    }

    @Override
    public AdminInfo selectByAId(AdminInfo admin) {
        return adminDao.selectByAId(admin);
    }

    //修改
    @Override
    public String updateA(AdminInfo admin) {
        int num = adminDao.updateAdmin(admin);
        if (num>0){
            return "修改成功";
        }
        return "修改失败";
    }

    @Override
    public String delA(AdminInfo admin) {
        int num = adminDao.delAdmin(admin);
        if (num>0){
            return "删除成功";
        }
        return "删除失败";
    }


    @Override
    public String updateAPwd(AdminInfo admin, HttpServletRequest request) {
        //取出用户存入session中的密码
        HttpSession session = request.getSession();
        AdminInfo a = (AdminInfo) session.getAttribute("user");
        String pwd = a.getAPwd();
        //加密下旧密码
        String oldPwd = mdFive.encrypt(admin.getOldAPwd(),a.getASalt());

        //判断用户输入的旧密码是否正确
        if(oldPwd.equals(pwd)){
            //加密新密码
            String p = mdFive.encrypt(admin.getNewAPwd(),a.getASalt());
            //存入加密后的新密码
            a.setAPwd(p);
            int num= adminDao.updateAPwd(a);
            if(num >0){
                return "修改密码成功";
            }

        }else{
            return "旧密码输入不正确";
        }

        return "修改密码失败";
    }


}

