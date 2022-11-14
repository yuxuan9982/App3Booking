package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.model.PageProvider;
import com.example.book_keeping_app.ResourceTable;
import com.example.book_keeping_app.model.Rec;
import com.example.book_keeping_app.model.Rec_db;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookSlice extends AbilitySlice {
    List<Integer> in=new ArrayList<>();
    List<Integer> out=new ArrayList<>();
    PageSlider ps;
    OrmContext o_ctx;
    String note;Integer from;
    //获取对应的标签
    HashMap<Integer,String> mp;
    HashMap<Integer,Integer> bac;
    public void init_inout(){
        int begin=ResourceTable.Id_i1;
//        for(int i=1;i<=5;i++,begin++)
//            in.add(begin);
//        begin=ResourceTable.Id_o1;
//        for(int i=1;i<=33;i++,begin++)
//            out.add(begin);
        in.add(ResourceTable.Id_i1);in.add(ResourceTable.Id_i2);in.add(ResourceTable.Id_i3);
        in.add(ResourceTable.Id_i4);in.add(ResourceTable.Id_i5);
        out.add(ResourceTable.Id_o1);out.add(ResourceTable.Id_o2);out.add(ResourceTable.Id_o3);out.add(ResourceTable.Id_o4);
        out.add(ResourceTable.Id_o5);out.add(ResourceTable.Id_o6);out.add(ResourceTable.Id_o7);out.add(ResourceTable.Id_o8);
        out.add(ResourceTable.Id_o9);out.add(ResourceTable.Id_o10);out.add(ResourceTable.Id_o11);out.add(ResourceTable.Id_o12);
        out.add(ResourceTable.Id_o13);out.add(ResourceTable.Id_o14);out.add(ResourceTable.Id_o15);out.add(ResourceTable.Id_o16);
        out.add(ResourceTable.Id_o17);out.add(ResourceTable.Id_o18);out.add(ResourceTable.Id_o19);out.add(ResourceTable.Id_o20);
        out.add(ResourceTable.Id_o21);out.add(ResourceTable.Id_o22);out.add(ResourceTable.Id_o23);out.add(ResourceTable.Id_o24);
        out.add(ResourceTable.Id_o25);out.add(ResourceTable.Id_o26);out.add(ResourceTable.Id_o27);out.add(ResourceTable.Id_o28);
        out.add(ResourceTable.Id_o29);out.add(ResourceTable.Id_o30);out.add(ResourceTable.Id_o31);out.add(ResourceTable.Id_o32);
        out.add(ResourceTable.Id_o33);
    }
    public void init_map(){
        String[] basic_in={"工资","兼职","理财","礼金","其他"};
        String[] basic_out={"餐饮","购物","日用","交通","水果"
                ,"游戏","运动","零食","娱乐","通讯","服饰","美容"
        ,"住房","居家","育儿","养老"
                ,"社交","旅行","烟酒","数码","汽车","医药",
                "书籍","学习","宠物","出礼","礼物","办公","维修",
                "捐赠","彩票","亲友","其他"};
        int cnt=0;
        mp=new HashMap<>();
        for(Integer o:out){
            mp.put(o,basic_out[cnt++]);
        }
        cnt=0;
        for(Integer i:in){
            mp.put(i,basic_in[cnt++]);
        }

    }
    public int getId(Component o){
        switch (o.getId()){
            case ResourceTable.Id_i1:
                return ResourceTable.Media_salary;
            case ResourceTable.Id_i2:
                return ResourceTable.Media_parttime;
            case ResourceTable.Id_i3:
                return ResourceTable.Media_manage;
            case ResourceTable.Id_i4:
                return ResourceTable.Media_money;
            case ResourceTable.Id_i5:
                return ResourceTable.Media_els;
            case ResourceTable.Id_o1:
                return ResourceTable.Media_eating;
            case ResourceTable.Id_o2:
                return ResourceTable.Media_shopping;
            case ResourceTable.Id_o3:
                return ResourceTable.Media_daily;
            case ResourceTable.Id_o4:
                return ResourceTable.Media_transport;
            case ResourceTable.Id_o5:
                return ResourceTable.Media_fruit;
            case ResourceTable.Id_o6:
                return ResourceTable.Media_electronic;
            case ResourceTable.Id_o7:
                return ResourceTable.Media_sport;
            case ResourceTable.Id_o8:
                return ResourceTable.Media_snack;
            case ResourceTable.Id_o9:
                return ResourceTable.Media_entertainment;
            case ResourceTable.Id_o10:
                return ResourceTable.Media_adressbook;
            case ResourceTable.Id_o11:
                return ResourceTable.Media_clothe;
            case ResourceTable.Id_o12:
                return ResourceTable.Media_face;
            case ResourceTable.Id_o13:
                return ResourceTable.Media_house;
            case ResourceTable.Id_o14:
                return ResourceTable.Media_inhome;
            case ResourceTable.Id_o15:
                return ResourceTable.Media_son;
            case ResourceTable.Id_o16:
                return ResourceTable.Media_father;
            case ResourceTable.Id_o17:
                return ResourceTable.Media_social;
            case ResourceTable.Id_o18:
                return ResourceTable.Media_travel;
            case ResourceTable.Id_o19:
                return ResourceTable.Media_wine;
            case ResourceTable.Id_o20:
                return ResourceTable.Media_computer;
            case ResourceTable.Id_o21:
                return ResourceTable.Media_car;
            case ResourceTable.Id_o22:
                return ResourceTable.Media_medi;
            case ResourceTable.Id_o23:
                return ResourceTable.Media_book;
            case ResourceTable.Id_o24:
                return ResourceTable.Media_learning;
            case ResourceTable.Id_o25:
                return ResourceTable.Media_per;
            case ResourceTable.Id_o26:
                return ResourceTable.Media_money;
            case ResourceTable.Id_o27:
                return ResourceTable.Media_gift;
            case ResourceTable.Id_o28:
                return ResourceTable.Media_desk;
            case ResourceTable.Id_o29:
                return ResourceTable.Media_repair;
            case ResourceTable.Id_o30:
                return ResourceTable.Media_donate;
            case ResourceTable.Id_o31:
                return ResourceTable.Media_ticket;
            case ResourceTable.Id_o32:
                return ResourceTable.Media_friends;
            case ResourceTable.Id_o33:
                return ResourceTable.Media_els;
        }
        return ResourceTable.Media_house;
    }
    int type;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_Book);

        DatabaseHelper helper=new DatabaseHelper(this);
        o_ctx=helper.getOrmContext("database","database.db", Rec_db.class);

        TabList tabList=(TabList) findComponentById(ResourceTable.Id_tab_list1);
        String[] tab_name={"支出","收入"};
        tabList.removeAllComponents();//if not , return make 2 more
        for(int i=0;i<=1;i++){
            TabList.Tab tab=tabList.new Tab(getContext());
            tab.setText(tab_name[i]);
            tabList.addTab(tab);
            if(i==0)tab.select();
        }
        ps=(PageSlider) findComponentById(ResourceTable.Id_slider);
        List<Integer> lst=new ArrayList<>();
        lst.add(ResourceTable.Layout_get_out);lst.add(ResourceTable.Layout_get_in);
        ps.setProvider(new PageProvider(lst,this));
//        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
//            @Override
//            public void onSelected(TabList.Tab tab) {
//                ps.setCurrentPage(tab.getPosition());
//            }
//            @Override
//            public void onUnselected(TabList.Tab tab) {
//
//            }
//            @Override
//            public void onReselected(TabList.Tab tab) {
//
//            }
//        });
        ps.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int i, float v, int i1) {}
            @Override
            public void onPageSlideStateChanged(int i) {}
            @Override
            public void onPageChosen(int i) {
                tabList.selectTabAt(i);type=i;
            }
        });
        Button rtn=(Button) findComponentById(ResourceTable.Id_return1);
        rtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //onBackPressed();
                terminate();
            }
        });
        init_inout();init_map();
        for(Integer o:out){
            Image img=(Image) findComponentById(o);
            img.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    Integer i=component.getId();
                    note=mp.get(i);from=getId(component);
                    caculator();
                }
            });
        }
        for(Integer i:in){
            Image img=(Image) findComponentById(i);
            img.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    note=mp.get(component.getId());from=getId(component);
                    caculator();
                }
            });
        }
    }
    public void set_but_back(Component component,int r,int g,int b){
        ShapeElement element=new ShapeElement();
        element.setShape(ShapeElement.RECTANGLE);
        //element.setRgbColor(new RgbColor(r,g,b));
        element.setStroke(2,new RgbColor(0,0,255));
        component.setBackground(element);
    }
    public double calc(String val){
        double sum=0,fg=1,tmp=0,sm=0;
        int len=val.length();
        for(int i=0;i<len;i++){
            if(val.charAt(i)=='-'){
                sum+=tmp*fg;fg=-1;tmp=0;sm=0;
            }else if(val.charAt(i)=='+'){
                sum+=tmp*fg;fg=1;tmp=0;sm=0;
            }else if(val.charAt(i)!='.'){
                if(sm==0)tmp=tmp*10+val.charAt(i)-'0';
                else {
                    tmp=tmp+(val.charAt(i)-'0')/sm;sm*=10;
                }
            }else{
                sm=10;
            }
        }
        sum+=tmp*fg;
        return sum;
    }
    String val;
    String[][] content;
    TextField msg;
    public void caculator(){//这部分注意后续逻辑的添加
        val="";
        CommonDialog cd=new CommonDialog(this);
        cd.setAlignment(LayoutAlignment.BOTTOM);
        DirectionalLayout dl=new DirectionalLayout(this);
        dl.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);

        Text text=new Text(this);
        text.setTextAlignment(TextAlignment.RIGHT);
        text.setText(String.valueOf(calc(val)));
        text.setTextSize(AttrHelper.vp2px(40,getContext()));
        text.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        text.setPadding(0,0,20,0);

        Text hinter=new Text(this);
        hinter.setTextAlignment(TextAlignment.LEFT);
        hinter.setText(val.length()==0?"0":val);
        hinter.setTextSize(AttrHelper.vp2px(20,getContext()));
        hinter.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        hinter.setPadding(30,0,0,0);
//        TextField hinter=new TextField(this);
//        hinter.setTextAlignment(TextAlignment.LEFT);
//        hinter.sethin
        TableLayout tl=new TableLayout(this);
        tl.setColumnCount(4);
        tl.setRowCount(4);
        tl.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        tl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        Text[][] num=new Text[4][4];
        int wid= getLayoutParams().width;
        content=new String[][]{{"7", "8", "9", "日期"}, {"4", "5", "6", "+"},{"1","2","3","-"},
                {".","0","<-","完成"}};
        for(int i=0;i<=3;i++)for(int j=0;j<=3;j++){
            num[i][j]=new Text(this);
            num[i][j].setTextSize(AttrHelper.vp2px(30,this));
            num[i][j].setWidth(wid/4);
            num[i][j].setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
            num[i][j].setText(content[i][j]);
            num[i][j].setTextAlignment(TextAlignment.CENTER);
            tl.addComponent(num[i][j]);
            if((i==0&&j==3)||(i==3&&j==2)||(j==3&&i==3))continue;
            num[i][j].setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    Text tmp=(Text)component;
                    val=val+tmp.getText();
                    text.setText(String.valueOf(calc(val)));
                    hinter.setText(val.length()==0?"0":val);
                }
            });
        }
        num[3][2].setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(val.length()>0)
                    val=val.substring(0,val.length()-1);
                double v=calc(val);
                text.setText(String.valueOf(v) );
                hinter.setText(val.length()==0?"0":val);

            }
        });
        dl.addComponent(text);dl.addComponent(hinter);
        //add message
        msg=new TextField(this);
        msg.setHint("备注");
        msg.setTextSize(AttrHelper.vp2px(30,this));
        msg.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        msg.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        dl.addComponent(msg);
        //message
        dl.addComponent(tl);
        cd.setContentCustomComponent(dl);
        cd.setAutoClosable(true);
        cd.show();

        num[3][3].setClickedListener(o->{
            //data_item item=new data_item(LocalDate.now(),1,1,ResourceTable.Media_eating,v);
            Rec record=new Rec(type,from,msg.getText().length()==0?note:msg.getText(),calc(val),
                    LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());

            o_ctx.insert(record);o_ctx.flush();
            cd.destroy();terminate();
        });
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
