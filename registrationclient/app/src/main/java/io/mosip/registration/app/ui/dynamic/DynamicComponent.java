package io.mosip.registration.app.ui.dynamic;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DynamicComponent    {
    private List<DynamicView> views;
    protected Context context;



    String fieldName="";

    public DynamicComponent(String fieldName, Context context){
        this.context = context;
        views = new ArrayList<>();
       this.fieldName=fieldName;
    }
    public DynamicView getPrimaryView() {
        return views.get(0);
    }
    public DynamicView getSecondaryView() {
        return views.get(1);
    }
    public void setPrimaryView(DynamicView primaryView) {
        this.setView(0,primaryView);
    }

    public DynamicView getView(int index) {
        return views.get(index);
    }
    public DynamicView setView(int index, DynamicView view){
        return views.set(index,view);
    }
    public DynamicView addView(DynamicView view){
        views.add(view);
        return view;
    }

    public int getViewCount(){
        if(views!=null)
            return views.size();
        else
            return 0;
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValueJSON(){

        String values="\""+getFieldName()+"\":[";
        for(DynamicView vw:views){
            vw.validateEntry();
            values=values+"{\"language"+"\":\""+vw.getLanguageCode()+"\",";
            values=values+"\"value"+"\":\""+vw.getValue()+"\"},";
        }
        values=values.substring(0, values.lastIndexOf(","));
        values=values+"]";

        return values;
    }


}
