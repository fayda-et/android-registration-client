package io.mosip.registration.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.mosip.registration.app.ui.dynamic.DynamicComponent;
import io.mosip.registration.app.ui.dynamic.DynamicComponentFactory;

public class DocumentController extends Fragment {

    ViewGroup leftPanel = null;
    View theView =null;

    public DocumentController()
    {
        super(R.layout.activity_document_controller);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        theView= inflater.inflate(R.layout.activity_document_controller, container, false);
        leftPanel = theView.findViewById(R.id.pnlLeftPanel);
        loadUI();
        setEventListener();
        return theView;
    }

    private void setEventListener(){
        theView.findViewById(R.id.btnScanDocument).setOnClickListener(v -> openCamera(v));
        theView.findViewById(R.id.btnAttachDocument).setOnClickListener(v -> openGallery(v));

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


    private List<DynamicComponent> loadedFields = new ArrayList<>();
    private void loadUI() {



     /*   pnlPrimary.removeAllViews();
        pnlSecondary.removeAllViews();*/

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
                if (item.get("name").toString().equalsIgnoreCase("Documents") ){//pvt|| item.get("fieldCategory").toString().equalsIgnoreCase("kyc")) {
                    JSONArray fields = item.getJSONArray("fields");

                    for(int fieldIndex=0;fieldIndex<fields.length();fieldIndex++) {
                        JSONObject field = fields.getJSONObject(fieldIndex);
                        DynamicComponent component =null;
                        String fieldName = field.getString("id");
                        component = factory.getDropdownComponent(fieldName,field.getJSONObject("label"), null);
                        if(component!=null) {
                            leftPanel.addView((View) component.getView(2));
                        }

                    }
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openCamera(View v){
        int REQUEST_CODE = 99;
        int preference = ScanConstants.OPEN_CAMERA;
        Intent intent = new Intent(this.getApplicationContext(), ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void  openGallery(View v){
        int REQUEST_CODE = 99;
        int preference = ScanConstants.OPEN_MEDIA;
        Intent intent = new Intent(this.getApplicationContext(), ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getApplicationContext().getContentResolver(), uri);
                System.out.println(uri);
                this.getApplicationContext().getContentResolver().delete(uri, null, null);
              //scannedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}