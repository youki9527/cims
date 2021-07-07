package com.cims.dao;



import com.cims.pojo.ClientInfo;
import com.cims.pojo.ContactInfo;
import org.apache.ibatis.annotations.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Mapper
public interface ContactInfoDao {

    //查询Bybirth---------------------------------------------------------项目
    @Select("select * from clientInfo where MONTH(CBirth) = MONTH(NOW()) and DAY(CBirth) = DAY(NOW()) and CMid=#{CMid}")
    List<ClientInfo> selectByBirthCMid(ClientInfo client);

    //查询
    @Select("select * from clientInfo where CMid= #{CMid}")
    List<ContactInfo> select(ContactInfo contact);

    //根据id查询
    @Select("select * from clientInfo where CId=#{CId}")
    ContactInfo selectByCId(ContactInfo contact);

    //修改
    @Update("update clientInfo set CName=#{CName},CTel=#{CTel},CBirth=#{CBirth},CAdd=#{CAdd},CSex=#{CSex},CEmail=#{CEmail} where CId=#{CId}")
    int update(ContactInfo contact);

    //删除
    @Delete("delete from clientInfo where CId=#{CId}")
    int del(ContactInfo contact);

    //根据编号查询
    @Select("select * from clientInfo where CId=#{CId}")
    List<ContactInfo> findByCId(ContactInfo contact);

    //根据用户名查询
    @Select("select * from clientInfo where CName=#{CName}")
    List<ContactInfo> findByCName(ContactInfo contact);

    //excel查询所有信息
    //查询
    @Select("select * from clientInfo")
    List<ContactInfo> findAll();

    //新增联系人
    @Insert("insert into clientInfo (CName,CTel,CBirth,CAdd,CSex,CEmail,CMid) value (#{CName},#{CTel},#{CBirth},#{CAdd},#{CSex},#{CEmail},#{CMid})")
    int saveAdd(ContactInfo contact);




}
