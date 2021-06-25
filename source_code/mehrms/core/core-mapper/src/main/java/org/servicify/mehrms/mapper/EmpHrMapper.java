package org.servicify.mehrms.mapper;

import org.servicify.mehrms.model.EmpHr;

public interface EmpHrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmpHr record);

    int insertSelective(EmpHr record);

    EmpHr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpHr record);

    int updateByPrimaryKey(EmpHr record);

    Integer deleteByEmpID(Integer eid);

    Integer deleteByHrID(Integer hrid);

}