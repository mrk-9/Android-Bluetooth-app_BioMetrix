/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.biomx.android.client.bluetooth.ConnectionStreamReader;
import com.biomx.android.client.util.BioMXRUtil;

/**
 * @author Raghu
 * 
 */
public class SensorDataWriter implements Runnable {

	private List<int[]> dataList;
	private File file;

	public SensorDataWriter(File file, List<int[]> _dataList) {
		this.file = file;
		this.dataList = _dataList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(file.getAbsolutePath(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		for (int[] deviceData : dataList) {
			if (deviceData == null) {
				continue;
			}
			long timeStamp = 0;
			String content = "";
			int index = 0;
			float gx = 0, gy = 0, gz = 0, mx = 0, my = 0, mz = 0, ax = 0, ay = 0, az = 0, qw = 0, qx = 0, qy = 0, qz = 0, hax = 0, hay = 0, haz = 0;
			if (deviceData.length == 52) {
				boolean isG = deviceData[4] == ConnectionStreamReader.G_BYTE
						&& deviceData[5] == ConnectionStreamReader.G_BYTE;

				boolean isM = deviceData[12] == ConnectionStreamReader.M_BYTE
						&& deviceData[13] == ConnectionStreamReader.M_BYTE;

				boolean isA = deviceData[20] == ConnectionStreamReader.A_BYTE
						&& deviceData[21] == ConnectionStreamReader.A_BYTE;

				boolean isI = deviceData[28] == ConnectionStreamReader.I_BYTE
						&& deviceData[29] == ConnectionStreamReader.I_BYTE;

				boolean isQ = deviceData[34] == ConnectionStreamReader.Q_BYTE
						&& deviceData[35] == ConnectionStreamReader.Q_BYTE;

				if (!(isG && isM && isA && isI && isQ)) {
					continue;
				}

				timeStamp = BioMXRUtil.decodeIntData((byte) deviceData[0],
						(byte) deviceData[1], (byte) deviceData[2],
						(byte) deviceData[3]);
				// if (isI) {
				index = BioMXRUtil.decodeIntData((byte) deviceData[30],
						(byte) deviceData[31], (byte) deviceData[32],
						(byte) deviceData[33]);
				// }

				// if (isG) {
				gx = ((float) BioMXRUtil.decodeShortData((byte) deviceData[6],
						(byte) deviceData[7]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gy = ((float) BioMXRUtil.decodeShortData((byte) deviceData[8],
						(byte) deviceData[9]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gz = ((float) BioMXRUtil.decodeShortData((byte) deviceData[10],
						(byte) deviceData[11]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				// }

				// if (isM) {
				mx = BioMXRUtil.decodeShortData((byte) deviceData[14],
						(byte) deviceData[15]);
				my = BioMXRUtil.decodeShortData((byte) deviceData[16],
						(byte) deviceData[17]);
				mz = BioMXRUtil.decodeShortData((byte) deviceData[18],
						(byte) deviceData[19]);
				// }

				// if (isA) {
				ax = BioMXRUtil.decodeShortData((byte) deviceData[22],
						(byte) deviceData[23]);
				ay = BioMXRUtil.decodeShortData((byte) deviceData[24],
						(byte) deviceData[25]);
				az = BioMXRUtil.decodeShortData((byte) deviceData[26],
						(byte) deviceData[27]);
				// }

				// if (isQ) {
				qw = BioMXRUtil.decodeFloatData((byte) deviceData[36],
						(byte) deviceData[37], (byte) deviceData[38],
						(byte) deviceData[39]);
				qx = BioMXRUtil.decodeFloatData((byte) deviceData[40],
						(byte) deviceData[41], (byte) deviceData[42],
						(byte) deviceData[43]);
				qy = BioMXRUtil.decodeFloatData((byte) deviceData[44],
						(byte) deviceData[45], (byte) deviceData[46],
						(byte) deviceData[47]);
				qz = BioMXRUtil.decodeFloatData((byte) deviceData[48],
						(byte) deviceData[49], (byte) deviceData[50],
						(byte) deviceData[51]);
				// }
			} else if (deviceData.length == 34) {
				boolean isG = (int) deviceData[4] == ConnectionStreamReader.G_BYTE
						&& (int) deviceData[5] == ConnectionStreamReader.G_BYTE;
				boolean isH = (int) deviceData[12] == ConnectionStreamReader.H_BYTE
						&& (int) deviceData[13] == ConnectionStreamReader.H_BYTE;
				boolean isA = (int) deviceData[26] == ConnectionStreamReader.A_BYTE
						&& (int) deviceData[27] == ConnectionStreamReader.A_BYTE;
				if (!(isG && isH && isA)) {
					continue;
				}
				timeStamp = BioMXRUtil.decodeIntData((byte) deviceData[0],
						(byte) deviceData[1], (byte) deviceData[2],
						(byte) deviceData[3]);

				// if (isG) {
				gx = ((float) BioMXRUtil.decodeShortData((byte) deviceData[6],
						(byte) deviceData[7]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gy = ((float) BioMXRUtil.decodeShortData((byte) deviceData[8],
						(byte) deviceData[9]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gz = ((float) BioMXRUtil.decodeShortData((byte) deviceData[10],
						(byte) deviceData[11]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				// }

				hax = BioMXRUtil.decodeIntData((byte) deviceData[14],
						(byte) deviceData[15], (byte) deviceData[16],
						(byte) deviceData[17]);
				hay = BioMXRUtil.decodeIntData((byte) deviceData[18],
						(byte) deviceData[19], (byte) deviceData[20],
						(byte) deviceData[21]);
				haz = BioMXRUtil.decodeIntData((byte) deviceData[22],
						(byte) deviceData[23], (byte) deviceData[24],
						(byte) deviceData[25]);

				// if (isA) {
				ax = BioMXRUtil.decodeShortData((byte) deviceData[28],
						(byte) deviceData[29]);
				ay = BioMXRUtil.decodeShortData((byte) deviceData[30],
						(byte) deviceData[31]);
				az = BioMXRUtil.decodeShortData((byte) deviceData[32],
						(byte) deviceData[33]);
				// }

			} else if (deviceData.length == 18) {

				// timeStamp = (System.currentTimeMillis() - previousTime) +
				// (System.currentTimeMillis() - previousTime) * 1000;

				boolean isQ = deviceData[0] == ConnectionStreamReader.Q_BYTE
						&& deviceData[1] == ConnectionStreamReader.Q_BYTE;
				if (!isQ) {
					continue;
				}
				qw = BioMXRUtil.decodeFloatData((byte) deviceData[2],
						(byte) deviceData[3], (byte) deviceData[4],
						(byte) deviceData[5]);
				qx = BioMXRUtil.decodeFloatData((byte) deviceData[6],
						(byte) deviceData[7], (byte) deviceData[8],
						(byte) deviceData[9]);
				qy = BioMXRUtil.decodeFloatData((byte) deviceData[10],
						(byte) deviceData[11], (byte) deviceData[12],
						(byte) deviceData[13]);
				qz = BioMXRUtil.decodeFloatData((byte) deviceData[14],
						(byte) deviceData[15], (byte) deviceData[16],
						(byte) deviceData[17]);
			}
			// headerLine =
			// "Time,Index,AccX,AccY,AccZ,MagnX,MagnY,MagnZ,GyroX,GyroY,GyroZ,qW,qX,qY,qZ,hax,hay,haz";
			content += timeStamp + "," + index + ",";
			content += ax + "," + ay + "," + az + ",";
			content += mx + "," + my + "," + mz + ",";
			content += gx + "," + gy + "," + gz + ",";
			content += qw + "," + qx + "," + qy + "," + qz + ",";
			content += hax + "," + hay + "," + haz + "," + "\n";
			try {
				bufferWritter.write(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
