package org.servicify.mehrms.web.controller.sta;

import org.servicify.mehrms.mapper.AccountsAllMapper;
import org.servicify.mehrms.model.AccountsAll;
import org.servicify.mehrms.model.ScoreStatic;
import org.servicify.mehrms.service.sta.StaScoreService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/sta")
public class StaScoreController {
    @Autowired
    StaScoreService staScoreService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @Autowired
    AccountsAllMapper accountsAllMapper;
    @GetMapping("/score")
    public ScoreStatic getScoreStatic(Date date,Integer integer) throws Exception{
//        Date date,Integer integer
//
        loggerInfoService.insertLoggerInfos("/sta/score","查询积分统计信息");
        return  staScoreService.getScoreStatic(date,integer);
    }
}
