package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.EmpHrMapper;
import org.servicify.mehrms.mapper.EmployeeMapper;
import org.servicify.mehrms.mapper.HrMapper;
import org.servicify.mehrms.model.EmpHr;
import org.servicify.mehrms.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Servicify
 * Date 2021/6/8
 * Time 17:16
 **/
@Service
public class EmpHrService {
    @Autowired
    HrMapper hrMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    EmpHrMapper empHrMapper;
//    更新账号的绑定信息
    @Transactional
    public Integer updateEmpHrInfo(EmpHr empHr){

        empHrMapper.deleteByHrID(empHr.getHrid());
        System.out.println(empHr.getHrid());
        System.out.println(empHr.getEid());

        empHrMapper.insertSelective(empHr);
//            更改姓名
        String name = employeeMapper.selectByPrimaryKey(empHr.getEid()).getName();
        Hr hr = hrMapper.selectByPrimaryKey(empHr.getHrid());
        hr.setName(name);
        hrMapper.updateByPrimaryKeySelective(hr);
        return 1;
    }
}
