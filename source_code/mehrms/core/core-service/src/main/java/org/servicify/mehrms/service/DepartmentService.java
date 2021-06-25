package org.servicify.mehrms.service;

import org.servicify.mehrms.mapper.DepartmentMapper;
import org.servicify.mehrms.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    public List<Department> getAllDepartments() {
//        -1为使用递归进行调用的初始数值，parentID的起点（即前端从根目录开始展示数据【股东会（为公司的最高决策机构）】根目录）
//        比如从parentID=-1开始查到该条数据id=1，而parentID=1的数据查到该条数据的id=4......以此类推，
//        depPath主要方便搜索，比如depPath为.1.4.5的路径对应的为总办，在.1.4.5.**下的机构，则都是总办下面的人，
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }


    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep( dep);
    }

    public void deleteDepById(Department dep) {
        departmentMapper.deleteDepById(dep);
    }


    public List<Department> getAllDepartmentsWithOutChildren() {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}
