package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.RuleRole;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Mapper
public interface RuleRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RuleRole record);

    int insertSelective(RuleRole record);

    RuleRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RuleRole record);

    int updateByPrimaryKey(RuleRole record);

//    员工奖惩查询所有员工分页信息
    List<RuleRole> getAllInfoByPage(@Param("page") Integer page, @Param("size") Integer size,
                                    @Param("rr") RuleRole ruleRole,
                                    @Param("beginDateScope") Date[] beginDateScope,@Param("name")String name, @Param("workID")String workID);
//    获取条件参数插入后的记录总数
    Long getTotal(@Param("rr") RuleRole ruleRole, @Param("beginDateScope")Date[] beginDateScope,@Param("name")String name, @Param("workID")String workID);

    List<RuleRole> getAllInfosByEidAndDate(@Param("eid")Integer eid, @Param("beginDateScope")Date[] beginDateScope);

    List<RuleRole> getInfosByTimes(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}