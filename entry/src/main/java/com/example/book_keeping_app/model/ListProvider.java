package com.example.book_keeping_app.model;

import com.example.book_keeping_app.ResourceTable;
import ohos.agp.components.*;
import ohos.agp.components.element.PixelMapElement;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import java.io.IOException;
import java.util.List;

public class ListProvider extends BaseItemProvider {
    List<Rec> list;
    Context ctx;
    ClickedListener listener;
    public void setClickedListener(ClickedListener listener){
        this.listener=listener;
    }
    public static interface  ClickedListener{
        void click(int pos);
    }
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
        img.setScaleMode(Image.ScaleMode.STRETCH);
        Text t1=(Text) dl.findComponentById(ResourceTable.Id_list_type);
        t1.setText((now.getType()==0?"支：":"收：")+now.getMessage()  );

        Text t2=(Text) dl.findComponentById(ResourceTable.Id_list_note);
        t2.setText( String.valueOf(now.getYear())+"-"+String.valueOf(now.getMonth())+"-"+String.valueOf(now.getDay()) );
        Text t3=(Text) dl.findComponentById(ResourceTable.Id_list_money);
        t3.setText(String.valueOf(Math.abs(now.getVal())*(now.getType()==0?-1:1))  );

        dl.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                listener.click(i);
            }
        });
        return dl;
    }
}
