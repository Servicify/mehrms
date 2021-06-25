package org.servicify.mehrms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Position implements Serializable{
    private Integer id;

    private String name;

    @JsonFormat(pattern="yyyy-mm-dd",timezone = "Asia/Shanghai")
    private Date createDate;

    private Boolean enabled;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(name, position.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Position() {
    }
    public Position(String name) {
        this.name=name;
    }

}