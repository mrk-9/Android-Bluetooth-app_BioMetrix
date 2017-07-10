/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;
import java.sql.SQLException;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dao.manager.XDeviceDaoManager;
import com.biomx.android.client.handler.ConnectionHandler;
import com.biomx.android.client.util.Constants;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class ConnectSensorButtonClickListener implements OnClickListener {

	private ConnectionService connectionService;
	private ProgressBar progressBar;
	private XDevice device;
	private Context context;
	private ConnectionHandler connectionHandler;

	public ConnectSensorButtonClickListener(
			ConnectionService connectionService, ProgressBar progressBar,
			XDevice device, Context context, ConnectionHandler connectionHandler) {
		this.connectionService = connectionService;
		this.progressBar = progressBar;
		this.device = device;
		this.context = context;
		this.connectionHandler = connectionHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		progressBar.setVisibility(View.VISIBLE);
		view.setVisibility(View.GONE);
		try {
			this.connectionService.connect();
			XDeviceDaoManager deviceDaoManager = new XDeviceDaoManager(context);
			String deviceAddress = this.device.getAddress();
			XDevice xDevice = deviceDaoManager.getBy(deviceAddress);
			if (xDevice == null) {
				deviceDaoManager.create(device);
				DataHolder.connectedDevices.put(deviceAddress, device);
			} else {
				DataHolder.connectedDevices.put(deviceAddress, xDevice);
			}
		} catch (IOException e) {
			Message msg = this.connectionHandler.obtainMessage();
			Bundle data = new Bundle();
			data.putString(
					Constants.HANDLER_TOAST_MESSAGE,
					this.context
							.getString(R.string.notification_error_failed_to_connect_device));
			msg.setData(data);
			msg.what = Constants.HANDLER_MESSAGE_FAILURE;
			this.connectionHandler.sendMessage(msg);
		} catch (SQLException e) {
			Message msg = this.connectionHandler.obtainMessage();
			Bundle data = new Bundle();
			data.putString(
					Constants.HANDLER_TOAST_MESSAGE,
					this.context
							.getString(R.string.notification_error_failed_to_connect_device));
			msg.setData(data);
			msg.what = Constants.HANDLER_MESSAGE_FAILURE;
			this.connectionHandler.sendMessage(msg);
		}
		progressBar.setVisibility(View.GONE);
	}

}
