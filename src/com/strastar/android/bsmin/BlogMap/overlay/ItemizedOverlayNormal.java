package com.strastar.android.bsmin.BlogMap.overlay;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.strastar.android.bsmin.BlogMap.R;

public class ItemizedOverlayNormal extends ItemizedOverlay<OverlayItem>{
	private Context mContext;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	public ItemizedOverlayNormal(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
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

	public void addOverlays(ArrayList<OverlayItem> items) {
		for (OverlayItem item : items) mOverlays.add(item);
		populate(); 
	}

	@Override
	protected boolean onTap(int index) { 
		OverlayItem item = mOverlays.get(index); 
		Dialog dialog = new Dialog(mContext);
		dialog.setContentView( R.layout.info );
		dialog.setTitle(item.getTitle());
		dialog.setCancelable(true);
		TextView textview = (TextView)dialog.findViewById(R.id.text1);
		textview.setText("\t"+ item.getPoint().getLatitudeE6()+item.getPoint().getLongitudeE6()+item.getTitle());
		dialog.show();
		return true;
	}
}