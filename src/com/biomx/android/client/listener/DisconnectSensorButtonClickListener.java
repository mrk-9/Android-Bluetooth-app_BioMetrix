/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;

import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.handler.ConnectionHandler;
import com.biomx.android.client.util.Constants;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class DisconnectSensorButtonClickListener implements OnClickListener {

	private ConnectionService connectionService;
	private ConnectionHandler connectionHandler;

	public DisconnectSensorButtonClickListener(
			ConnectionService connectionService,
			ConnectionHandler connectionHandler) {
		this.connectionService = connectionService;
		this.connectionHandler = connectionHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		try {
			this.connectionService.sendCommand(BioMXCommand.HAULT_STREAMING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.connectionService.disconnect();
		synchronized (DataHolder.lockConnectionService) {
			DataHolder.connectionServices.remove(this.connectionService
					.getAddress());
			DataHolder.connectedDevices.remove(this.connectionService
					.getAddress());
			DataHolder.currentDeviceData.remove(this.connectionService);
		}
		Message msg = this.connectionHandler.obtainMessage();
		msg.what = Constants.HANDLER_MESSAGE_STATE_CHANGE;
		this.connectionHandler.sendMessage(msg);
	}

}
