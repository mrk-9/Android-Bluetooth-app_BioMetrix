/**
 * 
 */
package com.biomx.android.client.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Raghu
 * 
 */
public class CaptureSessionBeginButtonClickListener implements OnClickListener {

	private Button button;

	public CaptureSessionBeginButtonClickListener(Button button) {
		this.button = button;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		button.callOnClick();
	}

}
