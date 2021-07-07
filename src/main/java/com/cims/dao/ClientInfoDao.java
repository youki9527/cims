package com.cims.dao;


import com.cims.pojo.ClientInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 关于userInfo的数据库操作
 */
@Mapper //这个接口是执行的mybatis的数据库操作
public interface ClientInfoDao {

    //验证用户名是否重名
    @Select("select count(*) from clientInfo where CName=#{CName}")
    int valName(ClientInfo client);

    //查询用户名是否存在，并且取出盐值
    @Select("select * from clientInfo where CName=#{CName}")
    ClientInfo selectByName(ClientInfo client);

    //注册
    @Insert("insert into clientInfo (CName,CSex,CBirth,CTel,CEmail,CDegree,CNo,CAdd,CPic) value (#{CName},#{CSex},#{CBirth},#{CTel},#{CEmail},#{CDegree},#{CNo},#{CAdd},#{CPic})")
    int zhuce(ClientInfo client);

    //查询---------------------------------------------------------项目
    @Select("select * from clientInfo ")
    List<ClientInfo> select();

    //查询Bybirth---------------------------------------------------------项目
    @Select("select * from clientInfo where MONTH(CBirth) = MONTH(NOW()) and DAY(CBirth) = DAY(NOW())")
    List<ClientInfo> selectByBirth(ClientInfo client);

    //根据Cid查询
    @Select("select * from clientInfo where CId=#{CId}")
    ClientInfo selectByCId(ClientInfo client);

    //根据CMname查询
    @Select("select * from clientInfo")
    List<ClientInfo> findByCMid(ClientInfo client);

    //修改
    @Update("update clientInfo set CName=#{CName},CSex=#{CSex},CBirth=#{CBirth},CTel=#{CTel},CEmail=#{CEmail},CDegree=#{CDegree},CNo=#{CNo},CAdd=#{CAdd},CPic=#{CPic}where CId=#{CId}")
    int update(ClientInfo client);

    //删除
    @Update("update  clientInfo set CMid=null where CId=#{CId}")
    int delCMname(ClientInfo client);

    //根据编号查询
    @Select("select * from clientInfo where CId=#{CId}")
    List<ClientInfo> findByCId(ClientInfo client);

    //根据用户名查询
    @Select("select * from clientInfo where CName=#{CName}")
    List<ClientInfo> findByCName(ClientInfo client);


}
