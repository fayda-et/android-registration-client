package io.mosip.registration.app;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.mosip.registration.app.ui.dynamic.DynamicComponent;

public class NewRegistrationController extends AppCompatActivity {



    ViewPager pnlMainScreen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_registration_controller);
        loadUI();
    }



    public String loadJSONFromResource(int resourceID) {
        String json = null;
        try {
            InputStream is = getApplicationContext().getResources().openRawResource(resourceID);//getActivity().getAssets().open("yourfilename.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public String getDemographicDataJSON(){
        String jsonData="{";
        for(DynamicComponent comp:loadedFields){
            jsonData=jsonData + comp.getValueJSON() + ",";
        }

        return jsonData.substring(0,jsonData.lastIndexOf(","))+"}";
    }


    public void nextScreenClick(View view){

//        int index = pnlMainScreen.getCurrentItem();
//        index=index+1;
//        if(((MainViewPagerAdapter)pnlMainScreen.getAdapter()).getCount()>index) {
//            pnlMainScreen.getAdapter().notifyDataSetChanged();
//            pnlMainScreen.setCurrentItem(index);
//        }

        if(currentScreenIndex<1) {
            currentScreenIndex++;

            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(loadedScreens.get(currentScreenIndex-1))
                    .show(loadedScreens.get(currentScreenIndex))
                    .commit();
        }
    }

    public void prevScreenClick(View view){


//        int index = pnlMainScreen.getCurrentItem();
//        index=index-1;
//        if(index>=0) {
//            pnlMainScreen.getAdapter().notifyDataSetChanged();
//            pnlMainScreen.setCurrentItem(index,true);
//        }



        if(currentScreenIndex>0) {
            currentScreenIndex--;
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .hide(loadedScreens.get(currentScreenIndex+1))
            .show(loadedScreens.get(currentScreenIndex))
                    .commit();
        }
    }

    private int currentScreenIndex=0;

    private List<DynamicComponent> loadedFields = new ArrayList<>();
    private Map<String,List<JSONObject>> groupedFields = new ArrayMap<>();

    private List<Fragment> loadedScreens = new ArrayList<>();
    FragmentTransaction fragmentTransaction =null;
    private void loadUI() {

        BiometricRegistrationController biom=new BiometricRegistrationController();
        DemographicRegistrationController demo=new DemographicRegistrationController();
        loadedScreens.add(demo);
        loadedScreens.add(biom);


         fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setReorderingAllowed(true)
                .add(R.id.fragment_container_view, demo, null);


                fragmentTransaction.add(R.id.fragment_container_view, biom, null)
                .setReorderingAllowed(true)
                        .commit();

        fragmentTransaction.hide(biom);

    }








}