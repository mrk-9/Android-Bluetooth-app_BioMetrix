package com.biomx.android.client.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.handler.ConnectionHandler;
import com.biomx.android.client.listener.CalibrationBeginCalibrateButtonClickListener;
import com.biomx.android.client.listener.CalibrationDataChangeListener;
import com.biomx.android.client.listener.CalibrationDataChangeObserver;
import com.biomx.android.client.listener.CalibrationDialogCloseButtonClickListener;
import com.biomx.android.client.listener.CalibrationSaveDataButtonClickListener;
import com.biomx.android.client.listener.CaptureSessionDownloadDataButtonClickListener;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class CalibrationDialog extends Dialog implements
		CalibrationDataChangeListener {

	private Button beginCalibrationButton;
	private Button saveCalibrationButton;
	private Button calibrateAgainButton;

	public CalibrationDialog(Context context, XDevice device) {
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.layout_calibration);

		beginCalibrationButton = (Button) this
				.findViewById(R.id.button_begin_calibration);
		calibrateAgainButton = (Button) this
				.findViewById(R.id.button_calibrate_again);

		android.view.View.OnClickListener doneButtonOnClickListener = new CalibrationBeginCalibrateButtonClickListener(
				device);
		beginCalibrationButton.setOnClickListener(doneButtonOnClickListener);
		calibrateAgainButton.setOnClickListener(doneButtonOnClickListener);

		CalibrationDataChangeObserver calibrationDataChangeObserver = new CalibrationDataChangeObserver();

		saveCalibrationButton = (Button) this
				.findViewById(R.id.button_save_calibration_settings);
		android.view.View.OnClickListener saveDeleteFileButtonClickListener = new CalibrationSaveDataButtonClickListener(
				device, calibrationDataChangeObserver);
		saveCalibrationButton
				.setOnClickListener(saveDeleteFileButtonClickListener);

		Button closeButton = (Button) this.findViewById(R.id.button_close);
		android.view.View.OnClickListener downloadDataCloseButtonClickListener = new CalibrationDialogCloseButtonClickListener(
				this);
		closeButton.setOnClickListener(downloadDataCloseButtonClickListener);

		calibrationDataChangeObserver.addDataChangeListener(this);

		ConnectionService connectionService = DataHolder.connectionServices
				.get(device.getAddress());

		ConnectionHandler handler = connectionService.getHandler();
		handler.setCalibrationDataChangeObserver(calibrationDataChangeObserver);

		Button doneButton = (Button) this.findViewById(R.id.button_download);
		android.view.View.OnClickListener captureSessionDownloadDataButtonClickListener = new CaptureSessionDownloadDataButtonClickListener();
		doneButton
				.setOnClickListener(captureSessionDownloadDataButtonClickListener);
	}

	@Override
	public void onCalibrationCompleted(boolean isCalibraionSuccess) {
		this.beginCalibrationButton.setSelected(false);
		this.calibrateAgainButton.setSelected(false);
		this.saveCalibrationButton.setSelected(false);

		this.beginCalibrationButton.setVisibility(View.GONE);
		this.calibrateAgainButton.setVisibility(View.VISIBLE);

		this.saveCalibrationButton
				.setVisibility(isCalibraionSuccess ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onCalibrationSaveCompleted() {
		// TODO Auto-generated method stub

	}
}
