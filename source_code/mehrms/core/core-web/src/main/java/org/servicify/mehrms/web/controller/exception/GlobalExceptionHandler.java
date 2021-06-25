package org.servicify.mehrms.web.controller.exception;

import org.servicify.mehrms.model.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
//  对于数据库操作异常反馈信息
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RespBean SQLException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据已被其他相关表关联，操作失败！请检查输入内容是否正确或联系管理员！");
        }
        return RespBean.error("数据库异常，操作失败！请检查输入内容是否正确或联系管理员！");
    }
}
