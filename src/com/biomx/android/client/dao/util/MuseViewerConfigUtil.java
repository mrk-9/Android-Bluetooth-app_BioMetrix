/**
 * 
 */
package com.biomx.android.client.dao.util;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * @author Raghu
 * 
 */
public class MuseViewerConfigUtil extends OrmLiteConfigUtil {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Class<?>[] classes = new Class[] {};
		writeConfigFile("ormlite_config.txt", classes);
	}

}
