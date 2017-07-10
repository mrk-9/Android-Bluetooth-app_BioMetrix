/**
 * 
 */
package com.biomx.android.client.listener;

import android.view.View;
import android.view.View.OnClickListener;

import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dialog.CalibrationDialog;

/**
 * @author Raghu
 * 
 */
public class CalibrateSensorButtonClickListener implements OnClickListener {

	private XDevice device;

	public CalibrateSensorButtonClickListener(XDevice device) {
		this.device = device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		/*
		 * Toast.makeText(v.getContext(),
		 * R.string.notification_calibration_not_implemented,
		 * Toast.LENGTH_LONG).show();
		 */

		CalibrationDialog downloadDataDialog = new CalibrationDialog(
				v.getContext(), this.device);
		downloadDataDialog.show();

		/*ConnectionService connectionService = DataHolder.connectionServices
				.get(device.getAddress());
		Button calibrationButton = (Button) v;
		calibrationButton.setSelected(true);
		try {
			connectionService.sendCommand(BioMXCommand.START_CALIBRATION);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
