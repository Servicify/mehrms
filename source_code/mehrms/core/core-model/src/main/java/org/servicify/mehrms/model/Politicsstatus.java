package org.servicify.mehrms.model;

import java.io.Serializable;
import java.util.Objects;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Politicsstatus implements Serializable{
    private Integer id;

    private String name;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Politicsstatus that = (Politicsstatus) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Politicsstatus(String name) {
        this.name=name;
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

}