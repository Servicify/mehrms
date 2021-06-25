package org.servicify.mehrms.model;

//用来处理分页数据

import java.util.List;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
public class RespPageBean {
    private Long total;
//  由于需要处理不同类型的数据，所以类型待定
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
