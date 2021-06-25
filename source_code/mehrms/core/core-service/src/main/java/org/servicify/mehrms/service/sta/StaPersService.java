package org.servicify.mehrms.service.sta;

import org.servicify.mehrms.mapper.DepartmentMapper;
import org.servicify.mehrms.mapper.EmployeeMapper;
import org.servicify.mehrms.mapper.JobLevelMapper;
import org.servicify.mehrms.mapper.PositionMapper;
import org.servicify.mehrms.model.*;
import org.servicify.mehrms.utils.CreateStaBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class StaPersService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    JobLevelMapper jobLevelMapper;
    @Autowired
    PositionMapper positionMapper;
    public RespPageBean getStaPersInfos(Integer integer) {
        List<Employee> employeeList=employeeMapper.getAllEmployee();
        RespPageBean resp=new RespPageBean();
//        判断Integer的值，进行不同的查询，减少查询时间，提高用户体验
        if(integer==0){
            List<Department> departmentList=departmentMapper.getAllDepartments();
            List<StaBasicInfo> deplist = CreateStaBasicInfo.createDepStaBasicInfosList(departmentList);
            for(int i=0;i<employeeList.size();i++) {
                for (int q = 0; q < deplist.size(); q++) {
                    if (deplist.get(q).getId() == employeeList.get(i).getDepartmentId()) {
                        Integer integer1=new Integer(0);
                        integer1+=deplist.get(q).getValue()+1;
                        deplist.get(q).setValue( integer1);
                    }
                }
            }
            resp.setData(deplist);
        }else if(integer ==1){
            List<JobLevel> jobLevelList = jobLevelMapper.getAllJobLevels();
            List<StaBasicInfo> joblist = CreateStaBasicInfo.createJobStaBasicInfosList(jobLevelList);
            for(int i=0;i<employeeList.size();i++) {
                for (int q = 0; q < joblist.size(); q++) {
                    if (joblist.get(q).getId() == employeeList.get(i).getJobLevelId()) {
                        Integer integer1=new Integer(0);
                        integer1+=joblist.get(q).getValue()+1;
                        joblist.get(q).setValue( integer1);
                    }
                }
            }
            resp.setData(joblist);
        }else if(integer ==2){
            List<Position> positionList = positionMapper.getAllPositions();
            List<StaBasicInfo> poslist = CreateStaBasicInfo.createPosStaBasicInfosList(positionList);
            for(int i=0;i<employeeList.size();i++) {
                for (int q = 0; q < poslist.size(); q++) {
                    if (poslist.get(q).getId() == employeeList.get(i).getPosId()) {
                        Integer integer1=new Integer(0);
                        integer1+=poslist.get(q).getValue()+1;
                        poslist.get(q).setValue( integer1);
                    }
                }
            }
            resp.setData(poslist);
        }
        return resp;
    }
}
