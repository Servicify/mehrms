package org.servicify.mehrms.web.controller.system.basic;

import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.Rule;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.servicify.mehrms.service.system.basic.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/system/basic/ecmana")
public class EcManaController {
    @Autowired
    RuleService ruleService;
    @Autowired
    LoggerInfoService loggerInfoService;
//    查询所有的规则
    @GetMapping("/")
    public List<Rule> getAllEcManas(){
        loggerInfoService.insertLoggerInfos("/system/basic/ecmana/","查询所有的奖惩规则信息");
        return ruleService.getAllRules();
    }

//    已启用规则调用接口
    @GetMapping("/enabled")
    public List<Rule> getAllEnabledEcManas(){
        loggerInfoService.insertLoggerInfos("/system/basic/ecmana/enabled","查询已启用的奖惩规则信息");
        return ruleService.getAllEnabledRules();
    }
//    添加一个规则
    @PostMapping("/")
    public RespBean addRule(@RequestBody Rule rule){
        if(ruleService.addRule(rule)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/ecmana/","添加奖惩规则信息");
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

//    修改规则
    @PutMapping("/")
    public RespBean updateRuleById(@RequestBody Rule rule){
        if(ruleService.updateRuleById(rule)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/ecmana/","更新奖惩规则信息");
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
//    删除规则
    @DeleteMapping("/{id}")
    public RespBean deleteRuleById(@PathVariable Integer id){
        if(ruleService.deleteRuleById(id)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/ecmana/{id}","删除单条奖惩规则信息");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
//    批量删除规则
    @DeleteMapping("/")
    public RespBean deleteRulesByIds(Integer[] ids) {
        if (ruleService.deleteRulesByIds(ids) == ids.length) {
            loggerInfoService.insertLoggerInfos("/system/basic/ecmana/","批量奖惩规则信息");
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

//    查询单条规则，涉及奖惩组以及员工奖惩
    @PostMapping("/rulename")
    public List<Rule> getEcMana(@RequestBody Rule rule){
        loggerInfoService.insertLoggerInfos("/system/basic/ecmana/","查询单条奖惩规则信息");
        return ruleService.selectRule(rule);
    }

}
