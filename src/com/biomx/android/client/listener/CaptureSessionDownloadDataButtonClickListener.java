/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.File;

import android.view.View;
import android.view.View.OnClickListener;

import com.biomx.android.client.dialog.DownloadDataDialog;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class CaptureSessionDownloadDataButtonClickListener implements
		OnClickListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		File root = android.os.Environment.getExternalStorageDirectory();
		File dir = new File(root.getAbsolutePath() + DataHolder.homeFolderPath);
		for (String fileName : dir.list()) {
			if (fileName.endsWith(".zip")) {
				new File(dir, fileName).delete();
			}
		}
		DownloadDataDialog downloadDataDialog = new DownloadDataDialog(view.getContext());
		downloadDataDialog.show();
	}

}
