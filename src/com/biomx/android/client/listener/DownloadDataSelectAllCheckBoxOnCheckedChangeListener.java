/**
 * 
 */
package com.biomx.android.client.listener;

import com.biomx.android.client.R;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

/**
 * @author Raghu
 * 
 */
public class DownloadDataSelectAllCheckBoxOnCheckedChangeListener implements
		OnCheckedChangeListener {

	private ListView sensitivityListView;

	public DownloadDataSelectAllCheckBoxOnCheckedChangeListener(
			ListView sensitivityListView) {
		this.sensitivityListView = sensitivityListView;
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
		int childCount = this.sensitivityListView.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = this.sensitivityListView.getChildAt(i);
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.text_view_list_file_chooser);
			checkBox.setChecked(isChecked);
		}
		
	}

}
