package org.servicify.mehrms.web.controller;

import org.servicify.mehrms.model.Hr;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.service.HrService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
public class HrInfoController {

    @Autowired
    HrService hrService;
    @Autowired
    LoggerInfoService loggerInfoService;
    @Value("${fastdfs.nginx.host}")
    String nginxHost;


    @GetMapping("/hr/info")
    public Hr getCurrentHr(Authentication authentication) {
        loggerInfoService.insertLoggerInfos("/hr/info","查询当前用户信息：");
        return ((Hr) authentication.getPrincipal());
    }

    @PutMapping("/hr/info")
    public RespBean updateHr(@RequestBody Hr hr, Authentication authentication) {
        if (hrService.updateHr(hr) == 1) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr, authentication.getCredentials(), authentication.getAuthorities()));
            loggerInfoService.insertLoggerInfos("/hr/info","修改当前用户信息：");
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PutMapping("/hr/pass")
    public RespBean updateHrPasswd(@RequestBody Map<String, Object> info) {
        String oldpass = (String) info.get("oldpass");
        System.out.println(oldpass);
        String pass = (String) info.get("pass");
        System.out.println(pass);
        Integer hrid = (Integer) info.get("hrid");
        System.out.println(hrid);
        if (hrService.updateHrPasswd(oldpass, pass, hrid)) {
            loggerInfoService.insertLoggerInfos("/hr/info","修改当前用户密码：");
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }


}
