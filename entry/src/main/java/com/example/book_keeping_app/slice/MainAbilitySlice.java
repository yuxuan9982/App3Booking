package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.app.Context;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button b1=(Button) findComponentById(ResourceTable.Id_but1);
        b1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent in=new Intent();
                AbilitySlice slice= new BookSlice();
                present(slice,in);
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