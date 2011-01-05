package com.strastar.android.bsmin.BlogMap.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.strastar.android.bsmin.BlogMap.R;
import com.strastar.android.bsmin.BlogMap.provider.SearchHistoryProvider;

public class MyMapActivity extends MapActivity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap);
        handleIntent(getIntent());
    }
    
    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	public boolean onSearchRequested() {
	    // pause some stuff here
		return super.onSearchRequested();
	}
	
	private void handleIntent(Intent intent) {
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
	    		  SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE);
	      suggestions.saveRecentQuery(query, null);
	      search(query);
	    }
	}
	
	private void search(String query) {
		Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
	}
}
