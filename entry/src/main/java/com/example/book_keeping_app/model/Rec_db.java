package com.example.book_keeping_app.model;

//数据库

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {Rec.class},version = 1)
public abstract class Rec_db extends OrmDatabase {
}

