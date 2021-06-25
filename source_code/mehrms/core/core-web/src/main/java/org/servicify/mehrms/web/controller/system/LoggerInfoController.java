package org.servicify.mehrms.web.controller.system;

import org.servicify.mehrms.model.RespPageBean;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/sys/log")
public class LoggerInfoController {
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/")
    public RespPageBean getLoggerInfos(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          String requestManager, Date[] beginDateScope){
        loggerInfoService.insertLoggerInfos("/sys/log","查询操作日志记录");
        return loggerInfoService.getLoggerInfos(page,size,requestManager,beginDateScope);
    }
}
