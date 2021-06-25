package org.servicify.mehrms.web.controller.salary;

import org.servicify.mehrms.model.AccountsBaseInfo;
import org.servicify.mehrms.model.DisabledData;
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
@RequestMapping("/sal/search")
public class SalSearchController {
    @Autowired
    SalSearchService salSearchService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/")
    public RespPageBean getAllInfosByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          AccountsBaseInfo accountsBaseInfo,
                                          float[] RealSalaryScope, float[] CreditEmpScope,
                                          float[] CreditComScope, Date[] beginDateScope){
//        打印测试
        loggerInfoService.insertLoggerInfos("/sal/search/","查询所有员工工资表信息");
        return salSearchService.getAllInfosByPage(page,size,accountsBaseInfo,
                RealSalaryScope,CreditEmpScope,CreditComScope,beginDateScope);
    }

//    @GetMapping("/enabled")
//    public RespPageBean getAllEnabledInfosByPage(@RequestParam(defaultValue = "1") Integer page,
//                                          @RequestParam(defaultValue = "10") Integer size,
//                                          AccountsBaseInfo accountsBaseInfo,
//                                          float[] RealSalaryScope, float[] CreditEmpScope,
//                                          float[] CreditComScope, Date[] beginDateScope){
////        打印测试
//        loggerInfoService.insertLoggerInfos("/sal/search/","查询所有可修改的员工工资表信息");
//        return salSearchService.getAllEnabledInfosByPage(page,size,accountsBaseInfo,
//                RealSalaryScope,CreditEmpScope,CreditComScope,beginDateScope);
//    }

//    @GetMapping("/create")
//    public RespBean getAndCreateEnabledInfos(Date beginDate) throws Exception{
//        if(salSearchService.getAndCreateEnabledInfos(beginDate)==1) {
//            loggerInfoService.insertLoggerInfos("/sal/search/","创建员工工资表信息");
//            return RespBean.ok("创建成功！");
//        }else if(salSearchService.getAndCreateEnabledInfos(beginDate)==2){
//            return RespBean.error("该月工资表已创建！");
//        }else {
//            return RespBean.error("创建失败！");
//        }
//    }

    @PutMapping("/")
    public RespBean putInfo(@RequestBody AccountsBaseInfo accountsBaseInfo){
        if(salSearchService.putInfo(accountsBaseInfo)==1){
            loggerInfoService.insertLoggerInfos("/sal/search/","修改已不可修改的员工工资表信息中的备注");
            return RespBean.ok("备注修改成功!");
        }else{
            return RespBean.error("备注修改失败!");
        }
    }

//    @PutMapping("/enabled")
//    public RespBean putEnabledInfo(@RequestBody AccountsBaseInfo accountsBaseInfo){
//        if(salSearchService.putEnabledInfo(accountsBaseInfo)==1){
//            loggerInfoService.insertLoggerInfos("/sal/search/enabled",
//                    "月末处理员工工资表信息");
//            return RespBean.ok("该记录更新成功!");
//        }else{
//            return RespBean.error("该记录更新失败!");
//        }
//    }
////  批量执行月末处理接口
//    @PutMapping("/disabled")
//    public RespBean putdisabledInfo(@RequestBody DisabledData disabledData){
//        if(salSearchService.putdisabledInfo(disabledData)==1){
//            loggerInfoService.insertLoggerInfos("/sal/search/disabled",
//                    "批量月末处理员工工资表信息");
//            return RespBean.ok("批量月末处理成功!");
//        }else{
//            return RespBean.error("批量月末处理失败!");
//        }
//    }

////    批量更改员工工资表创建时间
//    @GetMapping("/editDate")
//    @Transactional
//    public RespBean putEmpSalaryInfos(Integer[] ids,Date date)throws Exception{
//        Integer integer=salSearchService.putEmpSalaryInfos(ids,date);
//        if(integer==1){
//            return RespBean.error("修改失败，请重新选则修改时间段!");
//        }else if(integer==2){
//            return RespBean.error("修改失败，请确保所选记录时间段信息一致!");
//        }else if(integer==3){
//            loggerInfoService.insertLoggerInfos("/sal/search/editDate",
//                    "批量更改员工工资表创建时间");
//            return RespBean.ok("修改成功!");
//        }
//        return RespBean.error("操作失败，请重新操作！");
//    }

    @GetMapping("/mail/send")
    public RespBean resendSalaryInfo(Integer id){
        if(salSearchService.resendSalaryInfo(id)==1){
            loggerInfoService.insertLoggerInfos("/sal/search/mail/send",
                    "重新发动员工工资表信息详情邮件");
            return RespBean.ok("已成功发送员工工资表信息详情邮件！");
        }else{
            return RespBean.error("发送失败，请检查该员工的邮箱信息是否正确！");
        }
    }

}













