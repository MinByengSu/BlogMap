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
import org.xmlpull.v1.XmlPullParser;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.widget.Toast;
import android.widget.ZoomControls;





import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.strastar.android.bsmin.BlogMap.R;
import com.strastar.android.bsmin.BlogMap.provider.SearchHistoryProvider;
import com.strastar.android.bsmin.BlogMap.util.*;

public class MyMapActivity extends MapActivity {
	
	 List<com.google.android.maps.Overlay> mapOverlays;
	 ItemizedOverlay_circle itemizedOverlay;
	 ItemizedOverlay_normal itemizedOverlay2;
	 ArrayList<OverlayItem2> agentOverlayList = new ArrayList<OverlayItem2>();
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
     private String bestProvider;
     Writer writer;
     int item_number;
     
     String latitude [];
     String longitude [];
     String title [];
     String streetAddress [];

	  GeoPoint agentPoint;
	  OverlayItem2 agentOverlay;
   
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap);
        handleIntent(getIntent());
        
        LocationManager lm =(LocationManager)getSystemService(Context.LOCATION_SERVICE);   
        bestProvider = lm.getBestProvider(new Criteria(), true);
        
        Location loc = lm.getLastKnownLocation("gps");
        Location loc2 = lm.getLastKnownLocation(bestProvider);
          
        DispLocListener locListenD = new DispLocListener();
        lm.requestLocationUpdates("gps", 1000, 10.0f, locListenD);
    
		if(loc!=null) {
        	Latitude = loc.getLatitude();
        	Longitude= loc.getLongitude();
        	lm.requestLocationUpdates("gps", 1000, 10.0f, locListenD);
        }
        else{
    
        	if(loc2!=null){
        		Latitude = loc2.getLatitude();
        		lati = Latitude;
        		Longitude= loc2.getLongitude();
        		longi = Longitude;
        		lm.requestLocationUpdates(bestProvider, 1000, 10.0f, locListenD);
        	}
        	else        		
       	   		Toast.makeText(MyMapActivity.this,"위치정보를 확인할 수 없습니다." ,Toast.LENGTH_SHORT).show();
        }
		
		
		SetMaps(1000);
        
    }
    
    @Override
    public void onNewIntent(Intent intent) {
    	mapOverlays.clear();
    	agentOverlayList.clear();
		SetMaps(1000);
        setIntent(intent);
        handleIntent(intent);
    }


	@Override
	public boolean onSearchRequested() {
	    // pause some stuff here
		return super.onSearchRequested();
	}
		
	private void handleIntent(Intent intent) {
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	mapOverlays.clear();
	    	agentOverlayList.clear();
			SetMaps(1000);	
	    	
	    	
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
	    		  SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE);
	      suggestions.saveRecentQuery(query, null);
	      search(query);
	      jsonParse();
	    }
	}
		
	private void search(String query) {
		
		InputStream isText = null;
		try {
			URL text = new URL("http://ajax.googleapis.com/ajax/services/search/local?v=1.0&" +
							   "hl=ko&rsz=large&q="+java.net.URLEncoder.encode(query)+"&sll="+lati+","+longi);
			isText = text.openStream();
			
			 writer = new StringWriter();  
			  
	        char[] buffer = new char[1024];  
	         
	            Reader reader = new BufferedReader(  
	                    new InputStreamReader(isText, "UTF-8"));  
	            int n;  
	            while ((n = reader.read(buffer)) != -1) {  
	                writer.write(buffer, 0, n);  
	            }  
	            jsonParse();
			     // Toast.makeText(this, writer.toString(), Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "빠져나왔다", Toast.LENGTH_SHORT).show();
		}finally {  
			try {
				isText.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
	
	public void jsonParse(){
		
		
		float []tmp_distence = new float[1];
		Log.v("jsonParse","0");
	 try {
		 	Log.v("jsonParse","1");
		 	JSONObject  jsonObject =  new JSONObject(writer.toString());
		 	Log.v("jsonParse","2");
		 	JSONObject responseData = jsonObject.getJSONObject("responseData");
		 	JSONArray results = responseData.getJSONArray("results");//new JSONArray(jsonObject.getString("results")); 
		 	
		 	Log.v("jsonParse","3");
		    latitude = new String[results.length()]; 
			Log.v("jsonParse","4");
			longitude = new String[results.length()]; 
            title  = new String[results.length()];
            Log.v("jsonParse","5");
           
            item_number = results.length();
            
            for(int i = 0; i < results.length(); i++){ 
            	Log.v("jsonParse","6");
            	latitude [i] = results.getJSONObject(i).getString("lat").toString();
                longitude [i] = results.getJSONObject(i).getString("lng").toString();
                title [i] = results.getJSONObject(i).getString("title").toString();
                
                agentPoint = new GeoPoint((int)(Double.parseDouble(latitude[i])*1E6),(int)(Double.parseDouble(longitude[i])*1E6));
				
                Location.distanceBetween(Latitude, Longitude,Double.parseDouble(latitude[i]), Double.parseDouble(longitude [i]), tmp_distence);

                
                agentOverlay = new OverlayItem2(agentPoint,title[i]," ");
                
				agentOverlay.setLat(latitude [i]);
				agentOverlay.setLng(latitude [i]);
				agentOverlay.setTitle(title[i]);
				
				agentOverlayList.add(agentOverlay);
				
				 
			     itemizedOverlay2.addOverlays(agentOverlayList);
                Log.v("jsonParse","7");
            } 
            Log.v("jsonParse","8");
           //Toast.makeText(this, "빠져나왔다"+title[0], Toast.LENGTH_SHORT).show();
            
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v("jsonParse","10");
			e.printStackTrace();
		} 

		Log.v("jsonParse","11");
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
    
	  public void SetMaps(float distences){
		 
    	MapView mapView = (MapView) findViewById(R.id.map_view);
	        mapView.setBuiltInZoomControls(true);  //줌컨트롤을 활성
	        mapOverlays = mapView.getOverlays(); 
	        if (mapOverlays.size() > 0) 
	        	mapOverlays.clear(); 
	        GeoPoint p=new GeoPoint((int)(Latitude*1E6),(int)(Longitude*1E6));
	        
	        drawable = this.getResources().getDrawable(R.drawable.mymarker);
	        drawable2 = this.getResources().getDrawable(R.drawable.navi_icon1);
	        
	        itemizedOverlay = new ItemizedOverlay_circle(drawable,distence);
	        itemizedOverlay2 = new ItemizedOverlay_normal(drawable2,this);
	        
	        
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
			// TODO Auto-generated method stub
			return false;
		}

}
