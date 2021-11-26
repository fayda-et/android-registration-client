package io.mosip.registration.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.mosip.registration.app.ui.dynamic.DynamicComponent;
import io.mosip.registration.app.ui.home.Dashboard;

public class NewRegistrationController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ViewPager pnlMainScreen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_registration_controller);
        loadUI();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.include);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().hide();
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

        if(currentScreenIndex<loadedScreens.size()-1) {
            currentScreenIndex++;

            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out
                    )
                    .hide(loadedScreens.get(currentScreenIndex-1))
                    .show(loadedScreens.get(currentScreenIndex))
                    .commit();
//            TextView vw = findViewById(R.id.toolbar_title);
//            vw.setText(screenTitles.get(currentScreenIndex));


            Toolbar vw = findViewById(R.id.include);
            vw.setTitle(screenTitles.get(currentScreenIndex));
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
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .hide(loadedScreens.get(currentScreenIndex+1))
            .show(loadedScreens.get(currentScreenIndex))
                    .commit();
            Toolbar vw = findViewById(R.id.include);
            vw.setTitle(screenTitles.get(currentScreenIndex));
        }else{


            finish();
        }
    }

    private int currentScreenIndex=0;

    private List<DynamicComponent> loadedFields = new ArrayList<>();
    private Map<String,List<JSONObject>> groupedFields = new ArrayMap<>();
    private List<String> screenTitles = new ArrayList<>();
    private List<Fragment> loadedScreens = new ArrayList<>();
    FragmentTransaction fragmentTransaction =null;
    private void loadUI() {

        BiometricRegistrationController biom=new BiometricRegistrationController();
        DemographicRegistrationController demo=new DemographicRegistrationController();
        DocumentController documentController = new DocumentController();
        loadedScreens.add(demo);
        screenTitles.add("Demographic Data");
        loadedScreens.add(biom);
        screenTitles.add("Biometric Data");
        loadedScreens.add(documentController);
        screenTitles.add("Proof Documents");


         fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setReorderingAllowed(true)
                .add(R.id.fragment_container_view, demo, null)
                .add(R.id.fragment_container_view, biom, null)
                .add(R.id.fragment_container_view,documentController,null)
                        .commit();
        fragmentTransaction.hide(biom);
        fragmentTransaction.hide(documentController);

      //  TextView vw = findViewById(R.id.toolbar_title);
        Toolbar vw = findViewById(R.id.include);
        vw.setTitle(screenTitles.get(0));


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }




}