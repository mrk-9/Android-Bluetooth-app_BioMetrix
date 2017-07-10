package com.biomx.android.client.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.Constants;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class CaptureSessionStartStopButtonClickListener implements
		OnClickListener {

	private Collection<XDevice> devices;
	private boolean isStop = false;
	private Button downloadDataButton;
	private Handler handler;

	public CaptureSessionStartStopButtonClickListener(
			Collection<XDevice> devices, Button downloadDataButton) {
		this.devices = devices;
		this.downloadDataButton = downloadDataButton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		button.setText(isStop ? R.string.button_caption_start
				: R.string.button_caption_stop);
		sendCommad();
		if (isStop) {
			processStop();
		} else {
			clearStreamData();
			processStart();
		}
		isStop = !isStop;
	}

	private void clearStreamData() {
		DataHolder.currentDeviceData.clear();
		for (XDevice device : DataHolder.connectedDevices.values()) {
			String address = device.getAddress();
			ConnectionService connectionService = DataHolder.connectionServices
					.get(address);
			List<int[]> dataList = connectionService.getDataList();
			dataList.clear();
			connectionService.setCount(0);
		}
	}

	private void sendCommad() {
		DecimalFormat decimalFormat = new DecimalFormat("000");
		long time = System.currentTimeMillis();
		File root = android.os.Environment.getExternalStorageDirectory();
		File dir = new File(root.getAbsolutePath() + DataHolder.homeFolderPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		for (XDevice device : this.devices) {
			ConnectionService connectionService = DataHolder.connectionServices
					.get(device.getAddress());
			if (isStop) {
				String command = BioMXCommand.HAULT_STREAMING.toString();
				try {
					connectionService.sendCommand(command);
				} catch (IOException e) {
					e.printStackTrace();
				}
				connectionService.stop();
			} else {
				String command = "";
				device = DataHolder.connectedDevices.get(device.getAddress());
				String filename = device.getName() + "_" + device.getPosition()
						+ "_" + time + ".csv";
				File file = new File(dir, filename);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				connectionService.setFile(file);
				FileWriter fileWritter = null;
				try {
					fileWritter = new FileWriter(file.getAbsolutePath(), true);
				} catch (IOException e) {
					e.printStackTrace();
				}
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				String content = "Time,Index,AccX,AccY,AccZ,MagnX,MagnY,MagnZ,GyroX,GyroY,GyroZ,qW,qX,qY,qZ,hax,hay,haz"+ "\n";
				try {
					bufferWritter.write(content);
					bufferWritter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (connectionService.isHdr()
						&& (device.getSensitivity() == 100 || device
								.getSensitivity() == 400)) {
					String formatedSampleRate = decimalFormat.format(device
							.getSampleRate());
					String calibCommnad = BioMXCommand.STREAM_HDR.toString();
					command = calibCommnad.replace("xxx", formatedSampleRate);
				} else {
					if (DataHolder.pickQuaternions && DataHolder.pickRawData) {
						String formatedSampleRate = decimalFormat.format(device
								.getSampleRate());
						String calibCommnad = BioMXCommand.STREAM_RAW
								.toString();
						command = calibCommnad.replace("xxx",
								formatedSampleRate);
					} else {
						String formatedSampleRate = decimalFormat.format(device
								.getSampleRate());
						String rawCommnad = BioMXCommand.STREAM_QUARTENION
								.toString();
						command = rawCommnad.replace("xxx", formatedSampleRate);
					}
				}
				try {
					connectionService.sendCommand(command);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void processStart() {
		handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				for (ConnectionService connectionService : DataHolder.currentDeviceData
						.keySet()) {
					Handler refreshGraphUIHandler = connectionService
							.getRefreshGraphUIHandler();
					Message msg = refreshGraphUIHandler.obtainMessage();
					msg.what = Constants.HANDLER_MESSAGE_GRAPH_DATA_CHANGE;
					msg.obj = DataHolder.currentDeviceData
							.get(connectionService);
					refreshGraphUIHandler.sendMessage(msg);
				}
				handler.postDelayed(this, 50);
			}
		}, 1000);
		downloadDataButton.setVisibility(View.INVISIBLE);
	}

	private void processStop() {
		downloadDataButton.setVisibility(View.VISIBLE);
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
			handler = null;
		}
		for (XDevice device : DataHolder.connectedDevices.values()) {
			ConnectionService connectionService = DataHolder.connectionServices
					.get(device.getAddress());
			connectionService.writeData();
		}
	}
	
}
