/**
 * 
 */
package com.biomx.android.client.dao.util;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * @author Raghu
 * 
 */
public class MuseViewerDatabaseHelperFactory {

	private static MuseViewerDatabaseHelper museViwerDatabaseHelper;

	private MuseViewerDatabaseHelperFactory() {

	}

	public static MuseViewerDatabaseHelper createMuseViewDatabaseHelper(
			Context context) {

		if (MuseViewerDatabaseHelperFactory.museViwerDatabaseHelper == null) {
			MuseViewerDatabaseHelperFactory.museViwerDatabaseHelper = OpenHelperManager
					.getHelper(context, MuseViewerDatabaseHelper.class);
		}

		return MuseViewerDatabaseHelperFactory.museViwerDatabaseHelper;
	}

	public static void destroyMuseViewDatabaseHelper() {
		if (MuseViewerDatabaseHelperFactory.museViwerDatabaseHelper != null) {
			OpenHelperManager.releaseHelper();
			MuseViewerDatabaseHelperFactory.museViwerDatabaseHelper = null;
		}
	}

}
