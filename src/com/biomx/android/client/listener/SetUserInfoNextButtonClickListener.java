/**
 * 
 */
package com.biomx.android.client.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.biomx.android.client.R;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dao.manager.XDeviceDaoManager;

/**
 * @author Raghu
 * 
 */
public class SetUserInfoNextButtonClickListener implements OnClickListener {

	private Context context;
	private Button nextMenu;
	private ListView sensorsListView;

	public SetUserInfoNextButtonClickListener(Context context, Button nextMenu,
			ListView sensorsListView) {
		this.context = context;
		this.nextMenu = nextMenu;
		this.sensorsListView = sensorsListView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		/*boolean hasCustomPosition = false;
		boolean hasPositionHip = false;*/
		ListAdapter adapter = sensorsListView.getAdapter();
		List<XDevice> devices = new ArrayList<XDevice>();
		for (int i = 0; i < sensorsListView.getCount(); i++) {
			XDevice device = (XDevice) adapter.getItem(i);
			String position = "";

			View sensorItemView = sensorsListView.getChildAt(i);
			CheckBox isCustomPositionComp = (CheckBox) sensorItemView
					.findViewById(R.id.text_view_list_sensor_is_custom_position);
			boolean isCustomPosition = isCustomPositionComp.isChecked();

			TextView deviceTextView = (TextView) sensorItemView
					.findViewById(R.id.text_view_list_sensor_name);
			if (isCustomPosition) {
				/*hasCustomPosition = true;*/
				EditText customPositionEditText = (EditText) sensorItemView
						.findViewById(R.id.edit_text_list_sensor_custom_position);
				position = customPositionEditText.getText().toString();
			} else {
				Spinner positionsSpinner = (Spinner) sensorItemView
						.findViewById(R.id.text_view_list_sensor_position);
				position = positionsSpinner.getSelectedItem().toString();
			}

			if (position.matches("")) {
				String errorMsg = this.context.getText(
						R.string.notification_error_invalid_device_position)
						.toString();
				Toast.makeText(
						this.context,
						String.format(errorMsg, deviceTextView.getText()
								.toString()), Toast.LENGTH_LONG).show();
				return;
			}
			/*if (position.toLowerCase(Locale.ENGLISH).equals("hips")) {
				hasPositionHip = true;
			}*/
			device.setPosition(position);
			device.setCustomPosition(isCustomPosition);
			devices.add(device);

		}
		/*if (hasCustomPosition && !hasPositionHip) {
			Toast.makeText(this.context,
					R.string.notification_error_custom_hip_position_required,
					Toast.LENGTH_LONG).show();
			return;
		}*/

		XDeviceDaoManager deviceDaoManager = new XDeviceDaoManager(context);
		try {
			deviceDaoManager.update(devices);
		} catch (SQLException e) {
			e.printStackTrace();
			Toast.makeText(this.context,
					R.string.notification_error_update_device_position,
					Toast.LENGTH_LONG).show();
			return;
		}

		this.nextMenu.callOnClick();
	}

}
