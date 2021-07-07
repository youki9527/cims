package com.cims.dao;


import com.cims.pojo.ManagerInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManagerInfoDao {



    //修改密码
    @Update("update managerInfo set MPwd=#{MPwd} where MLogin=#{MLogin}")
    int updatePwd(ManagerInfo manager) ;

    //登录
    @Select("select * from managerInfo where MLogin=#{MLogin} and MPwd=#{MPwd}")
    ManagerInfo login(ManagerInfo manager);


    //查询用户名是否存在，并且取出盐值
    @Select("select * from managerInfo where MLogin=#{MLogin}")
    ManagerInfo selectByMLogin(ManagerInfo manager);

    //查询
    @Select("select * from managerInfo ")
    List<ManagerInfo> select();

    //根据id查询
    @Select("select * from managerInfo where MId=#{MId}")
    ManagerInfo selectByMId(ManagerInfo manager);

    //修改
    @Update("update managerInfo set MPic=#{MPic},MName=#{MName},MSex=#{MSex},MBirth=#{MBirth},MTel=#{MTel},MEmail=#{MEmail},MDegree=#{MDegree},MNo=#{MNo},MRemark=#{MRemark} where MId=#{MId}")
    int update(ManagerInfo manager);

    //删除
    @Delete("delete from managerInfo where MId=#{MId}")
    int del(ManagerInfo manager);

    //根据编号查询
    @Select("select * from managerInfo where MId=#{MId}")
    List<ManagerInfo> findByMId(ManagerInfo manager);

    //根据用户名查询
    @Select("select * from managerInfo where MName=#{MName}")
    List<ManagerInfo> findByMName(ManagerInfo manager);

    //excel查询所有信息
    //查询
    @Select("select * from managerInfo")
    List<ManagerInfo> findAll();

    //新增客户经理
    @Insert("insert into managerInfo (MLogin,MName,MPic,MSex,MBirth,MTel,MEmail,MDegree,MNo,MRemark,MPwd,MSalt) value (#{MLogin},#{MName},#{MPic},#{MSex},#{MBirth},#{MTel},#{MEmail},#{MDegree},#{MNo},#{MRemark},#{MPwd},#{MSalt})")
    int saveAdd(ManagerInfo manager);

    // 验证登录名是否重名
    @Select("select count(*) from managerInfo where MLogin=#{MLogin}")
    int valName(ManagerInfo manager);

}
