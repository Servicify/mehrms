package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Employeetrain {
    private Integer id;

    private Integer eid;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date trainBeginDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date trainEndDate;

    private String trainContent;

    private String remark;

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

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

    public Date getTrainBeginDate() {
        return trainBeginDate;
    }

    public void setTrainBeginDate(Date trainBeginDate) {
        this.trainBeginDate = trainBeginDate;
    }

    public Date getTrainEndDate() {
        return trainEndDate;
    }

    public void setTrainEndDate(Date trainEndDate) {
        this.trainEndDate = trainEndDate;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}