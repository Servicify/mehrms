package org.servicify.mehrms.service.sta;

import org.servicify.mehrms.model.AccountsAll;
import org.servicify.mehrms.model.MonthDate;
import org.servicify.mehrms.model.ScoreStatic;
import org.servicify.mehrms.service.per.RuleRoleService;
import org.servicify.mehrms.service.sal.AccountsAllService;
import org.servicify.mehrms.service.sal.SalSearchService;
import org.servicify.mehrms.utils.GetLastMonths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class StaScoreService {
    @Autowired
    SalSearchService salSearchService;
    @Autowired
    AccountsAllService accountsAllService;
    @Autowired
    RuleRoleService ruleRoleService;
    public ScoreStatic getScoreStatic(Date date, Integer integer) throws Exception{
//        获取对应的月份数
        List<MonthDate> listDate= GetLastMonths.getLastMonths(date,integer);
//        过滤不存在的日期
        listDate=accountsAllService.getDateList(listDate);
//        创建一个关于返回统计数据的对应时间集合
        ArrayList<String> dateList=new ArrayList<String>();
//        获取到月份对应的记录
        List<AccountsAll> accountsAllList=accountsAllService.getInfoByDates(listDate);
//        获取不同时间段内不同分数段的人数
        ScoreStatic scoreStatic=salSearchService.getInfosByTimes(listDate);
//
        ScoreStatic scoreStatic1=ruleRoleService.getInfosByTimes(listDate);
        scoreStatic.setBonusList(scoreStatic1.getBonusList());
        scoreStatic.setPunishmentsList(scoreStatic1.getPunishmentsList());
        ArrayList<Float> avgScoreList=new ArrayList<Float>();
        for(int i=0;i<listDate.size();i++){
            String string=String.valueOf(accountsAllList.get(i).getAvgRecordBonus());
            avgScoreList.add(Float.parseFloat(string));
        }
        scoreStatic.setAvgScore(avgScoreList);
        for(int i=0;i<listDate.size();i++){
            dateList.add(listDate.get(i).getYearAndMonth());
        }
        scoreStatic.setYearsAndMonths(dateList);
        return scoreStatic;
    }
}
