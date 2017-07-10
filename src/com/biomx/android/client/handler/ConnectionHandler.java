/**
 * 
 */
package com.biomx.android.client.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.biomx.android.client.R;
import com.biomx.android.client.adapter.ConnectDeviceSensorsListAdapter;
import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.listener.CalibrationDataChangeObserver;
import com.biomx.android.client.util.BioMXRUtil;
import com.biomx.android.client.util.Constants;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class ConnectionHandler extends Handler {

	private Context context;
	private ConnectDeviceSensorsListAdapter sensorsListAdapter;
	private CalibrationDataChangeObserver calibrationDataChangeObserver;

	public ConnectionHandler(Context context,
			ConnectDeviceSensorsListAdapter sensorsListAdapter) {
		this.context = context;
		this.sensorsListAdapter = sensorsListAdapter;
	}

	@Override
	public void handleMessage(Message msg) {
		int what = msg.what;
		Bundle data = msg.getData();
		String message = data.getString(Constants.HANDLER_TOAST_MESSAGE);
		switch (what) {
		case Constants.HANDLER_MESSAGE_SUCCESS:
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			BioMXRUtil.refreshPairedSensorsList(sensorsListAdapter, context);
			break;
		case Constants.HANDLER_MESSAGE_FAILURE:
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			BioMXRUtil.refreshPairedSensorsList(sensorsListAdapter, context);
			break;
		case Constants.HANDLER_MESSAGE_STATE_CHANGE:
			BioMXRUtil.refreshPairedSensorsList(sensorsListAdapter, context);
			break;
		case Constants.HANDLER_MESSAGE_BATTERY_STATE_CHANGE:
			handleBatteryChange(data);
			break;
		case Constants.HANDLER_MESSAGE_CALIBRATION_MESSAGE:
			handleCalibrationResult(data);
			break;
		case Constants.HANDLER_MESSAGE_CALIBRATION_DATA_MAGN_RECEIVED:
			handleCalibMagnData(data);
			break;
		/*
		 * case Constants.HANDLER_MESSAGE_CALIBRATION_DATA_ACC_RECEIVED:
		 * handleCalibAccData(data); break;
		 */
		default:
			break;
		}
	}

	private void handleBatteryChange(Bundle data) {
		float batteryPercentage = data
				.getFloat(Constants.HANDLER_MESSAGE_KEY_BATTERY);
		String deviceAddr = data
				.getString(Constants.HANDLER_MESSAGE_KEY_DEVICE);
		XDevice device = DataHolder.connectedDevices.get(deviceAddr);
		device.setBattery(batteryPercentage);
		ConnectionService connectionService = DataHolder.connectionServices
				.get(deviceAddr);
		try {
			connectionService.sendCommand(BioMXCommand.GET_HDR_STATUS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.sensorsListAdapter.notifyDataSetChanged();
	}

	private void handleCalibrationResult(Bundle data) {
		boolean isCalibrationSuccess = data
				.getBoolean(Constants.HANDLER_MESSAGE_KEY_CALIBRATION);
		Toast.makeText(
				this.context,
				isCalibrationSuccess ? R.string.notification_calibration_success
						: R.string.notification_calibration_failed,
				Toast.LENGTH_LONG).show();
		this.calibrationDataChangeObserver
				.notifyCalibrationCompleted(isCalibrationSuccess);
	}

	private void handleCalibMagnData(Bundle data) {
		int[] resultData = data
				.getIntArray(Constants.HANDLER_MESSAGE_KEY_MAGNETOMETER_CALIB_DATA);
		String content = "";

		float mxx = BioMXRUtil.decodeFloatData((byte) resultData[0],
				(byte) resultData[1], (byte) resultData[2],
				(byte) resultData[3]);
		content += "Mxx \t" + mxx + "\n";
		
		float mxy = BioMXRUtil.decodeFloatData((byte) resultData[4],
				(byte) resultData[5], (byte) resultData[6],
				(byte) resultData[7]);
		content += "Mxy \t" + mxy + "\n";
		
		float mxz = BioMXRUtil.decodeFloatData((byte) resultData[8],
				(byte) resultData[9], (byte) resultData[10],
				(byte) resultData[11]);
		content += "Mxz \t" +  mxz + "\n";
		//================
		float myx = BioMXRUtil.decodeFloatData((byte) resultData[12],
				(byte) resultData[13], (byte) resultData[14],
				(byte) resultData[15]);
		content += "Myx \t" + myx + "\n";
		
		float myy = BioMXRUtil.decodeFloatData((byte) resultData[16],
				(byte) resultData[17], (byte)resultData[18],
				(byte) resultData[19]);
		content += "Myy \t" + myy + "\n";
		
		float myz = BioMXRUtil.decodeFloatData((byte) resultData[20],
				(byte) resultData[21], (byte)resultData[22],
				(byte) resultData[23]);
		content += "Myz \t" + myz + "\n";
		//================		
		float mzx = BioMXRUtil.decodeFloatData((byte) resultData[24],
				(byte) resultData[25], (byte)resultData[26],
				(byte) resultData[27]);
		content += "Mzx \t" + mzx + "\n";
		
		float mzy = BioMXRUtil.decodeFloatData((byte) resultData[28],
				(byte) resultData[29], (byte)resultData[30],
				(byte) resultData[31]);
		content += "Mzy \t" + mzy + "\n";
		
		float mzz = BioMXRUtil.decodeFloatData((byte) resultData[32],
				(byte) resultData[33], (byte)resultData[34],
				(byte) resultData[35]);
		content += "Mzz \t" + mzz + "\n";
		//================
		float bx = BioMXRUtil.decodeFloatData((byte) resultData[36],
				(byte) resultData[37], (byte)resultData[38],
				(byte) resultData[39]);
		content += "bx \t\t" + bx + "\n";
		
		float by = BioMXRUtil.decodeFloatData((byte) resultData[40],
				(byte) resultData[41], (byte)resultData[42],
				(byte) resultData[43]);
		content += "by \t\t" + by + "\n";
		
		float bz = BioMXRUtil.decodeFloatData((byte) resultData[44],
				(byte) resultData[45], (byte)resultData[46],
				(byte) resultData[47]);
		content += "bz \t\t" + bz + "\n";
		//================
		String deviceAddr = data
				.getString(Constants.HANDLER_MESSAGE_KEY_DEVICE);
		XDevice device = DataHolder.connectedDevices.get(deviceAddr);
		File root = android.os.Environment.getExternalStorageDirectory();
		File dir = new File(root.getAbsolutePath() + DataHolder.homeFolderPath);
		long calibDataStreamStartTime = System.currentTimeMillis();
		String filename = device.getName() + "_Magnetometer_params"
				+ calibDataStreamStartTime + ".txt";
		File file = new File(dir, filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(file.getAbsolutePath(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		try {
			bufferWritter.write(content);
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * ConnectionService connectionService = DataHolder.connectionServices
		 * .get(deviceAddr); try {
		 * connectionService.sendCommand(BioMXCommand.CALIB_ACC_DATA); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */
		this.calibrationDataChangeObserver.notifyCalibrationSaveCompleted();
	}

	/*
	 * private void handleCalibAccData(Bundle data) { int[] resultData = data
	 * .getIntArray(Constants.HANDLER_MESSAGE_KEY_ACCELEROMETER_CALIB_DATA);
	 * String content = "Axx,Axy,Axz,Ayx,Ayy,Ayz,Azx,Azy,Azz,bAx,bAy,bAz" +
	 * "\n"; for (int i = 0; i < 12; i++) { float value =
	 * BioMXRUtil.decodeFloatData((byte) resultData[i], (byte) resultData[i +
	 * 1], (byte) resultData[i + 2], (byte) resultData[i + 3]); content += value
	 * + ","; } content += "\n"; FileWriter fileWritter = null; try {
	 * fileWritter = new FileWriter(file.getAbsolutePath(), true); } catch
	 * (IOException e) { e.printStackTrace(); } BufferedWriter bufferWritter =
	 * new BufferedWriter(fileWritter); try { bufferWritter.write(content);
	 * bufferWritter.close(); } catch (IOException e) { e.printStackTrace(); }
	 * this.calibrationDataChangeObserver.notifyCalibrationSaveCompleted(); }
	 */

	public void setListAdapter(
			ConnectDeviceSensorsListAdapter sensorsListAdapter) {
		this.sensorsListAdapter = sensorsListAdapter;

	}

	public void setCalibrationDataChangeObserver(
			CalibrationDataChangeObserver calibrationDataChangeObserver) {
		this.calibrationDataChangeObserver = calibrationDataChangeObserver;
	}
}
