/**
 * 
 */
package com.biomx.android.client.listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.biomx.android.client.R;

/**
 * @author Raghu
 * 
 */
public class CaptureSessionMenuButtonClickListener implements OnClickListener {

	private Activity activity;
	private RelativeLayout contentContainer;
	private Button[] menuButtons;

	public CaptureSessionMenuButtonClickListener(
			RelativeLayout contentContainer, Activity activity,
			Button[] menuButtons) {
		this.contentContainer = contentContainer;
		this.activity = activity;
		this.menuButtons = menuButtons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		for (Button button : menuButtons) {
			button.setSelected(false);
		}
		v.setSelected(true);
		v.setEnabled(true);
		this.contentContainer.removeAllViews();
		View captureSession = this.activity.getLayoutInflater().inflate(
				R.layout.layout_capture_session_main, null);
		this.contentContainer.addView(captureSession);

		Button beginButton = (Button) this.activity
				.findViewById(R.id.button_begin);
		OnClickListener captureSessionBeginButtonClickListener = new CaptureSessionBeginButtonClickListener(
				menuButtons[1]);
		beginButton.setOnClickListener(captureSessionBeginButtonClickListener);
	}
}
