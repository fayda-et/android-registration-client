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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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

        DemographicRegistrationController domo = new DemographicRegistrationController();


        ((MainPagerAdapter)pnlMainScreen.getAdapter()).addView(domo);
        //((MainPagerAdapter)pnlMainScreen.getAdapter()).addView(biom);
        pnlMainScreen.getAdapter().notifyDataSetChanged();
        pnlMainScreen.setCurrentItem(0);
    }



    // ...
    private SmartFragmentStatePagerAdapter adapterViewPager;

    // Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FirstFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return SecondFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }






}