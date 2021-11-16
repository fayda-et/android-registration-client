package io.mosip.registration.app.ui.dynamic;

import android.view.View;

public interface DynamicView {
     String getValue();
     void setValue(String value);
     String getLanguageCode();
     boolean validateEntry();

     void setOnFocusChangeListener( View.OnFocusChangeListener watcher);

}
