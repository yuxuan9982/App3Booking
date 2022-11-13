package com.example.book_keeping_app.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class data_item {
    public int hash;
    public Date date;
    public int type;
    public int detail_type;
    public int resource;
    public double val;
    public String back;
    public data_item(Date date,int type,int detail_type,int resource,double val){
        this.date=date;
        this.type=type;
        this.detail_type=detail_type;
        this.resource=resource;
        this.val=val;
        Random r=new Random();
        hash=r.nextInt();
    }
}
