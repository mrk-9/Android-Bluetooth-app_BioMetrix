/**
 * 
 */
package com.biomx.android.client.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.biomx.android.client.R;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dao.manager.XDeviceDaoManager;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class SetCaptureSettingsNextButtonClickListener implements
		OnClickListener {

	private Activity activity;
	private Button nextMenu;
	private ListView sensorsListView;

	public SetCaptureSettingsNextButtonClickListener(Activity activity,
			Button nextMenu, ListView sensorsListView) {
		this.activity = activity;
		this.nextMenu = nextMenu;
		this.sensorsListView = sensorsListView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		ListAdapter adapter = sensorsListView.getAdapter();
		List<XDevice> devices = new ArrayList<XDevice>();
		for (int i = 0; i < sensorsListView.getCount(); i++) {
			XDevice device = (XDevice) adapter.getItem(i);
			View sensorItemView = sensorsListView.getChildAt(i);
			String sampleRateS = "";
			Spinner sampleRateSpinner = (Spinner) sensorItemView
					.findViewById(R.id.spinner_list_samplerate);
			sampleRateS = sampleRateSpinner.getSelectedItem().toString();
			if (sampleRateS == null || sampleRateS.isEmpty()) {
				String errorMsg = this.activity.getText(
						R.string.notification_error_invalid_sample_rate)
						.toString();
				Toast.makeText(this.activity,
						String.format(errorMsg, device.getName().toString()),
						Toast.LENGTH_LONG).show();
				return;
			}
			device.setSampleRate(Integer.parseInt(sampleRateS));
			devices.add(device);
		}

		XDeviceDaoManager deviceDaoManager = new XDeviceDaoManager(activity);
		try {
			deviceDaoManager.update(devices);
		} catch (SQLException e) {
			e.printStackTrace();
			Toast.makeText(this.activity,
					R.string.notification_error_update_capture_settings,
					Toast.LENGTH_LONG).show();
			return;
		}
		CheckBox rawDataCheckBox = (CheckBox) this.activity.findViewById(R.id.check_box_raw_data);
		DataHolder.pickRawData = rawDataCheckBox.isChecked();
		
		CheckBox quaternionsCheckBox = (CheckBox) this.activity.findViewById(R.id.check_box_quaternions);
		DataHolder.pickQuaternions = quaternionsCheckBox.isChecked();
		
		Thread commandSender = new Thread(new ConfigCommandSender());
		commandSender.start();
		
		this.nextMenu.callOnClick();
	}

}
