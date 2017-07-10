/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.util.PlotStatistics;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYLegendWidget;
import com.androidplot.xy.XYPlot;
import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.handler.RefreshGraphUIHandler;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class CaptureSessionButtonClickListener implements OnClickListener {

	private Activity activity;
	private RelativeLayout contentContainer;
	private Button[] menuButtons;

	public CaptureSessionButtonClickListener(RelativeLayout contentContainer,
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
				R.layout.layout_capture_session, null);
		this.contentContainer.addView(captureSession);

		TextView connectedSensorInfo = (TextView) this.activity
				.findViewById(R.id.text_view_content_header_sensor_info);
		String sensorInfo = "";
		String connectedSensors = "";
		Collection<XDevice> values = DataHolder.connectedDevices.values();
		for (XDevice xDevice : values) {
			String name = xDevice.getName().toLowerCase(Locale.ENGLISH)
					.replace("muse_", "").replace("biomx_", "");
			connectedSensors += name + " on " + xDevice.getPosition() + " ("
					+ "\u00B1" + xDevice.getSensitivity() + "g, "
					+ xDevice.getSampleRate() + "hz)" + "; ";
		}
		String infoText = this.activity.getText(
				R.string.header_description_sensor_info).toString();
		sensorInfo = String
				.format(infoText, DataHolder.connectedDevices.size());
		sensorInfo = sensorInfo + " "
				+ connectedSensors.substring(0, connectedSensors.length() - 2);
		connectedSensorInfo.setText(sensorInfo);
		connectedSensorInfo.setSelected(true);

		TextView recordingInfoInfo = (TextView) this.activity
				.findViewById(R.id.text_view_content_header_recording_info);
		String recordingInfo = (String) this.activity
				.getText(R.string.header_description_recording_raw_acc_gyro_magn);
		if (DataHolder.pickRawData && DataHolder.pickQuaternions) {
			recordingInfo = String.format(recordingInfo,
					"Raw Data and Quaternions");
		} else if (DataHolder.pickRawData) {
			recordingInfo = String.format(recordingInfo, "Raw Data");
		} else if (DataHolder.pickQuaternions) {
			recordingInfo = String.format(recordingInfo, "Quaternions");
		}
		recordingInfoInfo.setText(recordingInfo);

		ArrayList<String> captureSessionMenuItems = new ArrayList<String>();
		captureSessionMenuItems.add(this.activity
				.getString(R.string.capture_session_options));
		captureSessionMenuItems.add(this.activity
				.getString(R.string.capture_session_new_capture));
		captureSessionMenuItems.add(this.activity
				.getString(R.string.capture_session_new_positions));
		captureSessionMenuItems.add(this.activity
				.getString(R.string.capture_session_new_session));
		captureSessionMenuItems.add(this.activity
				.getString(R.string.capture_session_end_session));
		Spinner captureSessionMenuSpinner = (Spinner) this.activity
				.findViewById(R.id.spinner_list_cpature_session_menu);
		ArrayAdapter<String> newActivityMenuAdapter = new ArrayAdapter<String>(
				this.activity, R.layout.spinner_item, captureSessionMenuItems);
		captureSessionMenuSpinner.setAdapter(newActivityMenuAdapter);
		OnItemSelectedListener itemSelectedListener = new CaptureSessionMenuSpinnerItemSelectedListener(
				activity, menuButtons);
		captureSessionMenuSpinner
				.setOnItemSelectedListener(itemSelectedListener);

		XYPlot lineChart = (XYPlot) this.activity.findViewById(R.id.chart);
		lineChart.setRangeStepValue(5);
		GraphYAxisValueFormatter graphYAxisValueFormatter = new GraphYAxisValueFormatter();
		lineChart.setRangeValueFormat(graphYAxisValueFormatter);
		lineChart.getRangeLabelWidget().pack();

		GraphXAxisValueFormatter graphXAxisValueFormatter = new GraphXAxisValueFormatter();
		lineChart.setDomainValueFormat(graphXAxisValueFormatter);
		lineChart.setDomainStepValue(10);
		lineChart.setDomainLabel("Secs");
		lineChart.getDomainLabelWidget().pack();
		lineChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		lineChart.setDomainBoundaries(0, 200, BoundaryMode.FIXED);
		lineChart.getBackgroundPaint().setColor(Color.WHITE);
		lineChart.setPlotMargins(0, 0, 0, 0);
		lineChart.setPlotPadding(50, 10, 50, 30);
		lineChart.setGridPadding(0, 10, 5, 0);

		XYGraphWidget graphWidget = lineChart.getGraphWidget();
		graphWidget.getBackgroundPaint().setColor(Color.WHITE);
		graphWidget.getGridBackgroundPaint().setColor(Color.WHITE);
		graphWidget.setSize(new SizeMetrics(0, SizeLayoutType.FILL, 0,
				SizeLayoutType.FILL));
		graphWidget.getDomainGridLinePaint().setColor(Color.TRANSPARENT);
		graphWidget.getDomainLabelPaint().setColor(Color.BLACK);
		graphWidget.getDomainOriginLinePaint().setColor(Color.BLACK);
		graphWidget.getDomainOriginLabelPaint().setColor(Color.BLACK);

		graphWidget.getRangeGridLinePaint().setColor(Color.TRANSPARENT);
		graphWidget.getRangeSubGridLinePaint().setColor(Color.TRANSPARENT);
		graphWidget.getRangeOriginLinePaint().setColor(Color.BLACK);
		graphWidget.getRangeLabelPaint().setColor(Color.BLACK);
		graphWidget.getRangeOriginLabelPaint().setColor(Color.BLACK);

		XYLegendWidget legendWidget = lineChart.getLegendWidget();
		legendWidget.setSize(new SizeMetrics(100, SizeLayoutType.ABSOLUTE, 200,
				SizeLayoutType.ABSOLUTE));
		legendWidget.getTextPaint().setColor(Color.BLACK);
		legendWidget.setIconSizeMetrics(new SizeMetrics(15,
				SizeLayoutType.ABSOLUTE, 15, SizeLayoutType.ABSOLUTE));

		PlotStatistics histStats = new PlotStatistics(200, false);
		lineChart.addListener(histStats);

		LinearLayout topActionBarLayout = (LinearLayout) captureSession
				.findViewById(R.id.linear_layout_top_action_button_bar);
		Map<String, XDevice> connectedDevices = DataHolder.connectedDevices;
		Collection<XDevice> devices = connectedDevices.values();
		final Button[] topBarButtons = new Button[devices.size()];

		RelativeLayout leftActionBarLayout = (RelativeLayout) captureSession
				.findViewById(R.id.linear_layout_left_action_button_bar);
		Button[] leftBarButtons = new Button[leftActionBarLayout
				.getChildCount()];

		int index = 0;
		for (XDevice device : devices) {
			Button button = new Button(activity);
			button.setText(device.getPosition());
			button.setBackgroundResource(R.drawable.drawable_button_grey);
			topActionBarLayout.addView(button);
			topBarButtons[index++] = button;
			Handler refreshGraphUIHandler = new RefreshGraphUIHandler(
					this.activity, leftBarButtons, lineChart, button,
					graphXAxisValueFormatter, device);
			OnClickListener captureSessionTopButtonBarOnClickListener = new CaptureSessionTopButtonBarOnClickListener(
					topBarButtons, refreshGraphUIHandler);
			button.setOnClickListener(captureSessionTopButtonBarOnClickListener);
			ConnectionService connectionService = DataHolder.connectionServices
					.get(device.getAddress());
			connectionService.setRefreshGraphUIHandler(refreshGraphUIHandler);
			try {
				connectionService.sendCommand(BioMXCommand.HAULT_STREAMING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		OnClickListener captureSessionLeftButtonBarOnClickListener = new CaptureSessionLeftButtonBarOnClickListener(
				topBarButtons, leftBarButtons);
		for (int i = 0; i < leftActionBarLayout.getChildCount(); i++) {
			Button button = (Button) leftActionBarLayout.getChildAt(i);
			leftBarButtons[i] = button;
			button.setOnClickListener(captureSessionLeftButtonBarOnClickListener);
		}

		Button downloadDataButton = (Button) captureSession
				.findViewById(R.id.button_download_data);
		OnClickListener captureSessionDownloadDataButtonClickListener = new CaptureSessionDownloadDataButtonClickListener();
		downloadDataButton
				.setOnClickListener(captureSessionDownloadDataButtonClickListener);

		Button startButton = (Button) captureSession
				.findViewById(R.id.button_start);
		OnClickListener captureSessionStartStopButtonClickListener = new CaptureSessionStartStopButtonClickListener(
				devices, downloadDataButton);
		startButton
				.setOnClickListener(captureSessionStartStopButtonClickListener);

		leftBarButtons[leftBarButtons.length - 1].callOnClick();
		topBarButtons[0].callOnClick();

	}
}
