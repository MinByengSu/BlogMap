package com.strastar.android.bsmin.BlogMap.provider;

import android.content.SearchRecentSuggestionsProvider;

public class SearchHistoryProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "com.strastar.provider.BlogMap";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchHistoryProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }	
}