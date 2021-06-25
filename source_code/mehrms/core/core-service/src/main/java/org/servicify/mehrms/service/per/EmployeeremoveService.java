package org.servicify.mehrms.service.per;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.servicify.mehrms.mapper.*;
import org.servicify.mehrms.model.*;
import org.servicify.mehrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class EmployeeremoveService {
    @Autowired
    EmployeeremoveMapper employeeremoveMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    EmpBeforeRemoveMapper empBeforeRemoveMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    JobLevelMapper jobLevelMapper;
    @Autowired
    PositionMapper positionMapper;
//    【员工岗位】查询
    public RespPageBean getPerMvAllEmpsInfo(Integer page, Integer size, Employee employee) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> data=employeeMapper.getEmployeeIdsByPage(page,size,employee);
        Long total=employeeMapper.getTotalMv(employee);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
    //    【员工岗位】【修改】信息
    public Integer updatePerMvEmpInfo(Employee employee) {
//        获取前端传入修改对象的id值
        Integer id =new Integer(employee.getId());
//        查询id对应的employee表中的对象
        Employee employee1= employeeMapper.selectByPrimaryKey(id);
        EmpBeforeRemove empBeforeRemove=new EmpBeforeRemove();
        Employeeremove employeeremove=new Employeeremove();
//        判断前端传入更新对象信息在employee表中对应的信息是否相等，若相等则不做修改以及插入更新记录，
//        相反，先设置变动时间，然后插入更新记录，最后更新employee表中对应id信息的员工记录。
        if(employee.getDepartmentId()==employee1.getDepartmentId()
                &&employee.getPosId()==employee1.getPosId()
                &&employee.getJobLevelId()==employee1.getJobLevelId()){
            return 1;
        }
        else{
            employeeMapper.updateByPrimaryKeySelective(employee);
            employeeremove.setRemoveDate(new Date());
            employeeremove.setEid(employee.getId());
            employeeremove.setAfterDepId(employee.getDepartmentId());
            employeeremove.setAfterPositionId(employee.getPosId());
            employeeremove.setAfterJobLevelId(employee.getJobLevelId());
            employeeremoveMapper.insertSelective(employeeremove);
            empBeforeRemove.setEmpremoveId(employeeremove.getId());
            empBeforeRemove.setBeforeDepName(departmentMapper.selectByPrimaryKey((employee1.getDepartmentId())).getName());
            empBeforeRemove.setBeforeJobLevelName(jobLevelMapper.selectByPrimaryKey((employee1.getJobLevelId())).getName());
            empBeforeRemove.setBeforePositionName(positionMapper.selectByPrimaryKey((employee1.getPosId())).getName());
            empBeforeRemove.setBeforeJobLevelTitleName(jobLevelMapper.selectByPrimaryKey((employee1.getJobLevelId())).getTitleLevel());
            return empBeforeRemoveMapper.insertSelective(empBeforeRemove);
        }
    }
//      【员工调岗】查询
    public RespPageBean getPerMvAllRecordsInfo(Integer page, Integer size, Employeeremove employeeremove, Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employeeremove> data=employeeremoveMapper.getAllRecordsInfo(page,size,employeeremove,beginDateScope);
        Long total=employeeremoveMapper.getAllRecordsInfosTotal(employeeremove,beginDateScope);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
//      【员工调岗】记录【修改】操作
//       此处修改记录当修改部门等信息时，同时更新employee表中的相关数据
    @Transactional
    public Integer updatePerMvRecordInfo(Employeeremove employeeremove) {
//        获取前端传入修改对象的id值
        Integer id =new Integer(employeeremove.getEid());
//        查询id对应的employee表中的对象
        Employee employee= new Employee();
        Integer did=employeeremove.getAfterDepId();
        Integer jid=employeeremove.getAfterJobLevelId();
        Integer pid=employeeremove.getAfterPositionId();
        employee.setId(id);
        employee.setDepartmentId(did);
        employee.setJobLevelId(jid);
        employee.setPosId(pid);
//        判断前端传入更新对象信息在employee表中对应的信息是否相等，若相等则不做修改以及插入更新记录，
//        相反，先设置变动时间，然后插入更新记录，最后更新employee表中对应id信息的员工记录。
        employeeMapper.updateByPrimaryKeySelective(employee);
        return employeeremoveMapper.updateByPrimaryKeySelective(employeeremove);
    }

//    通过日期集合查询对应的集合

}
