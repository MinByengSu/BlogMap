package com.strastar.android.bsmin.BlogMap.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.strastar.android.bsmin.BlogMap.R;

public class MyMapActivity extends MapActivity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap);
        
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        	String query = intent.getStringExtra(SearchManager.QUERY);
        	search(query);
        }
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private void search(String query) {
		Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
	}
}
