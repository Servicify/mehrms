package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.Employeeremove;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface EmployeeremoveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeremove record);

    int insertSelective(Employeeremove record);

    Employeeremove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeremove record);

    int updateByPrimaryKey(Employeeremove record);

    List<Employeeremove> getAllRecordsInfo(@Param("page") Integer page,
                                     @Param("size")Integer size,
                                     @Param("employeeremove")Employeeremove employeeremove,
                                     @Param("beginDateScope")Date[] beginDateScope);

    Long getAllRecordsInfosTotal(@Param("employeeremove")Employeeremove employeeremove,
                                 @Param("beginDateScope")Date[] beginDateScope);

    List<Employeeremove> getAllRemoveInfosByMonths(Date startDate,Date endDate);
}