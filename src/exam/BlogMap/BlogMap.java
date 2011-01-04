package exam.BlogMap;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.os.Bundle;

public class BlogMap extends MapActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      
    }
    
    protected boolean isRouteDisplayed(){
    	return false;
    }
    
    
    
}