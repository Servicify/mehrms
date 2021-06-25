package org.servicify.mehrms.service.per;

import jdk.internal.dynalink.beans.StaticClass;
import org.servicify.mehrms.mapper.RuleMapper;
import org.servicify.mehrms.mapper.RuleRoleMapper;
import org.servicify.mehrms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class RuleRoleService {
    @Autowired
    RuleRoleMapper ruleRoleMapper;
    @Autowired
    RuleMapper ruleMapper;
    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
    DecimalFormat decimalFormat= new DecimalFormat("##.00");
    public RespPageBean getAllRuleRoleInfoByPage(Integer page, Integer size, RuleRole ruleRole, Date[] beginDateScope,String name,String workID) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<RuleRole> data=ruleRoleMapper.getAllInfoByPage(page,size,ruleRole,beginDateScope,name,workID);
        Long total=ruleRoleMapper.getTotal(ruleRole,beginDateScope,name,workID);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addRuleRole(RuleRole ruleRole) {
        ruleRole.setCreateDate(new Date());
        return  ruleRoleMapper.insertSelective(ruleRole);
    }

    public Integer updateRuleRole(RuleRole ruleRole) {
        return ruleRoleMapper.updateByPrimaryKey(ruleRole);
    }

    public Integer deleteRuleRole(Integer id) {
        return ruleRoleMapper.deleteByPrimaryKey(id);
    }

//    计算阶段绩效分数
    public Integer getAllScoreByEid(RuleRole ruleRole,Date[] beginDateScope){
        List<RuleRole> list=ruleRoleMapper.getAllInfosByEidAndDate(ruleRole.getEid(),beginDateScope);
        int count =100;
        if (list == null || list.size() == 0) {
            return count;
        }
        for(int i=0;i<list.size();i++){
            count+=ruleMapper.selectByPrimaryKey(list.get(i).getRid()).getRuleValue();
        }
        if (count >= 100) {
            count=100;
        }
        if(count<=0){
            count=0;
        }
        return count;
    }

    public ScoreStatic getInfosByTimes(List<MonthDate> listDate) {
        List<Integer> bonusList = new ArrayList<Integer>();
        List<Integer> punishmentsList = new ArrayList<Integer>();
        Integer bonusCounts=new Integer(0);
        Integer punishmentsCounts=new Integer(0);
        for(int i=0;i<listDate.size();i++){
            List<RuleRole> list1=ruleRoleMapper.
                    getInfosByTimes(listDate.get(i).getStartDate(),listDate.get(i).getEndDate());
            for(int j=0;j<list1.size();j++){
                if(list1.get(j).getRule().getRuleValue()>=0){
                    bonusCounts++;
                }else{
                    punishmentsCounts++;
                }
            }
            bonusList.add(bonusCounts);
            punishmentsList.add(punishmentsCounts);
            bonusCounts=0;
            punishmentsCounts=0;
        }
        ScoreStatic scoreStatic=new ScoreStatic();
        scoreStatic.setBonusList(bonusList);
        scoreStatic.setPunishmentsList(punishmentsList);
        return scoreStatic;
    }
}
