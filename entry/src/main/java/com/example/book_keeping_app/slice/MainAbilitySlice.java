package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.ResourceTable;
import com.example.book_keeping_app.model.ListProvider;
import com.example.book_keeping_app.model.Rec;
import com.example.book_keeping_app.model.Rec_db;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.PopupDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MainAbilitySlice extends AbilitySlice {
    int cnt=0;
    OrmContext o_ctx;
    List<Rec> recList;

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        if(requestCode==0){
            update_rec();
        }
    }
    int year,month,order;
    double in,out;
    //排序方式
    //本月分析
    //年度分析
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
                Operation operation=new Intent.OperationBuilder().
                        withDeviceId("").
                        withBundleName("com.example.book_keeping_app").
                        withAbilityName("com.example.book_keeping_app.Book").
                        build();
                in.setOperation(operation);
                //startAbility(in);
                startAbilityForResult(in,0);
            }
        });
        LocalDate date=LocalDate.now();
        Text time=(Text) findComponentById(ResourceTable.Id_date);
        year=date.getYear();month=date.getMonthValue();
        time.setText(String.valueOf(year)+"-"+String.valueOf(month)+"▼");
        //for debug
        Rec record=new Rec(0,ResourceTable.Media_computer,"msg",123,
                LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());
        o_ctx.insert(record);o_ctx.flush();
        //for debug
        //list container part
        update_rec();
        //list container part
        get_tot();
        get_more();
        get_order();
    }
    public void get_order(){
        Image i_order=(Image) findComponentById(ResourceTable.Id_order);
        i_order.setClickedListener(o->{
            PopupDialog pd=new PopupDialog(getContext(),o);
            DirectionalLayout dl=new DirectionalLayout(getContext());
            set_but_back(dl,192,192,192);
            dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
            dl.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
            Text m=new Text(getContext()),y=new Text(getContext());
            m.setText("插入顺序");y.setText("时间顺序");
            m.setTextSize(AttrHelper.vp2px(30,getContext()));
            y.setTextSize(AttrHelper.vp2px(30,getContext()));
            m.setPadding(10,10,10,10);
            y.setPadding(10,10,10,10);
            dl.addComponent(m);dl.addComponent(y);
            m.setClickedListener(v->{order=0;pd.destroy();update_rec();});
            y.setClickedListener(v->{
                order=1;
                pd.destroy();
                update_rec();}
            );
            pd.setCustomComponent(dl);
            pd.setAutoClosable(true);
            pd.show();
        });
    }
    public void get_more(){
        Image more=(Image) findComponentById(ResourceTable.Id_more);
        more.setClickedListener(o->{
            PopupDialog pd=new PopupDialog(getContext(),o);
            DirectionalLayout dl=new DirectionalLayout(getContext());
            set_but_back(dl,192,192,192);
            dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
            dl.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
            Text m=new Text(getContext()),y=new Text(getContext());
            m.setText("本月统计");y.setText("年度统计");
            m.setTextSize(AttrHelper.vp2px(30,getContext()));
            y.setTextSize(AttrHelper.vp2px(30,getContext()));
            m.setPadding(10,10,10,10);
            y.setPadding(10,10,10,10);
            dl.addComponent(m);dl.addComponent(y);

            m.setClickedListener(v->{
                Intent in=new Intent();
                analyzeSlice slice= new analyzeSlice();
                in.setParam("year",year);
                in.setParam("month",month);
                present(slice,in);pd.destroy();
            });
            y.setClickedListener(v->{
                Intent in=new Intent();
                analyzeSlice slice= new analyzeSlice();
                in.setParam("year",year);
                present(slice,in);pd.destroy();
            });

            pd.setCustomComponent(dl);
            pd.setAutoClosable(true);
            pd.show();
        });
    }
    public void get_tot(){
        Text date=(Text) findComponentById(ResourceTable.Id_date);
        date.setClickedListener(o->{
            CommonDialog cd=new CommonDialog(getContext());
            DirectionalLayout dl=(DirectionalLayout) LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_time_picker,null,false);
            //dl.setWidth(AttrHelper.vp2px(300,getContext()));
            //dl.setHeight(AttrHelper.vp2px(300,getContext()));
            DatePicker picker=(DatePicker) dl.findComponentById(ResourceTable.Id_datepicker);
            picker.setDateOrder(DatePicker.DateOrder.YM);
            Button b1=(Button) dl.findComponentById(ResourceTable.Id_pick_back);
            Button b2=(Button) dl.findComponentById(ResourceTable.Id_pic_confirm);
            b1.setClickedListener(v->{cd.destroy();});
            b2.setClickedListener(v->{
                year=picker.getYear();month=picker.getMonth();
                date.setText(String.valueOf(year)+"-"+String.valueOf(month)+"▼");
                update_rec();
                cd.destroy();
            });
            cd.setContentCustomComponent(dl);
            cd.setAutoClosable(true);
            cd.show();
        });
    }
    @Override
    public void onActive() {
        super.onActive();
    }
    public void update_rec(){
        in=out=0;
        if(order==0)
            recList=o_ctx.query(o_ctx.where(Rec.class).equalTo("year",year).equalTo("month",month));
        else{
            recList=o_ctx.query(o_ctx.where(Rec.class).equalTo("year",year).equalTo("month",month));
            TreeMap<Integer, ArrayList<Rec> > mp=new TreeMap<>();
            for(Rec r:recList){
                int y1=r.getYear(),m1=r.getMonth(),d1=r.getDay();
                int tot=-y1*366*31-m1*31-d1;
                if(mp.get(tot)==null) mp.put(tot,new ArrayList<>());
                ArrayList<Rec> arr=mp.get(tot);arr.add(r);
                mp.put(tot, arr );
            }
            List<Rec> new_lst=new ArrayList<>();
            for(Integer o:mp.keySet()){
                ArrayList<Rec> v=mp.get(o);
                new_lst.addAll(v);
            }
            recList=new_lst;
        }
        ListContainer listContainer=(ListContainer) findComponentById(ResourceTable.Id_list_container);
        ListProvider provider= new ListProvider(recList,this);
        listContainer.setItemProvider(provider);
        provider.setClickedListener(new ListProvider.ClickedListener() {
            @Override
            public void click(int pos) {
                CommonDialog dialog=new CommonDialog(getContext());
                DirectionalLayout dl= (DirectionalLayout) LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_extern_function,null,false);
                dialog.setContentCustomComponent(dl);
                dialog.show();
                dialog.setCornerRadius(30);
                dialog.setAutoClosable(true);
                Rec r=recList.get(pos);
                Text detail=(Text) dl.findComponentById(ResourceTable.Id_detail);
                Text del=(Text) dl.findComponentById(ResourceTable.Id_delete);
                del.setClickedListener(o->{
                    o_ctx.delete(r);o_ctx.flush();update_rec();dialog.destroy();
                });
                detail.setClickedListener(o->{
                    dialog.destroy();
                    Intent in2=new Intent();
                    detail_infoSlice slice=new detail_infoSlice();
                    in2.setParam("type",r.getType());
                    in2.setParam("money",r.getVal());
                    in2.setParam("year",r.getYear());
                    in2.setParam("month",r.getMonth());
                    in2.setParam("day",r.getDay());
                    in2.setParam("note",r.getMessage());
                    present(slice,in2);
                });
            }
        });
        update_inout();
    }
    public void set_but_back(Component component,int r,int g,int b){
        ShapeElement element=new ShapeElement();
        element.setShape(ShapeElement.RECTANGLE);
        element.setCornerRadius(30);
        element.setRgbColor(new RgbColor(r,g,b));
        //element.setStroke(10,new RgbColor(0,0,255));
        component.setBackground(element);
    }
    public void update_inout(){
        Text in1=(Text) findComponentById(ResourceTable.Id_in_money);
        Text out1=(Text) findComponentById(ResourceTable.Id_out_money);
        for(Rec r:recList){
            if(r.getType()==0)out+=r.getVal();
            else in+=r.getVal();
        }
        in1.setText("总收入："+String.valueOf(in));
        out1.setText("总支出："+String.valueOf(out));
    }
    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

}
