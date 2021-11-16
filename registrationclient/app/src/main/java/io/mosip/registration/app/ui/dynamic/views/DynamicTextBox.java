package io.mosip.registration.app.ui.dynamic.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import io.mosip.registration.app.R;
import io.mosip.registration.app.ui.dynamic.DynamicView;

public class DynamicTextBox extends LinearLayout implements DynamicView {
    EditText editText;
    String languageCode="";
    String labelText="";
    String validationRule="";
    final int layoutId=R.layout.dynamic_text_box;

    public DynamicTextBox(Context context,String langCode,String label,String validation) {
        super(context);

        languageCode=langCode;
        labelText=label;
        validationRule=validation;

        init(context);
    }



    public DynamicTextBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicTextBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DynamicTextBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        inflate(context,layoutId , this);
        initComponents();
   }

    private void initComponents() {
        editText = findViewById(R.id.txt_input_box);
        editText.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ViewGroup pnl = findViewById(R.id.control_holder);
                    if(b){
                        pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_focused));

                    }
                    else{
                        pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));

                    }

            }
        });
        //editText.setHint(labelText);
        ((TextView) findViewById(R.id.textbox_label)).setText(labelText);

        editText.setTag(this);
    }

    public String getValue(){
        return editText.getText().toString();
    }

    @Override
    public void setValue(String value) {
        editText.setText(value);
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public boolean validateEntry() {
        ViewGroup pnl = findViewById(R.id.control_holder);

        if(getValue().length()>0){
            pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
            return true;
        }

        else{
            pnl.setBackground(getResources().getDrawable(R.drawable.rounded_corner_error));
            return false;
        }
    }

    public EditText getEditText() {
        return editText;
    }

    public void setOnFocusChangeListener( View.OnFocusChangeListener watcher){
        editText.setOnFocusChangeListener(watcher);

    }
}
