package io.mosip.registration.app;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.mosip.registration.app.ui.dynamic.DynamicComponent;
import io.mosip.registration.app.ui.dynamic.DynamicComponentFactory;
import io.mosip.registration.app.ui.dynamic.MainPagerAdapter;

public class NewRegistrationController extends AppCompatActivity {



    ViewPager pnlMainScreen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_registration_controller);

        pnlMainScreen = findViewById(R.id.pnlMainScreenPanel);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter();
        pnlMainScreen.setAdapter(mainPagerAdapter);
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
//        String allData=getDemographicDataJSON();
//
//        System.out.println("The Data is");
//        System.out.println(allData);
//        System.out.println("OVER");
        int index = pnlMainScreen.getCurrentItem();
        index=index+1;
        if(((MainPagerAdapter)pnlMainScreen.getAdapter()).getCount()>index) {
            pnlMainScreen.getAdapter().notifyDataSetChanged();
            pnlMainScreen.setCurrentItem(index);
        }
    }

    public void prevScreenClick(View view){
//        String allData=getDemographicDataJSON();
//
//        System.out.println("The Data is");
//        System.out.println(allData);
//        System.out.println("OVER");

        int index = pnlMainScreen.getCurrentItem();
        index=index-1;
        if(index>=0) {
            pnlMainScreen.getAdapter().notifyDataSetChanged();
            pnlMainScreen.setCurrentItem(index,true);
        }
    }

    private List<DynamicComponent> loadedFields = new ArrayList<>();
    private Map<String,List<JSONObject>> groupedFields = new ArrayMap<>();
    private void loadUI() {

        DemographicRegistrationController domo = new DemographicRegistrationController(getApplicationContext());
        DemographicRegistrationController domo2 = new DemographicRegistrationController(getApplicationContext());

        ((MainPagerAdapter)pnlMainScreen.getAdapter()).addView(domo);
        ((MainPagerAdapter)pnlMainScreen.getAdapter()).addView(domo2);
        pnlMainScreen.getAdapter().notifyDataSetChanged();
        pnlMainScreen.setCurrentItem(0);
    }


}