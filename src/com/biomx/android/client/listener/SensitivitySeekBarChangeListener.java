/**
 * 
 */
package com.biomx.android.client.listener;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.BioMXRUtil;
import com.biomx.android.client.util.Constants;

/**
 * @author Raghu
 * 
 */
public class SensitivitySeekBarChangeListener implements
		OnSeekBarChangeListener {

	private XDevice device;
	private boolean isHdr;

	public SensitivitySeekBarChangeListener(XDevice device, boolean isHdr) {
		this.device = device;
		this.isHdr = isHdr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.SeekBar.OnSeekBarChangeListener#onProgressChanged(android
	 * .widget.SeekBar, int, boolean)
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress > 15 && progress < 20) {
			progress = 21;
		}
		progress = ((int) Math.round(progress / 20)) * 20;
		seekBar.setProgress(progress);
		int sensitivityIndex = progress / 20;
		if (sensitivityIndex > Constants.sensitivities.length - 1) {
			sensitivityIndex = Constants.sensitivities.length - 1;
		}
		if (!isHdr && sensitivityIndex > 3) {
			sensitivityIndex = 3;
		}
		device.setSensitivity(Constants.sensitivities[sensitivityIndex]);
		BioMXRUtil.setSeekBarImage(device, seekBar, isHdr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.SeekBar.OnSeekBarChangeListener#onStartTrackingTouch(android
	 * .widget.SeekBar)
	 */
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(android
	 * .widget.SeekBar)
	 */
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

}
