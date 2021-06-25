package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.Employeeremove;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Component
public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size
                                    , @Param("emp")Employee employee, @Param("beginDateScope")Date[] beginDateScope);

    List<Employee> getAllEmployee();

    Long getTotal( @Param("emp")Employee employee, @Param("beginDateScope")Date[] beginDateScope);

    Integer maxWorkID();

    Integer addEmps(@Param("list") List<Employee> list);

    Employee getEmployeeById(Integer id);

//    对应查询员工账套接口
    List<Employee> getEmployeeByPageWithSalary(@Param("page") Integer page, @Param("size") Integer size);

    Integer updateEmployeeSalaryById(@Param("eid") Integer eid, @Param("sid") Integer sid);

    List<Employee> getEmployeeIdsByPage(@Param("page") Integer page, @Param("size") Integer size
            , @Param("emp")Employee employee);

    Long getTotalMv(@Param("emp")Employee employee);

    List<Employee> getAllEmpInfos();
}