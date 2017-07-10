/**
 * 
 */
package com.biomx.android.client.bluetooth;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.listener.SensorDataWriter;
import com.biomx.android.client.util.Constants;
import com.biomx.android.client.util.DataHolder;

/**
 * @author prince
 * 
 */
public class ConnectionStreamReader implements Runnable {

	public static int GYRO_FACTOR = 16;
	public static float GYRO_HDR_FACTOR = 8.2f;

	public static List<Byte> commandsFirstChars = new ArrayList<Byte>();
	public static Byte G_BYTE = (byte) 'G';
	public static Byte M_BYTE = (byte) 'M';
	public static Byte A_BYTE = (byte) 'A';
	public static Byte I_BYTE = (byte) 'I';
	public static Byte Q_BYTE = (byte) 'Q';
	public static Byte H_BYTE = (byte) 'H';
	public static List<String> headersList = new ArrayList<String>();
	private List<int[]> dataList = new ArrayList<int[]>();

	static {
		commandsFirstChars.add((byte) 'Q');
		commandsFirstChars.add((byte) 't');
		commandsFirstChars.add((byte) 'C');
		commandsFirstChars.add((byte) 'P');
		commandsFirstChars.add((byte) 'S');
		commandsFirstChars.add((byte) 'N');
		commandsFirstChars.add((byte) 'F');
		commandsFirstChars.add((byte) 'B');
		commandsFirstChars.add((byte) 'T');
		commandsFirstChars.add((byte) 'F');

		headersList.add("QQ");
		headersList.add("tt");
		headersList.add("CC");
		headersList.add("PP");
		headersList.add("SS");
		headersList.add("NN");
		headersList.add("FF");
		headersList.add("BV");
		headersList.add("BQ");
		headersList.add("TT");
		headersList.add("FV");
	}

	private InputStream mmInStream;
	private ConnectionService connectionService;
	private Handler handler;
	private XDevice xDevice;
	private String currentCommand;
	private boolean hdr;
	private int count;
	private File file;

	public ConnectionStreamReader(InputStream mmInStream,
			ConnectionService connectionService, Handler handler,
			XDevice xDevice) {
		this.mmInStream = mmInStream;
		this.connectionService = connectionService;
		this.handler = handler;
		this.xDevice = xDevice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (this.currentCommand == null) {
			return;
		}
		if (this.currentCommand.startsWith("?!CALIB")) {
			try {
				processCALIBCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (this.currentCommand.startsWith("?!TXHDR")) {
			try {
				processTXHDRCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (this.currentCommand.startsWith("?!START")) {
			try {
				processSTARTCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (this.currentCommand
				.equals(BioMXCommand.GET_BATTQ.toString())) {
			try {
				processBQ();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (this.currentCommand.equals(BioMXCommand.GET_HDR_STATUS
				.toString())) {
			try {
				processHDRMode();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (this.currentCommand.equals(BioMXCommand.START_CALIBRATION
				.toString())) {
			try {
				processCalibrationCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (this.currentCommand.equals(BioMXCommand.CALIB_MAGN_DATA
				.toString())) {
			try {
				processCalibrationDataCommand();
			} catch (IOException e) {
			}
		} /*else if (this.currentCommand.equals(BioMXCommand.CALIB_ACC_DATA
				.toString())) {
			try {
				processCalibrationDataCommand();
			} catch (IOException e) {
			}
		} */else if (this.currentCommand.equals(BioMXCommand.HAULT_STREAMING
				.toString())) {
			this.currentCommand = null;
		}
	}

	private void processCalibrationCommand() throws IOException {
		/**
		 * We have to read 8 bytes to check HDR (CALSTATX where x is 1 when the
		 * calibration is success otherwise x is 0)
		 */
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (this.currentCommand != null) {
			char C = (char) this.mmInStream.read(); // C
			if (C == 'C') {
				char A = (char) this.mmInStream.read(); // A
				if (A == 'A') {
					byte[] buffer = new byte[6];
					this.mmInStream.read(buffer);
					int x = buffer[5];
					boolean calibrationSuccess = x > 0;
					Message msg = this.handler.obtainMessage();
					msg.what = Constants.HANDLER_MESSAGE_CALIBRATION_MESSAGE;
					Bundle data = new Bundle();
					data.putBoolean(Constants.HANDLER_MESSAGE_KEY_CALIBRATION,
							calibrationSuccess);
					data.putString(Constants.HANDLER_MESSAGE_KEY_DEVICE,
							this.xDevice.getAddress());
					msg.setData(data);
					this.handler.sendMessage(msg);
					this.currentCommand = null;
				} else {
					Message msg = this.handler.obtainMessage();
					msg.what = Constants.HANDLER_MESSAGE_CALIBRATION_MESSAGE;
					Bundle data = new Bundle();
					data.putBoolean(Constants.HANDLER_MESSAGE_KEY_CALIBRATION,
							false);
					msg.setData(data);
					this.handler.sendMessage(msg);
					break;
				}
			} else {
				if (this.currentCommand == null) {
					Message msg = this.handler.obtainMessage();
					msg.what = Constants.HANDLER_MESSAGE_CALIBRATION_MESSAGE;
					Bundle data = new Bundle();
					data.putBoolean(Constants.HANDLER_MESSAGE_KEY_CALIBRATION,
							false);
					msg.setData(data);
					this.handler.sendMessage(msg);
					break;
				} else {
					continue;
				}
			}
		}
	}

	private void processHDRMode() throws IOException {
		/**
		 * We have to read 8 bytes to check HDR (HDRMUSEx where x is 1 when the
		 * platform is HDR otherwise x is 0)
		 */
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode Started");
		int h = this.mmInStream.read();
		Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + h);
		int count = 0;
		while(h != 72 && count < 10){
			count++;
			h = this.mmInStream.read();
			Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + h);
		}
		int d = this.mmInStream.read();
		Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + d);
		int r = this.mmInStream.read();
		Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + r);
		if (h == 72 && d == 68 && r == 82) {
			int y = this.mmInStream.read();
			Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + y);
			y = this.mmInStream.read();
			Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + y);
			y = this.mmInStream.read();
			Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + y);
			y = this.mmInStream.read();
			Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + y);
			int x = this.mmInStream.read();
			Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode - Value " + x);
			this.hdr = x > 0;
		}
		this.currentCommand = null;
		Log.d("com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER", "com.biomx.android.client.bluetooth.CONNECTIONSTREAMREADER :: Checking Is HDR Mode Ended and the result is " + this.hdr);
	}

	private void processBQ() throws IOException {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		int firstRead = 0;
		int secondRead = 0;
		firstRead = mmInStream.read();
		int count = 0;
		while(firstRead != 66 && count < 10){
			firstRead = mmInStream.read();
			count++;
		}
		if (firstRead == 66) {
			secondRead = mmInStream.read();
			if (secondRead == 81) {
				byte[] buffer = new byte[2];
				this.mmInStream.read(buffer);
				Message msg = this.handler.obtainMessage();
				msg.what = Constants.HANDLER_MESSAGE_BATTERY_STATE_CHANGE;
				Bundle data = new Bundle();
				data.putFloat(Constants.HANDLER_MESSAGE_KEY_BATTERY, buffer[0]);
				data.putString(Constants.HANDLER_MESSAGE_KEY_DEVICE,
						xDevice.getAddress());
				msg.setData(data);
				this.handler.sendMessage(msg);
			}
		}
		this.currentCommand = null;
	}

	private void processCALIBCommand() throws IOException {
		while (this.currentCommand != null) {
			int firstRead = 0;
			int secondRead = 0;
			firstRead = mmInStream.read();
			if (firstRead == 116) {
				secondRead = mmInStream.read();
				if (secondRead == 116) {
					int[] buffer = new int[52];
					for (int i = 0; i < 52; i++) {
						buffer[i] = this.mmInStream.read();
					}
					count++;
					if (count == 10000) {
						writeData();
						count = 0;
					}
					this.dataList.add(buffer);
					DataHolder.currentDeviceData.put(this.connectionService,
							buffer);
				}
			}
		}
	}

	public void writeData() {
		List<int[]> _dataList = new ArrayList<int[]>();
		_dataList.addAll(dataList);
		dataList.clear();
		SensorDataWriter sensorDataWriter = new SensorDataWriter(file,
				_dataList);
		Thread sensorDataWriteThread = new Thread(sensorDataWriter);
		sensorDataWriteThread.start();
	}

	private void processTXHDRCommand() throws IOException {
		while (this.currentCommand != null) {
			int firstRead = 0;
			int secondRead = 0;
			firstRead = mmInStream.read();
			if (firstRead == 116) {
				secondRead = mmInStream.read();
				if (secondRead == 116) {
					int[] buffer = new int[34];
					for (int i = 0; i < 34; i++) {
						buffer[i] = this.mmInStream.read();
					}
					count++;
					if (count == 10000) {
						writeData();
						count = 0;
					}
					this.dataList.add(buffer);
					DataHolder.currentDeviceData.put(this.connectionService,
							buffer);
				}
			}
		}
	}

	private void processSTARTCommand() throws IOException {
		while (this.currentCommand != null) {
			int firstRead = 0;
			int secondRead = 0;
			firstRead = mmInStream.read();
			if (firstRead == Q_BYTE) {
				secondRead = mmInStream.read();
				if (secondRead == Q_BYTE) {
					int[] buffer = new int[16];
					for (int i = 0; i < 16; i++) {
						buffer[i] = this.mmInStream.read();
					}
					count++;
					if (count == 10000) {
						writeData();
						count = 0;
					}
					if (count != 0 && count % 10000 == 0) {
						writeData();
					}
					this.dataList.add(buffer);
					DataHolder.currentDeviceData.put(this.connectionService,
							buffer);
				}
			}
		}
	}

	private void processCalibrationDataCommand() throws IOException {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		char C = (char) this.mmInStream.read();
		if (C == 'C') {
			C = (char) this.mmInStream.read();
			if (C == 'C') {
				int[] result = new int[48];
				for (int i = 0; i < 48; i++) {
					result[i] = this.mmInStream.read();
				}
				Message msg = this.handler.obtainMessage();
				Bundle data = new Bundle();
				if (this.currentCommand.equals(BioMXCommand.CALIB_MAGN_DATA.toString())) {
					msg.what = Constants.HANDLER_MESSAGE_CALIBRATION_DATA_MAGN_RECEIVED;
					data.putIntArray(
							Constants.HANDLER_MESSAGE_KEY_MAGNETOMETER_CALIB_DATA,
							result);
				} /*else if (this.currentCommand
						.equals(BioMXCommand.CALIB_ACC_DATA.toString())) {
					msg.what = Constants.HANDLER_MESSAGE_CALIBRATION_DATA_ACC_RECEIVED;
					data.putIntArray(
							Constants.HANDLER_MESSAGE_KEY_ACCELEROMETER_CALIB_DATA,
							result);
				}*/
				data.putString(Constants.HANDLER_MESSAGE_KEY_DEVICE,
						xDevice.getAddress());
				msg.setData(data);
				this.handler.sendMessage(msg);
			}
		}
		this.currentCommand = null;
	}

	/**
	 * @return the dataList
	 */
	public List<int[]> getDataList() {
		return dataList;
	}

	public void setCurrentCommand(String currentCommand) {
		this.currentCommand = currentCommand;
	}

	/**
	 * @return the hdr
	 */
	public boolean isHdr() {
		return hdr;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
