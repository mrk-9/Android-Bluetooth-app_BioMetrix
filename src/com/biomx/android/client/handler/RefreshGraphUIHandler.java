/**
 * 
 */
package com.biomx.android.client.handler;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.biomx.android.client.R;
import com.biomx.android.client.bluetooth.ConnectionStreamReader;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.listener.GraphXAxisValueFormatter;
import com.biomx.android.client.util.BioMXRUtil;
import com.biomx.android.client.util.Constants;

/**
 * @author Raghu
 * 
 */
public class RefreshGraphUIHandler extends Handler {

	private Context context;
	private Button[] leftBarButtons;
	private XYPlot lineChart;
	private SimpleXYSeries axs;
	private SimpleXYSeries ays;
	private SimpleXYSeries azs;
	private SimpleXYSeries gxs;
	private SimpleXYSeries gys;
	private SimpleXYSeries gzs;
	private SimpleXYSeries mxs;
	private SimpleXYSeries mys;
	private SimpleXYSeries mzs;
	private SimpleXYSeries qxs;
	private SimpleXYSeries qys;
	private SimpleXYSeries qzs;
	private SimpleXYSeries qws;
	private SimpleXYSeries ahxs;
	private SimpleXYSeries ahys;
	private SimpleXYSeries ahzs;
	private SimpleXYSeries exs;
	private SimpleXYSeries eys;
	private SimpleXYSeries ezs;
	// private long prevTimeStamp;
	private int timeChangeCount;
	private GraphXAxisValueFormatter graphXAxisValueFormatter;
	private Button button;
	private int boundaryAcc;
	private int boundaryGyro;
	private int boundaryMagn;

	public RefreshGraphUIHandler(Context context, Button[] leftBarButtons,
			XYPlot lineChart, Button button,
			GraphXAxisValueFormatter graphXAxisValueFormatter, XDevice device) {
		this.context = context;
		this.leftBarButtons = leftBarButtons;
		this.lineChart = lineChart;
		this.button = button;
		this.graphXAxisValueFormatter = graphXAxisValueFormatter;
		int sensitivity = device.getSensitivity();
		if (sensitivity == 2) {
			boundaryAcc = 2000;
			boundaryGyro = 250;
			boundaryMagn = 4000;
		} else if (sensitivity == 4) {
			boundaryAcc = 4000;
			boundaryGyro = 250;
			boundaryMagn = 4000;
		} else if (sensitivity == 8) {
			boundaryAcc = 8000;
			boundaryGyro = 500;
			boundaryMagn = 8000;
		} else if (sensitivity == 16) {
			boundaryAcc = 16000;
			boundaryGyro = 500;
			boundaryMagn = 16000;
		} else if (sensitivity == 100 || sensitivity == 400) {
			boundaryAcc = 4000;
			boundaryGyro = 2000;
			boundaryMagn = 16000;
		}
		
		initVals();
	}

	private void initVals() {
		axs = new SimpleXYSeries("AccX");
		axs.useImplicitXVals();
		ays = new SimpleXYSeries("AccY");
		ays.useImplicitXVals();
		azs = new SimpleXYSeries("AccZ");
		azs.useImplicitXVals();
		gxs = new SimpleXYSeries("GyroX");
		gxs.useImplicitXVals();
		gys = new SimpleXYSeries("GyroY");
		gys.useImplicitXVals();
		gzs = new SimpleXYSeries("GyroZ");
		gzs.useImplicitXVals();
		mxs = new SimpleXYSeries("MagnX");
		mxs.useImplicitXVals();
		mys = new SimpleXYSeries("MagnY");
		mys.useImplicitXVals();
		mzs = new SimpleXYSeries("MagnZ");
		mzs.useImplicitXVals();
		qxs = new SimpleXYSeries("QX");
		qxs.useImplicitXVals();
		qys = new SimpleXYSeries("QY");
		qys.useImplicitXVals();
		qzs = new SimpleXYSeries("QZ");
		qzs.useImplicitXVals();
		qws = new SimpleXYSeries("QW");
		qws.useImplicitXVals();
		exs = new SimpleXYSeries("EX");
		exs.useImplicitXVals();
		eys = new SimpleXYSeries("EY");
		eys.useImplicitXVals();
		ezs = new SimpleXYSeries("EZ");
		ezs.useImplicitXVals();
		ahxs = new SimpleXYSeries("AHX");
		ahxs.useImplicitXVals();
		ahys = new SimpleXYSeries("AHY");
		ahys.useImplicitXVals();
		ahzs = new SimpleXYSeries("AHZ");
		ahzs.useImplicitXVals();
	}

	@Override
	public void handleMessage(Message msg) {

		int what = msg.what;
		if (what != Constants.HANDLER_MESSAGE_GRAPH_DATA_CHANGE) {
			return;
		}
		int[] deviceData = (int[]) msg.obj;
		Button leftBarButton = null;
		for (Button button : this.leftBarButtons) {
			if (button.isSelected()) {
				leftBarButton = button;
				break;
			}
		}
		if (deviceData != null) {
			// int timeStamp = BioMXRUtil.decodeIntData(deviceData[0],
			// deviceData[1], deviceData[2], deviceData[3]);
			// long currentSec = timeStamp;
			// if (this.prevTimeStamp != currentSec) {
			timeChangeCount++;
			// }
			// this.prevTimeStamp = currentSec;
			if (timeChangeCount > 200) {
				timeChangeCount = 0;
				initVals();
				this.graphXAxisValueFormatter.changeStartCount();
			}

			float gx = 0, gy = 0, gz = 0, mx = 0, my = 0, mz = 0, ax = 0, ay = 0, az = 0, qw = 0, qx = 0, qy = 0, qz = 0;
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
					return;
				}
				gx = ((float) BioMXRUtil.decodeShortData((byte) deviceData[6],
						(byte) deviceData[7]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gy = ((float) BioMXRUtil.decodeShortData((byte) deviceData[8],
						(byte) deviceData[9]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gz = ((float) BioMXRUtil.decodeShortData((byte) deviceData[10],
						(byte) deviceData[11]))
						/ ConnectionStreamReader.GYRO_FACTOR;

				mx = BioMXRUtil.decodeShortData((byte) deviceData[14],
						(byte) deviceData[15]);
				my = BioMXRUtil.decodeShortData((byte) deviceData[16],
						(byte) deviceData[17]);
				mz = BioMXRUtil.decodeShortData((byte) deviceData[18],
						(byte) deviceData[19]);

				ax = BioMXRUtil.decodeShortData((byte) deviceData[22],
						(byte) deviceData[23]);
				ay = BioMXRUtil.decodeShortData((byte) deviceData[24],
						(byte) deviceData[25]);
				az = BioMXRUtil.decodeShortData((byte) deviceData[26],
						(byte) deviceData[27]);

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
			} else if (deviceData.length == 34) {
				boolean isG = (int) deviceData[4] == ConnectionStreamReader.G_BYTE
						&& (int) deviceData[5] == ConnectionStreamReader.G_BYTE;
				boolean isH = (int) deviceData[12] == ConnectionStreamReader.H_BYTE
						&& (int) deviceData[13] == ConnectionStreamReader.H_BYTE;
				boolean isA = (int) deviceData[26] == ConnectionStreamReader.A_BYTE
						&& (int) deviceData[27] == ConnectionStreamReader.A_BYTE;
				if (!(isG && isH && isA)) {
					return;
				}
				gx = ((float) BioMXRUtil.decodeShortData((byte) deviceData[6],
						(byte) deviceData[7]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gy = ((float) BioMXRUtil.decodeShortData((byte) deviceData[8],
						(byte) deviceData[9]))
						/ ConnectionStreamReader.GYRO_FACTOR;
				gz = ((float) BioMXRUtil.decodeShortData((byte) deviceData[10],
						(byte) deviceData[11]))
						/ ConnectionStreamReader.GYRO_FACTOR;

				ax = BioMXRUtil.decodeShortData((byte) deviceData[28],
						(byte) deviceData[29]);
				ay = BioMXRUtil.decodeShortData((byte) deviceData[30],
						(byte) deviceData[31]);
				az = BioMXRUtil.decodeShortData((byte) deviceData[32],
						(byte) deviceData[33]);

			} else if (deviceData.length == 16) {
				boolean isQ = deviceData[0] == ConnectionStreamReader.Q_BYTE
						&& deviceData[1] == ConnectionStreamReader.Q_BYTE;
				if (!isQ) {
					return;
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

			axs.addLast(null, Float.parseFloat(ax + ""));
			ays.addLast(null, Float.parseFloat(ay + ""));
			azs.addLast(null, Float.parseFloat(az + ""));

			gxs.addLast(null, gx);
			gys.addLast(null, gy);
			gzs.addLast(null, gz);

			mxs.addLast(null, Float.parseFloat(mx + ""));
			mys.addLast(null, Float.parseFloat(my + ""));
			mzs.addLast(null, Float.parseFloat(mz + ""));

			qws.addLast(null, qw);
			qxs.addLast(null, qx);
			qys.addLast(null, qy);
			qzs.addLast(null, qz);

		}

		if (!this.button.isSelected()) {
			return;
		}
		lineChart.clear();
		String selectedOption = leftBarButton.getContentDescription()
				.toString();

		if (selectedOption.equals(this.context
				.getString(R.string.button_caption_acc))) {
			lineChart.addSeries(axs,
					new LineAndPointFormatter(Color.parseColor("#01BAD5"),
							null, null, null));
			lineChart.addSeries(ays,
					new LineAndPointFormatter(Color.parseColor("#E46C6D"),
							null, null, null));
			lineChart.addSeries(azs,
					new LineAndPointFormatter(Color.parseColor("#A9C185"),
							null, null, null));
			lineChart.setRangeBoundaries(boundaryAcc * -1, boundaryAcc, BoundaryMode.FIXED);
		} else if (selectedOption.equals(this.context
				.getString(R.string.button_caption_gyro))) {
			lineChart.addSeries(gxs,
					new LineAndPointFormatter(Color.parseColor("#01BAD5"),
							null, null, null));
			lineChart.addSeries(gys,
					new LineAndPointFormatter(Color.parseColor("#E46C6D"),
							null, null, null));
			lineChart.addSeries(gzs,
					new LineAndPointFormatter(Color.parseColor("#A9C185"),
							null, null, null));
			lineChart.setRangeBoundaries(boundaryGyro * -1, boundaryGyro, BoundaryMode.FIXED);
		} else if (selectedOption.equals(this.context
				.getString(R.string.button_caption_magn))) {
			lineChart.addSeries(mxs,
					new LineAndPointFormatter(Color.parseColor("#01BAD5"),
							null, null, null));
			lineChart.addSeries(mys,
					new LineAndPointFormatter(Color.parseColor("#E46C6D"),
							null, null, null));
			lineChart.addSeries(mzs,
					new LineAndPointFormatter(Color.parseColor("#A9C185"),
							null, null, null));
			lineChart.setRangeBoundaries(boundaryMagn * -1, boundaryMagn, BoundaryMode.FIXED);
		} else if (selectedOption.equals(this.context
				.getString(R.string.button_caption_quat))) {
			lineChart.addSeries(qxs,
					new LineAndPointFormatter(Color.parseColor("#01BAD5"),
							null, null, null));
			lineChart.addSeries(qys,
					new LineAndPointFormatter(Color.parseColor("#E46C6D"),
							null, null, null));
			lineChart.addSeries(qzs,
					new LineAndPointFormatter(Color.parseColor("#A9C185"),
							null, null, null));
			lineChart.addSeries(qws,
					new LineAndPointFormatter(Color.parseColor("#204e55"),
							null, null, null));
			lineChart.setRangeBoundaries(-2, 2, BoundaryMode.FIXED);
		} else if (selectedOption.equals(this.context
				.getString(R.string.button_caption_eluer))) {
			lineChart.addSeries(exs,
					new LineAndPointFormatter(Color.parseColor("#01BAD5"),
							null, null, null));
			lineChart.addSeries(eys,
					new LineAndPointFormatter(Color.parseColor("#E46C6D"),
							null, null, null));
			lineChart.addSeries(ezs,
					new LineAndPointFormatter(Color.parseColor("#A9C185"),
							null, null, null));
			lineChart.setRangeBoundaries(-1, 1, BoundaryMode.FIXED);
		} else if (selectedOption.equals(this.context
				.getString(R.string.button_caption_lacc))) {
			lineChart.addSeries(ahxs,
					new LineAndPointFormatter(Color.parseColor("#01BAD5"),
							null, null, null));
			lineChart.addSeries(ahys,
					new LineAndPointFormatter(Color.parseColor("#E46C6D"),
							null, null, null));
			lineChart.addSeries(ahzs,
					new LineAndPointFormatter(Color.parseColor("#A9C185"),
							null, null, null));
			lineChart.setRangeBoundaries(-1, 1, BoundaryMode.FIXED);
		}

		lineChart.redraw();
	}
}
