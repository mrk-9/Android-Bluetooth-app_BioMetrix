/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;

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
public class CalibrationBeginCalibrateButtonClickListener implements
		OnClickListener {

	private XDevice device;

	public CalibrationBeginCalibrateButtonClickListener(XDevice device) {
		this.device = device;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		Button calibrateButton = (Button) arg0;
		calibrateButton.setSelected(true);
		ConnectionService connectionService = DataHolder.connectionServices
				.get(device.getAddress());
		try {
			connectionService.sendCommand(BioMXCommand.START_CALIBRATION);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
