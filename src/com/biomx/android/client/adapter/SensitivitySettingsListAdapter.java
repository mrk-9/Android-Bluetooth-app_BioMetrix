/**
 * 
 */
package com.biomx.android.client.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.biomx.android.client.R;
import com.biomx.android.client.util.SensitivitySetting;

/**
 * @author Raghu
 * 
 */
public class SensitivitySettingsListAdapter extends BaseAdapter {

	private List<SensitivitySetting> sensitivitySettings;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (this.sensitivitySettings == null) {
			return 0;
		}
		return this.sensitivitySettings.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.sensitivitySettings.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)"
	 */
	@Override
	public View getView(int position, View sensitivityItemView, ViewGroup parent) {
		Context context = parent.getContext();
		if (sensitivityItemView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			sensitivityItemView = inflater.inflate(
					R.layout.layout_sensitivity_settings_item, null);
		}
		SensitivitySetting sensitivitySetting = (SensitivitySetting) this.getItem(position);

		TextView settingsTextView = (TextView) sensitivityItemView
				.findViewById(R.id.text_view_list_setting);
		settingsTextView.setText(sensitivitySetting.getSetting());

		TextView accTextView = (TextView) sensitivityItemView
				.findViewById(R.id.text_view_list_accg);
		accTextView.setText(sensitivitySetting.getAcc());

		TextView gyroTextView = (TextView) sensitivityItemView
				.findViewById(R.id.text_view_list_gyrodps);
		gyroTextView.setText(sensitivitySetting.getGyro());

		TextView magnTextView = (TextView) sensitivityItemView
				.findViewById(R.id.text_view_list_magnmga);
		magnTextView.setText(sensitivitySetting.getMagn());

		return sensitivityItemView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
	}

	public void setSensitivitySettings(
			List<SensitivitySetting> sensitivitySettings) {
		this.sensitivitySettings = sensitivitySettings;
	}

	/**
	 * @return the sensitivitySettings
	 */
	public List<SensitivitySetting> getSensitivitySettings() {
		return sensitivitySettings;
	}
}
