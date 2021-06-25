package org.servicify.mehrms.model;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class Passwords {
    private String oldpass;

    private String pass;

    private Integer hrid;

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getHrid() {
        return hrid;
    }

    public void setHrid(Integer hrid) {
        this.hrid = hrid;
    }
}
