package com.strastar.android.bsmin.BlogMap.activity;

import java.util.ArrayList;

import android.app.*;
import android.content.*;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.net.*;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.maps.*;
import com.strastar.android.bsmin.BlogMap.R;

public class ItemizedOverlay_normal extends ItemizedOverlay<OverlayItem2>{
private Context mContext;
private ArrayList<OverlayItem2> mOverlays = new ArrayList<OverlayItem2>();
 
 public ItemizedOverlay_normal(Drawable defaultMarker, Context context) {
	 		super(boundCenterBottom(defaultMarker));
	 		// TODO Auto-generated constructor stub
	 			mContext = context;
 }

 @Override
 protected OverlayItem2 createItem(int i) {
	 // TODO Auto-generated method stub
  return mOverlays.get(i); 
 }

 @Override
 public int size() {
	 // TODO Auto-generated method stub
  return  mOverlays.size();
 }
 
 public void addOverlay(OverlayItem2 overlay) { 
     mOverlays.add(overlay); 
     populate(); 
 }
 
 public void addOverlays(ArrayList<OverlayItem2> overlayList) { 
	 if(overlayList.size()>0){
		 for(int i=0;i<overlayList.size();i++){
			 mOverlays.add(overlayList.get(i));  			 
		 }
	}
    populate(); 
 }
 
 
 
 protected boolean onTap(int index) { 
		final OverlayItem2 item = mOverlays.get(index); 
			
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView( R.layout.info );
	   
		//aa = true;

		dialog.setTitle(item.getTitle());
		TextView cartext = (TextView)dialog.findViewById(R.id.lat);
		TextView colorwatertext = (TextView)dialog.findViewById(R.id.lagi);
		TextView watertext = (TextView)dialog.findViewById(R.id.title);
	

		TextView addresstext = (TextView)dialog.findViewById(R.id.dialogaddress);
		addresstext.setText("   "+item.getLat()+item.getLng()+item.getTitle());
		
	
		dialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == 4) {
					dialog.cancel();
					return true;
				}
				return false;
			}
		});
		dialog.show();
		return true;
	}

 
 
 
 
 
 public void draw(Canvas canvas, MapView mapView, boolean shadow){
     super.draw(canvas, mapView, false); }


}

 class OverlayItem2 extends OverlayItem{

	//String Title;
	String lat;  		   
	String lng;  		 
	String streetAddress;
	String url;			 
	String title;		 
	
	OverlayItem2(GeoPoint point, String title, String snippet) {
			super(point, title, snippet);
			// TODO Auto-generated constructor stub
	}
	
	public String getTitle() {
			return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	


}