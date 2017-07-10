/**
 * 
 */
package com.biomx.android.client.util;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.SeekBar;

import com.biomx.android.client.R;
import com.biomx.android.client.adapter.ConnectDeviceSensorsListAdapter;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dao.manager.XDeviceDaoManager;

/**
 * @author Raghu
 * 
 */
public class BioMXRUtil {

	public static void refreshPairedSensorsList(
			ConnectDeviceSensorsListAdapter sensorsListAdapter, Context context) {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		Set<BluetoothDevice> bondedBluetoothDevices = bluetoothAdapter
				.getBondedDevices();
		List<XDevice> devices = new ArrayList<XDevice>();
		int i = 0;
		XDeviceDaoManager xDeviceDaoManager = new XDeviceDaoManager(context);
		for (BluetoothDevice bluetoothDevice : bondedBluetoothDevices) {
			String name = bluetoothDevice.getName();
			String nameLowerCase = name.toLowerCase(Locale.ENGLISH);
			if (nameLowerCase.startsWith("muse_")
					|| nameLowerCase.startsWith("biomx_")) {
				XDevice device = null;
				try {
					device = xDeviceDaoManager.getBy(bluetoothDevice
							.getAddress());
				} catch (SQLException e) {
				}
				if (device == null) {
					device = new XDevice();
					device.setId(i++);
					device.setAddress(bluetoothDevice.getAddress());
					device.setName(name);
				}
				device.setDevice(bluetoothDevice);
				devices.add(device);
			}
		}
		sensorsListAdapter.setDevices(devices);
		sensorsListAdapter.notifyDataSetChanged();
	}

	public static short decodeShortData(byte msB, byte lsB) {
		byte[] buffer = new byte[] { msB, lsB };
		return ByteBuffer.wrap(buffer).getShort();
	}

	public static int decodeIntData(byte b4, byte b3, byte b2, byte b1) {
		byte[] buffer = new byte[] { b4, b3, b2, b1 };
		return ByteBuffer.wrap(buffer).getInt();
	}

	public static float decodeFloatData(byte b4, byte b3, byte b2, byte b1) {
		byte[] buffer = new byte[] { b4, b3, b2, b1 };
		return ByteBuffer.wrap(buffer).getFloat();
	}

	public static void setSeekBarImage(XDevice device, SeekBar seekBar,
			boolean isHdr) {
		switch (device.getSensitivity()) {
		case 2:
			seekBar.setBackgroundResource(isHdr ? R.drawable.slider_hdr_2g
					: R.drawable.slider_reg_2g);
			break;

		case 4:
			seekBar.setBackgroundResource(isHdr ? R.drawable.slider_hdr_4g
					: R.drawable.slider_reg_4g);
			break;

		case 8:
			seekBar.setBackgroundResource(isHdr ? R.drawable.slider_hdr_8g
					: R.drawable.slider_reg_8g);
			break;

		case 16:
			seekBar.setBackgroundResource(isHdr ? R.drawable.slider_hdr_16g
					: R.drawable.slider_reg_16g);
			break;

		case 100:
			seekBar.setBackgroundResource(R.drawable.slider_hdr_100g);
			break;

		case 400:
			seekBar.setBackgroundResource(R.drawable.slider_hdr_400g);
			break;

		default:
			break;
		}
	}
}
