package com.biomx.android.client.util;

public enum SensitivitySetting {
	
	TWO_G("2G", "\u00B1" + "2", "\u00B1" + "250", "\u00B1" + "4"),
	FOUR_G("4G", "\u00B1" + "4", "\u00B1" + "250", "\u00B1" + "8"),
	EIGHT_G("8G", "\u00B1" + "8", "\u00B1" + "500", "\u00B1" + "12"),
	SIXTEEN_G("16G", "\u00B1" + "16", "\u00B1" + "500", "\u00B1" + "16"),
	HUNDRED_G("100G", "\u00B1" + "100", "\u00B1" + "2000", "\u00B1" + "16"),
	FOUR_HUNDRED_G("400G", "\u00B1" + "400", "\u00B1" + "2000", "\u00B1" + "16");
	
	private String setting;
	
	private String acc;
	
	private String gyro;
	
	private String magn;
	
	private SensitivitySetting(String setting, String acc, String gyro, String magn) {
		this.setting = setting;
		this.acc = acc;
		this.gyro = gyro;
		this.magn = magn;
	}

	/**
	 * @return the setting
	 */
	public String getSetting() {
		return setting;
	}

	/**
	 * @return the acc
	 */
	public String getAcc() {
		return acc;
	}

	/**
	 * @return the gyro
	 */
	public String getGyro() {
		return gyro;
	}

	/**
	 * @return the magn
	 */
	public String getMagn() {
		return magn;
	}
	
}
