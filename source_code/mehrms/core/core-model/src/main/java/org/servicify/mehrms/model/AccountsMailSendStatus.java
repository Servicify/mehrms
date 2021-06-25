package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class AccountsMailSendStatus implements Serializable {
    private Integer accountsMailId;

    private Integer accountsBaseInfoId;

    private Boolean status;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date sendDate;

    public Integer getAccountsMailId() {
        return accountsMailId;
    }

    public void setAccountsMailId(Integer accountsMailId) {
        this.accountsMailId = accountsMailId;
    }

    public Integer getAccountsBaseInfoId() {
        return accountsBaseInfoId;
    }

    public void setAccountsBaseInfoId(Integer accountsBaseInfoId) {
        this.accountsBaseInfoId = accountsBaseInfoId;
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