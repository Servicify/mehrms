package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.MenuMapper;
import org.servicify.mehrms.mapper.MenuRoleMapper;
import org.servicify.mehrms.model.Hr;
import org.servicify.mehrms.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;
    public List<Menu> getMenusByHrId(){
        return menuMapper.getMenusByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

//    添加通过角色查询该角色所访问的菜单内容的权限
//    @Cacheable
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

//    加上@Transactional注解，避免操作失败后，对已操作数据的毁坏
    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
//  删除rid相关的对应的权限注册信息
        menuRoleMapper.deleteByRid(rid);
//  添加一个判断，如果插入的权限数组长度为空，那么不用插入，直接返回
        if(mids==null||mids.length==0){
            return true;
        }
//  删除权限后，根据前端传来的mids信息，重新添加权限
        Integer result=menuRoleMapper.insertRecord(rid,mids);
//        响应调用端
        return result==mids.length;
    }
}
