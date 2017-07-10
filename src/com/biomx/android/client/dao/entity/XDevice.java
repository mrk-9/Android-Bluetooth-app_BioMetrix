/**
 * 
 */
package com.biomx.android.client.dao.entity;

import android.bluetooth.BluetoothDevice;

import com.j256.ormlite.field.DatabaseField;

public class XDevice {

	public static final String TABLE_DEVICE_DATA = "device";

	public static final String COLUMN_ID = "id";

	private static final String COLUMN_NAME = "name";

	public static final String COLUMN_ADDRESS = "address";

	private static final String COLUMN_POSITION = "position";

	private static final String COLUMN_CUSTOM_POSITION = "customPosition";

	private static final String COLUMN_SAMPLE_RATE = "sampleRate";

	private static final String COLUMN_SENSITIVITY = "sensitivity";

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private long id;

	@DatabaseField(columnName = COLUMN_NAME, canBeNull = false)
	private String name;

	@DatabaseField(columnName = COLUMN_ADDRESS, canBeNull = false, unique = true)
	private String address;

	@DatabaseField(columnName = COLUMN_POSITION)
	private String position;

	@DatabaseField(columnName = COLUMN_CUSTOM_POSITION)
	private boolean customPosition;

	@DatabaseField(columnName = COLUMN_SAMPLE_RATE)
	private int sampleRate;
	
	@DatabaseField(columnName = COLUMN_SENSITIVITY)
	private int sensitivity;

	private float battery;

	private BluetoothDevice device;

	private boolean available;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the customPosition
	 */
	public boolean isCustomPosition() {
		return customPosition;
	}

	/**
	 * @param customPosition
	 *            the customPosition to set
	 */
	public void setCustomPosition(boolean customPosition) {
		this.customPosition = customPosition;
	}

	/**
	 * @return the sampleRate
	 */
	public int getSampleRate() {
		return sampleRate;
	}

	/**
	 * @param sampleRate the sampleRate to set
	 */
	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	/**
	 * @return the sensitivity
	 */
	public int getSensitivity() {
		return sensitivity;
	}

	/**
	 * @param sensitivity the sensitivity to set
	 */
	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	/**
	 * @return the battery
	 */
	public float getBattery() {
		return battery;
	}

	/**
	 * @param battery
	 *            the battery to set
	 */
	public void setBattery(float battery) {
		this.battery = battery;
	}

	/**
	 * @return the device
	 */
	public BluetoothDevice getDevice() {
		return device;
	}

	/**
	 * @param device
	 *            the device to set
	 */
	public void setDevice(BluetoothDevice device) {
		this.device = device;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

}
