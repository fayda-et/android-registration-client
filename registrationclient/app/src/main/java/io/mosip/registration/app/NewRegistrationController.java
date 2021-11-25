package io.mosip.registration.app;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import io.mosip.registration.app.ui.dynamic.DynamicComponent;
import io.mosip.registration.app.ui.dynamic.MainViewPagerAdapter;
import io.mosip.registration.app.ui.dynamic.views.MainFragmentPagerAdapter;

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
//        String allData=getDemographicDataJSON();
//
//        System.out.println("The Data is");
//        System.out.println(allData);
//        System.out.println("OVER");
        int index = pnlMainScreen.getCurrentItem();
        index=index+1;
        if(((MainViewPagerAdapter)pnlMainScreen.getAdapter()).getCount()>index) {
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

        List<Fragment> fragments = new Vector<Fragment>();

//for each fragment you want to add to the pager
        Bundle page = new Bundle();
        page.putString("url", "url");

        fragments.add(Fragment.instantiate(this,DemographicRegistrationController.class.getName(),page));
        fragments.add(Fragment.instantiate(this,BiometricRegistrationController.class.getName(),page));

//after adding all the fragments write the below lines

        MainFragmentPagerAdapter mPagerAdapter  = new MainFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);

        pnlMainScreen = findViewById(R.id.view_pager);

        pnlMainScreen.setAdapter(mPagerAdapter);

    }








}