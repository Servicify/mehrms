package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.EmployeeMapper;
import org.servicify.mehrms.mapper.Work_AgeMapper;
import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.Work_Age;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class WorkAgeService {
    @Autowired
    Work_AgeMapper work_ageMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Transactional
    //    通过传递来的eid数据添加表中为存在对应工龄信息的数据，通过eid进行插入，并计算工龄工龄，若入职前未设置，则直接计算入职工龄
    public Integer compWorkAge(){
        List<Employee> list1=employeeMapper.getAllEmployee();
        List<Integer> list2=new ArrayList<Integer>();
        List<Work_Age> list3=work_ageMapper.getAllAgeInfo();
        List<Integer> list4=new ArrayList<Integer>();
//      验证所有员工在work_age表中是否有对应的数据
        for(int i=0;i<list1.size();i++){
            list2.add(list1.get(i).getId());
        }
//        若都存在，则更新工龄时长即可.
        if(list2.size()==list3.size()){
            computedWorkAge(list1,list3);
            return 1;
        }
//        若不相等,则通过比对找出在work_age表中没有对应数据的员工号.并建立相应的数据记录.
        for(int i=0;i<list2.size();i++){
                if(work_ageMapper.selectByEidKey(list1.get(i).getId())==null){
//                    使用list5记录相同的数据
                    list4.add(list1.get(i).getId());
                }
//            }
        }
        return addAndComputedWorkAge(list4);
    }
//    计算工龄
@Transactional
    public Integer computedWorkAge(List<Employee> list1,List<Work_Age> list3){
        SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
        DecimalFormat decimalFormat= new DecimalFormat("##.00");
        for(int i=0;i<list1.size();i++){
            Employee employee=list1.get(i);
            Date beginContract=employee.getBeginContract();
            Date now=new Date();
//            如果离职则只计算离职之前的工龄,否则实时更新工龄
            if(list1.get(i).getWorkState().equals("离职")){
                now=list1.get(i).getNotWorkDate();
            }
            double month=(Double.parseDouble(yearFormat.format(now))
                    -Double.parseDouble(yearFormat.format(beginContract)))*12
                    +(Double.parseDouble(monthFormat.format(now))
                    -Double.parseDouble(monthFormat.format(beginContract)));
            float afterWorkAge=Float.parseFloat(decimalFormat.format(month/12));
            float beforeWorkAge=new Float(0f);
            if(list3.get(i).getBeforeWorkAge()==null){
                beforeWorkAge=0f;
            }else{
                beforeWorkAge=list3.get(i).getBeforeWorkAge();
            }
            float workAge=afterWorkAge+beforeWorkAge;
            list3.get(i).setBeforeWorkAge(beforeWorkAge);
            list3.get(i).setAfterWorkAge(afterWorkAge);
            list3.get(i).setWorkAge(workAge);
            work_ageMapper.updateByPrimaryKey(list3.get(i));
        }
        return 1;
    }
//    为新添加的员工建立对应的work_age记录
    @Transactional
    public Integer addAndComputedWorkAge(List<Integer> list){
        for(int i=0;i<list.size();i++){
            Work_Age work_age=new Work_Age();
            work_age.setEid(list.get(i));
            work_ageMapper.insertSelective(work_age);
        }
        List<Employee> list1=employeeMapper.getAllEmployee();
        List<Work_Age> list2=work_ageMapper.getAllAgeInfo();
        return computedWorkAge(list1,list2);
    }
}
