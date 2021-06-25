package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.Work_Age;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface Work_AgeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Work_Age record);

    int insertSelective(Work_Age record);

    Work_Age selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Work_Age record);

    int updateByPrimaryKey(Work_Age record);

    List<Work_Age> getAllAgeInfo();

    Work_Age selectByEidKey(@Param("eid") Integer eid);
}