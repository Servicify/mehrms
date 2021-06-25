package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.EmpInfoMailStatus;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface EmpInfoMailStatusMapper {
    int deleteByPrimaryKey(Integer empInfoMailStatusId);

    int insert(EmpInfoMailStatus record);

    int insertSelective(EmpInfoMailStatus record);

    EmpInfoMailStatus selectByPrimaryKey(Integer empInfoMailStatusId);

    int updateByPrimaryKeySelective(EmpInfoMailStatus record);

    int updateByPrimaryKey(EmpInfoMailStatus record);

    EmpInfoMailStatus selectByEidKey(@Param("eid") Integer eid);
}