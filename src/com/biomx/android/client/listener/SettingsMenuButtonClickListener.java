/**
 * 
 */
package com.biomx.android.client.listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.biomx.android.client.R;
import com.biomx.android.client.adapter.ConnectDeviceSensorsListAdapter;
import com.biomx.android.client.util.BioMXRUtil;

/**
 * @author Raghu
 * 
 */
public class SettingsMenuButtonClickListener implements OnClickListener {

	private Activity activity;
	private RelativeLayout contentContainer;
	private Button[] menuButtons;

	public SettingsMenuButtonClickListener(RelativeLayout contentContainer,
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
		for (Button button : menuButtons) {
			button.setSelected(false);
		}
		v.setSelected(true);
		this.contentContainer.removeAllViews();
		View settingsContainer = this.activity.getLayoutInflater().inflate(
				R.layout.layout_settings, null);
		this.contentContainer.addView(settingsContainer);

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

		Button refreshPairedSensotsButton = (Button) this.activity
				.findViewById(R.id.button_refresh_sensors);
		android.view.View.OnClickListener refreshButtonClickListener = new RefreshSensorsListButtonClickListener(
				sensorsListAdapter, activity);
		refreshPairedSensotsButton
				.setOnClickListener(refreshButtonClickListener);
	}

}
