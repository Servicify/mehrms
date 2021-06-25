package org.servicify.mehrms.web.controller.per;

import org.servicify.mehrms.model.*;
import org.servicify.mehrms.service.per.EmployeetrainService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.servicify.mehrms.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/per/train")
public class EmployeetrainController {
    @Autowired
    LoggerInfoService loggerInfoService;
    @Autowired
    EmployeetrainService employeetrainService;
//    查询员工培训信息
    @GetMapping("/")
    public RespPageBean getTrainRecords(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        Employeetrain employeetrain, Date[] beginDateScope){
        loggerInfoService.insertLoggerInfos("/per/train","查询员工培训信息");
        return employeetrainService.getTrainRecords(page,size,employeetrain,beginDateScope);
    }
//    添加员工培训记录
    @PostMapping("/")
    public RespBean addTrainRecord(@RequestBody Employeetrain employeetrain){
        if(employeetrainService.addTrainRecord(employeetrain)==1){
            loggerInfoService.insertLoggerInfos("/per/train","添加员工培训信息");
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }
//    更改员工培训记录
    @PutMapping("/")
    public RespBean updateTrainRecordById(@RequestBody Employeetrain employeetrain){
        if(employeetrainService.updateTrainRecordById(employeetrain)==1){
            loggerInfoService.insertLoggerInfos("/per/train","更改员工培训信息");
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
//    删除员工培训记录
    @DeleteMapping("/{id}")
    public RespBean deleteTrainRecordByEid(@PathVariable Integer id){
        if(employeetrainService.deleteTrainRecordByEid(id)==1){
            loggerInfoService.insertLoggerInfos("/per/train","删除员工培训信息");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
