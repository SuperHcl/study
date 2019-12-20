package com.supergo.http;

import java.io.Serializable;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/20 18:32
 *
 ****/
public class Error implements Serializable {
    private String  field;
    private Integer id;
    private String msg;
    private Integer vmId=1;

    public Error() {
    }

    public Error(String field, Integer id, String msg) {
        this.field = field;
        this.id = id;
        this.msg = msg;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getVmId() {
        return vmId;
    }

    public void setVmId(Integer vmId) {
        this.vmId = vmId;
    }
}
