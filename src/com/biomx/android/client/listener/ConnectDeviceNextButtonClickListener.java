/**
 * 
 */
package com.biomx.android.client.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class ConnectDeviceNextButtonClickListener implements OnClickListener {

	private Button nextMenu;

	public ConnectDeviceNextButtonClickListener(Button nextMenu) {
		this.nextMenu = nextMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		for (ConnectionService connectionService : DataHolder.connectionServices
				.values()) {
			if (connectionService.isConnected()) {
				this.nextMenu.callOnClick();
				return;
			}
		}
		Toast.makeText(v.getContext(),
				R.string.notification_error_no_sensors_connected,
				Toast.LENGTH_LONG).show();
	}

}
