package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;

public class detail_infoSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_detail_info);
        Text t1=(Text) findComponentById(ResourceTable.Id_d_type);
        Text t2=(Text) findComponentById(ResourceTable.Id_d_money);
        Text t3=(Text) findComponentById(ResourceTable.Id_d_date);
        Text t4=(Text) findComponentById(ResourceTable.Id_d_note);
        t1.setText("类型:"+(intent.getIntParam("type",0) ==0?"支出":"收入" ));
        t2.setText("金额:"+String.valueOf(intent.getDoubleParam("money",0) )  );
        int year,month,day;
        year=intent.getIntParam("year",0);
        month=intent.getIntParam("month",0);
        day=intent.getIntParam("day",0);
        t3.setText("日期:"+String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
        t4.setText("备注:"+intent.getStringParam("note") );
        Button back=(Button) findComponentById(ResourceTable.Id_d_ret);
        back.setClickedListener(o->{
            terminate();
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
