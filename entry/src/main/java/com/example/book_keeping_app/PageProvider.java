package com.example.book_keeping_app;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.List;

public class PageProvider extends PageSliderProvider {
    private List<Integer> datalist;
    private AbilitySlice slice;
    @Override
    public int getCount() {
        return datalist.size();
    }
    public PageProvider(List<Integer> datalist, AbilitySlice slice){
        this.datalist=datalist;
        this.slice=slice;
    }
    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        int id=datalist.get(i);
        Component component= LayoutScatter.getInstance(this.slice).parse(id,null,false);
        componentContainer.addComponent(component);
        return component;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent((Component) o);
    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        return false;
    }
}
