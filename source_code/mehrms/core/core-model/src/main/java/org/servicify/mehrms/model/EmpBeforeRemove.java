package org.servicify.mehrms.model;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class EmpBeforeRemove {
    private Integer id;

    private Integer empremoveId;

    private String beforeDepName;

    private String beforePositionName;

    private String beforeJobLevelName;

    private String beforeJobLevelTitleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpremoveId() {
        return empremoveId;
    }

    public void setEmpremoveId(Integer empremoveId) {
        this.empremoveId = empremoveId;
    }

    public String getBeforeDepName() {
        return beforeDepName;
    }

    public void setBeforeDepName(String beforeDepName) {
        this.beforeDepName = beforeDepName;
    }

    public String getBeforePositionName() {
        return beforePositionName;
    }

    public void setBeforePositionName(String beforePositionName) {
        this.beforePositionName = beforePositionName;
    }

    public String getBeforeJobLevelName() {
        return beforeJobLevelName;
    }

    public void setBeforeJobLevelName(String beforeJobLevelName) {
        this.beforeJobLevelName = beforeJobLevelName;
    }

    public String getBeforeJobLevelTitleName() {
        return beforeJobLevelTitleName;
    }

    public void setBeforeJobLevelTitleName(String beforeJobLevelTitleName) {
        this.beforeJobLevelTitleName = beforeJobLevelTitleName;
    }
}