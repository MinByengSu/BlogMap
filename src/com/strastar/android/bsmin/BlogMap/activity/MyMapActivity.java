package com.strastar.android.bsmin.BlogMap.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.strastar.android.bsmin.BlogMap.R;
import com.strastar.android.bsmin.BlogMap.overlay.ItemizedOverlayCircle;
import com.strastar.android.bsmin.BlogMap.overlay.ItemizedOverlayNormal;
import com.strastar.android.bsmin.BlogMap.provider.SearchHistoryProvider;

public class MyMapActivity extends MapActivity {
	
	private final static String QUERY_STRING = "http://ajax.googleapis.com/ajax/services/search/local?v=1.0&hl=ko&rsz=large&q=%s&sll=%f,%f"; // %s %f %f - 3 args

	List<Overlay> mapOverlays;
	ItemizedOverlayCircle itemizedOverlay;
	ItemizedOverlayNormal itemizedOverlay2;
	ArrayList<OverlayItem> agentOverlayList = new ArrayList<OverlayItem>();
	Drawable drawable;
	Drawable drawable2;
	OverlayItem overlayitem;
	MapView mapView; 
	ZoomControls mZoom;
	double Longitude = 0f;
	double Latitude = 0f;
	double longi;
	double lati;

	float distence=2000;
	String text;
	Writer writer;
	int item_number;

	String latitude [];
	String longitude [];
	String title [];
	String streetAddress [];

	GeoPoint agentPoint;
	OverlayItem agentOverlay;

	private void updateLocation() {
		LocationManager lm =(LocationManager)getSystemService(Context.LOCATION_SERVICE);   
		String bestProvider = lm.getBestProvider(new Criteria(), true);
		Location loc = lm.getLastKnownLocation("gps");
		DispLocListener locListenD = new DispLocListener();
		if(loc != null) {
			Latitude = loc.getLatitude();
			Longitude= loc.getLongitude();
			lm.requestLocationUpdates("gps", 1000, 10.0f, locListenD);
		} else {
			loc = lm.getLastKnownLocation(bestProvider);
			if (loc != null) {
				Latitude = loc.getLatitude();
				lati = Latitude;
				Longitude= loc.getLongitude();
				longi = Longitude;
				lm.requestLocationUpdates(bestProvider, 1000, 10.0f, locListenD);
			} else {
				Toast.makeText(MyMapActivity.this,"위치정보를 확인할 수 없습니다." ,Toast.LENGTH_SHORT).show();
			}
		}
		
		setMaps(1000);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymap);
		handleIntent(getIntent());
		updateLocation();
	}

	@Override
	public void onNewIntent(Intent intent) {
		mapOverlays.clear();
		agentOverlayList.clear();
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			mapOverlays.clear();
			agentOverlayList.clear();
			
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
					SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE);
			suggestions.saveRecentQuery(query, null);
			search(query);
			setMaps(1000);
		} else if (Intent.ACTION_MAIN.equals(intent.getAction())) {
			
		} else {
			finish();
			return;
		}
	}

	private void search(String query) {
		InputStream is = null;
		try {
			URL text = new URL(String.format(QUERY_STRING, java.net.URLEncoder.encode(query), lati, longi));
			is = text.openStream();
			Writer writer = new StringWriter();  
			char[] buffer = new char[1024];  
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			int n;  
			while ((n = reader.read(buffer)) != -1) {  
				writer.write(buffer, 0, n);  
			}
			
			jsonParse(writer.toString());
		} catch (Exception e) {
			Toast.makeText(this, "빠져나왔다", Toast.LENGTH_SHORT).show();
		}finally {  
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

	}

	public void jsonParse(String strJson){
		float []tmp_distence = new float[1];
		try {
			JSONObject jsonObject =  new JSONObject(strJson);
			JSONObject responseData = jsonObject.getJSONObject("responseData");
			JSONArray results = responseData.getJSONArray("results");//new JSONArray(jsonObject.getString("results")); 

			latitude = new String[results.length()]; 
			longitude = new String[results.length()]; 
			title  = new String[results.length()];

			item_number = results.length();

			for(int i = 0; i < results.length(); i++){ 
				latitude [i] = results.getJSONObject(i).getString("lat").toString();
				longitude [i] = results.getJSONObject(i).getString("lng").toString();
				title [i] = results.getJSONObject(i).getString("title").toString();

				agentPoint = new GeoPoint((int)(Double.parseDouble(latitude[i])*1E6),(int)(Double.parseDouble(longitude[i])*1E6));
				Location.distanceBetween(Latitude, Longitude,Double.parseDouble(latitude[i]), Double.parseDouble(longitude [i]), tmp_distence);
				agentOverlayList.add(new OverlayItem(agentPoint, title[i], ""));
				itemizedOverlay2.addOverlays(agentOverlayList);
			} 
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}

	private class DispLocListener implements LocationListener {
		public void onLocationChanged(Location location) {
			Latitude = location.getLatitude();
			Longitude= location.getLongitude();

		}
		public void onProviderDisabled(String provider) { 
		}
		public void onProviderEnabled(String provider) {
		}
		public void onStatusChanged(String provider, int status, Bundle extras) { 
		}
	}

	public void setMaps(float distences){

		MapView mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);  // 줌컨트롤을 활성
		mapOverlays = mapView.getOverlays(); 
		if (mapOverlays.size() > 0) 
			mapOverlays.clear(); 
		GeoPoint p=new GeoPoint((int)(Latitude*1E6),(int)(Longitude*1E6));

		drawable = this.getResources().getDrawable(R.drawable.mymarker);
		drawable2 = this.getResources().getDrawable(R.drawable.navi_icon1);

		itemizedOverlay = new ItemizedOverlayCircle(drawable,distence);
		itemizedOverlay2 = new ItemizedOverlayNormal(drawable2,this);


		overlayitem = new OverlayItem(p,"","");
		MapController mc=mapView.getController();
		itemizedOverlay2.addOverlays(agentOverlayList);
		mapOverlays.add(itemizedOverlay2);
		mc.setZoom(15); 
		if(Latitude!=0f&&Longitude!=0f){
			mc.animateTo(p);
			itemizedOverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedOverlay);
			mc.setZoom(15); 
		}else {
			mc.setZoom(15);
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}