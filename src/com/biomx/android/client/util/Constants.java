/**
 * 
 */
package com.biomx.android.client.util;

import android.util.SparseIntArray;

/**
 * @author Raghu
 * 
 */
public class Constants {

	public static final String DATABASE_NAME = "museviwer";

	public static final int HANDLER_MESSAGE_SUCCESS = 100;
	public static final int HANDLER_MESSAGE_STATE_CHANGE = 101;
	public static final int HANDLER_MESSAGE_BATTERY_STATE_CHANGE = 102;
	public static final int HANDLER_MESSAGE_GRAPH_DATA_CHANGE = 103;
	public static final int HANDLER_MESSAGE_CALIBRATION_MESSAGE = 104;
	public static final int HANDLER_MESSAGE_CALIBRATION_DATA_MAGN_RECEIVED = 105;
	/*public static final int HANDLER_MESSAGE_CALIBRATION_DATA_ACC_RECEIVED = 106;*/
	
	public static final int HANDLER_MESSAGE_FAILURE = 200;

	public static final String HANDLER_TOAST_MESSAGE = "toast_msg";
	public static final String HANDLER_MESSAGE_KEY_BATTERY = "battery";
	public static final String HANDLER_MESSAGE_KEY_DEVICE = "device";
	public static final String HANDLER_MESSAGE_KEY_CALIBRATION = "calibration";
	public static final String HANDLER_MESSAGE_KEY_MAGNETOMETER_CALIB_DATA = "magnCalibData";
	/*public static final String HANDLER_MESSAGE_KEY_ACCELEROMETER_CALIB_DATA = "accCalibData";*/


	public static SparseIntArray graphYAcc = new SparseIntArray();
	public static SparseIntArray graphYGyro = new SparseIntArray();
	public static SparseIntArray graphYMagn = new SparseIntArray();

	public static SparseIntArray sensitivityAcc = new SparseIntArray();
	public static SparseIntArray sensitivityGyro = new SparseIntArray();
	public static SparseIntArray sensitivityMagn = new SparseIntArray();

	public static Integer[] sensitivities = new Integer[] { 2, 4, 8, 16, 100,
			400 };

	static {
		graphYAcc.clear();
		graphYAcc.put(sensitivities[0], 2000);
		graphYAcc.put(sensitivities[1], 4000);
		graphYAcc.put(sensitivities[1], 8000);
		graphYAcc.put(sensitivities[3], 16000);
		graphYAcc.put(sensitivities[4], 4000);
		graphYAcc.put(sensitivities[5], 4000);

		graphYGyro.clear();
		graphYGyro.put(sensitivities[0], 250);
		graphYGyro.put(sensitivities[1], 250);
		graphYGyro.put(sensitivities[1], 500);
		graphYGyro.put(sensitivities[3], 500);
		graphYGyro.put(sensitivities[4], 2000);
		graphYGyro.put(sensitivities[5], 2000);

		graphYMagn.clear();
		graphYMagn.put(sensitivities[0], 4000);
		graphYMagn.put(sensitivities[1], 4000);
		graphYMagn.put(sensitivities[1], 8000);
		graphYMagn.put(sensitivities[3], 16000);
		graphYMagn.put(sensitivities[4], 16000);
		graphYMagn.put(sensitivities[5], 16000);

		sensitivityAcc.clear();
		sensitivityAcc.put(sensitivities[0], 0);
		sensitivityAcc.put(sensitivities[1], 16);
		sensitivityAcc.put(sensitivities[2], 24);
		sensitivityAcc.put(sensitivities[3], 8);
		sensitivityAcc.put(sensitivities[4], 0);
		sensitivityAcc.put(sensitivities[5], 48);

		sensitivityGyro.clear();
		sensitivityGyro.put(sensitivities[0], 0);
		sensitivityGyro.put(sensitivities[1], 0);
		sensitivityGyro.put(sensitivities[2], 8);
		sensitivityGyro.put(sensitivities[3], 8);
		sensitivityGyro.put(sensitivities[4], 24);
		sensitivityGyro.put(sensitivities[5], 24);

		sensitivityMagn.clear();
		sensitivityMagn.put(sensitivities[0], 0);
		sensitivityMagn.put(sensitivities[1], 32);
		sensitivityMagn.put(sensitivities[2], 64);
		sensitivityMagn.put(sensitivities[3], 96);
		sensitivityMagn.put(sensitivities[4], 96);
		sensitivityMagn.put(sensitivities[5], 96);
	}
}
