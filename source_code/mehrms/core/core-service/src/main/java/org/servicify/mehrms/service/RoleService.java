package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.MenuRoleMapper;
import org.servicify.mehrms.mapper.RoleMapper;
import org.servicify.mehrms.model.Menu;
import org.servicify.mehrms.model.MenuRole;
import org.servicify.mehrms.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }
    @Transactional
    public Integer addRole(Role role) {
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
    }
    @Transactional
    public Integer deleteRoleById(Integer rid) {
        List<MenuRole> list=menuRoleMapper.selectByRidKey(rid);
        for(int i=0;i<list.size();i++){
            menuRoleMapper.deleteByPrimaryKey(list.get(i).getId());
        }
        return roleMapper.deleteByPrimaryKey(rid);
    }

}
