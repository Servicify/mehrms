package org.servicify.mehrms.web.controller.system;

import org.servicify.mehrms.model.Color;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.service.system.ColorService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/sys/cfg")
public class ColorController {
    @Autowired
    ColorService colorService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/")
    public Color getInfo(){
        loggerInfoService.insertLoggerInfos("/sys/cfg/","查询当前用户设置的颜色");
        return colorService.getInfo();
    }

    @PutMapping("/")
    public RespBean updateColorInof(@RequestBody Color color){
        if(colorService.updateColorInof(color)==1){
            loggerInfoService.insertLoggerInfos("/sys/cfg/","更新当前用户设置的颜色");
            return RespBean.ok("更新成功，请刷新浏览器！");
        }else{
            return RespBean.error("更新失败，请重试或者联系管理员！");
        }
    }
}
