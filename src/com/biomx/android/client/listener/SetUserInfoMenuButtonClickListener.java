/**
 * 
 */
package com.biomx.android.client.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biomx.android.client.R;
import com.biomx.android.client.adapter.UserInfoSensorsListAdapter;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class SetUserInfoMenuButtonClickListener implements OnClickListener {

	private Activity activity;
	private RelativeLayout contentContainer;
	private Button[] menuButtons;

	public SetUserInfoMenuButtonClickListener(RelativeLayout contentContainer,
			Activity activity, Button[] menuButtons) {
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

		if (DataHolder.connectedDevices.size() == 0) {
			Toast.makeText(v.getContext(),
					R.string.notification_error_no_sensors_connected,
					Toast.LENGTH_LONG).show();
			return;
		}

		for (Button button : menuButtons) {
			button.setSelected(false);
		}
		v.setSelected(true);
		v.setEnabled(true);
		this.contentContainer.removeAllViews();
		View captureSession = this.activity.getLayoutInflater().inflate(
				R.layout.layout_set_user_info, null);
		this.contentContainer.addView(captureSession);

		TextView connectedSensortInfo = (TextView) this.activity
				.findViewById(R.id.text_view_content_header_sensor_info);
		String sensorInfo = "";
		String connectedSensors = "";
		Collection<XDevice> values = DataHolder.connectedDevices.values();
		for (XDevice xDevice : values) {
			String name = xDevice.getName().toLowerCase(Locale.ENGLISH)
					.replace("muse_", "").replace("biomx_", "");
			connectedSensors += name + ", ";
		}
		String infoText = this.activity.getText(
				R.string.header_description_sensor_info).toString();
		sensorInfo = String
				.format(infoText, DataHolder.connectedDevices.size());
		sensorInfo = sensorInfo + " "
				+ connectedSensors.substring(0, connectedSensors.length() - 2);
		connectedSensortInfo.setText(sensorInfo);
		connectedSensortInfo.setSelected(true);

		ListView sensorsListView = (ListView) this.activity
				.findViewById(R.id.list_view_biomx_user_info_sensors);

		UserInfoSensorsListAdapter sensorsListAdapter = new UserInfoSensorsListAdapter();
		sensorsListView.setAdapter(sensorsListAdapter);

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		Set<BluetoothDevice> bondedBluetoothDevices = bluetoothAdapter
				.getBondedDevices();
		List<XDevice> devices = new ArrayList<XDevice>();
		for (BluetoothDevice bluetoothDevice : bondedBluetoothDevices) {
			String nameL = bluetoothDevice.getName().toLowerCase(Locale.ENGLISH);
			if (nameL.startsWith("muse_") || nameL.startsWith("biomx_")) {
				ConnectionService connectionService = DataHolder.connectionServices
						.get(bluetoothDevice.getAddress());
				if (connectionService == null
						|| !connectionService.isConnected()) {
					continue;
				}
				XDevice device = DataHolder.connectedDevices
						.get(bluetoothDevice.getAddress());
				device.setDevice(bluetoothDevice);
				devices.add(device);
			}
		}
		sensorsListAdapter.setDevices(devices);
		sensorsListAdapter.notifyDataSetChanged();

		List<Button> menus = Arrays.asList(menuButtons);
		int indexOf = menus.indexOf(v);
		Button nextMenu = menus.get(indexOf + 1);
		Button nextButton = (Button) this.activity
				.findViewById(R.id.button_next);
		OnClickListener connectDeviceNextButtonClickListener = new SetUserInfoNextButtonClickListener(
				this.activity, nextMenu, sensorsListView);
		nextButton.setOnClickListener(connectDeviceNextButtonClickListener);

		Button prevMenu = menus.get(indexOf - 1);
		Button prevButton = (Button) this.activity
				.findViewById(R.id.button_back);
		OnClickListener connectDeviceBackButtonClickListener = new SetUserInfoBackButtonClickListener(
				prevMenu);
		prevButton.setOnClickListener(connectDeviceBackButtonClickListener);

	}

}
