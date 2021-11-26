package io.mosip.registration.app.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

import io.mosip.registration.app.NewRegistrationController;
import io.mosip.registration.app.R;
import io.mosip.registration.app.ui.login.LoginActivity;

public class RegistrationPopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_pop_up);
    //    setEventListener();
    }

//    private void setEventListener(){
//        MaterialCardView card =  findViewById(R.id.cardNewRegistration);
//      card.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              Intent intent = new Intent(RegistrationPopUp.this, NewRegistrationController.class);
//              startActivity(intent);
//          }
//      });
//
//
//    }
}