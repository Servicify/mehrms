package org.servicify.mehrms.mapper;

import org.servicify.mehrms.model.Color;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface ColorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Color record);

    int insertSelective(Color record);

    Color selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Color record);

    int updateByPrimaryKey(Color record);

    List<Color> selectAllInfos();

    Color selectByHrid(Integer hrid);
}