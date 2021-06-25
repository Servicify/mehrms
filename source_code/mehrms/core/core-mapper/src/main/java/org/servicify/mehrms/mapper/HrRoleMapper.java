package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.HrRole;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Component
public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    void deleteByHrid(Integer hrid);

    Integer addRole(@Param("hrid") Integer hrid,@Param("rids") Integer[] rids);

    List<HrRole> selectByHrRoleID(Integer hrid);
}