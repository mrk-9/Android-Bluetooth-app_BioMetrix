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
public class CalibrationDialogCloseButtonClickListener implements
		OnClickListener {

	private Dialog calibrationDialog;

	public CalibrationDialogCloseButtonClickListener(Dialog calibrationDialog) {
		this.calibrationDialog = calibrationDialog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		this.calibrationDialog.dismiss();
	}

}
