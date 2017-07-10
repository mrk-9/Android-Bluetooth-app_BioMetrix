/**
 * 
 */
package com.biomx.android.client.listener;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Raghu
 * 
 */
public class SensitivitySettingsDoneButtonOnClickListener implements
		OnClickListener {

	private Dialog sensitivitySettingsDialog;

	public SensitivitySettingsDoneButtonOnClickListener(
			Dialog sensitivitySettingsDialog) {
		this.sensitivitySettingsDialog = sensitivitySettingsDialog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		this.sensitivitySettingsDialog.dismiss();
	}

}
