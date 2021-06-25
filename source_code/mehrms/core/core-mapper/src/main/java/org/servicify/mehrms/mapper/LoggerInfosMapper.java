package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.LoggerInfos;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface LoggerInfosMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoggerInfos record);

    int insertSelective(LoggerInfos record);

    LoggerInfos selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoggerInfos record);

    int updateByPrimaryKey(LoggerInfos record);

    List<LoggerInfos> getLoggerInfos(@Param("page") Integer page, @Param("size")Integer size,
                                     @Param("requestManager")String requestManager,
                                     @Param("beginDateScope") Date[] beginDateScope);

    Long getTotal(@Param("requestManager")String requestManager, @Param("beginDateScope")Date[] beginDateScope);
}