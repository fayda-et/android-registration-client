package io.mosip.registration.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.ImageViewCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BiometricRegistrationController extends AppCompatActivity {
private int currentModalityIndex=1;
private String modalityList[]={"Iris","Left Four Fingers","Right Four Fingers","Two Thumb Fingers","Face","Exception Photo"};
    ViewGroup modalityPanel=null;
    TextView currentModalityLable=null;
    int maxModality=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biometric_registration_controller);
        modalityPanel = findViewById(R.id.pnlCurrentModalityViewArea);
        currentModalityLable =findViewById(R.id.lblCurrentModality);
        findViewById(R.id.btnException).setVisibility(View.GONE);
    }

    private void setCurrentModalityIris(){
        for(int i=1;i<5;i++){
            modalityPanel.getChildAt(i).setVisibility(View.GONE);
        }
        currentModalityIndex=1;
        modalityPanel.getChildAt(currentModalityIndex).setVisibility(View.VISIBLE);
        currentModalityLable.setText(modalityList[currentModalityIndex-1]);
    }

    public void setCurrentModalityLeftFourFinger(){
        for(int i=1;i<5;i++){
            modalityPanel.getChildAt(i).setVisibility(View.GONE);
        }
        currentModalityIndex=2;
        modalityPanel.getChildAt(currentModalityIndex).setVisibility(View.VISIBLE);
        currentModalityLable.setText(modalityList[currentModalityIndex-1]);
    }

    public void setCurrentModalityRightFourFinger(){
        for(int i=1;i<5;i++){
            modalityPanel.getChildAt(i).setVisibility(View.GONE);
        }
        currentModalityIndex=3;
        modalityPanel.getChildAt(currentModalityIndex).setVisibility(View.VISIBLE);
        currentModalityLable.setText(modalityList[currentModalityIndex-1]);
    }

    public void setCurrentModalityTwoThumbFingers(){
        for(int i=1;i<5;i++){
            modalityPanel.getChildAt(i).setVisibility(View.GONE);
        }
        currentModalityIndex=4;
        modalityPanel.getChildAt(currentModalityIndex).setVisibility(View.VISIBLE);
        currentModalityLable.setText(modalityList[currentModalityIndex-1]);
    }

    public void setCurrentModalityFace(){
        for(int i=1;i<maxModality;i++){
            modalityPanel.getChildAt(i).setVisibility(View.GONE);
        }
        currentModalityIndex=5;
        modalityPanel.getChildAt(currentModalityIndex).setVisibility(View.VISIBLE);
        currentModalityLable.setText(modalityList[currentModalityIndex-1]);
    }

    public void setCurrentModalityExceptionPhoto(){
        for(int i=1;i<maxModality;i++){
            modalityPanel.getChildAt(i).setVisibility(View.GONE);
        }
        currentModalityIndex=6;
        modalityPanel.getChildAt(currentModalityIndex).setVisibility(View.VISIBLE);
        currentModalityLable.setText(modalityList[currentModalityIndex-1]);
    }

    private void showModalityByIndex(){
        switch (currentModalityIndex){
            case 1: setCurrentModalityIris(); break;
            case 2: setCurrentModalityLeftFourFinger(); break;
            case 3: setCurrentModalityRightFourFinger();break;
            case 4: setCurrentModalityTwoThumbFingers();break;
            case 5: setCurrentModalityFace();break;
            case 6: setCurrentModalityExceptionPhoto();break;
        }
    }

    public void nextButtonClick(View view){

        if(currentModalityIndex<maxModality){

            currentModalityIndex++;
            showModalityByIndex();
        }

    }

    public void prevButtonClick(View view){

        if(currentModalityIndex>1){
            currentModalityIndex--;
            showModalityByIndex();
        }

    }

    public void irisButtonClick(View view){
     setCurrentModalityIris();
    }
    public void fingerButtonClick(View view){
       setCurrentModalityLeftFourFinger();
    }
    public void facePhotoButtonClick(View view){
       setCurrentModalityFace();
    }
    public void exceptionPhotoButtonClick(View view){setCurrentModalityExceptionPhoto();
    }

    List<String> biometricExceptions=new ArrayList<>();

    public void irisExceptionImageClick(View view){
        if(view.getId()==R.id.irisLeft){
            if(biometricExceptions.contains(Constants.LEFT_IRIS_SUB_TYPE)){
                view.setBackground(getResources().getDrawable(R.drawable.iris));
                biometricExceptions.remove(Constants.LEFT_IRIS_SUB_TYPE);
            }else{
                view.setBackground(getResources().getDrawable(R.drawable.iris_exception));
                biometricExceptions.add(Constants.LEFT_IRIS_SUB_TYPE);
            }
        }
        else if(view.getId()==R.id.irisRight){
            if(biometricExceptions.contains(Constants.RIGHT_IRIS_SUB_TYPE)){
                view.setBackground(getResources().getDrawable(R.drawable.iris));
                biometricExceptions.remove(Constants.RIGHT_IRIS_SUB_TYPE);
            }else{
                view.setBackground(getResources().getDrawable(R.drawable.iris_exception));
                biometricExceptions.add(Constants.RIGHT_IRIS_SUB_TYPE);
            }
        }
        View excepButton= findViewById(R.id.btnException);
        if(biometricExceptions.size()==0){
            maxModality=5;
            excepButton.setVisibility(View.GONE);
        }
        else{
            maxModality=6;
            excepButton.setVisibility(View.VISIBLE);
        }


    }

    public void fingerExceptionClick(View view){
       ImageView imageView= ((ImageView)view);
        view.setAlpha(0.1F);
        int id =view.getId();
        String finger="";
        switch (id){
            case R.id.leftLittleFinger:finger=Constants.LEFT_LITTLE_FINGER;break;
            case R.id.leftRingFinger:finger=Constants.LEFT_RING_FINGER;break;
            case R.id.leftMiddleFinger:finger=Constants.LEFT_MIDDLE_FINGER;break;
            case R.id.leftIndexFinger:finger=Constants.LEFT_INDEX_FINGER;break;

            case R.id.rightLittleFinger:finger=Constants.RIGHT_LITTLE_FINGER;break;
            case R.id.rightRingFinger:finger=Constants.RIGHT_RING_FINGER;break;
            case R.id.rightMiddleFinger:finger=Constants.RIGHT_MIDDLE_FINGER;break;
            case R.id.rightIndexFinger:finger=Constants.RIGHT_INDEX_FINGER;break;
        }
        if(biometricExceptions.contains(finger)){
            biometricExceptions.remove(finger);
            imageView.setAlpha(1F);
        }
        else{
            biometricExceptions.add(finger);
            imageView.setAlpha(.25F);
        }

    }
}