/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;

import com.biomx.android.client.bluetooth.BioMXCommand;
import com.biomx.android.client.bluetooth.ConnectionService;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.Constants;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class ConfigCommandSender implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Collection<XDevice> devices = DataHolder.connectedDevices.values();
		for (XDevice device : devices) {
			ConnectionService connectionService = DataHolder.connectionServices
					.get(device.getAddress());
			String command = "";
			int deviceSensitivity = device.getSensitivity();
			int sensitivity = 0;
			String sensitivityS = "";
			DecimalFormat decimalFormat = new DecimalFormat("000");
			if (deviceSensitivity != 0) {
				if (!connectionService.isHdr()) {
					sensitivity = Constants.sensitivityAcc
							.get(deviceSensitivity);
					sensitivityS = decimalFormat.format(sensitivity);
					command = BioMXCommand.CONF_ACCELERO_FULL_SCALE.toString()
							.replace("xxx", sensitivityS);
					try {
						connectionService.sendCommand(command);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
					}
				}

				sensitivity = Constants.sensitivityGyro.get(deviceSensitivity);
				sensitivityS = decimalFormat.format(sensitivity);
				command = BioMXCommand.CONF_GYRO_FULL_SCALE.toString().replace(
						"xxx", sensitivityS);
				try {
					connectionService.sendCommand(command);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
				}

				sensitivity = Constants.sensitivityMagn.get(deviceSensitivity);
				sensitivityS = decimalFormat.format(sensitivity);
				command = BioMXCommand.CONF_MAGNETO_FULL_SCALE.toString()
						.replace("xxx", sensitivityS);
				try {
					connectionService.sendCommand(command);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
				}

				if (connectionService.isHdr()
						&& (deviceSensitivity == 100 || deviceSensitivity == 400)) {
					sensitivity = Constants.sensitivityMagn
							.get(deviceSensitivity);
					sensitivityS = decimalFormat.format(sensitivity);
					command = BioMXCommand.CONF_HDR_FULL_SCALE.toString()
							.replace("xxx", sensitivityS);
					try {
						connectionService.sendCommand(command);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
					}
				} else {
					sensitivity = Constants.sensitivityAcc
							.get(deviceSensitivity);
					sensitivityS = decimalFormat.format(sensitivity);
					command = BioMXCommand.CONF_ACCELERO_FULL_SCALE.toString()
							.replace("xxx", sensitivityS);
					try {
						connectionService.sendCommand(command);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
					}
				}

				command = BioMXCommand.STORE_ALL_PARAMS.toString();
				try {
					connectionService.sendCommand(command);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
