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
import ohos.agp.utils.LayoutAlignment;
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
    int year,month,week,order,special,syl,syr,sml,smr,swl,swr,smonl,smonr;
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
        get_set();
    }
    public DirectionalLayout build_dl(int type){
        DirectionalLayout dl=new DirectionalLayout(this);
        if(type==0)dl.setOrientation(Component.VERTICAL);else dl.setOrientation(Component.HORIZONTAL);
        dl.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        dl.setAlignment(LayoutAlignment.CENTER);
        return dl;
    }
    public void get_set(){
        Image i_set=(Image) findComponentById(ResourceTable.Id_setup);
        i_set.setClickedListener(o->{
            PopupDialog pd=new PopupDialog(getContext(),o);
            DirectionalLayout dl=new DirectionalLayout(getContext());
            set_but_back(dl,192,192,192);
            dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
            dl.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
            Text m=new Text(getContext()),y=new Text(getContext());
            m.setText("游戏指南");y.setText("版本说明");
            m.setTextSize(AttrHelper.vp2px(30,getContext()));
            y.setTextSize(AttrHelper.vp2px(30,getContext()));
            m.setPadding(10,10,10,10);
            y.setPadding(10,10,10,10);
            dl.addComponent(m);dl.addComponent(y);
            y.setClickedListener(v->{
                CommonDialog cd=new CommonDialog(this);
                cd.setAutoClosable(true);
                DirectionalLayout dl2=build_dl(0);

                Text title= new Text(this);
                //set_but_back(title,104,0,254);
                title.setTextSize(50);title.setMultipleLine(true);
                title.setText("信息说明");

                Text content= new Text(this);
                content.setTextSize(50);content.setMultipleLine(true);
                content.setMultipleLine(true);
                content.setText("游戏名：简约记账\n作者：于轩\n联系方式：2309941940@qq.com");

                Text end=new Text(this);
                end.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
                end.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
                end.setTextSize(50);
                end.setText("ver:--1.01");
                dl2.setPadding(20,20,20,20);
                dl2.addComponent(title);dl2.addComponent(content);dl2.addComponent(end);
                set_but_back(dl2,230,231,249);
                cd.setContentCustomComponent(dl2);
                cd.show();
                pd.destroy();
            });
            m.setClickedListener(v-> {
                CommonDialog cd=new CommonDialog(this);
                cd.setAutoClosable(true);
                DirectionalLayout dl2=build_dl(0);
                set_but_back(dl2,220,220,220);
                ScrollView sv=new ScrollView(this);
                sv.setHeight(AttrHelper.vp2px(600,getContext()));
                sv.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);

                Text content= new Text(this);
                //set_but_back(content,220,220,220);
                content.setTextSize(50);content.setMultipleLine(true);
                content.setText("   这是一款简易记账本。\n" +
                        "   你可以通过上面的倒三角图标选择你想要查看的月份和年份\n" +
                        "   如果你想要删除某条记录，只需要点击这条记录就可以删除，如果你想要查看某条记录，需要点击这条记录并选择查看明细\n" +
                        "   查看月度统计和年度统计请点击更多按钮，然后进入月度统计界面和年度统计界面\n" +
                        "   此外，如果你想要进一步筛选，可以点击更多按钮，然后进行特征筛选，目前支持对年份月份以及周还有金额的范围进行筛选\n" +
                        "   本软件提供了两种排序方式，默认屎按照你操作进行的顺序进行排序，你也可以选择按照时间降序进行排序，" +
                                "考虑到升序在实际应用中十分罕见，并且应用价值不高，因此本软件不提供支持\n" +
                        "   如果你想要添加新的账单，你可以点击添加按钮，进入添加账单界面\n" +
                        "   进入账单界面以后，你可以选择想要记录的类别\n" +
                        "   你可以选择收入账单和支出账单，通过左右滑动界面实现\n" +
                        "   本软件提供了33种支出类型以及5种收入类型，理论上能够覆盖绝大多数情况的要求，如果仍然没有你想要的类别，你可以点击" +
                                "”其他“这种类别\n" +
                        "   点击类别以后，你会获取到作者实现的内置简易计算器，你可以在这个计算器进行表达式的加减乘除计算，" +
                                "表达式会被记录，防止用户在使用过程中遗忘前面的信息\n" +
                        "   该计算器具备添加备注功能\n" +
                        "   该计算器并不是十分完善，作者仅仅做了几种比较简单的check，请确保你的表达式是合法的\n" +
                        "   你可以在计算器中自由选择日期，如果你选择的日期是今天，那么会显示一个今天的图标，否则会给你显示你选的日期的具体数值"+
                        "   记录完成后，点击完成按钮，你会返回到账单界面\n"
                                );

                sv.addComponent(content);
                dl2.addComponent(sv);
                cd.setContentCustomComponent(dl2);
                cd.show();
                pd.destroy();
            });
            pd.setCustomComponent(dl);
            pd.setAutoClosable(true);
            pd.show();
        });
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
            Text sel=new Text(getContext());

            m.setText("本月统计");y.setText("年度统计");sel.setText("条件筛选");

            sel.setClickedListener(v->{
                CommonDialog s_d=new CommonDialog(getContext());
                DirectionalLayout s_dl=(DirectionalLayout) LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_select,null,false);
                s_d.setContentCustomComponent(s_dl);
                s_d.setAutoClosable(true);
                s_d.show();

                Button conf=(Button) s_dl.findComponentById(ResourceTable.Id_s_confirm);
                Button ret=(Button) s_dl.findComponentById(ResourceTable.Id_s_ret);
                TextField yl=(TextField) s_dl.findComponentById(ResourceTable.Id_s_year_l);
                TextField yr=(TextField) s_dl.findComponentById(ResourceTable.Id_s_year_r);
                TextField ml=(TextField) s_dl.findComponentById(ResourceTable.Id_s_month_l);
                TextField mr=(TextField) s_dl.findComponentById(ResourceTable.Id_s_month_r);
                TextField wl=(TextField) s_dl.findComponentById(ResourceTable.Id_s_week_l);
                TextField wr=(TextField) s_dl.findComponentById(ResourceTable.Id_s_week_r);
                TextField monl=(TextField) s_dl.findComponentById(ResourceTable.Id_s_money_l);
                TextField monr=(TextField) s_dl.findComponentById(ResourceTable.Id_s_money_r);

                ret.setClickedListener(s->{
                    s_d.destroy();pd.destroy();
                });
                conf.setClickedListener(s->{
                    special=1;
                    syl=Integer.parseInt(yl.getText().length()==0? yl.getHint():yl.getText() );
                    syr=Integer.parseInt(yr.getText().length()==0? yr.getHint():yr.getText() );

                    sml=Integer.parseInt(ml.getText().length()==0? ml.getHint():ml.getText() );
                    smr=Integer.parseInt(mr.getText().length()==0? mr.getHint():mr.getText() );

                    swl=Integer.parseInt(wl.getText().length()==0? wl.getHint():wl.getText() );
                    swr=Integer.parseInt(wr.getText().length()==0? wr.getHint():wr.getText() );

                    smonl=Integer.parseInt(monl.getText().length()==0? monl.getHint():monl.getText() );
                    smonr=Integer.parseInt(monr.getText().length()==0? monr.getHint():monr.getText() );
                    Text date=(Text) findComponentById(ResourceTable.Id_date);
                    date.setText("条件筛选▼");
                    update_rec();
                    s_d.destroy();pd.destroy();
                });
            });


            m.setTextSize(AttrHelper.vp2px(30,getContext()));
            y.setTextSize(AttrHelper.vp2px(30,getContext()));
            sel.setTextSize(AttrHelper.vp2px(30,getContext()));

            m.setPadding(10,10,10,10);
            y.setPadding(10,10,10,10);
            sel.setPadding(10,10,10,10);
            dl.addComponent(m);dl.addComponent(y);dl.addComponent(sel);

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
                year=picker.getYear();month=picker.getMonth();special=0;
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
        recList=o_ctx.query(o_ctx.where(Rec.class).equalTo("year",year).equalTo("month",month));
        if(special==1){
            recList=o_ctx.query(o_ctx.where(Rec.class));
            List<Rec> new_r= new ArrayList<>();
            for(Rec r:recList){
                if(r.year>=syl&&r.year<=syr&&r.month>=sml&&r.month<=smr){
                    int dl=(swl-1)*7+1,dr=swr*7;
                    if(r.day>=dl&&r.day<=dr&&r.getVal()>=smonl&&r.getVal()<=smonr)
                        new_r.add(r);
                }
            }
            recList=new_r;
        }
        if(order==1){
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
