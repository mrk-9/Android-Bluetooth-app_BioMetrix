/**
 * 
 */
package com.biomx.android.client.listener;

import java.util.List;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author Raghu
 * 
 */
public class DownloadDataCheckBoxOnCheckedChangeListener implements
		OnCheckedChangeListener {

	private String fileName;
	private List<String> selectedFiles;

	public DownloadDataCheckBoxOnCheckedChangeListener(String fileName,
			List<String> selectedFiles) {
		this.fileName = fileName;
		this.selectedFiles = selectedFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton compoundButton,
			boolean isChecked) {
		if (isChecked) {
			this.selectedFiles.add(fileName);
		} else {
			this.selectedFiles.remove(fileName);
		}

	}

}
