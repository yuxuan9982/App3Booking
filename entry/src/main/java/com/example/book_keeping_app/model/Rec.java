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
    public int year,month,day;

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

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setVal(double val) {
        this.val = val;
    }
    public Rec(){

    }
    public Rec(int type,int resource,String message,double val,int year,int month,int day){
        this.type=type;this.resource=resource;this.message=message;this.val=val;
        this.year=year;this.month=month;this.day=day;
    }
}

