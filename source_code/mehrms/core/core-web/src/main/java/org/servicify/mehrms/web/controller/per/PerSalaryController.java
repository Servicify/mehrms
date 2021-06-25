package org.servicify.mehrms.web.controller.per;

import org.apache.ibatis.annotations.Delete;
import org.servicify.mehrms.model.*;
import org.servicify.mehrms.service.per.PerSalaryService;
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
@RequestMapping("/per/salary")
public class PerSalaryController {
    @Autowired
    PerSalaryService perSalaryService;
    @Autowired
    LoggerInfoService loggerInfoService;
//    【员工当前工资信息】查询信息
    @GetMapping("/emp")
    public RespPageBean getPerSalaryAllEmpInfo(@RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10") Integer size,
                                               ArgsRole argsRole,String sname){
        loggerInfoService.insertLoggerInfos("/per/salary/emp","查询员工当前薪资信息");
        return perSalaryService.getPerSalaryAllEmpInfo(page,size,argsRole,sname);
    }

//    【员工当前工资信息】修改单条员工工资参数记录
    @PutMapping("/emp")
    public RespBean putPerSalaryAllEmpInfo(@RequestBody ArgsRole argsRole){
        if(perSalaryService.putPerSalaryAllEmpInfo(argsRole)==1){
            loggerInfoService.insertLoggerInfos("/per/salary/emp","修改单条员工工资参数记录");
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

//    【员工当前工资信息】一键统一修改绩效分值
    @PutMapping("/emp/recordper")
    public RespBean putPerSalaryAllRecordPerInfo(@RequestBody ArgsRole argsRole){
        if(perSalaryService.putPerSalaryAllRecordPerInfo(argsRole)==1){
            loggerInfoService.insertLoggerInfos("/per/salary/emp/recordper","一键统一修改绩效分值");
            return RespBean.ok("绩效分值统一设置成功！");
        }
        return RespBean.error("绩效分值统一设置失败！");
    }
//    【员工当前工资信息】一键校验员工信息
    @GetMapping("/emp/validate")
    public RespBean validatePerSalaryAllInfo(){
        if(perSalaryService.validatePerSalaryAllInfo()==1){
            loggerInfoService.insertLoggerInfos("/per/salary/emp/validate","一键校验员工信息");
            return RespBean.ok("校验成功！");
        }else if(perSalaryService.validatePerSalaryAllInfo()==2){
            return RespBean.error("请先设置新员工的员工账套!");
        }
        return RespBean.error("校验失败！");
    }

//    【员工调薪记录】精准查询接口
//    【员工当前工资信息】查询信息
    @GetMapping("/")
    public RespPageBean getPerSalaryAllRecordInfo(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size,
                                                  ArgsRoleRecords argsRoleRecords,Date[] beginDateScope){
        loggerInfoService.insertLoggerInfos("/per/salary","查询员工薪资调整记录");
        return perSalaryService.getPerSalaryAllRecordInfo(page,size,argsRoleRecords,beginDateScope);
    }

    @PutMapping("/")
    public RespBean editPerSalaryRecordInfo(@RequestBody ArgsRoleRecords argsRoleRecords){
        if(perSalaryService.editPerSalaryRecordInfo(argsRoleRecords)==1){
            loggerInfoService.insertLoggerInfos("/per/salary","修改员工薪资调整记录");
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }
}

























