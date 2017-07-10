package com.biomx.android.client.activity;

import java.io.File;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.biomx.android.client.R;
import com.biomx.android.client.listener.CaptureSessionButtonClickListener;
import com.biomx.android.client.listener.CaptureSessionMenuButtonClickListener;
import com.biomx.android.client.listener.ConnectDeviceMenuButtonClickListener;
import com.biomx.android.client.listener.SetCaptureSettingsMenuButtonClickListener;
import com.biomx.android.client.listener.SetUserInfoMenuButtonClickListener;
import com.biomx.android.client.listener.SettingsMenuButtonClickListener;
import com.biomx.android.client.util.DataHolder;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		File root = android.os.Environment.getExternalStorageDirectory();
		File dir = new File(root.getAbsolutePath() + DataHolder.homeFolderPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.enable();
		}

		/*Intent intent = new Intent(this, AlarmBroadCastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 2123, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP,
				SystemClock.currentThreadTimeMillis(), 1000 * 60, pendingIntent);*/

		RelativeLayout contentContainer = (RelativeLayout) this
				.findViewById(R.id.content_container);

		Button[] menuButtons = new Button[6];
		Button captureSessionButton = (Button) this
				.findViewById(R.id.button_capture_session);
		menuButtons[0] = captureSessionButton;

		Button connectDeviceButton = (Button) this
				.findViewById(R.id.button_connect_device);
		connectDeviceButton.setEnabled(false);
		menuButtons[1] = connectDeviceButton;

		Button setUserInfoButton = (Button) this
				.findViewById(R.id.button_set_user_info);
		setUserInfoButton.setEnabled(false);
		menuButtons[2] = setUserInfoButton;

		Button setCaptureSettingsButton = (Button) this
				.findViewById(R.id.button_set_capture_settings);
		setCaptureSettingsButton.setEnabled(false);
		menuButtons[3] = setCaptureSettingsButton;

		Button captureSessButton = (Button) this
				.findViewById(R.id.button_capture_session_1);
		captureSessButton.setEnabled(false);
		menuButtons[4] = captureSessButton;

		Button settingsButton = (Button) this
				.findViewById(R.id.button_settings);
		menuButtons[5] = settingsButton;

		OnClickListener captureSessionButtonClickListener = new CaptureSessionMenuButtonClickListener(
				contentContainer, this, menuButtons);
		captureSessionButton
				.setOnClickListener(captureSessionButtonClickListener);

		OnClickListener connectDeviceButtonClickListener = new ConnectDeviceMenuButtonClickListener(
				contentContainer, this, menuButtons);
		connectDeviceButton
				.setOnClickListener(connectDeviceButtonClickListener);

		OnClickListener setUserInfoButtonClickListener = new SetUserInfoMenuButtonClickListener(
				contentContainer, this, menuButtons);
		setUserInfoButton.setOnClickListener(setUserInfoButtonClickListener);

		OnClickListener setCaptureSettingsButtonClickListener = new SetCaptureSettingsMenuButtonClickListener(
				contentContainer, this, menuButtons);
		setCaptureSettingsButton
				.setOnClickListener(setCaptureSettingsButtonClickListener);

		OnClickListener captureSessButtonClickListener = new CaptureSessionButtonClickListener(
				contentContainer, this, menuButtons);
		captureSessButton.setOnClickListener(captureSessButtonClickListener);

		OnClickListener settingsButtonClickListener = new SettingsMenuButtonClickListener(
				contentContainer, this, menuButtons);
		settingsButton.setOnClickListener(settingsButtonClickListener);

		captureSessionButton.callOnClick();
//		captureSessButton.callOnClick();
//		setCaptureSettingsButton.callOnClick();
	}

}
