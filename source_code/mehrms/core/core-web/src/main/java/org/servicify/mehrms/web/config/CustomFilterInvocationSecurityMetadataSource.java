package org.servicify.mehrms.web.config;

import org.servicify.mehrms.model.Menu;
import org.servicify.mehrms.model.Role;
import org.servicify.mehrms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/

//该类的作用是：根据用户传来的请求地址，分析出请求所需要的角色权限
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;
    //    添加工具
    AntPathMatcher antPathMatcher=new AntPathMatcher();
//    Collection为用户所需要的角色
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        获取用户的请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus=menuService.getAllMenusWithRole();
//        添加匹配规则
        for(Menu menu:menus){
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles=menu.getRoles();
                String[] str = new String[roles.size()];
                for(int i=0;i<roles.size();i++){
                    str[i]=roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
//        用户请求权限没有匹配上的统一登录匹配上
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
