package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.ArgsRoleRecords;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface ArgsRoleRecordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArgsRoleRecords record);

    int insertSelective(ArgsRoleRecords record);

    ArgsRoleRecords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArgsRoleRecords record);

    int updateByPrimaryKey(ArgsRoleRecords record);

    List<ArgsRoleRecords> getPerSalaryAllRecordInfo(@Param("page") Integer page, @Param("size") Integer size,
                                                    @Param("arr") ArgsRoleRecords argsRoleRecords,
                                                    @Param("beginDateScope")Date[] beginDateScope);

    Long getTotal(@Param("arr")ArgsRoleRecords argsRoleRecords, @Param("beginDateScope")Date[] beginDateScope);

}