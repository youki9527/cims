package com.cims.dao;

import com.cims.pojo.AdminInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 关于AdminInfo的数据库操作
 */
@Mapper //这个接口是执行的mybatis的数据库操作
public interface AdminDao {

    //管理员登录
    @Select("select * from adminInfo where AName=#{AName} and APwd=#{APwd}")
    AdminInfo loginAdmin(AdminInfo admin);
    //检查邮箱和用户名是否一致
    @Select("select * from adminInfo where AEmail=#{email} and AName=#{AName}")
    AdminInfo loginAdminEmail(AdminInfo admin);

    //验证管理员用户名是否重名
    @Select("select count(*) from adminInfo where AName=#{AName}")
    int valName(AdminInfo admin);

    //查询管理员用户名是否存在，并且取出盐值
    @Select("select * from adminInfo where AName=#{AName}")
    AdminInfo selectByAName(AdminInfo admin);

//    //注册
//    @Insert("insert into adminInfo (AName,APwd,ASalt) value (#{AName},#{APwd},#{Asalt})")
//    int zhuceAdmin(AdminInfo admin);

    //查询
    @Select("select * from adminInfo ")
    List<AdminInfo> select();


    //根据aid查询
    @Select("select * from adminInfo where AId=#{AId}")
    AdminInfo selectByAId(AdminInfo admin);

    //修改
    @Update("update adminInfo set AName=#{AName} where AId=#{AId}")
    int updateAdmin(AdminInfo admin);

    //查询
    @Select("select * from adminInfo")
    List<AdminInfo> findAll();

    //删除
    @Delete("delete from adminInfo where AId=#{AId}")
    int delAdmin(AdminInfo admin);

    //根据编号查询
    @Select("select * from adminInfo where AId=#{AId}")
    List<AdminInfo> findByAId(AdminInfo admin);

    //根据管理员名查询
    @Select("select * from adminInfo where AName=#{AName}")
    List<AdminInfo> findByAName(AdminInfo admin);

    //修改密码
    @Update("update adminInfo set APwd=#{APwd} where AId=#{AId}")
    int updateAPwd(AdminInfo admin);

}
