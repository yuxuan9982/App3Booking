package com.example.book_keeping_app;

import com.example.book_keeping_app.slice.detail_infoSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class detail_info extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(detail_infoSlice.class.getName());
    }
}
