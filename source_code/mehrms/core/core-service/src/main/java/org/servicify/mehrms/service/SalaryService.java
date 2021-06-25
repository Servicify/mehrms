package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.SalaryMapper;
import org.servicify.mehrms.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }

    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
//        提取添加对象的各个值，并且将添加的值进行计算，使得计算出来的值设置为该新建账套对象的合计应发工资金额金额
        float count=0;
        count+=salary.getBasicSalary();
        count+=salary.getTrafficSalary();
        count+=salary.getLunchSalary();
        count+=salary.getBonus();
        count+=salary.getPensionBase()*salary.getPensionPer();
        count+=salary.getMedicalBase()*salary.getMedicalPer();
        count+=salary.getAccumulationFundBase()*salary.getAccumulationFundPer();
        salary.setAllSalary(count);
        return salaryMapper.insertSelective(salary);
    }

    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public Integer updateSalaryById(Salary salary) {
//        将接受到的对象的更新信息进行更新
        salaryMapper.updateByPrimaryKeySelective(salary);
//        根据前端传入对象查询对应的更新信息后的记录
        Salary salary1=salaryMapper.selectByPrimaryKey(salary.getId());
//        提取除合计等参数进行合计计算
        float count=0;
        count+=salary1.getBasicSalary();
        count+=salary1.getTrafficSalary();
        count+=salary1.getLunchSalary();
        count+=salary1.getBonus();
        count+=salary1.getPensionBase()*salary1.getPensionPer();
        count+=salary1.getMedicalBase()*salary1.getMedicalPer();
        count+=salary1.getAccumulationFundBase()*salary1.getAccumulationFundPer();
//        将合计后的count对象的值与数据库中更新后的值的count字段的值进行比对，若相同则不更新该记录的count值，
//        否则继续执行更新count值的操作
        if(salary1.getAllSalary()!=count){
            salary1.setAllSalary(count);
            return salaryMapper.updateByPrimaryKeySelective(salary1);
        }else if(salary1.getAllSalary()==count){
            return 1;
        }else{
            return -1;
        }
    }
}
