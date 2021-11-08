package io.mosip.registration.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.mosip.registration.app.ui.dynamic.DynamicComponent;
import io.mosip.registration.app.ui.dynamic.DynamicComponentFactory;

public class DemographicRegistrationController extends AppCompatActivity {


    ViewGroup pnlPrimary = null;
    ViewGroup pnlSecondary = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demographic_registration_controller);

        pnlPrimary = findViewById(R.id.pnlPrimaryLanguagePanel);
        pnlSecondary = findViewById(R.id.pnlSecondaryLanguagePanel);
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




    private List<DynamicComponent> loadedFields = new ArrayList<>();
    private Map<String,List<JSONObject>> groupedFields = new ArrayMap<>();
    private void loadUI() {



        pnlPrimary.removeAllViews();
        pnlSecondary.removeAllViews();
        DynamicComponentFactory factory = new DynamicComponentFactory(getApplicationContext());


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

            for(String key:groupsOrder) {
                for (JSONObject field : groupedFields.get(key)) {
                    String fieldName = field.getString("id");
                    String controlType = field.getString("controlType");
                    boolean isRequired = field.getBoolean("inputRequired");
                    if (controlType.equalsIgnoreCase("textbox")) {
                        DynamicComponent component = factory.getTextComponent(field.getJSONObject("label"), field.getJSONArray("validators"));
                        pnlPrimary.addView((View) component.getPrimaryView());
                        pnlSecondary.addView((View) component.getSecondaryView());
                    } else if (controlType.equalsIgnoreCase("ageDate")) {
                        DynamicComponent component = factory.getAgeDateComponent(field.getJSONObject("label"), field.getJSONArray("validators"));
                        pnlPrimary.addView((View) component.getPrimaryView());
                        pnlSecondary.addView((View) component.getSecondaryView());
                    } else if (controlType.contains("button")) {
                        DynamicComponent component = factory.getSwitchComponent(field.getJSONObject("label"), field.getJSONArray("validators"));
                        pnlPrimary.addView((View) component.getPrimaryView());
                        pnlSecondary.addView((View) component.getSecondaryView());
                    } else if (controlType.equalsIgnoreCase("dropdown")) {
                        DynamicComponent component = factory.getDropdownComponent(field.getJSONObject("label"), field.getJSONArray("validators"));
                        pnlPrimary.addView((View) component.getPrimaryView());
                        pnlSecondary.addView((View) component.getSecondaryView());
                    }
                }
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}