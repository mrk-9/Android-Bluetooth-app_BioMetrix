/**
 * 
 */
package com.biomx.android.client.dao.util;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.biomx.android.client.R;
import com.biomx.android.client.dao.entity.DeviceData;
import com.biomx.android.client.dao.entity.XDevice;
import com.biomx.android.client.util.Constants;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @author Raghu
 * 
 */
public class MuseViewerDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	public MuseViewerDatabaseHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTableIfNotExists(connectionSource,
					DeviceData.class);
			TableUtils.createTableIfNotExists(connectionSource, XDevice.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, DeviceData.class, true);
			TableUtils.dropTable(connectionSource, XDevice.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
