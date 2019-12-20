package com.supergo.test.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_user")
@ApiModel(value = "user对象", description = "用户实体类")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "Id", required = true, dataType = "Long")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    @ApiModelProperty(value = "用户名", name = "username", required = true, dataType = "String")
    private String username;
    /**
     * 注册手机号
     */
    @Column(name = "phone")
    @ApiModelProperty(value = "注册手机号", name = "phone", dataType = "String")
    private String phone;

    /**
     * 注册邮箱
     */
    @Column(name = "email")
    @ApiModelProperty(value = "注册邮箱", name = "email", dataType = "String")
    private String email;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    @ApiModelProperty(value = "昵称", name = "nickName", dataType = "String")
    private String nickName;

    private Long account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }
}