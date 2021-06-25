package org.servicify.mehrms.model;

import java.io.Serializable;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class AccountsRole implements Serializable {
    private Integer id;

    private Integer accountsBaseInfoId;

    private Integer accountsAdvInfoId;

    private Integer accountsAllId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountsbaseinfoid() {
        return accountsBaseInfoId;
    }

    public void setAccountsbaseinfoid(Integer accountsBaseInfoId) {
        this.accountsBaseInfoId = accountsBaseInfoId;
    }

    public Integer getAccountsadvinfoid() {
        return accountsAdvInfoId;
    }

    public void setAccountsadvinfoid(Integer accountsAdvInfoId) {
        this.accountsAdvInfoId = accountsAdvInfoId;
    }

    public Integer getAccoutsallid() {
        return accountsAllId;
    }

    public void setAccoutsallid(Integer accountsAllId) {
        this.accountsAllId = accountsAllId;
    }
}