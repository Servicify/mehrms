package org.servicify.mehrms.service.sta;

import org.servicify.mehrms.mapper.*;
import org.servicify.mehrms.model.*;
import org.servicify.mehrms.utils.CreateStaBasicInfo;
import org.servicify.mehrms.utils.GetLastMonths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class StaRecordService {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeremoveMapper employeeremoveMapper;
    @Autowired
    EmployeetrainMapper employeetrainMapper;
    public RespPageBean getStaRecordsInfos(Integer integer, Date date,Integer months) throws Exception{
//        integer1的值为0时，查询部门调入信息
//        值为1时，查询部门的调出信息
//        值为2时，查询培训信息
//        date为查询多少个月的信息的截至日期
        List<MonthDate> listDate= GetLastMonths.getLastMonths(date,months);
        RespPageBean resp = new RespPageBean();
        for(int i=0;i<listDate.size();i++){
            if(integer==0){
//                调入
                List<Department> departmentList=departmentMapper.getAllDepartments();
                List<StaBasicInfo> deplist = CreateStaBasicInfo.createDepStaBasicInfosList(departmentList);
                List<Employeeremove> employeeremoveList1 = employeeremoveMapper.
                        getAllRemoveInfosByMonths(listDate.get(0).getStartDate(),listDate.get(listDate.size()-1).getEndDate());
                for(int j=0;j<employeeremoveList1.size();j++){
                    for(int q=0;q<deplist.size();q++){
                        if(employeeremoveList1.get(j).getAfterDepId()==deplist.get(q).getId()){
                            Integer integer1= new Integer(0);
                            integer1+=deplist.get(q).getValue()+1;
                            deplist.get(q).setValue(integer1);
                        }
                    }
                }
                resp.setData(deplist);
            }else if (integer==1){
//                调出
                List<Department> departmentList=departmentMapper.getAllDepartments();
                List<StaBasicInfo> deplist = CreateStaBasicInfo.createDepStaBasicInfosList(departmentList);
                List<Employeeremove> employeeremoveList2 = employeeremoveMapper.
                        getAllRemoveInfosByMonths(listDate.get(0).getStartDate(),listDate.get(listDate.size()-1).getEndDate());
                for(int j=0;j<employeeremoveList2.size();j++){
                    for(int q=0;q<deplist.size();q++){
//                        java中比较两个字符串是否相同使用A.equals(B)
                        String BeforeDepName=employeeremoveList2.get(j).getEmpBeforeRemove().getBeforeDepName();
                        String name=deplist.get(q).getName();
                        if(BeforeDepName==null){
                            continue;
                        }
                        if(BeforeDepName.equals(name)){
                            Integer integer1= new Integer(0);
                            integer1+=deplist.get(q).getValue()+1;
                            deplist.get(q).setValue(integer1);
                        }
                    }
                }
                resp.setData(deplist);
            }else if (integer==2){
//                由于培训的周期不确定,所以进行一整段时间的查询
                List<Employeetrain> employeetrainList = employeetrainMapper.getAllRecordsInfosByMonths(listDate.get(0).getStartDate(),listDate.get(listDate.size()-1).getEndDate());
                List<String> stringList1=new ArrayList<String>();
                List<String> stringList2=new ArrayList<String>();
                List<String> stringList3=new ArrayList<String>();
//                添加培训名称
                for(int j=0;j<employeetrainList.size();j++){
                    stringList1.add(employeetrainList.get(j).getTrainContent());
                    stringList2.add(employeetrainList.get(j).getTrainContent());
                }
//                添加第一个
                stringList3.add(stringList1.get(0));
//                将每一个与StringList3中所存的选项相比较，添加StringList3不存项
                for(int j=0;j<stringList1.size();j++){
                    for(int q=0;q<stringList3.size();q++){
                        String string1=stringList1.get(j);
                        String string2=stringList3.get(q);
//                        如果相等，终止循环；否则当循环至最后一项后，添加最后一项
                        if(string2.equals(string1)){
                            break;
                        }else if(q==stringList3.size()-1){
                            stringList3.add(stringList1.get(j));
                        }
                    }
                }
//              添加一个StaBasicInfoList统计项
                List<StaBasicInfo> staBasicInfoList=new ArrayList<StaBasicInfo>();
                for(int q=0;q<stringList3.size();q++){
                    StaBasicInfo staBasicInfo=new StaBasicInfo();
                    staBasicInfo.setName(stringList3.get(q));
                    staBasicInfo.setValue(0);
                    staBasicInfoList.add(staBasicInfo);
                }
                for(int j=0;j<employeetrainList.size();j++){
                    for(int q=0;q<stringList3.size();q++){
                        String content=employeetrainList.get(j).getTrainContent();
                        String name=staBasicInfoList.get(q).getName();
                        if(content.equals(name)){
                            Integer integer1=new Integer(0);
                            integer1=staBasicInfoList.get(q).getValue()+1;
                            staBasicInfoList.get(q).setValue(integer1);
                        }
                    }
                }
                resp.setData(staBasicInfoList);
            }
        }
        return resp;
    }
}
