package org.servicify.mehrms.web.controller.sta;

import org.servicify.mehrms.model.SalaryStatic;
import org.servicify.mehrms.service.sta.StaSalaryService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/sta")
public class StaSalaryController {
    @Autowired
    StaSalaryService staSalaryService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/salary")
    public SalaryStatic getSalaryStaticInfos(Date date,Integer integer)throws Exception{
//      Date date,Integer integer
//      date,integer
        loggerInfoService.insertLoggerInfos("/sta/salary","查询工资统计信息");
        return staSalaryService.getSalaryStaticInfos(date,integer);
    }
}
