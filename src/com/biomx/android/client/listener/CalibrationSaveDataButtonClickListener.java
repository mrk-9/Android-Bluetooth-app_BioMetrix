/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class CalibrationSaveDataButtonClickListener implements OnClickListener,
		CalibrationDataChangeListener {

	private XDevice device;
	private CalibrationDataChangeObserver calibrationDataChangeObserver;
	private Button saveButton;

	public CalibrationSaveDataButtonClickListener(XDevice device,
			CalibrationDataChangeObserver calibrationDataChangeObserver) {
		this.device = device;
		this.calibrationDataChangeObserver = calibrationDataChangeObserver;
		this.calibrationDataChangeObserver.addDataChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		saveButton = (Button) v;
		saveButton.setSelected(true);
		ConnectionService connectionService = DataHolder.connectionServices
				.get(this.device.getAddress());
		try {
			connectionService.sendCommand(BioMXCommand.CALIB_MAGN_DATA);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCalibrationCompleted(boolean isCalibrationSuccess) {

	}

	@Override
	public void onCalibrationSaveCompleted() {
		saveButton.setSelected(false);
		Toast.makeText(saveButton.getContext(),
				R.string.notification_calibration_save_success,
				Toast.LENGTH_LONG).show();
	}
}
