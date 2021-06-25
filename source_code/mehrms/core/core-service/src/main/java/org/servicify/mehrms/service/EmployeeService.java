package org.servicify.mehrms.service;

import jdk.net.SocketFlow;
import org.servicify.mehrms.mapper.*;
import org.servicify.mehrms.model.*;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    WorkAgeService workAgeService;
    @Autowired
    Work_AgeMapper work_ageMapper;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    AccountsMailSendStatusMapper accountsMailSendStatusMapper;
    @Autowired
    EmpInfoMailStatusMapper empInfoMailStatusMapper;
    @Autowired
    AccountsBaseInfoMapper accountsBaseInfoMapper;
    @Autowired
    EmpHrMapper empHrMapper;

//    添加打印日志
    public final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
    DecimalFormat decimalFormat= new DecimalFormat("##.00");
//  参数转换成日期对象，
    public RespPageBean getEmployeeByPage(Integer page, Integer size,Employee employee,Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        workAgeService.compWorkAge();
//        page为检索记录起始点的偏移量，即从第几个开始，第一页的偏移量为零，size为从page偏移量开始，检索多少条数据
        List<Employee> data=employeeMapper.getEmployeeByPage(page,size,employee,beginDateScope);
        Long total=employeeMapper.getTotal(employee,beginDateScope);
//        创建一个分页返回对象，并设置当前页码，和该页码中的对应信息
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
    @Transactional
    public Integer addEmp(Employee employee) {
//        计算合同年限
        Date beginContract=employee.getBeginContract();
        Date endContract=employee.getEndContract();
        double month=(Double.parseDouble(yearFormat.format(endContract))
                -Double.parseDouble(yearFormat.format(beginContract)))*12
                +(Double.parseDouble(monthFormat.format(endContract))
                -Double.parseDouble(monthFormat.format(beginContract)));
//        插入合同年限信息后，插入数据库中
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
        employeeMapper.insertSelective(employee);
//        如果数据插入成功，则将发送邮件的指示器result值更改为1
        Integer result=0;
        if(employee.getId()!=null){
            result=1;
        }
//        创建并核算新员工的工龄信息
        workAgeService.compWorkAge();
        if(employee.getBeforeWorkAge()!=null){
//            由于mybatis的xml中已配置插入新信息后，自动返回该数据的id至employee中，故直接使用employee.getId()获取该记录的id
            Work_Age work_age=work_ageMapper.selectByEidKey(employee.getId());
            work_age.setBeforeWorkAge(employee.getBeforeWorkAge());
            work_ageMapper.updateByPrimaryKeySelective(work_age);
        }
        if(result==1){
//            在EmployeeMapper.xml中对insertSelective添加使用组件回填技术，
//            <insert id="insertSelective" parameterType="org.servicify.mehrms.model.Employee" useGeneratedKeys="true"
//                  keyProperty="id">...
//            使得其方法在被调用以后，能够把添加员工后自动生成的id赋值到返回的
//            employee对象中，使得employee.getId()能够获得所添加员工的id
            Employee emp = employeeMapper.getEmployeeById(employee.getId());
//          添加打印日志
            logger.info(emp.toString());
            rabbitTemplate.convertAndSend("Servicify-topic","welcome",emp);
        }
        return  result;
    }
//    获取已存在的最大的工号
    @Transactional
    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }
//    删除员工信息
    @Transactional
    public Integer deleteEmpByEid(Integer id) {
        work_ageMapper.deleteByPrimaryKey(work_ageMapper.selectByEidKey(id).getId());
        empHrMapper.deleteByEmpID(id);
        return employeeMapper.deleteByPrimaryKey(id);
    }
//    更新员工信息
    @Transactional
    public Integer updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
        if(employee.getBeforeWorkAge()!=null){
            Work_Age work_age=work_ageMapper.selectByEidKey(employee.getId());
            work_age.setBeforeWorkAge(employee.getBeforeWorkAge());
            work_ageMapper.updateByPrimaryKeySelective(work_age);
            return 1;
        }
        return -1;
    }
//    批量添加员工信息
    @Transactional
    public Integer addEmps(List<Employee> list) {
//        Integer integer=employeeMapper.addEmps(list);
        for(int i=0;i<list.size();i++){
//            添加
            if(list.get(i).getName()==null)
            {
                break;
            }
            Integer id=employeeService.addEmp(list.get(i));
//            校准工龄
            workAgeService.compWorkAge();
//            插入工龄信息
            Work_Age work_age=work_ageMapper.selectByEidKey(id);
            work_age.setBeforeWorkAge(list.get(i).getBeforeWorkAge());
            work_ageMapper.updateByPrimaryKeySelective(work_age);
        }
        return list.size();
    }

//  对应员工账套功能的服务
    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> list = employeeMapper.getEmployeeByPageWithSalary(page, size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
//      此处无需传参，两个参数皆为员工信息高级查询附加条件所需的参数
        respPageBean.setTotal(employeeMapper.getTotal(null, null));
        return respPageBean;
    }
    @Transactional
    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid, sid);
    }
//    添加员工数据提示信息实现
    public List<Employee> getAllEmployee(){
        return employeeMapper.getAllEmployee();
    }
//    重新发送入职邮件
    public Integer resendEmpInfoMail(Integer id) {
//        仅作查询employee表中的信息
//        查询员工信息的包括employee在内的所有员工信息表的信息
        Employee employee=employeeMapper.getEmployeeById(id);;
        rabbitTemplate.convertAndSend("Servicify-topic","welcome",employee);
        return 1;
    }

}
