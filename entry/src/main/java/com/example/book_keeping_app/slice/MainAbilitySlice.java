package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;

import java.time.LocalDate;

public class MainAbilitySlice extends AbilitySlice {
    int cnt=0;
    RdbStore store;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Image b1=(Image) findComponentById(ResourceTable.Id_add);
        b1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent in=new Intent();
                AbilitySlice slice= new BookSlice();
                in.setParam("cnt",++cnt);
                present(slice,in);
            }
        });
        LocalDate date=LocalDate.now();
        Text time=(Text) findComponentById(ResourceTable.Id_date);
        time.setText(date.toString()+"▼");
        init_dataBase();
    }
    void init_dataBase(){
        DatabaseHelper helper=new DatabaseHelper(this);
        StoreConfig cfg=StoreConfig.newDefaultConfig("Store.db");
        RdbOpenCallback callback= new RdbOpenCallback() {
            @Override
            public void onCreate(RdbStore rdbStore) {
                rdbStore.executeSql("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER, salary REAL, blobType BLOB)");
            }

            @Override
            public void onUpgrade(RdbStore rdbStore, int i, int i1) {

            }
        };
        store= helper.getRdbStore(cfg,1,callback,null);
    }
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
