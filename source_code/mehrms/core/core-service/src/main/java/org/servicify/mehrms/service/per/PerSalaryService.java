package org.servicify.mehrms.service.per;

import org.servicify.mehrms.mapper.*;
import org.servicify.mehrms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.servicify.mehrms.utils.FloatNumberCompare;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class PerSalaryService {
    @Autowired
    ArgsRoleMapper argsRoleMapper;
    @Autowired
    SalaryMapper salaryMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    EmpSalaryMapper empSalaryMapper;
    @Autowired
    ArgsRoleRecordsMapper argsRoleRecordsMapper;
//    【员工当前工资信息】查询信息
    public RespPageBean getPerSalaryAllEmpInfo(Integer page, Integer size, ArgsRole argsRole,String sname) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
//        page为检索记录起始点的偏移量，即从第几个开始，第一页的偏移量为零，size为从page偏移量开始，检索多少条数据
        List<ArgsRole> data=argsRoleMapper.getPerSalaryAllEmpInfoByPage(page,size,argsRole,sname);
        Long total=argsRoleMapper.getTotal(argsRole,sname);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
    @Transactional
//    【员工当前工资信息】修改信息
    public Integer putPerSalaryAllEmpInfo(ArgsRole argsRole) {
        Integer id=argsRole.getId();
        ArgsRole argsRole2=argsRoleMapper.selectByPrimaryKey(id);
        argsRoleMapper.updateByPrimaryKeySelective(argsRole);
        ArgsRoleRecords argsRoleRecords=new ArgsRoleRecords();
        Integer eid=argsRoleMapper.selectByPrimaryKey(id).getEid();
        //        根据前端传入对象查询对应的更新信息后的记录
        EmpSalary empSalary=empSalaryMapper.selectByEidKey(eid);
        Integer sid=empSalary.getSid();
        Salary salary=salaryMapper.selectByPrimaryKey(sid);
//        获取该员工的账套工资合计信息
        float count=salary.getAllSalary();
//        提取更新后的工资倍数信息
        ArgsRole argsRole1=argsRoleMapper.selectByPrimaryKey(id);
        count*=argsRole1.getTimes();
        count+=argsRole1.getOthers();
        count-=argsRole1.getDeduct();
        argsRole1.setCount(count);
//        校验工资合计是否相等，若不等重新更新,并在args_role_records表中做记录。若相等，则直接在args_role_records表中做记录并返回成功。
//        简而言之,若校验不相等更新后插入更新记录;否则直接插入更新记录
//        ***************************************************************************************************************
//        由于浮点数的小数点后的数据永远不可能计算精确，应当使用其他办法比较
//        在此处通过在utils包中建立一个FloatNumberCompare类,设置一个静态函数idEqual,本文件中通过导入项目使用FloatNumberCompare.isEqual()进行调用比较
//        但金融上需要精度更高些
        if(FloatNumberCompare.isEqual(argsRole1.getCount(),argsRole2.getCount())
                &&FloatNumberCompare.isEqual(argsRole1.getTimes(),argsRole2.getTimes())
                &&FloatNumberCompare.isEqual(argsRole1.getOthers(),argsRole2.getOthers())
                &&FloatNumberCompare.isEqual(argsRole1.getDeduct(),argsRole2.getDeduct())
                &&FloatNumberCompare.isEqual(argsRole1.getRecordPer(),argsRole2.getRecordPer())
        ) {
            return 1;
        }else{
            argsRoleMapper.updateByPrimaryKeySelective(argsRole1);
            argsRoleRecords.setCreateDate(new Date());
            argsRoleRecords.setEid(argsRole1.getEid());
            argsRoleRecords.setDid(employeeMapper.selectByPrimaryKey(eid).getDepartmentId());
            argsRoleRecords.setSname(salaryMapper.selectByPrimaryKey(sid).getName());
            argsRoleRecords.setBasicSalary(salaryMapper.selectByPrimaryKey(sid).getBasicSalary());
            argsRoleRecords.setLunchSalary(salaryMapper.selectByPrimaryKey(sid).getLunchSalary());
            argsRoleRecords.setTrafficSalary(salaryMapper.selectByPrimaryKey(sid).getTrafficSalary());
            argsRoleRecords.setBonus(salaryMapper.selectByPrimaryKey(sid).getBonus());
            argsRoleRecords.setAllSalary(salaryMapper.selectByPrimaryKey(sid).getAllSalary());
            argsRoleRecords.setPensionBase(salaryMapper.selectByPrimaryKey(sid).getPensionBase());
            argsRoleRecords.setPensionPer(salaryMapper.selectByPrimaryKey(sid).getPensionPer());
            argsRoleRecords.setMedicalBase(salaryMapper.selectByPrimaryKey(sid).getMedicalBase());
            argsRoleRecords.setMedicalPer(salaryMapper.selectByPrimaryKey(sid).getMedicalPer());
            argsRoleRecords.setAccumulationFundBase(salaryMapper.selectByPrimaryKey(sid).getAccumulationFundBase());
            argsRoleRecords.setAccumulationFundPer(salaryMapper.selectByPrimaryKey(sid).getAccumulationFundPer());
            argsRoleRecords.setAfterTimes(argsRole1.getTimes());
            argsRoleRecords.setAfterOthers(argsRole1.getOthers());
            argsRoleRecords.setAfterDeduct(argsRole1.getDeduct());
            argsRoleRecords.setAfterPer(argsRole1.getRecordPer());
            argsRoleRecords.setAfterCount(argsRole1.getCount());
            return argsRoleRecordsMapper.insertSelective(argsRoleRecords);
        }
    }
    @Transactional
    //    【员工当前工资信息】统一修改绩效分值
    public Integer putPerSalaryAllRecordPerInfo(ArgsRole argsRole) {
        float recordPer=argsRole.getRecordPer();
        List<ArgsRole> data=argsRoleMapper.SelectAllInfo();
        for(int i=0;i<data.size();i++){
            data.get(i).setRecordPer(recordPer);
            argsRoleMapper.updateByPrimaryKeySelective(data.get(i));
            putPerSalaryAllEmpInfo(data.get(i));
        }
        return 1;
    }
    @Transactional
//    【员工当前工资信息】一键校验员工信息
    public Integer validatePerSalaryAllInfo() {
        Integer returnValue=new Integer(0);
//        获取employee表中的所有记录
        List<Employee> listBefore= employeeMapper.getAllEmployee();
//        获取args_role表中的所有记录
        List<ArgsRole> listAfter=argsRoleMapper.SelectAllInfo();
        ArrayList<Integer> list1=new ArrayList<Integer>();
        ArrayList<Integer> list2=new ArrayList<Integer>();
        ArrayList<Integer> list3=new ArrayList<Integer>();
        ArgsRole argsRole=new ArgsRole();
        for(int i=0;i<listBefore.size();i++){
            list1.add(listBefore.get(i).getId());
            list3.add(listBefore.get(i).getId());
        }
        for(int j=0;j<listAfter.size();j++){
            list2.add(listAfter.get(j).getEid());
        }
        if(list1.size()==list2.size()){
            //        对所有员工当前工资数据执行合计校验,并在校验完成之后,做调薪记录
            returnValue=1;
        }else {
//            由于args_role中的eid应用employee表中的id主键，故args_role表中的记录不可能多于employee表中的数目
//            故，只存在employee表中的员工数目>=args_role表中的员工数目。
//            遍历args_role中获取的eid数组所存的list2数组,若找到每个元素在list1中的存在,即在list1中删除该元素
//            list1中剩余可得未更新工资的员工数目
            for(int p=0;p<list2.size();p++){
//                Java ArrayList remove() 方法用于删除动态数组里的单个元素。
                list1.remove(list2.get(p));
            }
        }
        if(returnValue==0) {
            for (int i = 0; i < list1.size(); i++) {
                //        根据前端传入对象查询对应的更新信息后的记录
                if (empSalaryMapper.selectByEidKey(list1.get(i)) != null) {
                    argsRole.setEid(list1.get(i));
                    argsRole.setTimes(1.0f);
                    argsRole.setOthers(0.0f);
                    argsRole.setDeduct(0.0f);
                    argsRole.setRecordPer(0.0f);
                    argsRole.setCount(0.0f);
                    argsRoleMapper.insertSelective(argsRole);
                } else {
//              员工账套信息未设置,请先设置员工账套信息
                    returnValue = 2;
                }
            }
        }
        if(returnValue!=2){
            for(int i=0;i<list3.size();i++) {
                Integer eid=list3.get(i);
                ArgsRole argsRole1=argsRoleMapper.selectByEidKey(eid);
                putPerSalaryAllEmpInfo(argsRole1);
            }
            returnValue=1;
        }
        return returnValue;
    }
//          【员工调薪记录】查询信息
    public RespPageBean getPerSalaryAllRecordInfo(Integer page, Integer size,
                                                  ArgsRoleRecords argsRoleRecords, Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
//        page为检索记录起始点的偏移量，即从第几个开始，第一页的偏移量为零，size为从page偏移量开始，检索多少条数据
        List<ArgsRoleRecords> data=argsRoleRecordsMapper.getPerSalaryAllRecordInfo(page,size,argsRoleRecords,beginDateScope);
        Long total=argsRoleRecordsMapper.getTotal(argsRoleRecords,beginDateScope);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer editPerSalaryRecordInfo(ArgsRoleRecords argsRoleRecords) {
//        创建新对象,提取id和remark避免人为越过前端页面,强行传输参数
        ArgsRoleRecords argsRoleRecords1=new ArgsRoleRecords();
        argsRoleRecords1.setId(argsRoleRecords.getId());
        argsRoleRecords1.setRemark(argsRoleRecords.getRemark());
        return argsRoleRecordsMapper.updateByPrimaryKeySelective(argsRoleRecords1);
    }

}



























