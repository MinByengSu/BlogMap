package com.strastar.android.bsmin.BlogMap.activity;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.strastar.android.bsmin.BlogMap.R;

public class MyMapActivity extends MapActivity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}