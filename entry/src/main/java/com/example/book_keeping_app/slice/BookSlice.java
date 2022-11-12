package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.PageProvider;
import com.example.book_keeping_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.PageSlider;
import ohos.agp.components.TabList;

import java.util.ArrayList;
import java.util.List;

public class BookSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_Book);
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
