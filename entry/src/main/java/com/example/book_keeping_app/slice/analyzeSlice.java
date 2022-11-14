package com.example.book_keeping_app.slice;

import com.example.book_keeping_app.ResourceTable;
import com.example.book_keeping_app.model.AnalyzeProvider;
import com.example.book_keeping_app.model.ListProvider;
import com.example.book_keeping_app.model.Rec;
import com.example.book_keeping_app.model.Rec_db;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.lang.reflect.Array;
import java.util.*;

public class analyzeSlice extends AbilitySlice {
    OrmContext o_ctx;
    List<Rec> lst;
    double in,out;
    public class pair implements Comparable<pair>{
        Double first;Integer second;
        pair(Double first,Integer second){
            this.first=first;this.second=second;
        }
        @Override
        public int compareTo(pair pair) {
            int fg=0;
            if(this.first==pair.first&&this.second==pair.second)return 0;
            else if(this.first>pair.first)fg=1;
            else if(this.first<pair.first)fg=-1;
            else if(this.second>pair.second)fg=1;
            else fg=-1;
            return -fg;
        }
    }
    HashMap<Integer,Double> mp_in=new HashMap<>(),mp_out=new HashMap<>();
    ArrayList<pair> arr_in,arr_out;
    int year,month;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_analyze);
        year=intent.getIntParam("year",-1);month=intent.getIntParam("month",-1);

        DatabaseHelper helper=new DatabaseHelper(this);
        o_ctx=helper.getOrmContext("database","database.db", Rec_db.class);
        if(month==-1)
            lst=o_ctx.query(o_ctx.where(Rec.class).equalTo("year",year));
        else
            lst=o_ctx.query(o_ctx.where(Rec.class).equalTo("year",year).equalTo("month",month));
        for(Rec r:lst){
            if(r.getType()==0){
                out+=r.getVal();
                mp_out.put(r.getResource(),r.getVal()+(mp_out.get(r.getResource()) ==null? 0: mp_out.get(r.getResource()) )   );
            }
            else {
                in+=r.getVal();
                mp_in.put(r.getResource(),r.getVal()+(mp_in.get(r.getResource()) ==null?0:mp_in.get(r.getResource()) )   );
            }
        }
        show_rank();
        show_else();
    }
    public void show_else(){

    }
    public void show_rank(){
        arr_in=new ArrayList<>();
        arr_out=new ArrayList<>();
        for(Integer key:mp_in.keySet()){
            Double val=mp_in.get(key);
            arr_in.add(new pair(val,key));
        }
        for(Integer key:mp_out.keySet()){
            Double val=mp_out.get(key);
            arr_out.add(new pair(val,key));
        }
        Collections.sort(arr_in);Collections.sort(arr_out);

        List<Rec> lst_in=new ArrayList<>(),lst_out=new ArrayList<>();
        for(pair p:arr_in){
            Rec r=new Rec(1,p.second,"支出",p.first,year,month,0);
            lst_in.add(r);
        }
        for(pair p:arr_out){
            Rec r=new Rec(0,p.second,"支出",p.first,year,month,0);
            lst_out.add(r);
        }
        ListContainer listContainer1=(ListContainer) findComponentById(ResourceTable.Id_a_list_in);
        AnalyzeProvider provider= new AnalyzeProvider(lst_in,this);
        listContainer1.setItemProvider(provider);

        ListContainer listContainer2=(ListContainer) findComponentById(ResourceTable.Id_a_list_out);
        AnalyzeProvider provider2= new AnalyzeProvider(lst_out,this);
        listContainer2.setItemProvider(provider2);
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
