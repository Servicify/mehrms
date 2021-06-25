package org.servicify.mehrms.utils;

import org.servicify.mehrms.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class HrUtils {
    //    获取当前已登录用户的角色等信息
    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}

