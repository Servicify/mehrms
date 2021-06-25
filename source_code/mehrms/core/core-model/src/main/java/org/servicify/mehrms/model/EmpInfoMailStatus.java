package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class EmpInfoMailStatus implements Serializable {

    private Integer empInfoMailStatusId;

    private Integer eid;

    private Boolean status;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date sendDate;

    public Integer getEmpInfoMailStatusId() {
        return empInfoMailStatusId;
    }

    public void setEmpInfoMailStatusId(Integer empInfoMailStatusId) {
        this.empInfoMailStatusId = empInfoMailStatusId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}