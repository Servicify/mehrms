package org.servicify.mehrms.web.controller.per;

import org.servicify.mehrms.model.*;
import org.servicify.mehrms.service.*;
import org.servicify.mehrms.service.system.LoggerInfoService;
import org.servicify.mehrms.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
@RestController
@RequestMapping("/personnel/emp")
//人事管理员工资料功能
public class PerEmpController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    NationService nationService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    LoggerInfoService loggerInfoService;
    //  该查询请求，设置默认请求第一页，请求是个数据
//  由于前端的高级搜索有多个日期参数，其中包括劳务合同（起始日期、终止日期以及转正日期等）
//  若前端想要以数组的形式发送至后端，而后端想要接受这些日期数据，需要将其的参数转换为日期对象，并进行接收
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          Employee employee, Date[] beginDateScope){
//        打印测试
//        System.out.println(employee);
//        System.out.println(beginDateScope);
        loggerInfoService.insertLoggerInfos("/personnel/emp","查询员工信息");
        return employeeService.getEmployeeByPage(page,size,employee,beginDateScope);
    }
//    获取所有的员工信息表中的员工信息
    @GetMapping("/info")
    public List<Employee> getAllEmployee(){
        loggerInfoService.insertLoggerInfos("/personnel/emp/info","查询所有员工信息");
        return employeeService.getAllEmployee();
    }
//    添加员工信息
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        if (employeeService.addEmp(employee) == 1) {
            loggerInfoService.insertLoggerInfos("/personnel/emp","添加员工信息");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
//      获取所有的民族信息
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        loggerInfoService.insertLoggerInfos("/personnel/emp/nations","查询所有的民族信息");
        return nationService.getAllNations();
    }
//      获取所有的政治面貌信息
    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        loggerInfoService.insertLoggerInfos("/personnel/emp/politicsstatus","查询所有的政治面貌信息");
        return politicsstatusService.getAllPoliticsstatus();
    }
//      获取所有的职称信息
    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevels(){
        loggerInfoService.insertLoggerInfos("/personnel/emp/joblevels","查询所有的职称信息");
        return jobLevelService.getAllJobLevels();
    }
//      查询所有的职位信息
    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        loggerInfoService.insertLoggerInfos("/personnel/emp/positions","查询所有的职位信息");
        return positionService.getAllPositions();
    }
//      查询员工信息表中的最大工号，并加1返回
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
//      构建RespBean的build方法，并使用setter函数赋值，使得在能够返回状态信息的同时，也能返回工号
        RespBean respBean=RespBean.build()
                .setStatus(200)
                .setObj(String.format("%08d", employeeService.maxWorkID()+1));
        loggerInfoService.insertLoggerInfos("/personnel/emp/maxWorkID","查询最大工号信息");
        return respBean;
    }
//      查询所有的部门信息
    @GetMapping("/deps")
    public List<Department> getAllDepartments(){
        loggerInfoService.insertLoggerInfos("/personnel/emp/deps","查询所有的部门信息");
        return departmentService.getAllDepartments();
    }
//      删除员工信息
    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id){
        if(employeeService.deleteEmpByEid(id)==1){
            loggerInfoService.insertLoggerInfos("/personnel/emp/{id}","删除员工信息");
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
//      更新员工信息
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateEmp(employee)==1){
            loggerInfoService.insertLoggerInfos("/personnel/emp/","更新员工信息");
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
//      将员工信息以Excel表的形式进行导出
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        List<Employee> list = (List<Employee>) employeeService.getEmployeeByPage(null, null, null,null).getData();
        loggerInfoService.insertLoggerInfos("/personnel/emp/export","导出员工数据信息");
        return POIUtils.employee2Excel(list);
    }
//  导出员工上传模板
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadTemp(){
        loggerInfoService.insertLoggerInfos("/personnel/emp/download","导出员工信息上传模板");
        return POIUtils.employee2download();
    }
//  导入excel表格数据
    @PostMapping("/import")
    @Transactional
    public RespBean importData(MultipartFile file) throws IOException {
//        测试文件是否能够上传成功
//        file.transferTo(new File("E:\\javaboy.xls"));
//        由于数据库里存的民族职位等数据，存储的时id，而不是直接数据，所以，需将这些（民族、政治面貌、部门、职位、职称数据也传入进去。
        List<Employee> list = POIUtils.excel2Employee(file, nationService.getAllNations(),
                politicsstatusService.getAllPoliticsstatus(),
                departmentService.getAllDepartmentsWithOutChildren(),
                positionService.getAllPositions(),
                jobLevelService.getAllJobLevels());
        //      为每个新增的员工数据添加工号
        Integer workID=employeeService.maxWorkID()+1;
//        为每条新员工信息添加一个新的新员工ID
        for(int i=0;i<list.size();i++){
            list.get(i).setWorkID(String.format("%08d",workID));
            workID++;
        }
//        调用批量添加新员工信息业务处理接口
        if (employeeService.addEmps(list) == list.size()) {
            loggerInfoService.insertLoggerInfos("/personnel/emp/import","导入员工信息");
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }

//    重新发送员工入职邮件
    @GetMapping("/mail/send")
    public RespBean resendEmpInfoMail(Integer id){
        if(employeeService.resendEmpInfoMail(id)==1){
            loggerInfoService.insertLoggerInfos("/personnel/emp/mail/send","重新发送入职邮件");
            return RespBean.ok("入职邮件发送成功！");
        }
        return RespBean.error("发送失败，请检查该员工的邮箱信息是否正确！");
    }
}
