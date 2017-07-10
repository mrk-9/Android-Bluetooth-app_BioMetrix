/**
 * 
 */
package com.biomx.android.client.listener;

/**
 * @author Raghu
 *
 */
public interface CalibrationDataChangeListener {
	
	public void onCalibrationCompleted(boolean isCalibrationSuccess);

	public void onCalibrationSaveCompleted();
}
