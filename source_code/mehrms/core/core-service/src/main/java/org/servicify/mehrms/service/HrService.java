package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.ColorMapper;
import org.servicify.mehrms.mapper.EmpHrMapper;
import org.servicify.mehrms.mapper.HrMapper;
import org.servicify.mehrms.mapper.HrRoleMapper;
import org.servicify.mehrms.model.Color;
import org.servicify.mehrms.model.EmpHr;
import org.servicify.mehrms.model.Hr;
import org.servicify.mehrms.model.HrRole;
import org.servicify.mehrms.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class HrService implements UserDetailsService {
    //在实现的接口上，添加自动注入HrMapper
    @Autowired
    HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;
    @Autowired
    ColorMapper colorMapper;
    @Autowired
    EmpHrMapper empHrMapper;
    //    设置过滤器，避免username值为null。
    //    如果hr的值为null，则抛出用户名不存在的异常!
    //    如果用户名存在，则返回hr信息。
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null){
            throw new UsernameNotFoundException("当前用户名不存在！");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }
    //  该查询查询的是获取除当前用户以外的所有用户及其角色等信息，而当前已登录用户的修改查看的权限在个人中心查看
    public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }
    //  操作员管理更新接口
    @Transactional
    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    //  更新用户角色接口
    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    //  删除用户角色
    @Transactional
    public Integer deleteHrById(Integer id) {
        if(id==3) {
            return 2;
        }else if(id!=3){
//            登录的子管理员不会呈现，故只需要防止系统出现删除超管的现象即可
            if(hrRoleMapper.selectByHrRoleID(id) != null) {
                List<HrRole> list=hrRoleMapper.selectByHrRoleID(id);
                for (int i = 0; i < list.size(); i++) {
                    hrRoleMapper.deleteByPrimaryKey(list.get(i).getId());
                }
            }
            List<Color> list1= colorMapper.selectAllInfos();
            Integer num=new Integer(-1);
            for(int i=0;i<list1.size();i++){
                if(list1.get(i).getHrid()==id)
                {
                    num=list1.get(i).getId();
                    break;
                }
            }
            if(num!=-1){
                colorMapper.deleteByPrimaryKey(num);
            }
//            根据hrid删除信息
            empHrMapper.deleteByHrID(id);
            return hrMapper.deleteByPrimaryKey(id);
        }
        return -1;
    }

    public boolean updateHrPasswd(String oldpass, String pass, Integer hrid) {
        Hr hr = hrMapper.selectByPrimaryKey(hrid);
        System.out.println(hrid);
        System.out.println(hr);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder);
        System.out.println(oldpass);
        System.out.println(hr.getPassword());
        if (encoder.matches(oldpass, hr.getPassword())) {
            String encodePass = encoder.encode(pass);
            System.out.println(encodePass);
            System.out.println(hrid);
            Integer result = hrMapper.updatePasswd(hrid, encodePass);
            System.out.println(result);
            if (result == 1) {
                return true;
            }
        }
        return false;
    }

    public Integer addHr() {
        Hr hr=new Hr();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePass= encoder.encode("123");
        hr.setPassword(encodePass);
//        生成一个随机账号，密码默认“123”
        String a="";
        for(int i=0;i<10;i++){
            a+=(int)(Math.random()*10);
        }
        hr.setUsername(a);
        hr.setName("用户"+a+"");
        hr.setPhone("****");
        hr.setEnabled(true);
        hr.setAddress("****");
        hr.setTelephone("****");
        return hrMapper.insertSelective(hr);
    }

}
