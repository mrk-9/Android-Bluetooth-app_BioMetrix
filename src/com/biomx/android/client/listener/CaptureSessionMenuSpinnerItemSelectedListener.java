/**
 * 
 */
package com.biomx.android.client.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dao.manager.XDeviceDaoManager;
import com.biomx.android.client.util.DataHolder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;

/**
 * @author Raghu
 * 
 */
public class CaptureSessionMenuSpinnerItemSelectedListener implements
		OnItemSelectedListener {

	private Button[] menuButtons;
	private Context context;

	public CaptureSessionMenuSpinnerItemSelectedListener(Context context,
			Button[] menuButtons) {
		this.context = context;
		this.menuButtons = menuButtons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Object item = parent.getItemAtPosition(position);
		String selectedVal = item.toString();
		if (selectedVal.equals(this.context
				.getString(R.string.capture_session_options))) {
			return;
		}
		String newActivity = this.context
				.getString(R.string.capture_session_new_capture);
		String newPositions = this.context
				.getString(R.string.capture_session_new_positions);
		String newSession = this.context
				.getString(R.string.capture_session_new_session);
		String endSession = this.context
				.getString(R.string.capture_session_end_session);
		List<XDevice> devices = new ArrayList<XDevice>(
				DataHolder.connectedDevices.values());
		for (XDevice device : devices) {
			device.setSampleRate(0);
			device.setSensitivity(0);
			if (newPositions.equals(selectedVal)) {
				device.setPosition("");
				device.setCustomPosition(false);
			}
		}
		XDeviceDaoManager deviceDaoManager = new XDeviceDaoManager(context);
		try {
			deviceDaoManager.update(devices);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		String caption = "";
		if (newActivity.equals(selectedVal)) {
			caption = this.context
					.getString(R.string.button_caption_set_capture_settings);
		} else if (newPositions.equals(selectedVal)) {
			caption = this.context
					.getString(R.string.button_caption_set_user_info);
		} else if (newSession.equals(selectedVal)) {
			caption = this.context
					.getString(R.string.button_caption_connect_device);
		} else if (endSession.equals(selectedVal)) {
			Collection<ConnectionService> connectionServices = DataHolder.connectionServices
					.values();
			for (ConnectionService connectionService : connectionServices) {
				connectionService.disconnect();
			}
			DataHolder.connectionServices.clear();
			DataHolder.connectedDevices.clear();
			caption = this.context
					.getString(R.string.button_caption_capture_session);
		}

		for (Button button : this.menuButtons) {
			if (button.getText().equals(caption)) {
				button.callOnClick();
				return;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
	 * .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
