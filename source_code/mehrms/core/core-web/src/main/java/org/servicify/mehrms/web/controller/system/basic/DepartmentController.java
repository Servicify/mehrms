package org.servicify.mehrms.web.controller.system.basic;

import org.servicify.mehrms.model.Department;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.service.DepartmentService;
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
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    LoggerInfoService loggerInfoService;
//    使用递归的方式进行加载
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        loggerInfoService.insertLoggerInfos("/system/basic/department/","查询所有的部门信息");
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep) {
        departmentService.addDep(dep);
        if (dep.getResult() == 1) {
            loggerInfoService.insertLoggerInfos("/system/basic/department/","添加部门信息");
            return RespBean.ok("添加成功", dep);
        }
        return RespBean.error("添加失败");
    }

    // 删除功能，在该级部门既没有子部门，且父部门存在的情况下，才可删除
    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult() == -2) {
            return RespBean.error("该部门下有子部门，删除失败");
        } else if (dep.getResult() == -1) {
            return RespBean.error("该部门下有员工，删除失败");
        } else if (dep.getResult() == 1) {
            loggerInfoService.insertLoggerInfos("/system/basic/department/{id}","删除部门信息");
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
