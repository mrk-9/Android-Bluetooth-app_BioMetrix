package com.biomx.android.client.dao.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = DeviceData.TABLE_DEVICE_DATA)
public class DeviceData {

	public static final String TABLE_DEVICE_DATA = "devicedata";

	public static final String COLUMN_ID = "id";

	public static final String COLUMN_INDEX = "ii";

	public static final String COLUMN_ACCX = "ax";

	public static final String COLUMN_ACCY = "ay";

	public static final String COLUMN_ACCZ = "az";

	public static final String COLUMN_MAGNX = "mx";

	public static final String COLUMN_MAGNY = "my";

	public static final String COLUMN_MAGNZ = "mz";

	public static final String COLUMN_GYROX = "gx";

	public static final String COLUMN_GYROY = "gy";

	public static final String COLUMN_GYROZ = "gz";

	public static final String COLUMN_QW = "qw";

	public static final String COLUMN_QX = "qx";

	public static final String COLUMN_QY = "qy";

	public static final String COLUMN_QZ = "qz";

	public static final String COLUMN_TIME = "tt";

	public static final String COLUMN_EX = "ex";

	public static final String COLUMN_EY = "ey";

	public static final String COLUMN_EZ = "ez";

	public static final String COLUMN_AHX = "ahx";

	public static final String COLUMN_AHY = "ahy";

	public static final String COLUMN_AHZ = "ahz";

	public static final String COLUMN_DEVICE_NAME = "deviceName";

	public static final String COLUMN_DEVICE_ADDRESS = "deviceAddress";

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private long id;

	@DatabaseField(columnName = COLUMN_INDEX)
	private int index;

	@DatabaseField(columnName = COLUMN_ACCX)
	private short ax;

	@DatabaseField(columnName = COLUMN_ACCY)
	private short ay;

	@DatabaseField(columnName = COLUMN_ACCZ)
	private short az;

	@DatabaseField(columnName = COLUMN_MAGNX)
	private short mx;

	@DatabaseField(columnName = COLUMN_MAGNY)
	private short my;

	@DatabaseField(columnName = COLUMN_MAGNZ)
	private short mz;

	@DatabaseField(columnName = COLUMN_GYROX)
	private float gx;

	@DatabaseField(columnName = COLUMN_GYROY)
	private float gy;

	@DatabaseField(columnName = COLUMN_GYROZ)
	private float gz;

	@DatabaseField(columnName = COLUMN_QW)
	private float qw;

	@DatabaseField(columnName = COLUMN_QX)
	private float qx;

	@DatabaseField(columnName = COLUMN_QY)
	private float qy;

	@DatabaseField(columnName = COLUMN_QZ)
	private float qz;

	@DatabaseField(columnName = COLUMN_TIME)
	private int tt;

	@DatabaseField(columnName = COLUMN_EX)
	private float ex;

	@DatabaseField(columnName = COLUMN_EY)
	private float ey;

	@DatabaseField(columnName = COLUMN_EZ)
	private float ez;

	@DatabaseField(columnName = COLUMN_AHX)
	private int hax;

	@DatabaseField(columnName = COLUMN_AHY)
	private int hay;

	@DatabaseField(columnName = COLUMN_AHZ)
	private int haz;

	@DatabaseField(columnName = COLUMN_DEVICE_NAME, canBeNull = false)
	private String deviceName;

	@DatabaseField(columnName = COLUMN_DEVICE_ADDRESS, canBeNull = false)
	private String deviceAddress;

	private long deviceTime;

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
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the ax
	 */
	public short getAx() {
		return ax;
	}

	/**
	 * @param ax
	 *            the ax to set
	 */
	public void setAx(short ax) {
		this.ax = ax;
	}

	/**
	 * @return the ay
	 */
	public short getAy() {
		return ay;
	}

	/**
	 * @param ay
	 *            the ay to set
	 */
	public void setAy(short ay) {
		this.ay = ay;
	}

	/**
	 * @return the az
	 */
	public short getAz() {
		return az;
	}

	/**
	 * @param az
	 *            the az to set
	 */
	public void setAz(short az) {
		this.az = az;
	}

	/**
	 * @return the mx
	 */
	public short getMx() {
		return mx;
	}

	/**
	 * @param mx
	 *            the mx to set
	 */
	public void setMx(short mx) {
		this.mx = mx;
	}

	/**
	 * @return the my
	 */
	public short getMy() {
		return my;
	}

	/**
	 * @param my
	 *            the my to set
	 */
	public void setMy(short my) {
		this.my = my;
	}

	/**
	 * @return the mz
	 */
	public short getMz() {
		return mz;
	}

	/**
	 * @param mz
	 *            the mz to set
	 */
	public void setMz(short mz) {
		this.mz = mz;
	}

	/**
	 * @return the gx
	 */
	public float getGx() {
		return gx;
	}

	/**
	 * @param gx
	 *            the gx to set
	 */
	public void setGx(float gx) {
		this.gx = gx;
	}

	/**
	 * @return the gy
	 */
	public float getGy() {
		return gy;
	}

	/**
	 * @param gy
	 *            the gy to set
	 */
	public void setGy(float gy) {
		this.gy = gy;
	}

	/**
	 * @return the gz
	 */
	public float getGz() {
		return gz;
	}

	/**
	 * @param gz
	 *            the gz to set
	 */
	public void setGz(float gz) {
		this.gz = gz;
	}

	/**
	 * @return the qw
	 */
	public float getQw() {
		return qw;
	}

	/**
	 * @param qw
	 *            the qw to set
	 */
	public void setQw(float qw) {
		this.qw = qw;
	}

	/**
	 * @return the qx
	 */
	public float getQx() {
		return qx;
	}

	/**
	 * @param qx
	 *            the qx to set
	 */
	public void setQx(float qx) {
		this.qx = qx;
	}

	/**
	 * @return the qy
	 */
	public float getQy() {
		return qy;
	}

	/**
	 * @param qy
	 *            the qy to set
	 */
	public void setQy(float qy) {
		this.qy = qy;
	}

	/**
	 * @return the qz
	 */
	public float getQz() {
		return qz;
	}

	/**
	 * @param qz
	 *            the qz to set
	 */
	public void setQz(float qz) {
		this.qz = qz;
	}

	/**
	 * @return the tt
	 */
	public int getTt() {
		return tt;
	}

	/**
	 * @param tt
	 *            the tt to set
	 */
	public void setTt(int tt) {
		this.tt = tt;
	}

	/**
	 * @return the ex
	 */
	public float getEx() {
		return ex;
	}

	/**
	 * @param ex
	 *            the ex to set
	 */
	public void setEx(float ex) {
		this.ex = ex;
	}

	/**
	 * @return the ey
	 */
	public float getEy() {
		return ey;
	}

	/**
	 * @param ey
	 *            the ey to set
	 */
	public void setEy(float ey) {
		this.ey = ey;
	}

	/**
	 * @return the ez
	 */
	public float getEz() {
		return ez;
	}

	/**
	 * @param ez
	 *            the ez to set
	 */
	public void setEz(float ez) {
		this.ez = ez;
	}

	/**
	 * @return the hax
	 */
	public int getHax() {
		return hax;
	}

	/**
	 * @param hax
	 *            the hax to set
	 */
	public void setHax(int hax) {
		this.hax = hax;
	}

	/**
	 * @return the hay
	 */
	public int getHay() {
		return hay;
	}

	/**
	 * @param hay
	 *            the hay to set
	 */
	public void setHay(int hay) {
		this.hay = hay;
	}

	/**
	 * @return the haz
	 */
	public int getHaz() {
		return haz;
	}

	/**
	 * @param haz
	 *            the haz to set
	 */
	public void setHaz(int haz) {
		this.haz = haz;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the deviceAddress
	 */
	public String getDeviceAddress() {
		return deviceAddress;
	}

	/**
	 * @param deviceAddress
	 *            the deviceAddress to set
	 */
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	/**
	 * @return the deviceTime
	 */
	public long getDeviceTime() {
		return deviceTime;
	}

	/**
	 * @param deviceTime the deviceTime to set
	 */
	public void setDeviceTime(long deviceTime) {
		this.deviceTime = deviceTime;
	}

}
