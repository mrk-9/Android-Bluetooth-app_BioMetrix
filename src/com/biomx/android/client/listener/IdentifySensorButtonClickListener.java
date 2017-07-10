/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class IdentifySensorButtonClickListener implements OnClickListener {

	private XDevice device;

	public IdentifySensorButtonClickListener(XDevice device) {
		this.device = device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		final ConnectionService connectionService = DataHolder.connectionServices
				.get(device.getAddress());
		final Button identifyButton = (Button) arg0;
		identifyButton.setSelected(true);
		try {
			connectionService.sendCommand(BioMXCommand.START_STREAMING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					connectionService.sendCommand(BioMXCommand.HAULT_STREAMING);
					identifyButton.setSelected(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 3000);
	}

}
