package org.servicify.mehrms.model;

import java.io.Serializable;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Work_Age implements Serializable {
    private Integer id;

    private Integer eid;

    private Float beforeWorkAge;

    private Float afterWorkAge;

    private Float workAge;

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

    public Float getBeforeWorkAge() {
        return beforeWorkAge;
    }

    public void setBeforeWorkAge(Float beforeWorkAge) {
        this.beforeWorkAge = beforeWorkAge;
    }

    public Float getAfterWorkAge() {
        return afterWorkAge;
    }

    public void setAfterWorkAge(Float afterWorkAge) {
        this.afterWorkAge = afterWorkAge;
    }

    public Float getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Float workAge) {
        this.workAge = workAge;
    }


}