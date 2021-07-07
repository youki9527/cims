package com.cims.dao;

import com.cims.pojo.ClientInfo;
import com.cims.pojo.ManagerInfo;
import com.cims.pojo.ServiceInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ServiceInfoDao {

    //添加服务记录
    @Insert("insert into serviceInfo(SType,SDate,SUserName,SManagerName,SCost,SContent,SUserId,SManagerId,STime,SGrade,SFback) " +
            "values(#{SType},#{SDate},#{SUserName},#{SManagerName},#{SCost},#{SContent},#{SUserId},#{SManagerId},#{STime},#{SGrade},#{SFback})")
    @Options(useGeneratedKeys = true, keyProperty = "SId")
    int add(ServiceInfo serviceInfo);

    //查询客户的邮箱信息
    @Select("select CEmail from clientInfo where CId=#{CId}")
    String selectClientEmailByCId(ClientInfo clientInfo);

    //查询某个客户经理的服务列表信息
    @Select("select SFbackStatus from serviceInfo where  SId=#{SId}")
     int selectServiceInfoSFbackStatus(ServiceInfo serviceInfo);

    //更新插入客户反馈
    @Update("update serviceInfo set SFback=#{SFback},SGrade=#{SGrade},SFbackStatus=1 where SId=#{SId}")
    int updateServiceInfoBack(ServiceInfo serviceInfo);


    //查询客户经理负责的客户的信息
    @Select("select * from clientInfo where CMid=#{MId}")
    List<ClientInfo> selectClientInfo(ManagerInfo managerInfo);

    //查询某个客户经理的服务列表信息
    @Select("select * from serviceInfo where SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfo(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据服务类型类型
    @Select("select * from serviceInfo where SType=#{SType} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoBySType(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据开始日期
    @Select("select * from serviceInfo where SDate>#{startDay} or SDate=#{startDay} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoByStartDay(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据开始日期 服务类型
    @Select("select * from serviceInfo where SDate>#{startDay} or SDate=#{startDay} and SType=#{SType} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoByStartDayAndSType(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据结束日期
    @Select("select * from serviceInfo where SDate<#{endDay} or SDate=#{endDay} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoByEndDay(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据结束日期 服务类型
    @Select("select * from serviceInfo where SDate<#{endDay} or SDate=#{endDay} and SType=#{SType} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoByEndDayAndSType(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据开始日期 结束日期
    @Select("select * from serviceInfo where SDate between #{startDay} and  #{endDay} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoByStartAndEndDay(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据开始日期 结束日期 服务类型
    @Select("select * from serviceInfo where SDate between #{startDay} and  #{endDay} and SType=#{SType} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoByStartAndEndDayAndSType(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据服务编号
    @Select("select * from serviceInfo where SId=#{SIdOrUserName} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoBySIdAndSMId(ServiceInfo serviceInfo);

    //查询某个客户经理的服务列表信息 根据客户姓名
    @Select("select * from serviceInfo where SUserName=#{SIdOrUserName} and SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoBySUserName(ServiceInfo serviceInfo);

    //删除
    @Delete("delete from serviceInfo where SId=#{SId}")
    int del(ServiceInfo serviceInfo);

    //查询服务列表信息 根据SId
    @Select("select * from serviceInfo where SId=#{SId}")
    ServiceInfo selectServiceInfoBySId(ServiceInfo serviceInfo);

    //修改服务列表信息 根据SId
    @Update("update serviceInfo set SUserName=#{SUserName},SUserId=#{SUserId},SType=#{SType},SContent=#{SContent},SCost=#{SCost}," +
            "SDate=#{SDate},STime=#{STime} where SId=#{SId}")
    int update(ServiceInfo serviceInfo);

    //查询所有服务列表信息
    @Select("select * from serviceInfo where SManagerId=#{SManagerId}")
    List<ServiceInfo> selectServiceInfoAll(ServiceInfo serviceInfo );
}
