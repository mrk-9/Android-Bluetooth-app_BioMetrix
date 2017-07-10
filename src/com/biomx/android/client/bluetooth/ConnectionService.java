/**
 * 
 */
package com.biomx.android.client.bluetooth;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.handler.ConnectionHandler;
import com.biomx.android.client.util.Constants;

/**
 * @author prince
 * 
 */
public class ConnectionService {

	// Serial Port Profile UUID
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private BluetoothDevice device;
	private ConnectionHandler handler;
	private BluetoothAdapter mBtAdapter;
	private BluetoothSocket mmSocket;
	private InputStream mmInStream;
	private OutputStream mmOutStream;
	private Thread connectionReader;
	private String address;
	private ConnectionStreamReader connectionStreamReader;
	private Handler refreshGraphUIHandler;
	private XDevice xDevice;

	public ConnectionService(BluetoothDevice device, ConnectionHandler handler,
			XDevice xDevice) {
		this.device = device;
		this.handler = handler;
		this.xDevice = xDevice;
		this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public boolean isConnected() {
		if (this.mmSocket != null) {
			return this.mmSocket.isConnected();
		}
		return false;
	}

	public void connect() throws IOException {
		// Always cancel discovery because it will slow down a connection
		mBtAdapter.cancelDiscovery();
		// Get a BluetoothSocket for a connection with the given BluetoothDevice
		this.mmSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
		this.mmSocket.connect();
		this.mmInStream = this.mmSocket.getInputStream();
		this.mmOutStream = this.mmSocket.getOutputStream();
		this.address = device.getAddress();
		connectionStreamReader = new ConnectionStreamReader(this.mmInStream,
				this, this.handler, this.xDevice);
		this.sendCommand(BioMXCommand.HAULT_STREAMING);
		this.sendCommand(BioMXCommand.GET_BATTQ);
		Message msg = this.handler.obtainMessage();
		msg.what = Constants.HANDLER_MESSAGE_STATE_CHANGE;
		this.handler.sendMessage(msg);
	}

	public void disconnect() {
		if (this.connectionReader != null) {
			this.connectionReader.interrupt();
		}
		if (this.mmInStream != null) {
			try {
				this.mmInStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (this.mmOutStream != null) {
			try {
				this.mmOutStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (this.mmSocket != null) {
			try {
				this.mmSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Message msg = this.handler.obtainMessage();
		msg.what = Constants.HANDLER_MESSAGE_STATE_CHANGE;
		this.handler.sendMessage(msg);
	}

	public void sendCommand(String cmd) throws IOException {
		if (this.connectionReader != null) {
			this.connectionReader.interrupt();
		}
		this.connectionStreamReader.setCurrentCommand(cmd);
		this.connectionReader = new Thread(connectionStreamReader);
		this.connectionReader.start();
		sendCommand(cmd.getBytes());
	}

	private void sendCommand(byte[] bytes) throws IOException {
		this.mmOutStream.write(bytes);
	}

	public void sendCommand(BioMXCommand command) throws IOException {
		this.sendCommand(command.toString());
	}
	
	public void stop() {
		if (this.connectionReader != null) {
			this.connectionReader.interrupt();
		}
		this.connectionStreamReader.setCurrentCommand(null);
	}

	public String getAddress() {
		return address;
	}

	public ConnectionHandler getHandler() {
		return this.handler;
	}

	public Handler getRefreshGraphUIHandler() {
		return refreshGraphUIHandler;
	}

	public void setRefreshGraphUIHandler(Handler refreshGraphUIHandler) {
		this.refreshGraphUIHandler = refreshGraphUIHandler;
	}

	public List<int[]> getDataList() {
		return this.connectionStreamReader.getDataList();
	}

	public boolean isHdr() {
		return this.connectionStreamReader.isHdr();
	}

	public void writeData() {
		this.connectionStreamReader.writeData();
	}

	public void setFile(File file) {
		this.connectionStreamReader.setFile(file);
	}
	
	public int getCount() {
		return this.connectionStreamReader.getCount();
	}

	public void setCount(int count) {
		this.connectionStreamReader.setCount(count);
	}
}
