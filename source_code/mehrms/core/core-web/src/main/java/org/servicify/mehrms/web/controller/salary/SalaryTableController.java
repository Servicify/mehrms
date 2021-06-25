package org.servicify.mehrms.web.controller.salary;

import org.servicify.mehrms.model.AccountsBaseInfo;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.RespPageBean;
import org.servicify.mehrms.service.sal.SalSearchService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/salary/table")
public class SalaryTableController {

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
        loggerInfoService.insertLoggerInfos("/salary/table/","工资表管理中查询所有可修改的员工工资表信息");
        return salSearchService.getAllEnabledInfosByPage(page,size,accountsBaseInfo,
                RealSalaryScope,CreditEmpScope,CreditComScope,beginDateScope);
    }

//    创建员工工资表
    @GetMapping("/create")
    public RespBean getAndCreateEnabledInfos(Date beginDate) throws Exception{
        if(salSearchService.getAndCreateEnabledInfos(beginDate)==1) {
            loggerInfoService.insertLoggerInfos("/salary/table/create","创建员工工资表信息");
            return RespBean.ok("创建成功！");
        }else if(salSearchService.getAndCreateEnabledInfos(beginDate)==2){
            return RespBean.error("该月工资表已创建！");
        }else {
            return RespBean.error("创建失败！");
        }
    }

    //    对单个员工工资表信息进行修改
    @PutMapping("/enabled")
    public RespBean putEnabledInfo(@RequestBody AccountsBaseInfo accountsBaseInfo){
        if(salSearchService.putEnabledInfo(accountsBaseInfo)==1){
            loggerInfoService.insertLoggerInfos("/salary/table/enabled",
                    "修改员工工资表信息");
            return RespBean.ok("员工工资表信息修改成功!");
        }else{
            return RespBean.error("员工工资表信息修改失败!");
        }
    }

    //    批量更改员工工资表创建时间
    @GetMapping("/editDate")
    @Transactional
    public RespBean putEmpSalaryInfos(Integer[] ids,Date date)throws Exception{
        Integer integer=salSearchService.putEmpSalaryInfos(ids,date);
        if(integer==1){
            return RespBean.error("创建时间修改失败，请重新选则修改时间段!");
        }else if(integer==2){
            return RespBean.error("创建时间修改失败，请确保所选记录时间段信息一致!");
        }else if(integer==3){
            loggerInfoService.insertLoggerInfos("/salary/table/editDate",
                    "批量更改员工工资表创建时间");
            return RespBean.ok("创建时间修改成功!");
        }
        return RespBean.error("操作失败，请重新操作！");
    }

}
