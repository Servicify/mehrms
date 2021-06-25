package org.servicify.mehrms.web.controller.salary;


import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.RespPageBean;
import org.servicify.mehrms.model.Salary;
import org.servicify.mehrms.service.EmployeeService;
import org.servicify.mehrms.service.SalaryService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        loggerInfoService.insertLoggerInfos("/salary/sobcfg/","查询员工工资账套信息");
        return employeeService.getEmployeeByPageWithSalary(page, size);
    }

//  修改员工工资账套的选项的加载数据
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        loggerInfoService.insertLoggerInfos("/salary/sobcfg/salaries","查询所有的员工工资账套信息");
        return salaryService.getAllSalaries();
    }

//  更新账套数据
    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid, Integer sid) {
        Integer result = employeeService.updateEmployeeSalaryById(eid, sid);
//      employeeService.updateEmployeeSalaryById(eid, sid)对于已经存在的更新操作，会先删除后重新插入
//      此种更新操作会返回2，而对于尚未设置工资账套的员工会直接插入，并返回1
        if (result == 1 || result == 2) {
            loggerInfoService.insertLoggerInfos("/salary/sobcfg/","更新员工工资账套信息");
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
