package org.servicify.mehrms.mapper;

import org.servicify.mehrms.model.EmpBeforeRemove;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface EmpBeforeRemoveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmpBeforeRemove record);

    int insertSelective(EmpBeforeRemove record);

    EmpBeforeRemove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpBeforeRemove record);

    int updateByPrimaryKey(EmpBeforeRemove record);
}