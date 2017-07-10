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
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.biomx.android.client.R;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.listener.UserInfoListCustomPositionCheckBoxCheckedChangeListener;

/**
 * @author Raghu
 * 
 */
public class UserInfoSensorsListAdapter extends BaseAdapter {

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
	public XDevice getItem(int position) {
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
					R.layout.layout_user_info_sensor_item, null);
		}
		XDevice device = (XDevice) this.getItem(position);

		TextView deviceTextView = (TextView) sensorItemView
				.findViewById(R.id.text_view_list_sensor_name);
		deviceTextView.setText(device.getName());

		Spinner positionsSpinner = (Spinner) sensorItemView
				.findViewById(R.id.text_view_list_sensor_position);

		EditText customPositionEditText = (EditText) sensorItemView
				.findViewById(R.id.edit_text_list_sensor_custom_position);

		CheckBox isCustomPositionCheckBox = (CheckBox) sensorItemView
				.findViewById(R.id.text_view_list_sensor_is_custom_position);

		String xPosition = device.getPosition();
		if (xPosition != null && !xPosition.isEmpty()) {
			if (device.isCustomPosition()) {
				customPositionEditText.setText(xPosition);
			} else {
				String[] posArray = context.getResources().getStringArray(
						R.array.sensor_position_list);
				int indexOfPosition = Arrays.asList(posArray)
						.indexOf(xPosition);
				positionsSpinner.setSelection(indexOfPosition);
			}
		}

		OnCheckedChangeListener customPositionCheckedChangeListener = new UserInfoListCustomPositionCheckBoxCheckedChangeListener(
				positionsSpinner, customPositionEditText);
		isCustomPositionCheckBox
				.setOnCheckedChangeListener(customPositionCheckedChangeListener);

		isCustomPositionCheckBox.setChecked(device.isCustomPosition());

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
