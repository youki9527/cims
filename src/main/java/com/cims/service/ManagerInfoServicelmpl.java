package com.cims.service;


import com.cims.dao.ManagerInfoDao;
import com.cims.pojo.ManagerInfo;
import com.cims.util.MdFive;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class ManagerInfoServicelmpl implements ManagerInfoService{
    @Autowired(required = false)
    ManagerInfoDao managerInfoDao;

    //创建加密工具类对象
    @Autowired
    MdFive mdFive;


    @Override
    public String updatePwd(ManagerInfo manager, HttpServletRequest request) {
        //取出用户存入session中的密码
        HttpSession session = request.getSession();
        ManagerInfo m = (ManagerInfo) session.getAttribute("manager");
        String pwd = m.getMPwd();
        //加密下旧密码
        String oldPwd = mdFive.encrypt(manager.getOldMPwd(),m.getMSalt());

        //判断用户输入的旧密码是否正确
        if(oldPwd.equals(pwd)){
            //加密新密码
            String p = mdFive.encrypt(manager.getNewMPwd(),m.getMSalt());
            //存入加密后的新密码
            m.setMPwd(p);
            int num= managerInfoDao.updatePwd(m);
            if(num >0){
                return "修改密码成功";
            }

        }else{
            return "旧密码输入不正确";
        }

        return "修改密码失败";
    }

    //客户经理登录
    @Override
    public String loginManager(ManagerInfo manager,HttpServletRequest request) {
        //查询用户名是否存在，如果存在，就取出它的盐值
        ManagerInfo m = managerInfoDao.selectByMLogin(manager);
        if(m!=null){
            //加密用户输入的密码
            String pwd = mdFive.encrypt(manager.getMPwd(),m.getMSalt());
            //把加过密码的传到数据层中
            manager.setMPwd(pwd);
            //查询数据层的登录方法，并且拿到返回值
            ManagerInfo managerInfo =managerInfoDao.login(manager);
            //如果查询到值，managerinfo就不等于null，否则就等于null
            if(managerInfo!=null){
                //创建session对象
                HttpSession session = request.getSession();
                //存入用户对象
                session.setAttribute("manager",managerInfo);
                return "登录成功";
            }
        }else{
            return "用户名输入错误";
        }


        return "登录失败";
    }

    @Override
    public HashMap<String, Object> select(ManagerInfo manager) {

        HashMap<String,Object> map = new HashMap<String,Object>();
        //设置分页参数
        PageHelper.startPage(manager.getPage(),manager.getRow());
        //根据用户选择的查询条件，判断用户需要查询的
        List<ManagerInfo> list = new ArrayList<>();

        //判断用户输入的查询条件是否有值
        if(manager.getConValue().equals("")){
            list  = managerInfoDao.select();
        }else{
            if(manager.getCondition().equals("编号")){
                //设置用户输入的查询条件
                manager.setMId(Integer.parseInt(manager.getConValue()));
                list = managerInfoDao.findByMId(manager);
            }else if(manager.getCondition().equals("经理名")){

                manager.setMName(manager.getConValue());
                list = managerInfoDao.findByMName(manager);
            }else{
                list  = managerInfoDao.select();
            }
        }


        //把查询出来的数据转换成分页对象
        PageInfo<ManagerInfo> page = new PageInfo<ManagerInfo>(list);
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
    public ManagerInfo selectByMId(ManagerInfo manager) {
        return managerInfoDao.selectByMId(manager);
    }

    //修改
    @Override
    public String update(ManagerInfo manager) {
       int num = managerInfoDao.update(manager);
       if (num>0){
           return "修改成功";
       }
        return "修改失败";
    }

    //处理客户经理修改自己信息
    @Override
    public String updateInfo(ManagerInfo manager) {
        int num = managerInfoDao.update(manager);
        if (num>0){



            return "修改成功";
        }
        return "修改失败";
    }

    @Override
    public String del(ManagerInfo manager) {
        int num = managerInfoDao.del(manager);
        if (num>0){
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public void excelWrite(HttpServletResponse response) {

        OutputStream output=null;
        try {
            // 创建HSSFWorkbook对象
            HSSFWorkbook wb = new HSSFWorkbook();
            // 创建HSSFSheet对象,如果要创建多个sheet，就再创建sheet对象
            HSSFSheet sheet = wb.createSheet("客户经理表");

            // 创建HSSFRow对象，先写入列名
            HSSFRow colName = sheet.createRow(0);
            // 写入入第一行列名
            colName.createCell(0).setCellValue("编号");
            colName.createCell(1).setCellValue("姓名");
            colName.createCell(2).setCellValue("照片");
            colName.createCell(3).setCellValue("性别");
            colName.createCell(4).setCellValue("出生日期");
            colName.createCell(5).setCellValue("联系电话");
            colName.createCell(6).setCellValue("邮箱地址");
            colName.createCell(7).setCellValue("学历");
            colName.createCell(8).setCellValue("身份证号码");
            colName.createCell(9).setCellValue("备注");
            //查询员工所有信息
            List<ManagerInfo> list = managerInfoDao.findAll();
            for (int i = 1; i <=list.size(); i++) {
                //从第二行开始写入数据
                HSSFRow row = sheet.createRow(i);
                row.createCell(0).setCellValue(list.get(i-1).getMId());
                row.createCell(1).setCellValue(list.get(i-1).getMName());
                row.createCell(2).setCellValue(list.get(i-1).getMPic());
                row.createCell(3).setCellValue(list.get(i-1).getMSex());
                row.createCell(4).setCellValue(list.get(i-1).getMBirth());
                row.createCell(5).setCellValue(list.get(i-1).getMTel());
                row.createCell(6).setCellValue(list.get(i-1).getMEmail());
                row.createCell(7).setCellValue(list.get(i-1).getMDegree());
                row.createCell(8).setCellValue(list.get(i-1).getMNo());
                row.createCell(9).setCellValue(list.get(i-1).getMRemark());
            }
            //输出Excel文件到页面
            output=response.getOutputStream();
            String fileName="客户经理表";
            //解决中文文件名下载 变成下划线的问题
            fileName=new String(fileName.getBytes("utf-8"),"ISO8859-1")+"";
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);

        } catch (Exception e) {

            e.printStackTrace();
        }finally{
            try {
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    //新增客户经理
    @Override
    public String saveAdd(ManagerInfo manager) {
        //查询登录名是否重名
        int num = managerInfoDao.valName(manager);
        if (num>0){
            return "该登录名已存在";
        }else {
            //自动生成盐值
            Random rd = new Random();
            String MSalt = rd.nextInt(10000)+"";
            //加密用户输入的密码
            String pwd = mdFive.encrypt(manager.getMPwd(),MSalt);
            //把加过密的面命传到数据层中
            manager.setMPwd(pwd);
            //存入盐值
            manager.setMSalt(MSalt);
            //注册
            int n = managerInfoDao.saveAdd(manager);
            if (n>0){
                return "新增成功";
            }
        }
        return "新增失败";
    }


}
