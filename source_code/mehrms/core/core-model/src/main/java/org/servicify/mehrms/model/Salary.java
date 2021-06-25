package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Salary implements Serializable {
    private Integer id;

    private Integer basicSalary;

    private Integer bonus;

    private Integer lunchSalary;

    private Integer trafficSalary;

    private Float allSalary;

    private Integer pensionBase;

    private Float pensionPer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private Integer medicalBase;

    private Float medicalPer;

    private Integer accumulationFundBase;

    private Float accumulationFundPer;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}