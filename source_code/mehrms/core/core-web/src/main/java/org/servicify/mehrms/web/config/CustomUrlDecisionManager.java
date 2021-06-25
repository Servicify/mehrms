package org.servicify.mehrms.web.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/

//判断当前用户是否具备用户请求所用的角色
//参数针对MyFilter类和SystemConfigController类
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes){
            String needRole = configAttribute.getAttribute();
            if("ROLE_LOGIN".equals(needRole)){
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登录！");
                } else{
                    return;
                }
            }
//            获取用户角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            判断该角色是否与数据库所存储的角色相对应
            for(GrantedAuthority authority:authorities){
                if(authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
//        如果以上判断皆为false，则错误
        throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
