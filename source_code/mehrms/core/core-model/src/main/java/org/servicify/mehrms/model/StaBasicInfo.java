package org.servicify.mehrms.model;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class StaBasicInfo {
//  前端统计所需的名字
    private String name;
//  前端统计所需的该名字对应的值
    private Integer value;
//  后端比对所需的该名字对应的id；避免出现同名不同id的统计错误
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
