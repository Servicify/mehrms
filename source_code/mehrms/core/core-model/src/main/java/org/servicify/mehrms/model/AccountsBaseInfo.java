package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.nio.channels.Pipe;
import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class AccountsBaseInfo implements Serializable {
//  对象序列化接口的使用必须去掉toString()的使用
    private Integer id;

    private Integer eid;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date sendDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date endDate;

    private String dname;

    private String pname;

    private String jname;

    private Float workAge;

    private Integer basicSalary;

    private Integer bonus;

    private Integer lunchSalary;

    private Integer trafficSalary;

    private Integer pensionBase;

    private Float pensionPer;

    private Integer medicalBase;

    private Float medicalPer;

    private Integer accumulationFundBase;

    private Float accumulationFundPer;

    private String sname;

    private Float times;

    private Float others;

    private Float deduct;

    private Float prepaid;

    private Float recordPer;

    private Float realSalary;

    private String remark;

    private Boolean enabled;

    private String cashierName;

    private Employee employee;

    private Department department;

    private Position position;

    private JobLevel jobLevel;

    private ArgsRole argsRole;

    private AccountsAdvInfo accountsAdvInfo;

    private AccountsAll accountsAll;

    private Salary salary;

    private Work_Age work_age;

    private AccountsMailSendStatus accountsMailSendStatus;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public Float getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Float workAge) {
        this.workAge = workAge;
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

    public Float getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(Float prepaid) {
        this.prepaid = prepaid;
    }

    public Float getRecordPer() {
        return recordPer;
    }

    public void setRecordPer(Float recordPer) {
        this.recordPer = recordPer;
    }

    public Float getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(Float realSalary) {
        this.realSalary = realSalary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    public AccountsAdvInfo getAccountsAdvInfo() {
        return accountsAdvInfo;
    }

    public void setAccountsAdvInfo(AccountsAdvInfo accountsAdvInfo) {
        this.accountsAdvInfo = accountsAdvInfo;
    }

    public AccountsAll getAccountsAll() {
        return accountsAll;
    }

    public void setAccountsAll(AccountsAll accountsAll) {
        this.accountsAll = accountsAll;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public ArgsRole getArgsRole() {
        return argsRole;
    }

    public void setArgsRole(ArgsRole argsRole) {
        this.argsRole = argsRole;
    }

    public Work_Age getWork_age() {
        return work_age;
    }

    public void setWork_age(Work_Age work_age) {
        this.work_age = work_age;
    }

    public AccountsMailSendStatus getAccountsMailSendStatus() {
        return accountsMailSendStatus;
    }

    public void setAccountsMailSendStatus(AccountsMailSendStatus accountsMailSendStatus) {
        this.accountsMailSendStatus = accountsMailSendStatus;
    }
}