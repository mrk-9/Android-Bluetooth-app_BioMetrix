/**
 * 
 */
package com.biomx.android.client.listener;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Raghu
 * 
 */
public class AddSensorsButtonClickListener implements OnClickListener {

	private Context context;

	public AddSensorsButtonClickListener(Context context) {
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		final Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		ComponentName cn = new ComponentName("com.android.settings",
				"com.android.settings.bluetooth.BluetoothSettings");
		intent.setComponent(cn);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.context.startActivity(intent);

	}

}
