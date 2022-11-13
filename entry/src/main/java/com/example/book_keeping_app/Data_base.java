package com.example.book_keeping_app;

import com.example.book_keeping_app.model.data_item;
import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {data_item.class}, version = 1)
public abstract class Data_base extends OrmDatabase {
}