package org.servicify.mehrms.web.controller.config;


import org.servicify.mehrms.model.Menu;
import org.servicify.mehrms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
//    获取数据库中存储的菜单信息
    public List<Menu> getMenusByHrId(){
        return menuService.getMenusByHrId();
    }
}
