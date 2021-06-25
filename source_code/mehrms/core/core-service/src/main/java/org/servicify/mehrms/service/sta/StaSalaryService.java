package org.servicify.mehrms.service.sta;

import org.servicify.mehrms.model.MonthDate;
import org.servicify.mehrms.model.SalaryStatic;
import org.servicify.mehrms.service.sal.SalSearchService;
import org.servicify.mehrms.utils.GetLastMonths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class StaSalaryService {
    @Autowired
    SalSearchService salSearchService;
    public SalaryStatic getSalaryStaticInfos(Date date, Integer integer) throws Exception{
        //        获取对应的月份数
        List<MonthDate> listDate= GetLastMonths.getLastMonths(date,integer);
        return salSearchService.getAllInfosByTimes(listDate);
    }
}
