package org.servicify.mehrms.model;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Role {
    private Integer id;

    private String name;

    private String nameZh;

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
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