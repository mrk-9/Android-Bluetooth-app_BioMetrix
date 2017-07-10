package com.biomx.android.client.dialog;

import java.util.Arrays;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.biomx.android.client.R;
import com.biomx.android.client.adapter.SensitivitySettingsListAdapter;
import com.biomx.android.client.listener.SensitivitySettingsDoneButtonOnClickListener;
import com.biomx.android.client.util.SensitivitySetting;

/**
 * @author Raghu
 * 
 */
public class SensitivitySettingsDialog extends Dialog {

	public SensitivitySettingsDialog(Context context) {
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.layout_sensitivity_settings);
		ListView sensitivityListView = (ListView) this
				.findViewById(R.id.list_view_sensitivity_settings);

		SensitivitySetting[] sensitivitySettings = SensitivitySetting.values();
		SensitivitySettingsListAdapter sensitivitySettingsListAdapter = new SensitivitySettingsListAdapter();
		sensitivityListView.setAdapter(sensitivitySettingsListAdapter);
		sensitivitySettingsListAdapter.setSensitivitySettings(Arrays
				.asList(sensitivitySettings));
		sensitivitySettingsListAdapter.notifyDataSetChanged();

		Button doneButton = (Button) this.findViewById(R.id.button_done);
		android.view.View.OnClickListener doneButtonOnClickListener = new SensitivitySettingsDoneButtonOnClickListener(
				this);
		doneButton.setOnClickListener(doneButtonOnClickListener);
	}

}
