package org.servicify.mehrms.web.controller.salary;

import org.servicify.mehrms.model.AccountsBaseInfo;
import org.servicify.mehrms.model.DisabledData;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.RespPageBean;
import org.servicify.mehrms.service.sal.SalSearchService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/salary/month")
public class SalaryMonthController {
    @Autowired
    SalSearchService salSearchService;
    @Autowired
    LoggerInfoService loggerInfoService;

    @GetMapping("/")
    public RespPageBean getAllEnabledInfosByPage(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer size,
                                                 AccountsBaseInfo accountsBaseInfo,
                                                 float[] RealSalaryScope, float[] CreditEmpScope,
                                                 float[] CreditComScope, Date[] beginDateScope){
//        打印测试
        loggerInfoService.insertLoggerInfos("/salary/month/","月末处理中查询所有可修改的员工工资表信息");
        return salSearchService.getAllEnabledInfosByPage(page,size,accountsBaseInfo,
                RealSalaryScope,CreditEmpScope,CreditComScope,beginDateScope);
    }



    //  对单条或多条工资表信息作批量处理，执行月末处理接口
    @PutMapping("/disabled")
    public RespBean putdisabledInfo(@RequestBody DisabledData disabledData){
        if(salSearchService.putdisabledInfo(disabledData)==1){
            loggerInfoService.insertLoggerInfos("/salary/month/disabled",
                    "批量月末处理员工工资表信息");
            return RespBean.ok("批量月末处理成功!");
        }else{
            return RespBean.error("批量月末处理失败!");
        }
    }
}
