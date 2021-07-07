package com.cims.dao;

import com.cims.pojo.ScheduleInfo;
import com.cims.pojo.ServiceInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ScheduleInfoDao {

    //添加 日程记录
    @Insert("insert into scheduleInfo(ScheduleDate,ScheduleTime,ScheduleMId,ScheduleMName,ScheduleUserId,ScheduleUserName,ScheduleUPNumber,ScheduleUaddress,ScheduleSType) " +
            "values(#{ScheduleDate},#{ScheduleTime},#{ScheduleMId},#{ScheduleMName},#{ScheduleUserId},#{ScheduleUserName},#{ScheduleUPNumber},#{ScheduleUaddress},#{ScheduleSType})")
    int add(ScheduleInfo scheduleInfo);

    //查询 所有日程列表信息
    @Select("select * from scheduleInfo where ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoAll(ScheduleInfo scheduleInfo);

    //查询某个客户经理的日程列表信息 根据开始日期
    @Select("select * from scheduleInfo where ScheduleDate>#{endDay} or ScheduleDate=#{endDay}  and  ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoByStartDay (ScheduleInfo scheduleInfo);

    //查询某个客户经理的日程列表信息 根据截止日期
    @Select("select * from scheduleInfo where ScheduleDate<#{endDay} or ScheduleDate=#{endDay}  and  ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoByEndDay (ScheduleInfo scheduleInfo);

    //查询某个客户经理的日程列表信息 根据服务类型
    @Select("select * from scheduleInfo where  ScheduleSType=#{ScheduleSType} and  ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoByScheduleSType(ScheduleInfo scheduleInfo);

    //查询某个客户经理的日程列表信息 根据开始日期 结束日期
    @Select("select * from serviceInfo where ScheduleDate between #{startDay} and  #{endDay}  and ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoByStartAndEndDay(ScheduleInfo scheduleInfo);

    //查询某个客户经理的日程列表信息 根据开始日期 服务类型
    @Select("select * from scheduleInfo where ScheduleDate>#{startDay} or ScheduleDate=#{startDay} and ScheduleSType=#{ScheduleSType} and  ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoByStartDayAndScheduleSType(ScheduleInfo scheduleInfo);

    //查询某个客户经理的日程列表信息 根据截止日期 服务类型
    @Select("select * from scheduleInfo where ScheduleDate>#{endDay} or ScheduleDate=#{endDay} and ScheduleSType=#{ScheduleSType} and  ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoByEndDayAndScheduleSType(ScheduleInfo scheduleInfo);

    //查询某个客户经理的日程列表信息 根据开始日期 结束日期 服务类型
    @Select("select * from serviceInfo where ScheduleDate between #{startDay} and  #{endDay} and ScheduleSType=#{ScheduleSType} and ScheduleMId=#{ScheduleMId}")
    List<ScheduleInfo> selectScheduleInfoByStartAndEndDayAndScheduleSType(ScheduleInfo scheduleInfo);


    //查询 所有日程列表信息（没有过期）
    @Select("select * from scheduleInfo where ScheduleMId=#{ScheduleMId} and ScheduleStatus=0")
    List<ScheduleInfo> selectScheduleInfo(ScheduleInfo scheduleInfo);


    //查询日程列表信息 根据ScheduleId
    @Select("select * from scheduleInfo where ScheduleId=#{ScheduleId}")
    ScheduleInfo selectScheduleInfoByScheduleId(ScheduleInfo scheduleInfo);

    //删除 日程记录
    @Delete("delete from scheduleInfo where ScheduleId=#{ScheduleId}")
    int del(ScheduleInfo scheduleInfo);

    //修改日程记录信息 根据ScheduleId
    @Update("update scheduleInfo set ScheduleDate=#{ScheduleDate},ScheduleTime=#{ScheduleTime},ScheduleUserName=#{ScheduleUserName},ScheduleUserId=#{ScheduleUserId},ScheduleSType=#{ScheduleSType}," +
            "ScheduleUPNumber=#{ScheduleUPNumber},ScheduleUaddress=#{ScheduleUaddress} where ScheduleId=#{ScheduleId}")
    int update(ScheduleInfo scheduleInfo);

    //修改日程记录状态信息 根据ScheduleId
    @Update("update scheduleInfo set ScheduleStatus=1 where ScheduleId=#{ScheduleId}")
    int updateScheduleStatus(ScheduleInfo scheduleInfo);
}
