package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Employeeremove {
    private Integer id;

    private Integer eid;

    private Integer afterDepId;

    private Integer afterJobLevelId;

    private Integer afterPositionId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date removeDate;

    private String reason;

    private String remark;

    private Employee employee;

    private Department department;

    private JobLevel jobLevel;

    private Position position;

    private EmpBeforeRemove empBeforeRemove;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getAfterDepId() {
        return afterDepId;
    }

    public void setAfterDepId(Integer afterDepId) {
        this.afterDepId = afterDepId;
    }

    public Integer getAfterJobLevelId() {
        return afterJobLevelId;
    }

    public void setAfterJobLevelId(Integer afterJobLevelId) {
        this.afterJobLevelId = afterJobLevelId;
    }

    public Integer getAfterPositionId() {
        return afterPositionId;
    }

    public void setAfterPositionId(Integer afterPositionId) {
        this.afterPositionId = afterPositionId;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public EmpBeforeRemove getEmpBeforeRemove() {
        return empBeforeRemove;
    }

    public void setEmpBeforeRemove(EmpBeforeRemove empBeforeRemove) {
        this.empBeforeRemove = empBeforeRemove;
    }

}