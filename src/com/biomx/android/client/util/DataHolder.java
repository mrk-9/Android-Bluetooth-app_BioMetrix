package com.biomx.android.client.util;

import java.util.HashMap;
import java.util.Map;

import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;


public class DataHolder {
	
	public static Object lockConnectionService = new Object();
	public static Map<String, ConnectionService> connectionServices = new HashMap<String, ConnectionService>();
	public static Map<String, XDevice> connectedDevices = new HashMap<String, XDevice>();
	public static Map<ConnectionService, int[]> currentDeviceData = new HashMap<ConnectionService, int[]>();
	public static String homeFolderPath = "/museviewer";
	public static boolean pickRawData = false;
	public static boolean pickQuaternions = false;
}
