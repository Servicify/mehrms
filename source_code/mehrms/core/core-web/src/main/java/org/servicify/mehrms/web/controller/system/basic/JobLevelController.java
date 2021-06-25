package org.servicify.mehrms.web.controller.system.basic;


import org.servicify.mehrms.model.JobLevel;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.service.JobLevelService;
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
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {
    @Autowired
    LoggerInfoService loggerInfoService;
    @Autowired
    JobLevelService jobLevelService;
    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        loggerInfoService.insertLoggerInfos("/system/basic/joblevel/","查询所有的职称信息");
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel){
        if(jobLevelService.addJobLevel(jobLevel)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/joblevel/","添加职称信息"+jobLevel.toString());
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updateJobLevelById(@RequestBody JobLevel jobLevel){
        if(jobLevelService.updateJobLevelById(jobLevel)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/joblevel/","更新职称信息"+jobLevel.toString());
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
        if(jobLevelService.deleteJobLevelById(id)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/joblevel/{id}","删除职称信息"+"id="+id);
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelsByIds(Integer[] ids) {
        if (jobLevelService.deleteJobLevelsByIds(ids) == ids.length) {
            loggerInfoService.insertLoggerInfos("/system/basic/joblevel/","批量删除职称信息");
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
