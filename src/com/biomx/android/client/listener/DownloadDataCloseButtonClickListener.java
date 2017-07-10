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
public class DownloadDataCloseButtonClickListener implements OnClickListener {

	private Dialog downloadDataDialog;

	public DownloadDataCloseButtonClickListener(Dialog downloadDataDialog) {
		this.downloadDataDialog = downloadDataDialog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		this.downloadDataDialog.dismiss();
	}

}
