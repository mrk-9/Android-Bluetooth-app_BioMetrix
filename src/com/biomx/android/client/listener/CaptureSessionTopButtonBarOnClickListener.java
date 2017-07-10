/**
 * 
 */
package com.biomx.android.client.listener;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.biomx.android.client.util.Constants;

/**
 * @author Raghu
 * 
 */
public class CaptureSessionTopButtonBarOnClickListener implements
		OnClickListener {

	private Button[] topBarButtons;
	private Handler refreshGraphUIHandler;

	public CaptureSessionTopButtonBarOnClickListener(Button[] topBarButtons,
			Handler refreshGraphUIHandler) {
		this.topBarButtons = topBarButtons;
		this.refreshGraphUIHandler = refreshGraphUIHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View button) {
		for (Button topBarButton : this.topBarButtons) {
			topBarButton.setSelected(false);
		}
		button.setSelected(true);
		Message msg = this.refreshGraphUIHandler.obtainMessage();
		msg.what = Constants.HANDLER_MESSAGE_GRAPH_DATA_CHANGE;
		msg.obj = null;
		this.refreshGraphUIHandler.sendMessage(msg);
	}

}
