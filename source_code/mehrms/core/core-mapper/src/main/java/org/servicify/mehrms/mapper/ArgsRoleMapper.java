package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.ArgsRole;
import org.servicify.mehrms.model.Employee;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface ArgsRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArgsRole record);

    int insertSelective(ArgsRole record);

    ArgsRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArgsRole record);

    int updateByPrimaryKey(ArgsRole record);

    List<ArgsRole> getPerSalaryAllEmpInfoByPage(@Param("page") Integer page,
                                                @Param("size") Integer size,@Param("argsRole") ArgsRole argsRole,
                                                @Param("sname")String sname);

    Long getTotal(@Param("argsRole")ArgsRole argsRole,@Param("sname")String sname);

    List<ArgsRole> SelectAllInfo();

    ArgsRole   selectByEidKey(Integer eid);
}