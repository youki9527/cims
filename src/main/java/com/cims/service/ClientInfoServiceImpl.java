package com.cims.service;

import com.cims.dao.ClientInfoDao;
import com.cims.pojo.ClientInfo;
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

@Service  //UserInfoServiceImpl的对象创建交给spring管理
public class ClientInfoServiceImpl implements ClientInfoService {
    //创建一个userInfoDao的实现类对象
    @Autowired(required = false)
    ClientInfoDao clientInfoDao;

    //创建加密工具类对象
    @Autowired
    MdFive mdfive;

    //获取发件人邮箱
    @Value("${spring.mail.username}")
    String sendEmail;

    //创建发送邮件的对象
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public String zhuce(com.cims.pojo.ClientInfo client, HttpServletRequest request) {
        //查询客户是否重名
        int num = clientInfoDao.valName(client);
        if (num > 0) {
            return "用户名已经被注册";
        } else {
//            //取出存入session中的CMname
////            HttpSession session = request.getSession();
////            session.setAttribute("Mid",1);
//          //  client.setCMid((int) session.getAttribute("Mid"));
//            HttpSession session = request.getSession();
//            ManagerInfo manager = (ManagerInfo)session.getAttribute("manager");
//            client.setCMid(manager.getMId());
            //注册
            int n = clientInfoDao.zhuce(client);
            if (n > 0) {
                return "注册成功";
            }
        }
        return "注册失败";
    }

    @Override
    public void excelWrite(ClientInfo client, HttpServletResponse response, HttpServletRequest request) {

        OutputStream output = null;
        try {
            // 创建HSSFWorkbook对象
            HSSFWorkbook wb = new HSSFWorkbook();
            // 创建HSSFSheet对象,如果要创建多个sheet，就再创建sheet对象
            HSSFSheet sheet = wb.createSheet("客户表");

            // 创建HSSFRow对象，先写入列名
            HSSFRow colName = sheet.createRow(0);
            // 写入入第一行列名
            colName.createCell(0).setCellValue("客户编号");
            colName.createCell(1).setCellValue("客户名");
            colName.createCell(2).setCellValue("性别");
            colName.createCell(3).setCellValue("生日");
            colName.createCell(4).setCellValue("电话");
            colName.createCell(5).setCellValue("邮箱");
            colName.createCell(6).setCellValue("学历");
            colName.createCell(7).setCellValue("身份证号");
            colName.createCell(8).setCellValue("备注");
            //查询员工所有信息
            //取出存入session中的CMname
            HttpSession session = request.getSession();
            session.setAttribute("Mid", 1);
            client.setCMid((Integer) session.getAttribute("Mid"));

            List<ClientInfo> list = clientInfoDao.findByCMid(client);

            for (int i = 1; i <= list.size(); i++) {
                //从第二行开始写入数据
                HSSFRow row = sheet.createRow(i);
                row.createCell(0).setCellValue(list.get(i - 1).getCId());
                row.createCell(1).setCellValue(list.get(i - 1).getCName());
                row.createCell(2).setCellValue(list.get(i - 1).getCSex());
                row.createCell(3).setCellValue(list.get(i - 1).getCBirth());
                row.createCell(4).setCellValue(list.get(i - 1).getCTel());
                row.createCell(5).setCellValue(list.get(i - 1).getCEmail());
                row.createCell(6).setCellValue(list.get(i - 1).getCDegree());
                row.createCell(7).setCellValue(list.get(i - 1).getCNo());
                row.createCell(8).setCellValue(list.get(i - 1).getCAdd());
            }
            //输出Excel文件到页面
            output = response.getOutputStream();
            String fileName = "客户表";
            //解决中文文件名下载 变成下划线的问题
            fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1") + "";
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel");
            wb.write(output);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


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
            message.setSubject("亲爱的" + name + "，祝您生日快乐");
            //设置邮件正文
            message.setText(value);
            //发送邮件
            javaMailSender.send(message);
            //发送成功
            map.put("info", "发送成功");
            return map;

        } catch (Exception e) {
            System.out.println("发送邮件时发生异常！");
            e.printStackTrace();
        }
        map.put("info", "发送邮件异常");
        return map;
    }

    @Override
    public HashMap<String, Object> select(com.cims.pojo.ClientInfo client, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        //1 设置分页参数
        PageHelper.startPage(client.getPage(), client.getRow());
        //根据用户选择的查询条件，判断用户需要查询的
        List<com.cims.pojo.ClientInfo> cList = new ArrayList<>();

        //取出存入session中的CMname
//        HttpSession session = request.getSession();
//        session.setAttribute("Mid",1);
//        client.setCMid((Integer) session.getAttribute("Mid"));

        //判断用户输入的查询条件是否有值
        if (client.getConValue().equals("")) {
            cList = clientInfoDao.findByCMid(client);
        } else {
            if (client.getCondition().equals("客户编号")) {
                //设置用户输入的查询条件
                client.setCId(Integer.parseInt(client.getConValue()));
                cList = clientInfoDao.findByCId(client);
            } else if (client.getCondition().equals("客户名")) {

                client.setCName(client.getConValue());
                cList = clientInfoDao.findByCName(client);
            } else {
                cList = clientInfoDao.findByCMid(client);
            }
        }


        //3.把查询的数据转换成分页对象
        PageInfo<com.cims.pojo.ClientInfo> page = new PageInfo<com.cims.pojo.ClientInfo>(cList);

        //获取分页的当前页的集合
        map.put("clientList", page.getList());
        //总条数
        map.put("clientTotal", page.getTotal());
        //总页数
        map.put("clientTotalPage", page.getPages());
        //上一页
        if (page.getPrePage() == 0) {
            map.put("clientPre", 1);
        } else {
            map.put("clientPre", page.getPrePage());
        }

        //下一页
        //保持在最后一页
        if (page.getNextPage() == 0) {
            map.put("clientNext", page.getPages());
        } else {
            map.put("clientNext", page.getNextPage());
        }
        //当前页
        map.put("clientCur", page.getPageNum());

        return map;
    }

    @Override
    public HashMap<String, Object> selectByBirth(com.cims.pojo.ClientInfo client, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        //1 设置分页参数
        PageHelper.startPage(client.getPage(), client.getRow());
        //根据用户选择的查询条件，判断用户需要查询的
        List<com.cims.pojo.ClientInfo> cList = new ArrayList<>();

        //取出存入session中的CMname
//        HttpSession session = request.getSession();
//        session.setAttribute("Mid",1);
//        client.setCMid((int) session.getAttribute("Mid"));

        cList = clientInfoDao.selectByBirth(client);


        //3.把查询的数据转换成分页对象
        PageInfo<com.cims.pojo.ClientInfo> page = new PageInfo<com.cims.pojo.ClientInfo>(cList);

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


    @Override
    public ClientInfo selectByCId(ClientInfo client) {
        return clientInfoDao.selectByCId(client);
    }

    @Override
    public String update(ClientInfo client, HttpServletRequest request) {

        //验证修改的用户名是否重名
        int v = clientInfoDao.valName(client);
        if (v == 0 || client.getCName().equals(clientInfoDao.selectByCId(client).getCName())) {
//            //取出存入session中的CMname
//            HttpSession session = request.getSession();
//            session.setAttribute("Mid",1);
//            client.setCMid((Integer) session.getAttribute("Mid"));

            int num = clientInfoDao.update(client);
            if (num > 0) {
                return "修改成功";
            }
        } else {
            return "客户名重名";
        }

        return "修改失败";
    }

    @Override
    public String delCMid(ClientInfo client) {
        int num = clientInfoDao.delCMname(client);
        if (num > 0) {
            return "删除成功";
        }
        return "删除失败";
    }

}
