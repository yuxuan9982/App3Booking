package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.PageProvider;
import com.example.book_keeping_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;
import ohos.utils.system.SystemCapability;

import java.util.ArrayList;
import java.util.List;

public class BookSlice extends AbilitySlice {
    List<Integer> in=new ArrayList<>();
    List<Integer> out=new ArrayList<>();
    PageSlider ps;
    RdbStore store;
    public void init_inout(){
        int begin=ResourceTable.Id_i1;
        for(int i=1;i<=5;i++,begin++)
            in.add(begin);
        begin=ResourceTable.Id_o1;
        for(int i=1;i<=31;i++,begin++)
            out.add(begin);
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
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_Book);
        init_dataBase();
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
                tabList.selectTabAt(i);
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
        init_inout();
        for(Integer o:out){
            Image img=(Image) findComponentById(o);
            img.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    caculator();
                }
            });
        }
        for(Integer i:in){
            Image img=(Image) findComponentById(i);
            img.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
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
                text.setText(String.valueOf(calc(val)));
                hinter.setText(val.length()==0?"0":val);

            }
        });
        num[3][3].setClickedListener(o->{
            cd.destroy();terminate();
        });
        dl.addComponent(text);dl.addComponent(hinter);
        dl.addComponent(tl);

        cd.setContentCustomComponent(dl);
        cd.setAutoClosable(true);
        cd.show();
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
