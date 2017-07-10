/**
 * 
 */
package com.biomx.android.client.listener;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * @author Raghu
 * 
 */
public class UserInfoListCustomPositionCheckBoxCheckedChangeListener implements
		OnCheckedChangeListener {

	private Spinner positionsSpinner;
	private EditText customPositionEditText;

	public UserInfoListCustomPositionCheckBoxCheckedChangeListener(
			Spinner positionsSpinner, EditText customPositionEditText) {
		this.positionsSpinner = positionsSpinner;
		this.customPositionEditText = customPositionEditText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
		if (isChecked) {
			this.positionsSpinner.setVisibility(View.GONE);
			this.customPositionEditText.setVisibility(View.VISIBLE);
		} else {
			this.positionsSpinner.setVisibility(View.VISIBLE);
			this.customPositionEditText.setVisibility(View.GONE);
		}
	}

}
