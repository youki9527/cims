package com.cims.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cims.dao.ScheduleInfoDao;
import com.cims.pojo.ScheduleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ScheduleInfoServiceImpl implements ScheduleInfoService {

    //创建一个serviceInfoDao的实现类对象（数据库）
    @Autowired(required = false)
    ScheduleInfoDao scheduleInfoDao;

    //查询 日程
    @Override
    public HashMap<String, Object> select(ScheduleInfo scheduleInfo) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        //1.设置分页参数
        PageHelper.startPage(scheduleInfo.getPage(), scheduleInfo.getRow());
        //2查询数据库表数据
        List<ScheduleInfo> list = new ArrayList<>();
        //2.1不按服务类型
        if (scheduleInfo.getScheduleSType().equals("")) {
            //开始日期为空
            if (scheduleInfo.getStartDay().equals("")) {
                //截止日期为空 所有
                if (scheduleInfo.getEndDay().equals("")) {
                    list = scheduleInfoDao.selectScheduleInfoAll(scheduleInfo);
                }
                //截止日期不为空 按截止日期查询
                else {
                    list = scheduleInfoDao.selectScheduleInfoByEndDay(scheduleInfo);
                }
            }
            //开始日期不为空
            else {
                //截止日期为空 开始日期
                if (scheduleInfo.getEndDay().equals("")) {
                    list = scheduleInfoDao.selectScheduleInfoByStartDay(scheduleInfo);
                }
                //截止日期不为空 开始日期和截止日期
                else {
                    list = scheduleInfoDao.selectScheduleInfoByStartAndEndDay(scheduleInfo);
                }
            }
        }
        //2.2按服务类型
        else{
                //开始日期为空
                if (scheduleInfo.getStartDay().equals("")) {
                    //截止日期为空 按服务类型查询
                    if (scheduleInfo.getEndDay().equals("")) {
                        list = scheduleInfoDao.selectScheduleInfoByScheduleSType(scheduleInfo);
                    }
                    //截止日期不为空 按截止日期和服务类型查询
                    else {
                        list = scheduleInfoDao.selectScheduleInfoByEndDayAndScheduleSType(scheduleInfo);
                    }
                }
                //开始日期不为空
                else {
                    //截止日期为空 按开始日期和服务类型查询
                    if (scheduleInfo.getEndDay().equals("")) {
                        list = scheduleInfoDao.selectScheduleInfoByStartAndEndDayAndScheduleSType(scheduleInfo);
                    }
                    //截止日期不为空 按开始日期和结束日期和服务类型查询
                    else {
                        list = scheduleInfoDao.selectScheduleInfoByStartAndEndDayAndScheduleSType(scheduleInfo);
                    }
                }
            }
        //3.把查询的数据转换成分页对象
        PageInfo<ScheduleInfo> page = new PageInfo<ScheduleInfo>(list);
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
        return map;
    }


    @Override
    public ScheduleInfo selectByScheduleId(ScheduleInfo scheduleInfo) {

        return scheduleInfoDao.selectScheduleInfoByScheduleId(scheduleInfo);
    }


    //添加 日程
    @Override
    public HashMap<String, Object> addScheduleInfo(ScheduleInfo scheduleInfo) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        int res = scheduleInfoDao.add(scheduleInfo);
        if (res > 0) {
            map.put("info", "添加成功");
        } else {
            map.put("info", "添加失败");
        }
        return map;
    }

    //修改 日程
    @Override
    public String update(ScheduleInfo scheduleInfo) {

        int res = scheduleInfoDao.update(scheduleInfo);
        if (res > 0) {
            return "修改成功";
        }
        return "修改失败";
    }

    //删除 日程
    @Override
    public String del(ScheduleInfo scheduleInfo) {

        int res = scheduleInfoDao.del(scheduleInfo);
        if (res > 0) {
            return "删除成功";
        }
        return "删除失败";
    }

    //提醒快到期的日程
    @Override
    public HashMap<String, Object> remind(ScheduleInfo scheduleInfo) {

        HashMap<String, Object> map = new HashMap<String, Object>();

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
        Date currentTime = new Date(System.currentTimeMillis());

        //查询数据库表数据
        List<ScheduleInfo> list = new ArrayList<>();
        list = scheduleInfoDao.selectScheduleInfo(scheduleInfo);

        //信息数组
        List<String> remindInDateList = new ArrayList<>();
        List<String> remindOutDateList = new ArrayList<>();
        //超过现在时间 在预期里
        List<ScheduleInfo> ScheduleInfoInDateList = new ArrayList<>();
        //没有超过现在时间 过期
        List<ScheduleInfo> ScheduleInfoOutDateList = new ArrayList<>();

        //遍历数据库数据
        for (int i = 0; i < list.size(); i++) {

            //年月日
            String ScheduleDate = list.get(i).getScheduleDate();
            //时分秒
            String ScheduleTime = list.get(i).getScheduleTime();
            //将年月日时分秒
            String dateTime = ScheduleDate + " " + ScheduleTime;
            //将字符串装换为date
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("转换后的时间" + simpleDateFormat.format(date));

            //compareTo()方法的返回值，currentTime小于date返回-1，currentTime大于date返回1，相等返回0
            int compareTo = currentTime.compareTo(date);
            System.out.println(compareTo);
            //日程时间大于现在时间 提醒
            if (compareTo ==-1) {
                long diff = date.getTime() - currentTime.getTime();
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);
                System.out.print(diffDays + " days, ");
                System.out.print(diffHours + " hours, ");
                System.out.print(diffMinutes + " minutes, ");
                System.out.print(diffSeconds + " seconds.");
                ScheduleInfoInDateList.add(list.get(i));
                remindInDateList.add("你的编号为"+list.get(i).getScheduleId()+"的日程"+"日期为:"+list.get(i).getScheduleDate()+list.get(i).getScheduleTime()+"服务对象为"+list.get(i).getScheduleUserName()+"还剩"+diffDays+"天 "+diffHours+"时"+diffMinutes+"分"+"到期");
            }
            //日程时间小于等于现在时间 已经过期
            else {
                ScheduleInfoOutDateList.add(list.get(i));
                remindOutDateList.add("你的编号为"+list.get(i).getScheduleId()+"的日程"+"日期为:"+list.get(i).getScheduleDate()+list.get(i).getScheduleTime()+"服务对象为"+list.get(i).getScheduleUserName()+"已经到期");
                //将状态改为1 表示过期
                int res=scheduleInfoDao.updateScheduleStatus(list.get(i));
            }

        }
        map.put("OutDate",ScheduleInfoOutDateList);
        map.put("InDate",ScheduleInfoInDateList);
        map.put("OutDateText",remindOutDateList);
        map.put("InDateText",remindInDateList);
        return map;
    }


}
