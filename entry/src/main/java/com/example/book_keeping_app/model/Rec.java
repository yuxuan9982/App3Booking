package com.example.book_keeping_app.model;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

import java.util.Random;

@Entity(tableName = "record") //这里也可以添加索引，这里我简单处理
public class Rec extends OrmObject {
    @PrimaryKey //将Hash设置为主键
    private Integer Hash;
    private int type;
    private int resource;
    private String message;
    private double val;

    public Integer getHash() {
        return Hash;
    }

    public int getType() {
        return type;
    }

    public int getResource() {
        return resource;
    }

    public String getMessage() {
        return message;
    }

    public double getVal() {
        return val;
    }

    public void setHash(Integer hash) {
        Hash = hash;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVal(double val) {
        this.val = val;
    }

}

