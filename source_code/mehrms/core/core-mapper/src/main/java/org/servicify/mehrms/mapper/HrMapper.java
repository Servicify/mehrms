package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.Hr;
import org.servicify.mehrms.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
//  用户类映射
@Component
public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    Hr loadUserByUsername(String username);

    List<Role> getHrRolesById(Integer id);

    //  该查询查询的是获取除当前用户以外的所有用户及其角色等信息，而当前已登录用户的修改查看的权限在个人中心查看
    List<Hr> getAllHrs(@Param("hrid")Integer hrid, @Param("keywords") String keywords);

    Integer updatePasswd(@Param("hrid") Integer hrid, @Param("encodePass") String encodePass);

}