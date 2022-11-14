package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.ResourceTable;
import com.example.book_keeping_app.model.ListProvider;
import com.example.book_keeping_app.model.Rec;
import com.example.book_keeping_app.model.Rec_db;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;

import java.time.LocalDate;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    int cnt=0;
    OrmContext o_ctx;
    List<Rec> recList;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Image b1=(Image) findComponentById(ResourceTable.Id_add);

        // database
        DatabaseHelper helper=new DatabaseHelper(this);
        o_ctx=helper.getOrmContext("database","database.db", Rec_db.class);
        //
        b1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent in=new Intent();
//                AbilitySlice slice= new BookSlice();
//                in.setParam("cnt",++cnt);
//                present(slice,in);
                Operation operation=new Intent.OperationBuilder().withAbilityName("com.example.book_keeping_app.Book").build();
                in.setOperation(operation);
                startAbility(in);

            }
        });
        LocalDate date=LocalDate.now();
        Text time=(Text) findComponentById(ResourceTable.Id_date);
        time.setText(date.toString()+"▼");

        //\
//        OrmPredicates query=o_ctx.where(Rec.class);
//        List<Rec> reclist=o_ctx.query(query);
//
//        CommonDialog dialog=new CommonDialog(getContext());
//        dialog.setContentText(String.valueOf(reclist.size()));
//        dialog.show();
        //
        //for debug
        Rec record=new Rec(0,ResourceTable.Media_computer,"msg",123, LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());
        o_ctx.insert(record);o_ctx.flush();
        //for debug
        //list container part
        ListContainer listContainer=(ListContainer) findComponentById(ResourceTable.Id_list_container);
        recList= o_ctx.query(o_ctx.where(Rec.class));
        ListProvider provider= new ListProvider(recList,this);
        listContainer.setItemProvider(provider);

        //list container part
    }
    @Override
    public void onActive() {
        super.onActive();
    }
    public void update_rec(){
        recList=o_ctx.query(o_ctx.where(Rec.class));
        ListContainer listContainer=(ListContainer) findComponentById(ResourceTable.Id_list_container);
        ListProvider provider= new ListProvider(recList,this);
        listContainer.setItemProvider(provider);
    }
    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
