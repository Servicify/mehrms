package org.servicify.mehrms.web.controller.per;

import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.Employeeremove;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.RespPageBean;
import org.servicify.mehrms.service.per.EmployeeremoveService;
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
@RequestMapping("/per/mv")
public class PerMvController {
//    员工岗位信息页面请求接口
    @Autowired
    EmployeeremoveService employeeremoveService;
    @Autowired
    LoggerInfoService loggerInfoService;
//    获取【员工岗位】信息页面【查询】请求接口
    @GetMapping("/emp")
    public RespPageBean getPerMvAllEmpsInfo(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        Employee employee){
        loggerInfoService.insertLoggerInfos("/per/mv/emp","查询员工岗位信息");
        return employeeremoveService.getPerMvAllEmpsInfo(page,size,employee);
    }
    //      【员工岗位】【更新】信息
    @PutMapping("/emp")
    public RespBean updatePerMvEmpInfo(@RequestBody Employee employee){
        if(employeeremoveService.updatePerMvEmpInfo(employee)==1){
            loggerInfoService.insertLoggerInfos("/per/mv/emp","更新员工岗位信息");
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }
    //    【调岗记录】页面【查询】请求接口
    @GetMapping("/")
    public RespPageBean getPerMvAllRecordsInfo(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            Employeeremove employeeremove, Date[] beginDateScope){
        loggerInfoService.insertLoggerInfos("/per/mv/","查询调岗记录信息");
        return employeeremoveService.getPerMvAllRecordsInfo(page,size,employeeremove,beginDateScope);
    }
    //      【员工岗位】【更新】信息
    @PutMapping("/")
    public RespBean updatePerMvRecordInfo(@RequestBody Employeeremove employeeremove){
        if(employeeremoveService.updatePerMvRecordInfo(employeeremove)==1){
            loggerInfoService.insertLoggerInfos("/per/mv/","更改调岗记录信息");
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }
}
