package org.servicify.mehrms.web.controller;

import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.servicify.mehrms.web.config.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
public class LoginController {
    @Autowired
    LoggerInfoService loggerInfoService;

    @GetMapping("/login")
//    @CrossOrigin("*")       //    避免用户未登录，访问其他页面报错，并且不会跳转
    public RespBean login(){
        return RespBean.error("尚未登录，请登录！");
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream());
    }
}