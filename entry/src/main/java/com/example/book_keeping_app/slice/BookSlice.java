package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.PageProvider;
import com.example.book_keeping_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;

import java.util.ArrayList;
import java.util.List;

public class BookSlice extends AbilitySlice {
    List<Integer> in=new ArrayList<>();
    List<Integer> out=new ArrayList<>();
    Integer now_num=0;
    public void init_inout(){
        int begin=ResourceTable.Id_i1;
        for(int i=1;i<=5;i++,begin++)
            in.add(begin);
        begin=ResourceTable.Id_o1;
        for(int i=1;i<=31;i++,begin++)
            out.add(begin);
    }
    Context ctx;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_Book);
        ctx=getContext();
        TabList tabList=(TabList) findComponentById(ResourceTable.Id_tab_list1);
        String[] tab_name={"支出","收入"};
        tabList.removeAllComponents();//if not , return make 2 more
        for(int i=0;i<=1;i++){
            TabList.Tab tab=tabList.new Tab(getContext());
            tab.setText(tab_name[i]);
            tabList.addTab(tab);
            if(i==0)tab.select();
        }
        PageSlider ps=(PageSlider) findComponentById(ResourceTable.Id_slider);
        List<Integer> lst=new ArrayList<>();
        lst.add(ResourceTable.Layout_get_out);lst.add(ResourceTable.Layout_get_in);
        ps.setProvider(new PageProvider(lst,this));
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                ps.setCurrentPage(tab.getPosition());
            }
            @Override
            public void onUnselected(TabList.Tab tab) {

            }
            @Override
            public void onReselected(TabList.Tab tab) {

            }
        });
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
    public void caculator(){
        CommonDialog cd=new CommonDialog(ctx);
        cd.setAlignment(LayoutAlignment.BOTTOM);
        DirectionalLayout dl=new DirectionalLayout(ctx);
        dl.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);

        Text text=new Text(ctx);
        text.setTextAlignment(TextAlignment.RIGHT);
        text.setText(String.valueOf(now_num));
        text.setTextSize(AttrHelper.vp2px(50,getContext()));
        text.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);



        TableLayout tl=new TableLayout(this);
        tl.setColumnCount(4);
        tl.setRowCount(4);

        dl.addComponent(text);
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
