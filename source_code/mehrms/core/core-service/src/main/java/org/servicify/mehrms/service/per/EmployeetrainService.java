package org.servicify.mehrms.service.per;

import org.servicify.mehrms.mapper.EmployeetrainMapper;
import org.servicify.mehrms.model.Employee;
import org.servicify.mehrms.model.Employeetrain;
import org.servicify.mehrms.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
//  员工培训
@Service
public class EmployeetrainService {
    @Autowired
    EmployeetrainMapper employeetrainMapper;
//    查询员工培训记录业务处理接口
    public RespPageBean getTrainRecords(Integer page, Integer size, Employeetrain employeetrain, Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
//        page为检索记录起始点的偏移量，即从第几个开始，第一页的偏移量为零，size为从page偏移量开始，检索多少条数据
        List<Employee> data=employeetrainMapper.getTrainRecordsByPage(page,size,employeetrain,beginDateScope);
        Long total=employeetrainMapper.getTotal(employeetrain,beginDateScope);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
//      删除员工培训记录业务处理接口
    public Integer deleteTrainRecordByEid(Integer id) {
        return employeetrainMapper.deleteByPrimaryKey(id);
    }
//      添加员工培训记录业务处理接口
    public Integer addTrainRecord(Employeetrain employeetrain) {
        return employeetrainMapper.insertSelective(employeetrain);
    }
//      修改员工培训业务处理接口
    public Integer updateTrainRecordById(Employeetrain employeetrain) {
        return employeetrainMapper.updateByPrimaryKeySelective(employeetrain);
    }
}
