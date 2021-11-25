package io.mosip.registration.app;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
import io.mosip.registration.app.ui.dynamic.MainViewPagerAdapter;

public class DemographicRegistrationController extends Fragment {

    final int layoutId =R.layout.demographic_registration_controller;
    View theView =null;
    ViewGroup pnlPrimary = null;
    ViewPager pnlSecondary = null;
    //Context context=null;
//    public DemographicRegistrationController(Context context)
//    {
//       // super(context);
//       // this.context=context;
//        init();
//    }

    public DemographicRegistrationController()
    {
       //init();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        theView= inflater.inflate(R.layout.demographic_registration_controller, container, false);
        init();
        return theView;
    }

    private void init(){
        //inflate(context, layoutId, this);

        pnlPrimary = theView.findViewById(R.id.pnlPrimaryLanguagePanel);
        //TabLayout tabLayout
        pnlSecondary = theView.findViewById(R.id.pnlSecondaryLanguagePanel);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter();
        pnlSecondary.setAdapter(mainViewPagerAdapter);
        loadUI();
    }
    public Context getApplicationContext(){return theView.getContext();}


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


    public void nextClick(View view){
        String allData=getDemographicDataJSON();

        System.out.println("The Data is");
        System.out.println(allData);
        System.out.println("OVER");
    }

    private List<DynamicComponent> loadedFields = new ArrayList<>();
    private Map<String,List<JSONObject>> groupedFields = new ArrayMap<>();
    private void loadUI() {


        pnlPrimary.removeAllViews();
        pnlSecondary.removeAllViews();
        DynamicComponentFactory factory = new DynamicComponentFactory(getApplicationContext());
        JSONObject languages=null;

        String components = loadJSONFromResource(R.raw.new_registration_process);
        JSONObject compFromJson = null;
        List<String> groupsOrder= new ArrayList<>();
        try {
            compFromJson = new JSONObject(components);
            JSONArray screens = compFromJson.getJSONArray("screens");
            for (int i = 0; i < screens.length(); i++) {
                JSONObject item = screens.getJSONObject(i);
                if (item.get("name").toString().equalsIgnoreCase("DemographicDetails") ){//pvt|| item.get("fieldCategory").toString().equalsIgnoreCase("kyc")) {
                    JSONArray fields = item.getJSONArray("fields");
                    languages=item.getJSONObject("label");
                    for(int fieldIndex=0;fieldIndex<fields.length();fieldIndex++) {
                        JSONObject field = fields.getJSONObject(fieldIndex);

                        String groupName = field.getString("alignmentGroup");
                        if (groupName == null) {
                            groupName = "null";
                        }
                        if (groupedFields.containsKey(groupName)) {
                            groupedFields.get(groupName).add(field);
                        } else {
                            List<JSONObject> grpFields= new ArrayList<>();
                            grpFields.add(field);
                            groupedFields.put(groupName,grpFields);
                            groupsOrder.add(groupName);
                        }
                    }
                }


            }

            List<LinearLayout> secondaryLanguages=new ArrayList<>();
            int langCount=0;
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout pnlPrmLangSelection =theView.findViewById(R.id.pnlPrimaryLanguageSelection);
            LinearLayout pnlSecLangSelection =theView.findViewById(R.id.pnlSecondaryLanguageSelection);

            for (Iterator<String> it = languages.keys(); it.hasNext(); ) {
                String lang = it.next();
                String disc= languages.getString(lang);
                langCount++;
                LinearLayout langLayout= new LinearLayout(getApplicationContext());
                langLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                parms.setMarginStart(8);
                parms.setMargins(8,8,0,0);
                langLayout.setPadding(16,0,16,0);
                langLayout.setLayoutParams(parms);
                if(langCount==1){//Is Primary Language (the first from the list)
                    Button langButton = new Button(getApplicationContext());

                    langButton.setText(lang);
                    langButton.setTextSize(10);
                    langButton.setLayoutParams(btnParams);
                    pnlPrmLangSelection.addView(langButton);
                }
                else{//the rest are secondary languages


                    Button langButton = new Button(getApplicationContext());
                    langButton.setText(lang);
                    langButton.setTextSize(10);
                    langButton.setWidth(50);
                    langButton.setTag(langCount-2);

                    langButton.setLayoutParams(btnParams);
                    pnlSecLangSelection.addView(langButton);
                    secondaryLanguages.add(langLayout);

                    langButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ViewPager pager = theView.findViewById(R.id.pnlSecondaryLanguagePanel);
                           pager.setCurrentItem(Integer.parseInt(langButton.getTag().toString()),true);

                        }
                    });


                }

            }
            //pnlSecondary.removeViewAt(0);

            for(String key:groupsOrder) {
                for (JSONObject field : groupedFields.get(key)) {
                    String fieldName = field.getString("id");
                    String controlType = field.getString("controlType");
                    boolean isRequired = field.getBoolean("inputRequired");
                    DynamicComponent component =null;
                    if (controlType.equalsIgnoreCase("textbox")) {
                         component = factory.getTextComponent(fieldName,field.getJSONObject("label"), field.getJSONArray("validators"));
                    } else if (controlType.equalsIgnoreCase("ageDate")) {
                         component = factory.getAgeDateComponent(fieldName,field.getJSONObject("label"), field.getJSONArray("validators"));
                    } else if (controlType.contains("button")) {
                         component = factory.getSwitchComponent(fieldName,field.getJSONObject("label"), field.getJSONArray("validators"));
                    } else if (controlType.equalsIgnoreCase("dropdown")) {
                         component = factory.getDropdownComponent(fieldName,field.getJSONObject("label"), field.getJSONArray("validators"));
                    }

                    if(component!=null) {
                        loadedFields.add(component);
                        pnlPrimary.addView((View) component.getPrimaryView());
                        for (int i = 0; i < component.getViewCount()-1; i++) {
                            secondaryLanguages.get(i).addView((View) component.getView(i+1));
                        }
                    }
                }
            }

            //Populate secondary languages ui
            for(int i=0;i<secondaryLanguages.size();i++){
                ((MainViewPagerAdapter)pnlSecondary.getAdapter()).addView(secondaryLanguages.get(i));
                pnlSecondary.getAdapter().notifyDataSetChanged();
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}