package com.example.book_keeping_app.model;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
//zhujie
@Entity(tableName = "user")
public class data_item extends OrmObject {
    //zhujie
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private  int a;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getUserId() {
        return userId;
    }

    public int getA() {
        return a;
    }
    //    public int hash;
//    public int type;
//    public int detail_type;
//    public int resource;
//    public double val;
//    public String back;
//
//    public int getHash() {
//        return hash;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public int getDetail_type() {
//        return detail_type;
//    }
//
//    public int getResource() {
//        return resource;
//    }
//
//    public double getVal() {
//        return val;
//    }
//
//    public String getBack() {
//        return back;
//    }
//
//    public void setHash(int hash) {
//        this.hash = hash;
//    }
//
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public void setDetail_type(int detail_type) {
//        this.detail_type = detail_type;
//    }
//
//    public void setResource(int resource) {
//        this.resource = resource;
//    }
//
//    public void setVal(double val) {
//        this.val = val;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public void setBack(String back) {
//        this.back = back;
//    }
//
//    public data_item(int type, int detail_type, int resource, double val){
//        this.type=type;
//        this.detail_type=detail_type;
//        this.resource=resource;
//        this.val=val;
//        Random r=new Random();
//        hash=r.nextInt();
//
//    }
}
