package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class ArgsRoleRecords {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createDate;

    private Integer eid;

    private Integer did;

    private Integer basicSalary;

    private Integer bonus;

    private Integer lunchSalary;

    private Integer trafficSalary;

    private Float allSalary;

    private Integer pensionBase;

    private Float pensionPer;

    private Integer medicalBase;

    private Float medicalPer;

    private Integer accumulationFundBase;

    private Float accumulationFundPer;

    private String sname;

    private Float afterTimes;

    private Float afterOthers;

    private Float afterDeduct;

    private Float afterPer;

    private Float afterCount;

    private String remark;

    private Employee employee;

    private Department department;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Integer basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getLunchSalary() {
        return lunchSalary;
    }

    public void setLunchSalary(Integer lunchSalary) {
        this.lunchSalary = lunchSalary;
    }

    public Integer getTrafficSalary() {
        return trafficSalary;
    }

    public void setTrafficSalary(Integer trafficSalary) {
        this.trafficSalary = trafficSalary;
    }

    public Float getAllSalary() {
        return allSalary;
    }

    public void setAllSalary(Float allSalary) {
        this.allSalary = allSalary;
    }

    public Integer getPensionBase() {
        return pensionBase;
    }

    public void setPensionBase(Integer pensionBase) {
        this.pensionBase = pensionBase;
    }

    public Float getPensionPer() {
        return pensionPer;
    }

    public void setPensionPer(Float pensionPer) {
        this.pensionPer = pensionPer;
    }

    public Integer getMedicalBase() {
        return medicalBase;
    }

    public void setMedicalBase(Integer medicalBase) {
        this.medicalBase = medicalBase;
    }

    public Float getMedicalPer() {
        return medicalPer;
    }

    public void setMedicalPer(Float medicalPer) {
        this.medicalPer = medicalPer;
    }

    public Integer getAccumulationFundBase() {
        return accumulationFundBase;
    }

    public void setAccumulationFundBase(Integer accumulationFundBase) {
        this.accumulationFundBase = accumulationFundBase;
    }

    public Float getAccumulationFundPer() {
        return accumulationFundPer;
    }

    public void setAccumulationFundPer(Float accumulationFundPer) {
        this.accumulationFundPer = accumulationFundPer;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Float getAfterTimes() {
        return afterTimes;
    }

    public void setAfterTimes(Float afterTimes) {
        this.afterTimes = afterTimes;
    }

    public Float getAfterOthers() {
        return afterOthers;
    }

    public void setAfterOthers(Float afterOthers) {
        this.afterOthers = afterOthers;
    }

    public Float getAfterDeduct() {
        return afterDeduct;
    }

    public void setAfterDeduct(Float afterDeduct) {
        this.afterDeduct = afterDeduct;
    }

    public Float getAfterPer() {
        return afterPer;
    }

    public void setAfterPer(Float afterPer) {
        this.afterPer = afterPer;
    }

    public Float getAfterCount() {
        return afterCount;
    }

    public void setAfterCount(Float afterCount) {
        this.afterCount = afterCount;
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
}