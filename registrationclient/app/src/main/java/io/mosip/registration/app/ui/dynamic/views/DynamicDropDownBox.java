package io.mosip.registration.app.ui.dynamic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;


import io.mosip.registration.app.R;
import io.mosip.registration.app.ui.dynamic.DynamicView;

public class DynamicDropDownBox extends LinearLayout implements DynamicView {
    Spinner spinner;

    String languageCode="";
    String labelText="";
    String validationRule="";
    final int layoutId = R.layout.dynamic_dropdown_box;
    public DynamicDropDownBox(Context context, String langCode, String label, String validation) {
        super(context);

        languageCode=langCode;
        labelText=label;
        validationRule=validation;
        init(context);
    }

    public DynamicDropDownBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicDropDownBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DynamicDropDownBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        inflate(context, layoutId, this);
        ((TextView)findViewById(R.id.dropdown_label)).setText(labelText);
        initComponents();
    }

    private void initComponents() {
         spinner = findViewById(R.id.dropdown_input);

        spinner.setFocusableInTouchMode(true);
        spinner.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ViewGroup pnl = findViewById(R.id.dropdown_control_holder);
                if(b){
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_focused));
                }
                else{
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                }

            }
        });

        spinner.setTag(this);

    }

    public String getValue(){
        String value="";
         value = spinner.getSelectedItem().toString();

        return value;
    }

    @Override
    public void setValue(String value) {
      spinner.setSelection(getIndex(spinner,value));
    }

    private int getIndex(Spinner spinner, String val){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(val)){
                return i;
            }
        }

        return 0;
    }
    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public boolean validateEntry() {
        ViewGroup pnl = findViewById(R.id.dropdown_control_holder);

        if(getValue().length()>0){
            pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
            return true;
        }

        else{
            pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_error));
            return false;
        }
    }

    public void setOnFocusChangeListener( View.OnFocusChangeListener watcher){

      spinner.setOnFocusChangeListener(watcher);

    }

}
