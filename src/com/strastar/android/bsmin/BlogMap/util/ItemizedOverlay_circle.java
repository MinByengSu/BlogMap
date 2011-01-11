package com.strastar.android.bsmin.BlogMap.util;

import java.util.ArrayList;

import android.graphics.*;
import android.graphics.Paint.*;
import android.graphics.drawable.Drawable;
import com.google.android.maps.*;

public class ItemizedOverlay_circle extends ItemizedOverlay<OverlayItem>{

private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
 float distences;
 public ItemizedOverlay_circle(Drawable defaultMarker,float distence) {
  super(boundCenter(defaultMarker));
  // TODO Auto-generated constructor stub
  distences = distence;
 }

 @Override
 protected OverlayItem createItem(int i) {
  // TODO Auto-generated method stub
  return mOverlays.get(i); 
 }

 @Override
 public int size() {
  // TODO Auto-generated method stub
  return  mOverlays.size();
 }
 
 public void addOverlay(OverlayItem overlay) { 
     mOverlays.add(overlay); 
     populate(); 
 }
 
 public void draw(Canvas canvas, MapView mapView, boolean shadow){
     super.draw(canvas, mapView, false); 
     Paint paint1 = new Paint();
     Paint paint2 = new Paint();
     
  	 paint1.setARGB(20, 0, 191, 255);
  	 paint2.setARGB(200,0,191,255);
  	 paint2.setStyle(Style.STROKE);
  	 paint2.setStrokeWidth(2);

 	 Point pt = new Point(); 

 	 float dist =mapView.getProjection().metersToEquatorPixels(distences);
 	 
   	 mapView.getProjection().toPixels(this.mOverlays.get(0).getPoint(), pt); 
 	 canvas.drawCircle(pt.x, pt.y, dist, paint1);
 	 canvas.drawCircle(pt.x, pt.y, dist, paint2);
 	}
}

