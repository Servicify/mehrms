package org.servicify.mehrms.web.controller.system.basic;

import org.servicify.mehrms.model.Position;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.service.PositionService;
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
//接口一定要对应数据库表中的menu接口
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    PositionService positionService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/")
    public List<Position> getAllPositions(){
        loggerInfoService.insertLoggerInfos("/system/basic/pos/","查询所有的职位信息");
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPositions(@RequestBody Position position){
        if(positionService.addPositions(position)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/pos/","添加职位信息");
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if(positionService.updatePosition(position)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/pos/","更改职位信息");
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id){
        if(positionService.deletePositionById(id)==1){
            loggerInfoService.insertLoggerInfos("/system/basic/pos/","删除职位信息");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids){
        if(positionService.deletePositionByIds(ids)==ids.length){
            loggerInfoService.insertLoggerInfos("/system/basic/pos/","批量删除职位信息");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
