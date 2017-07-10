/**
 * 
 */
package com.biomx.android.client.listener;

import com.biomx.android.client.dialog.SensitivitySettingsDialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Raghu
 * 
 */
public class SensitivitySettingsTextViewClickListener implements
		OnClickListener {

	
	private Context context;

	public SensitivitySettingsTextViewClickListener(Activity context) {
		this.context = context;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		SensitivitySettingsDialog sensitivitySettingsDialog = new SensitivitySettingsDialog(
				context);
		sensitivitySettingsDialog.show();
	}

}
