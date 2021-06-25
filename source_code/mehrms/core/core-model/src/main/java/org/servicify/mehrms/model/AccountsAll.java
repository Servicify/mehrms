package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class AccountsAll implements Serializable {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date endDate;

    private Float allCount;

    private Float allRealSalary;

    private Float allCreditEmp;

    private Float allCreditCom;

    private Float avgRecordBonus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Float getAllCount() {
        return allCount;
    }

    public void setAllCount(Float allCount) {
        this.allCount = allCount;
    }

    public Float getAllRealSalary() {
        return allRealSalary;
    }

    public void setAllRealSalary(Float allRealSalary) {
        this.allRealSalary = allRealSalary;
    }

    public Float getAllCreditEmp() {
        return allCreditEmp;
    }

    public void setAllCreditEmp(Float allCreditEmp) {
        this.allCreditEmp = allCreditEmp;
    }

    public Float getAllCreditCom() {
        return allCreditCom;
    }

    public void setAllCreditCom(Float allCreditCom) {
        this.allCreditCom = allCreditCom;
    }

    public Float getAvgRecordBonus() {
        return avgRecordBonus;
    }

    public void setAvgRecordBonus(Float avgRecordBonus) {
        this.avgRecordBonus = avgRecordBonus;
    }
}