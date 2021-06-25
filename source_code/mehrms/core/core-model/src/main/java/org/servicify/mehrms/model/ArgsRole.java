package org.servicify.mehrms.model;

import java.io.Serializable;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class ArgsRole implements Serializable {
    private Integer id;

    private Integer eid;

    private Float times;

    private Float others;

    private Float deduct;

    private Float recordPer;

    private Float count;

    private Employee employee;

    private Department department;

    private Salary salary;


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

    public Float getTimes() {
        return times;
    }

    public void setTimes(Float times) {
        this.times = times;
    }

    public Float getOthers() {
        return others;
    }

    public void setOthers(Float others) {
        this.others = others;
    }

    public Float getDeduct() {
        return deduct;
    }

    public void setDeduct(Float deduct) {
        this.deduct = deduct;
    }

    public Float getRecordPer() {
        return recordPer;
    }

    public void setRecordPer(Float recordPer) {
        this.recordPer = recordPer;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
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

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

}