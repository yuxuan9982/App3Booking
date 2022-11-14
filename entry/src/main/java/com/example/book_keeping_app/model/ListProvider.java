package com.example.book_keeping_app.model;

import com.example.book_keeping_app.ResourceTable;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.List;

public class ListProvider extends BaseItemProvider {
    List<Rec> list;
    Context ctx;
    @Override
    public int getCount() {
        return list.size();
    }
    public ListProvider(List<Rec> list,Context ctx){
        this.list=list;this.ctx=ctx;
    }
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        DependentLayout dl=(DependentLayout) LayoutScatter.getInstance(ctx).parse(ResourceTable.Layout_list_item,null,false);
        Image img=(Image) dl.findComponentById(ResourceTable.Id_list_img);
        Rec now=list.get(i);
        img.setImageAndDecodeBounds(now.getResource());
        Text t1=(Text) dl.findComponentById(ResourceTable.Id_list_type);
        t1.setText(now.getType()==0?"支出":"收入");
        Text t2=(Text) dl.findComponentById(ResourceTable.Id_list_note);
        t2.setText(now.getMessage()  );
        Text t3=(Text) dl.findComponentById(ResourceTable.Id_list_money);
        t3.setText(String.valueOf(Math.abs(now.getVal())*(now.getType()==0?-1:1))  );
        return dl;
    }
}
