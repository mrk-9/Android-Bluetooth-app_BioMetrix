/**
 * 
 */
package com.biomx.android.client.listener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raghu
 * 
 */
public class CalibrationDataChangeObserver {

	private List<CalibrationDataChangeListener> dataChangeListeners = new ArrayList<CalibrationDataChangeListener>();

	public void addDataChangeListener(
			CalibrationDataChangeListener dataChangeListener) {
		dataChangeListeners.add(dataChangeListener);
	}

	public void notifyCalibrationCompleted(boolean isCalibrationSuccess) {
		for (CalibrationDataChangeListener dataChangeListener : this.dataChangeListeners) {
			dataChangeListener.onCalibrationCompleted(isCalibrationSuccess);
		}
	}

	public void notifyCalibrationSaveCompleted() {
		for (CalibrationDataChangeListener dataChangeListener : this.dataChangeListeners) {
			dataChangeListener.onCalibrationSaveCompleted();
		}
	}

}
