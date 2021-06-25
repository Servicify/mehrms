package org.servicify.mehrms.web.controller.salary;

import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.Salary;
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
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        loggerInfoService.insertLoggerInfos("/salary/sob/","查询工资账套信息");
        return salaryService.getAllSalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        if (salaryService.addSalary(salary) == 1) {
            loggerInfoService.insertLoggerInfos("/salary/sob/","添加工资账套信息");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalaryById(@PathVariable Integer id) {
        if (salaryService.deleteSalaryById(id) == 1) {
            loggerInfoService.insertLoggerInfos("/salary/sob/","删除工资账套信息");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateSalaryById(@RequestBody Salary salary) {
        if (salaryService.updateSalaryById(salary) == 1) {
            loggerInfoService.insertLoggerInfos("/salary/sob/","更新工资账套信息");
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
