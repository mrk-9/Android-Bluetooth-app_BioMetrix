package com.biomx.android.client.dao.manager;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.dao.util.MuseViewerDatabaseHelper;
import com.biomx.android.client.dao.util.MuseViewerDatabaseHelperFactory;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class XDeviceDaoManager {
	private Context context;

	public XDeviceDaoManager(Context context) {
		this.context = context;
	}

	public XDevice queryForId(long id) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<XDevice, Object> dao = museViewDatabaseHelper.getDao(XDevice.class);
		return dao.queryForId(id);
	}

	public void create(XDevice device) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<XDevice, Object> dao = museViewDatabaseHelper.getDao(XDevice.class);
		dao.create(device);
	}

	public List<XDevice> list() throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<XDevice, Object> dao = museViewDatabaseHelper.getDao(XDevice.class);
		List<XDevice> result = dao.queryForAll();
		return result;
	}

	public XDevice getBy(String address) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<XDevice, Object> dao = museViewDatabaseHelper.getDao(XDevice.class);
		QueryBuilder<XDevice, Object> queryBuilder = dao.queryBuilder();
		queryBuilder.where().eq(XDevice.COLUMN_ADDRESS, address);
		return queryBuilder.queryForFirst();
	}

	public void update(List<XDevice> devices) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<XDevice, Object> dao = museViewDatabaseHelper.getDao(XDevice.class);
		for (XDevice device : devices) {
			dao.update(device);
		}
	}
	
	public void update(XDevice device) throws SQLException {
		MuseViewerDatabaseHelper museViewDatabaseHelper = MuseViewerDatabaseHelperFactory
				.createMuseViewDatabaseHelper(this.context);
		Dao<XDevice, Object> dao = museViewDatabaseHelper.getDao(XDevice.class);
		dao.update(device);
	}

}
