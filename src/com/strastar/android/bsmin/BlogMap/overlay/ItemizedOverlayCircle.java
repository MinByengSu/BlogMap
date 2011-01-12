package com.strastar.android.bsmin.BlogMap.overlay;

import java.util.ArrayList;

import android.graphics.*;
import android.graphics.Paint.*;
import android.graphics.drawable.Drawable;
import com.google.android.maps.*;

public class ItemizedOverlayCircle extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	float mDistance;
	
	public ItemizedOverlayCircle(Drawable defaultMarker,float distance) {
		super(boundCenter(defaultMarker));
		mDistance = distance;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i); 
	}

	@Override
	public int size() {
		return  mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) { 
		mOverlays.add(overlay); 
		populate(); 
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false); // 항상 그림자 없이? 
		Paint paint1 = new Paint();
		Paint paint2 = new Paint();

		paint1.setARGB(20, 0, 191, 255);
		paint2.setARGB(200,0,191,255); // 투명한 레이어
		paint2.setStyle(Style.STROKE); // border?
		paint2.setStrokeWidth(2);

		Point pt = new Point(); 

		float dist = mapView.getProjection().metersToEquatorPixels(mDistance);

		mapView.getProjection().toPixels(this.mOverlays.get(0).getPoint(), pt); // 이건 뭐하는 거징?
		canvas.drawCircle(pt.x, pt.y, dist, paint1);
		canvas.drawCircle(pt.x, pt.y, dist, paint2);
	}
}