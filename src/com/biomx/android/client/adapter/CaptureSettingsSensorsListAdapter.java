/**
 * 
 */
package com.biomx.android.client.adapter;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.listener.SensitivitySeekBarChangeListener;
import com.biomx.android.client.util.BioMXRUtil;
import com.biomx.android.client.util.Constants;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class CaptureSettingsSensorsListAdapter extends BaseAdapter {

	private List<XDevice> devices;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (this.devices == null) {
			return 0;
		}
		return this.devices.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.devices.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return this.devices.get(position).getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)"
	 */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View sensorItemView, ViewGroup parent) {
		Context context = parent.getContext();
		if (sensorItemView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			sensorItemView = inflater.inflate(
					R.layout.layout_capture_settings_sensor_item, null);
		}
		XDevice device = (XDevice) this.getItem(position);

		TextView deviceTextView = (TextView) sensorItemView
				.findViewById(R.id.text_view_list_sensor_name);
		deviceTextView.setText(device.getName());

		Spinner sampleRateSpinner = (Spinner) sensorItemView
				.findViewById(R.id.spinner_list_samplerate);
		String sampleRate = device.getSampleRate() + "";
		if (sampleRate != null && !sampleRate.isEmpty()) {
			String[] posArray = context.getResources().getStringArray(
					R.array.sensor_sample_rate_list);
			int indexOfPosition = Arrays.asList(posArray).indexOf(sampleRate);
			if (indexOfPosition != -1) {
				sampleRateSpinner.setSelection(indexOfPosition);
			}
		}

		SeekBar seekBar = (SeekBar) sensorItemView
				.findViewById(R.id.seek_bar_list_sensitivity);
		seekBar.setMax(100);
		Integer[] sensitivities = Constants.sensitivities;
		ConnectionService connectionService = DataHolder.connectionServices.get(device.getAddress());
		boolean isHdr = connectionService.isHdr();
		OnSeekBarChangeListener sensitivitySeekBarChangeListener = new SensitivitySeekBarChangeListener(
				device, isHdr);
		seekBar.setOnSeekBarChangeListener(sensitivitySeekBarChangeListener);
		if (device.getSensitivity() != 0) {
			List<Integer> asList = Arrays.asList(sensitivities);
			int pos = asList.indexOf(device.getSensitivity());
			seekBar.setProgress(pos > 0 ? (pos * 20) : 0);
		} else {
			seekBar.setProgress(0);
			device.setSensitivity(sensitivities[0]);
		}
		BioMXRUtil.setSeekBarImage(device, seekBar, isHdr);
		return sensorItemView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
	}

	public void setDevices(List<XDevice> devices) {
		this.devices = devices;
	}

	/**
	 * @return the devices
	 */
	public List<XDevice> getDevices() {
		return devices;
	}
}
