package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.servicify.mehrms.model.Rule;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Mapper
public interface RuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rule record);

    int insertSelective(Rule record);

    Rule selectByPrimaryKey(Integer id);

    List<Rule> selectByRuleName(Rule rule);

    int updateByPrimaryKeySelective(Rule record);

    int updateByPrimaryKey(Rule record);

    List<Rule> getAllRules();

    Integer deleteRulesByIds(Integer[] ids);

    List<Rule> getAllEnabledRules();
}