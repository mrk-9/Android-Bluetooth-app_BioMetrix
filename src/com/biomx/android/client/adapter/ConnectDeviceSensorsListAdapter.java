/**
 * 
 */
package com.biomx.android.client.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.handler.ConnectionHandler;
import com.biomx.android.client.listener.CalibrateSensorButtonClickListener;
import com.biomx.android.client.listener.ConnectSensorButtonClickListener;
import com.biomx.android.client.listener.DisconnectSensorButtonClickListener;
import com.biomx.android.client.listener.IdentifySensorButtonClickListener;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class ConnectDeviceSensorsListAdapter extends BaseAdapter {

	private List<XDevice> devices;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (this.devices == null) {
			return 0;
		}
		return this.devices.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.devices.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return this.devices.get(position).getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)"
	 */
	@Override
	public View getView(int position, View sensorItemView, ViewGroup parent) {
		Context context = parent.getContext();
		if (sensorItemView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			sensorItemView = inflater.inflate(
					R.layout.layout_connect_device_sensor_item, null);
		}
		XDevice device = (XDevice) this.getItem(position);

		TextView deviceTextView = (TextView) sensorItemView
				.findViewById(R.id.text_view_list_sensor_name);
		deviceTextView.setText(device.getName());

		ConnectionHandler connectionHandler = null;

		String address = device.getAddress();
		ConnectionService connectionService = DataHolder.connectionServices
				.get(address);

		if (connectionService == null) {
			connectionHandler = new ConnectionHandler(context, this);
			connectionService = new ConnectionService(device.getDevice(),
					connectionHandler, device);
			synchronized (DataHolder.lockConnectionService) {
				DataHolder.connectionServices.put(address, connectionService);
			}
		} else {
			connectionHandler = connectionService.getHandler();
			connectionHandler.setListAdapter(this);
		}

		Button deviceStatusButton = (Button) sensorItemView
				.findViewById(R.id.button_list_sensor_status);
		deviceStatusButton.setVisibility(View.VISIBLE);
		boolean isConnected = connectionService.isConnected();
		ProgressBar progressBar = (ProgressBar) sensorItemView
				.findViewById(R.id.progress_bar_list_sensor_connect);
		if (isConnected) {
			deviceStatusButton
					.setBackgroundResource(R.drawable.drawable_button_identify);
			deviceStatusButton.setText("");
			OnClickListener identifySensorButtonClickListener = new IdentifySensorButtonClickListener(
					device);
			deviceStatusButton
					.setOnClickListener(identifySensorButtonClickListener);
		} else {
			deviceStatusButton
					.setBackgroundResource(R.drawable.drawable_button_yellow);
			deviceStatusButton.setText(R.string.button_caption_connect);
			OnClickListener connectSensorButtonClickListener = new ConnectSensorButtonClickListener(
					connectionService, progressBar, device, context,
					connectionHandler);
			deviceStatusButton
					.setOnClickListener(connectSensorButtonClickListener);
		}

		Button batteryStatusButton = (Button) sensorItemView
				.findViewById(R.id.button_list_sensor_battery);
		if (isConnected) {
			float battery = DataHolder.connectedDevices.get(device.getAddress()).getBattery();
			if (battery > 95) {
				batteryStatusButton
						.setBackgroundResource(R.drawable.drawable_bitmap_battery100);
			} else if (battery > 75) {
				batteryStatusButton
						.setBackgroundResource(R.drawable.drawable_bitmap_battery75);
			} else if (battery > 50) {
				batteryStatusButton
						.setBackgroundResource(R.drawable.drawable_bitmap_battery50);
			} else if (battery > 5) {
				batteryStatusButton
						.setBackgroundResource(R.drawable.drawable_bitmap_battery25);
			} else {
				batteryStatusButton
						.setBackgroundResource(R.drawable.drawable_bitmap_battery0);
			}
			batteryStatusButton.setVisibility(View.VISIBLE);
		} else {
			batteryStatusButton.setVisibility(View.INVISIBLE);
		}

		Button calibrateButton = (Button) sensorItemView
				.findViewById(R.id.button_list_sensor_calibrate);
		if (isConnected) {
			calibrateButton
					.setBackgroundResource(R.drawable.drawable_button_calibrate);
			OnClickListener calibarateSensorButtonClickListener = new CalibrateSensorButtonClickListener(device);
			calibrateButton
					.setOnClickListener(calibarateSensorButtonClickListener);
			calibrateButton.setVisibility(View.VISIBLE);
			calibrateButton.setSelected(false);
		} else {
			calibrateButton.setVisibility(View.INVISIBLE);
		}

		Button disconnectButton = (Button) sensorItemView
				.findViewById(R.id.button_list_sensor_disconnect);
		if (isConnected) {
			disconnectButton
					.setBackgroundResource(R.drawable.drawable_button_disconnect);
			disconnectButton.setVisibility(View.VISIBLE);
			OnClickListener disconnectSensorButtonClickListener = new DisconnectSensorButtonClickListener(
					connectionService, connectionHandler);
			disconnectButton
					.setOnClickListener(disconnectSensorButtonClickListener);
		} else {
			disconnectButton.setVisibility(View.INVISIBLE);
		}

		return sensorItemView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
	}

	public void setDevices(List<XDevice> devices) {
		this.devices = devices;
	}

	/**
	 * @return the devices
	 */
	public List<XDevice> getDevices() {
		return devices;
	}
}
