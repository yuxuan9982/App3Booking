package com.example.book_keeping_app;

import com.example.book_keeping_app.slice.analyzeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class analyze extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(analyzeSlice.class.getName());
    }
}
