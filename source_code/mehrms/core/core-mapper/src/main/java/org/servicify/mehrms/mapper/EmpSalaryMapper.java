package org.servicify.mehrms.mapper;

import org.servicify.mehrms.model.EmpSalary;
import org.springframework.stereotype.Component;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Component
public interface EmpSalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmpSalary record);

    int insertSelective(EmpSalary record);

    EmpSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpSalary record);

    int updateByPrimaryKey(EmpSalary record);
//  根据eid查询相关账套信息
    EmpSalary selectByEidKey(Integer eid);
}