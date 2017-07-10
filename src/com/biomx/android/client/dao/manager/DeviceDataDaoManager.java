package com.biomx.android.client.dao.manager;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.biomx.android.client.dao.entity.DeviceData;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dao.util.MuseViewerDatabaseHelper;
import com.biomx.android.client.dao.util.MuseViewerDatabaseHelperFactory;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

public class DeviceDataDaoManager {
	private Context context;

	public DeviceDataDaoManager(Context context) {
		this.context = context;
	}

	public DeviceData queryForId(long id) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper
				.getDao(DeviceData.class);
		return dao.queryForId(id);
	}

	public void create(List<DeviceData> deviceDatas) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper
				.getDao(DeviceData.class);
		for (DeviceData deviceData : deviceDatas) {
			dao.create(deviceData);
		}
	}

	public List<DeviceData> list(String deviceAddress) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper
				.getDao(DeviceData.class);
		QueryBuilder<DeviceData, Object> queryBuilder = dao.queryBuilder();
		queryBuilder.where().eq(DeviceData.COLUMN_DEVICE_ADDRESS, deviceAddress);
		return queryBuilder.query();
	}
	
	public List<DeviceData> listTopDatas(String deviceAddress) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper
				.getDao(DeviceData.class);
		QueryBuilder<DeviceData, Object> queryBuilder = dao.queryBuilder();
		queryBuilder.where().eq(DeviceData.COLUMN_DEVICE_ADDRESS, deviceAddress);
		queryBuilder.orderBy(DeviceData.COLUMN_TIME, false);
		queryBuilder.limit(20l);
		return queryBuilder.query();
	}

	public void create(DeviceData deviceData) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper.getDao(DeviceData.class);
		dao.create(deviceData);
	}
	
	public List<String[]> getDeviceData(String query) throws SQLException{
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper.getDao(DeviceData.class);
		GenericRawResults<String[]> res = dao.queryRaw(query);
		List<String[]> results = res.getResults();
		return results;
	}
	
	
	public List<String[]> getDeviceData(XDevice device) throws SQLException{
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper.getDao(DeviceData.class);
		String SQL_QUERY = "SELECT " 
				+ "d." + DeviceData.COLUMN_ACCX + " || ',' || " + "d." + DeviceData.COLUMN_ACCY + " || ',' || " + "d." + DeviceData.COLUMN_ACCZ + " || ',' || "
				+ "d." + DeviceData.COLUMN_MAGNX + " || ',' || " + "d." + DeviceData.COLUMN_MAGNY + " || ',' || " + "d." + DeviceData.COLUMN_MAGNZ + " || ',' || "
				+ "d." + DeviceData.COLUMN_GYROX + " || ',' || " + "d." + DeviceData.COLUMN_GYROY + " || ',' || " + "d." + DeviceData.COLUMN_GYROZ + " || ',' || "
				+ "d." + DeviceData.COLUMN_QW + " || ',' || " + "d." + DeviceData.COLUMN_QX + " || ',' || " + "d." + DeviceData.COLUMN_QY + " || ',' || " + "d." + DeviceData.COLUMN_QZ + " || ',' || "
				+ "d." + DeviceData.COLUMN_EX + " || ',' || " + "d." + DeviceData.COLUMN_EY + " || ',' || " + "d." + DeviceData.COLUMN_EZ + " || ',' || "
				+ "d." + DeviceData.COLUMN_AHX + " || ',' || " + "d." + DeviceData.COLUMN_AHY + " || ',' || " + "d." + DeviceData.COLUMN_AHZ + " || ',' || " + "d." + DeviceData.COLUMN_TIME + " "
				+ "FROM " + DeviceData.TABLE_DEVICE_DATA + " d " 
				+ "WHERE d." + DeviceData.COLUMN_DEVICE_ADDRESS + "='"+ device.getAddress() + "' " 
				+ "ORDER BY d."	+ DeviceData.COLUMN_TIME + " ASC ";
		GenericRawResults<String[]> res = dao.queryRaw(SQL_QUERY);
		List<String[]> results = res.getResults();
		return results;
	}
	
	public void clear() throws SQLException{
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<DeviceData, Object> dao = museViewDatabaseHelper.getDao(DeviceData.class);
		DeleteBuilder<DeviceData, Object> deleteBuilder = dao.deleteBuilder();
		deleteBuilder.delete();
	}
}
