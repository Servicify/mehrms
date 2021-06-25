package org.servicify.mehrms.web.controller.system.manager;

import org.servicify.mehrms.model.EmpHr;
import org.servicify.mehrms.model.Hr;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.Role;
import org.servicify.mehrms.service.EmpHrService;
import org.servicify.mehrms.service.HrService;
import org.servicify.mehrms.service.RoleService;
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
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    HrService hrService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @Autowired
    RoleService roleService;
    @Autowired
    EmpHrService empHrService;
    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        loggerInfoService.insertLoggerInfos("/system/manager/hr/","查询所有的用户信息");
        return  hrService.getAllHrs(keywords);
    }

    @GetMapping("/add")
    public RespBean addHr(){
        if (hrService.addHr() == 1) {
            loggerInfoService.insertLoggerInfos("/system/manager/hr/","添加一个用户信息");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr) {
        if (hrService.updateHr(hr) == 1) {
            loggerInfoService.insertLoggerInfos("/system/manager/hr/","更新用户信息");
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        loggerInfoService.insertLoggerInfos("/system/manager/hr/roles","查询所有用户对应的权限");
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid, Integer[] rids) {
        if (hrService.updateHrRole(hrid, rids)) {
            loggerInfoService.insertLoggerInfos("/system/manager/hr/roles","更新所有用户对应的权限");
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id) {
        if (hrService.deleteHrById(id) == 1) {
            loggerInfoService.insertLoggerInfos("/system/manager/hr/"+id,"删除用户");
            return RespBean.ok("删除成功!");
        }else if(hrService.deleteHrById(id) == 2) {
            return RespBean.error("该用户为超级管理员，禁止删除！");
        }else{
            return RespBean.error("删除失败!");
        }
    }

    @PutMapping("/emp")
    public RespBean updateEmpHrInfo(@RequestBody EmpHr empHr) {
        if (empHrService.updateEmpHrInfo(empHr)==1) {
            loggerInfoService.insertLoggerInfos("/hr/emp","修改hrid="+empHr.getHrid()+"的账号管理权限");
            return RespBean.ok("账户绑定更新成功!");
        }
        return RespBean.error("账户绑定更新失败!");
    }
}
