package io.mosip.registration.app.ui.dynamic.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import java.util.Calendar;
import java.util.Date;

import io.mosip.registration.app.R;
import io.mosip.registration.app.ui.dynamic.DynamicView;

public class DynamicAgeDateBox extends LinearLayout implements DynamicView {
    EditText dateBox;
    EditText monthBox;
    EditText yearBox;
    EditText ageBox;

    String languageCode="";
    String labelText="";
    String validationRule="";
final int layoutId =R.layout.dynamic_agedate_box;
    public DynamicAgeDateBox(Context context,String langCode,String label,String validation) {
        super(context);

        languageCode=langCode;
        labelText=label;
        validationRule=validation;
        init(context);
    }

    public DynamicAgeDateBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicAgeDateBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DynamicAgeDateBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        inflate(context, layoutId, this);
        ((TextView)findViewById(R.id.dob_label)).setText(labelText);
        initComponents();
    }

    private void initComponents() {
         dateBox= findViewById(R.id.dob_date);
         monthBox= findViewById(R.id.dob_month);
         yearBox= findViewById(R.id.dob_year);
         ageBox= findViewById(R.id.dob_age);
        dateBox.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        monthBox.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        yearBox.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        ageBox.setBackgroundTintMode(PorterDuff.Mode.CLEAR);


        dateBox.setTag(this);
        monthBox.setTag(this);
        yearBox.setTag(this);
        ageBox.setTag(this);

        dateBox.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ViewGroup pnl = findViewById(R.id.dob_control_holder);
                if(b){
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_focused));

                }
                else{
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                }

            }
        });
        monthBox.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ViewGroup pnl = findViewById(R.id.dob_control_holder);
                if(b){
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_focused));

                }
                else{
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                }

            }
        });
        yearBox.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ViewGroup pnl = findViewById(R.id.dob_control_holder);
                if(b){
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_focused));

                }
                else{
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                }

            }
        });
        ageBox.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ViewGroup pnl = findViewById(R.id.dob_control_holder);
                if(b){
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_focused));

                }
                else{
                    pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                }

            }
        });

        ageBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String age = editable.toString();
                if(age.isEmpty()==false){
                    int ageInt = Integer.parseInt(age)*-1;
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.YEAR,ageInt);
                    String dob = now.get(Calendar.DATE)+"/"+(now.get(Calendar.MONTH) + 1) + "/"+ now.get(Calendar.YEAR);
                    ((DynamicView)ageBox.getTag()).setValue(dob);
                }
            }
        });


    }

    public String getValue(){
        String dob="";
        dob =dateBox.getText().toString()+"/"+monthBox.getText().toString()+"/"+yearBox.getText().toString();

        return dob;
    }

    @Override
    public void setValue(String value) {
        String[] dob = value.split("/");
        if(dob.length==3){
            dateBox.setText(dob[0]);
            monthBox.setText(dob[1]);
            yearBox.setText(dob[2]);
        }

    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public boolean validateEntry() {
        ViewGroup pnl = findViewById(R.id.dob_control_holder);

        if(getValue().length()>3){
            pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
            return true;
        }

        else{
            pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_error));
            return false;
        }
    }

    public void setOnFocusChangeListener( View.OnFocusChangeListener watcher){
        ageBox.setOnFocusChangeListener(watcher);
        dateBox.setOnFocusChangeListener(watcher);
        yearBox.setOnFocusChangeListener(watcher);
        monthBox.setOnFocusChangeListener(watcher);


    }

}
