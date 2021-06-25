package org.servicify.mehrms.model;

import java.io.Serializable;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class AccountsAdvInfo implements Serializable {
    private Integer id;

    private Float allSalary;

    private Integer recordScore;

    private Float recordSalary;

    private Float count;

    private Float creditEmp;

    private Float creditCom;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAllSalary() {
        return allSalary;
    }

    public void setAllSalary(Float allSalary) {
        this.allSalary = allSalary;
    }

    public Integer getRecordScore() {
        return recordScore;
    }

    public void setRecordScore(Integer recordScore) {
        this.recordScore = recordScore;
    }

    public Float getRecordSalary() {
        return recordSalary;
    }

    public void setRecordSalary(Float recordSalary) {
        this.recordSalary = recordSalary;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public Float getCreditEmp() {
        return creditEmp;
    }

    public void setCreditEmp(Float creditEmp) {
        this.creditEmp = creditEmp;
    }

    public Float getCreditCom() {
        return creditCom;
    }

    public void setCreditCom(Float creditCom) {
        this.creditCom = creditCom;
    }

}