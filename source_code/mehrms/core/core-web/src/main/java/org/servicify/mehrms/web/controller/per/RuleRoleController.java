package org.servicify.mehrms.web.controller.per;

import org.servicify.mehrms.model.*;
import org.servicify.mehrms.service.per.RuleRoleService;
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
@RequestMapping("/per/ec")
public class RuleRoleController {
    @Autowired
    RuleRoleService ruleRoleService;
    @Autowired
    LoggerInfoService loggerInfoService;
//    员工奖惩功能的查询，以及精准查询
    @GetMapping("/")
    public RespPageBean getAllRuleRoleInfoByPage(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer size,
                                                 RuleRole ruleRole, Date[] beginDateScope,String name,String workID){
        loggerInfoService.insertLoggerInfos("/per/ec/","查询员工奖惩信息");
        return ruleRoleService.getAllRuleRoleInfoByPage(page,size,ruleRole,beginDateScope,name,workID);
    }
//     添加rule_role记录接口
    @PostMapping("/")
    public RespBean addRuleRole(@RequestBody RuleRole ruleRole) {
        if (ruleRoleService.addRuleRole(ruleRole) == 1) {
            loggerInfoService.insertLoggerInfos("/per/ec/","添加员工奖惩信息");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
//    修改数据接口
    @PutMapping("/")
    public RespBean updateRuleRole(@RequestBody RuleRole ruleRole){
        if(ruleRoleService.updateRuleRole(ruleRole)==1){
            loggerInfoService.insertLoggerInfos("/per/ec/","修改员工奖惩信息");
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
//    删除数据接口
    @DeleteMapping("/{id}")
    public RespBean deleteRuleRole(@PathVariable Integer id){
        if(ruleRoleService.deleteRuleRole(id)==1){
            loggerInfoService.insertLoggerInfos("/per/ec/","删除员工奖惩信息");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}