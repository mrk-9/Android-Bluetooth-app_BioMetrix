/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.biomx.android.client.R;
import com.biomx.android.client.util.DataHolder;
import com.biomx.android.client.util.FileCompressUtil;

/**
 * @author Raghu
 * 
 */
public class DownloadDataDownloadButtonOnClickListener implements
		OnClickListener {

	private Dialog downloadDataDialog;
	private List<String> selectedFiles;

	public DownloadDataDownloadButtonOnClickListener(Dialog downloadDataDialog,
			List<String> selectedFiles) {
		this.downloadDataDialog = downloadDataDialog;
		this.selectedFiles = selectedFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		if (this.selectedFiles.size() == 0) {
			Toast.makeText(view.getContext(),
					R.string.notification_error_no_files_selected_to_download,
					Toast.LENGTH_SHORT).show();
			return;
		}
		this.downloadDataDialog.dismiss();
		Toast.makeText(view.getContext(),
				R.string.notification_your_download_will_begin_shortly,
				Toast.LENGTH_LONG).show();
		File root = android.os.Environment.getExternalStorageDirectory();
		File dir = new File(root.getAbsolutePath() + DataHolder.homeFolderPath);
		String zipFileName = "biomx_data_" + System.currentTimeMillis()
				+ ".zip";

		File fileZip = new File(dir, zipFileName);
		FileCompressUtil compressUtil = new FileCompressUtil(
				this.selectedFiles, fileZip.getAbsolutePath());
		compressUtil.zip();

		Uri uriToZip = Uri.fromFile(fileZip);
		Intent shareZip = new Intent(Intent.ACTION_SEND);
		shareZip.setType("*/*");
		shareZip.putExtra(Intent.EXTRA_STREAM, uriToZip);
		view.getContext().startActivity(
				Intent.createChooser(shareZip, "Share Downloaded Data"));
	}

}
