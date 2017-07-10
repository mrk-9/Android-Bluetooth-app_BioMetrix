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
public class CaptureSessionLeftButtonBarOnClickListener implements
		OnClickListener {

	private Button[] topBarButtons;
	private Button[] leftBarButtons;

	public CaptureSessionLeftButtonBarOnClickListener(Button[] topBarButtons,
			Button[] leftBarButtons) {
		this.topBarButtons = topBarButtons;
		this.leftBarButtons = leftBarButtons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View button) {
		for (Button leftBarButton : this.leftBarButtons) {
			leftBarButton.setSelected(false);
		}
		button.setSelected(true);
		for (Button topBarButton : this.topBarButtons) {
			if (topBarButton.isSelected()) {
				topBarButton.callOnClick();
			}
		}
	}

}
