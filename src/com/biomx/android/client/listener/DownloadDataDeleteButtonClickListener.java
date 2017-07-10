/**
 * 
 */
package com.biomx.android.client.listener;

import java.io.File;
import java.util.List;

import com.biomx.android.client.R;
import com.biomx.android.client.util.DataHolder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Toast;

/**
 * @author Raghu
 * 
 */
public class DownloadDataDeleteButtonClickListener implements OnClickListener {

	private List<String> selectedFiles;
	private BaseAdapter downloadDataListAdapter;

	public DownloadDataDeleteButtonClickListener(List<String> selectedFiles,
			BaseAdapter downloadDataListAdapter) {
		this.selectedFiles = selectedFiles;
		this.downloadDataListAdapter = downloadDataListAdapter;
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
		File root = android.os.Environment.getExternalStorageDirectory();
		File dataDirectory = new File(root.getAbsolutePath()
				+ DataHolder.homeFolderPath);
		for (String fileName : this.selectedFiles) {
			new File(dataDirectory, fileName).delete();
		}
		this.selectedFiles.clear();
		this.downloadDataListAdapter.notifyDataSetChanged();
	}

}
