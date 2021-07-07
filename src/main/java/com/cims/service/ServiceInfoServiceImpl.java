package com.cims.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cims.dao.ServiceInfoDao;
import com.cims.pojo.ClientInfo;
import com.cims.pojo.ManagerInfo;
import com.cims.pojo.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    //创建一个serviceInfoDao的实现类对象（数据库）
    @Autowired(required = false)
    ServiceInfoDao serviceInfoDao;

    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //获取发件人邮箱
    @Value("${spring.mail.username}")
    String sendEmail;

    //获取ip地址邮箱
    @Value("${IP}")
    String IP;

    //获取ip地址邮箱
    @Value("${port}")
    String port;

    //创建发送对象的对象
    @Autowired
    JavaMailSender mailSender;

    //添加服务记录方法
    @Override
    public HashMap<String, Object> addServiceInfo(ServiceInfo serviceInfo) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        int res = serviceInfoDao.add(serviceInfo);
        //获取自增主键id
        System.out.println(serviceInfo.getSId());
        if (res > 0) {
            map.put("info", "添加成功");
        } else {
            map.put("info", "添加失败");
        }
        map.put("SId", serviceInfo.getSId());
        return map;
    }

    //查询客户邮箱信息
    @Override
    public String selectClientEmail(ClientInfo clientInfo) {
        return serviceInfoDao.selectClientEmailByCId(clientInfo);
    }

    //给客户通过邮件发送意见链接
    @Override
    public HashMap<String, Object> sendFeedbackLink(String email, int SId) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        try {

            //创建普通邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //设置发件人邮箱
            message.setFrom(sendEmail);
            //设置收件人邮箱
            message.setTo(email);
            //设置邮件标题
            message.setSubject("麻烦复制下面链接并打开填写本次服务的满意度哦");
            // 生成随机验证码
            Random rd = new Random();
            //四位随机数
            String valCode = rd.nextInt(9999) + "";
            //设置邮件正文
            message.setText("链接:" + IP + ":" + port + "/serviceInfoManage/serviceFeedback?SId=" + SId);
            //发送邮件
            mailSender.send(message);
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

    //判断客户是否已经提交反馈
    @Override
    public String judgeSFbackIsSubmit(ServiceInfo serviceInfo) {

        int res = serviceInfoDao.selectServiceInfoSFbackStatus(serviceInfo);
        if (res==1){

            return "已经提交反馈";
        }else{
            return "还没有提交反馈";
        }


    }

    //更新插入客户反馈
    @Override
    public String updateServiceInfoBack(ServiceInfo serviceInfo) {
        int res = serviceInfoDao.updateServiceInfoBack(serviceInfo);
        if (res > 0) {
            return "更新插入客户反馈成功";
        }
        return "更新插入客户反馈失败";
    }

    //查找经理负责的客户信息方法
    @Override
    public List<ClientInfo> addClientInfoByManagerId(ManagerInfo managerInfo) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        List<ClientInfo> clientInfoList = serviceInfoDao.selectClientInfo(managerInfo);
        return clientInfoList;
    }

    //查询
    @Override
    public HashMap<String, Object> select(ServiceInfo serviceInfo) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        //1.设置分页参数
        PageHelper.startPage(serviceInfo.getPage(), serviceInfo.getRow());
        //2查询数据库表数据
        List<ServiceInfo> list = new ArrayList<>();
        //2.1按开始截止日期和服务类型
        if (serviceInfo.getIdOrName().equals("")) {
            //不按服务类型
            if (serviceInfo.getSType().equals("")) {
                //开始日期为空
                if (serviceInfo.getStartDay().equals("")) {
                    //截止日期为空 所有
                    if (serviceInfo.getEndDay().equals("")) {
                        list = serviceInfoDao.selectServiceInfoAll(serviceInfo);
                    }
                    //截止日期不为空 按截止日期查询
                    else {
                        list = serviceInfoDao.selectServiceInfoByEndDay(serviceInfo);
                    }
                }
                //开始日期不为空
                else {
                    //截止日期为空 开始日期
                    if (serviceInfo.getEndDay().equals("")) {
                        list = serviceInfoDao.selectServiceInfoByStartDay(serviceInfo);
                    }
                    //截止日期不为空 开始日期和截止日期
                    else {
                        list = serviceInfoDao.selectServiceInfoByStartAndEndDay(serviceInfo);
                    }
                }
            }
            //按服务类型
            else{
                //开始日期为空
                if (serviceInfo.getStartDay().equals("")) {
                    //截止日期为空 按服务类型查询
                    if (serviceInfo.getEndDay().equals("")) {
                        list = serviceInfoDao.selectServiceInfoBySType(serviceInfo);
                    }
                    //截止日期不为空 按截止日期和服务类型查询
                    else {
                        list = serviceInfoDao.selectServiceInfoByEndDayAndSType(serviceInfo);
                    }
                }
                //开始日期不为空
                else {
                    //截止日期为空 按开始日期和服务类型查询
                    if (serviceInfo.getEndDay().equals("")) {
                        list = serviceInfoDao.selectServiceInfoByStartDayAndSType(serviceInfo);
                    }
                    //截止日期不为空 按开始日期和结束日期和服务类型查询
                    else {
                        list = serviceInfoDao.selectServiceInfoByStartAndEndDayAndSType(serviceInfo);
                    }
                }
            }
        }
        //2.2按服务编号或客户姓名
        else {
            //按服务编号
            if (serviceInfo.getIdOrName().equals("1"))
                list = serviceInfoDao.selectServiceInfoBySIdAndSMId(serviceInfo);
            //按客户姓名
            if (serviceInfo.getIdOrName().equals("2"))
                list = serviceInfoDao.selectServiceInfoBySUserName(serviceInfo);
        }

        //3.把查询的数据转换成分页对象
        PageInfo<ServiceInfo> page = new PageInfo<ServiceInfo>(list);
        //获取分页的当前页的数据集合
        map.put("list", page.getList());
        //总条数
        map.put("total", page.getTotal());
        //总页数
        map.put("totalPage", page.getPages());
        //当前页
        map.put("cur", page.getPageNum());
        //上一页
        if (page.getPrePage() == 0) {//保持在第一页（如果当前页为第一页，上一页会是0,设置上一页为1）
            map.put("pre", 1);
        } else {
            map.put("pre", page.getPrePage());
            System.out.println("上一页是第" + page.getPrePage() + "页");
        }
        //下一页
        if (page.getNextPage() == 0) {//保持在最后一页（如果当前页为最后一页,下一页会是0，设置下一页为最后一页也就是总页数）
            map.put("next", page.getPages());
        } else {
            map.put("next", page.getNextPage());
            System.out.println("下一页是第" + page.getNextPage() + "页");
        }
//        //自定义页码数组
//        int[] pageList = new int[page.getPages()];
//        for (int i = 0; i < page.getPages(); i = i + 1) {
//            pageList[i] = i + 1;
//        }
//        //当前页
//        map.put("pList", pageList);
        return map;

    }

    //分页测试
    @Override
    public HashMap<String, Object> page(ServiceInfo serviceInfo) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        //1.设置分页参数
        PageHelper.startPage(serviceInfo.getPage(), serviceInfo.getRow());
        //2查询数据库表数据
        List<ServiceInfo> list = new ArrayList<>();
        list = serviceInfoDao.selectServiceInfoAll(serviceInfo);
        //3.把查询的数据转换成分页对象
        PageInfo<ServiceInfo> page = new PageInfo<ServiceInfo>(list);

        map.put("code",0);
        map.put("msg","");
        map.put("count", page.getTotal());
        map.put("data",list);

        return  map;
    }

    //删除
    @Override
    public String del(ServiceInfo serviceInfo) {

        int res = serviceInfoDao.del(serviceInfo);
        if (res > 0) {
            return "删除成功";
        }
        return "删除失败";

    }

    //根据服务编号查询服务记录表信息
    @Override
    public ServiceInfo selectBySId(ServiceInfo serviceInfo) {

        return serviceInfoDao.selectServiceInfoBySId(serviceInfo);
    }

    //修改服务列表信息
    @Override
    public String update(ServiceInfo serviceInfo) {

        int res = serviceInfoDao.update(serviceInfo);
        if (res > 0) {
            return "修改成功";
        }
        return "修改失败";
    }

    //查询数据库表数据
    @Override
    public List selectServiceInfoAll(ServiceInfo serviceInfo) {

        //查询数据库表数据
        List<ServiceInfo> list = new ArrayList<>();
        list = serviceInfoDao.selectServiceInfoAll(serviceInfo);
        return list;
    }




}
