/**
 * 
 */
package com.biomx.android.client.bluetooth;

/**
 * @author prince
 *
 */
public enum BioMXCommand {
	
	
	GET_BATTQ("?!GETBATTQ!?"),
	START_STREAMING("?!START005!?"),
	STREAM_HDR("?!TXHDRxxx!?"),
	STREAM_RAW("?!CALIBxxx!?"),
	STREAM_QUARTENION("?!STARTxxx!?"),
	HAULT_STREAMING("?!STOPSEND!?"),
	CONF_ACCELERO_FULL_SCALE("?!ACLFSxxx!?"),
	CONF_GYRO_FULL_SCALE("?!GYLFSxxx!?"),
	CONF_MAGNETO_FULL_SCALE("?!MAGFSxxx!?"),
	CONF_HDR_FULL_SCALE("?!ACHFSxxx!?"),
	STORE_ALL_PARAMS("?!STOREPAR!?"),
	GET_HDR_STATUS("?!HDRMUSE?!?"),
	START_CALIBRATION("?!MAGCALIB!?"), 
	CALIB_MAGN_DATA("?!GETCALIB!?")/*,
	CALIB_ACC_DATA("?!GETACCAL!?"),*/;
	
	private String name;

	BioMXCommand(String name){
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
