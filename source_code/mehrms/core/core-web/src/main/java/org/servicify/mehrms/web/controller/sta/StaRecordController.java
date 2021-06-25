package org.servicify.mehrms.web.controller.sta;

import org.servicify.mehrms.model.RespPageBean;
import org.servicify.mehrms.service.sta.StaRecordService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/sta/record")
public class StaRecordController {
    @Autowired
    StaRecordService staRecordService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @GetMapping("/")
    public RespPageBean getStaRecordsInfos(Integer integer, Date date,Integer months)throws Exception{
        loggerInfoService.insertLoggerInfos("/sta/salary","查询人事记录统计信息");
        return staRecordService.getStaRecordsInfos(integer,date,months);
    }
}
