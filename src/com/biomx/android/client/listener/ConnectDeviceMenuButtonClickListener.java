/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.biomx.android.client.R;
import com.biomx.android.client.adapter.ConnectDeviceSensorsListAdapter;
import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.BioMXRUtil;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class ConnectDeviceMenuButtonClickListener implements OnClickListener {

	private Activity activity;
	private RelativeLayout contentContainer;
	private Button[] menuButtons;

	public ConnectDeviceMenuButtonClickListener(
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

		View connectDeviceSession = this.activity.getLayoutInflater().inflate(
				R.layout.layout_connect_device, null);
		this.contentContainer.addView(connectDeviceSession);

		Button addDeviceButton = (Button) this.activity
				.findViewById(R.id.button_add_device);
		android.view.View.OnClickListener addSensorsButtonClickListener = new AddSensorsButtonClickListener(
				this.activity);
		addDeviceButton.setOnClickListener(addSensorsButtonClickListener);

		ListView sensorsListView = (ListView) this.activity
				.findViewById(R.id.list_view_biomx_sensors);

		ConnectDeviceSensorsListAdapter sensorsListAdapter = new ConnectDeviceSensorsListAdapter();
		sensorsListView.setAdapter(sensorsListAdapter);

		BioMXRUtil.refreshPairedSensorsList(sensorsListAdapter, activity);

		Button refreshPairedSensorsButton = (Button) this.activity
				.findViewById(R.id.button_refresh_sensors);
		android.view.View.OnClickListener refreshButtonClickListener = new RefreshSensorsListButtonClickListener(
				sensorsListAdapter, activity);
		refreshPairedSensorsButton
				.setOnClickListener(refreshButtonClickListener);
		
		List<Button> menus = Arrays.asList(menuButtons);
		int indexOf = menus.indexOf(v);
		Button nextMenu = menus.get(indexOf + 1);
		Button nextButton = (Button) this.activity
				.findViewById(R.id.button_next);
		OnClickListener connectDeviceNextButtonClickListener = new ConnectDeviceNextButtonClickListener(nextMenu);
		nextButton.setOnClickListener(connectDeviceNextButtonClickListener);
		
		for (XDevice device : DataHolder.connectedDevices.values()) {
			ConnectionService connectionService = DataHolder.connectionServices.get(device.getAddress());
			try {
				connectionService.sendCommand(BioMXCommand.GET_BATTQ);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
