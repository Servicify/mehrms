package org.servicify.mehrms.model;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class RespBean {
    private Integer status;
    private String msg;
    private Object obj;

    private RespBean() {
    }

    private RespBean(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    //      如果成功的话，返回
    public static RespBean ok(String msg){
        return new RespBean(200,msg,null);
    }

    public static RespBean ok(String msg,Object obj){
        return new RespBean(200,msg,obj);
    }

    //      如果失败的话，返回
    public static RespBean error(String msg){
        return new RespBean(500,msg,null);
    }

    public static RespBean error(String msg,Object obj){
        return new RespBean(500,msg,obj);
    }

    //构建一个RespBean对象
    public static RespBean build(){
        return new RespBean();
    }


    public Object getObj() {
        return obj;
    }

    public RespBean setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer getStatus() {
        return status;
    }
//  为setter()设置一个返回值
    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }
}
