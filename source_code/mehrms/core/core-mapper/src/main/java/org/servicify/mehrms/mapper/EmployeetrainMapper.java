package org.servicify.mehrms.mapper;

import org.apache.ibatis.annotations.Param;
import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.Employeetrain;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public interface EmployeetrainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeetrain record);

    int insertSelective(Employeetrain record);

    Employeetrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeetrain record);

    int updateByPrimaryKey(Employeetrain record);
//      分页查询员工培训记录
    List<Employee> getTrainRecordsByPage(@Param("page")Integer page, @Param("size")Integer size,
                                         @Param("employeetrain")Employeetrain employeetrain, @Param("beginDateScope")Date[] beginDateScope);
//      获取分页总数
    Long getTotal(@Param("employeetrain")Employeetrain employeetrain,@Param("beginDateScope") Date[] beginDateScope);
//      查询阶段时间内员工的培训信息
    List<Employeetrain> getAllRecordsInfosByMonths(Date startDate, Date endDate);
}