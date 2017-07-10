/**
 * 
 */
package com.biomx.android.client.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.biomx.android.client.adapter.ConnectDeviceSensorsListAdapter;
import com.biomx.android.client.util.BioMXRUtil;

/**
 * @author Raghu
 * 
 */
public class RefreshSensorsListButtonClickListener implements OnClickListener {

	private ConnectDeviceSensorsListAdapter sensorsListAdapter;
	private Context context;

	public RefreshSensorsListButtonClickListener(
			ConnectDeviceSensorsListAdapter sensorsListAdapter, Context context) {
		this.sensorsListAdapter = sensorsListAdapter;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		BioMXRUtil.refreshPairedSensorsList(sensorsListAdapter, context);
	}

}
