package org.servicify.mehrms.web.controller.system.manager;

import org.servicify.mehrms.model.Menu;
import org.servicify.mehrms.model.RespBean;
import org.servicify.mehrms.model.Role;
import org.servicify.mehrms.service.MenuService;
import org.servicify.mehrms.service.RoleService;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
//  权限组功能设计
@RestController
@RequestMapping("/system/hr/permiss")
public class PermissController {
    @Autowired
    LoggerInfoService loggerInfoService;
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;
//    权限组菜单角色标题请求
    @GetMapping("/")
    public List<Role> getALlRoles(){
        loggerInfoService.insertLoggerInfos("/system/manager/permiss/","查询所有的权限信息");
        return roleService.getAllRoles();
    }
//    权限组菜单角色可访问的所有菜单资源请求
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        loggerInfoService.insertLoggerInfos("/system/manager/permiss/menus",
                "查询权限组菜单角色可访问的所有菜单资源请求");
        return menuService.getAllMenus();
    }
//    获取角色所绑定的默认的权限所对应的在权限组的菜单选项中的值
    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        loggerInfoService.insertLoggerInfos("/system/manager/permiss/mids/"+rid,
                "获取角色所绑定的默认的权限所对应的在权限组的菜单选项中的值");
        return menuService.getMidsByRid(rid);
    }

//    根据角色的updateId查询menu_role表中的对应权限，将其全部删除掉，然后重新插入
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        String s="";
        for(int i=0;i<mids.length;i++){
            s+=" "+mids[i];
        }
        if(menuService.updateMenuRole(rid,mids)){
            loggerInfoService.insertLoggerInfos("/system/manager/permiss/","更新权限信息");
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败！");
    }

//    权限添加请求
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if(roleService.addRole(role)==1){
            loggerInfoService.insertLoggerInfos("/system/manager/permiss/role","添加权限信息");
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

//    权限组巨额色色删除请求
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid){
        if(roleService.deleteRoleById(rid)==1){
            loggerInfoService.insertLoggerInfos("/system/manager/permiss/role/"+rid,"删除权限组角色");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("该角色已被管理员所使用，删除失败！");
    }
}
